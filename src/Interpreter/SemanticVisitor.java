package Interpreter;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.*;

/**
 * Esta clase realiza el **análisis semántico** del árbol generado por ANTLR para Mini-Pascal.
 * Extiende de `InterpreterBaseVisitor<Void>`, por lo tanto sobreescribe los métodos `visitXxx` para validar semánticamente el código.
 *
 * Funcionalidades clave:
 * - Manejo de scopes anidados (variables locales, globales, funciones, procedimientos)
 * - Validación de tipos en asignaciones
 * - Verificación de existencia de variables y funciones
 * - Control de retorno en funciones
 * - Reporte de errores semánticos
 */
public class SemanticVisitor extends InterpreterBaseVisitor<Void> {

    private Scope currentScope;
    private final List<String> errors = new ArrayList<>();
    private final Map<String, Symbol> functions = new HashMap<>();
    private boolean insideFunction = false;
    private String currentFunctionName = null;
    private boolean hasReturnValue = false;

    public SemanticVisitor() {
        this.currentScope = new Scope(null); // Scope global
    }

    public List<String> getErrors() {
        return errors;
    }

    private void reportError(Token token, String message) {
        errors.add(String.format("Error semántico en línea %d, columna %d: %s",
                token.getLine(), token.getCharPositionInLine(), message));
    }

    // Programa: inicia visitando el bloque principal
    @Override
    public Void visitProgram(InterpreterParser.ProgramContext ctx) {
        return visit(ctx.block());
    }

    // Bloque: incluye declaraciones y sentencias
    @Override
    public Void visitBlock(InterpreterParser.BlockContext ctx) {
        if (ctx.declarations() != null) {
            visit(ctx.declarations());
        }
        if (ctx.statements() != null) {
            visit(ctx.statements());
        }
        return null;
    }

    // Maneja todas las declaraciones: variables, funciones, procedimientos
    @Override
    public Void visitDeclarations(InterpreterParser.DeclarationsContext ctx) {
        if (ctx.varDeclList() != null) {
            visit(ctx.varDeclList());
        }
        for (var func : ctx.functionDecl()) {
            visit(func);
        }
        for (var proc : ctx.procedureDecl()) {
            visit(proc);
        }
        return null;
    }

    // Declaración de variable
    @Override
    public Void visitVarDecl(InterpreterParser.VarDeclContext ctx) {
        String type = ctx.type().getText().toLowerCase(); // Obtener tipo
        for (var idToken : ctx.ID()) {
            String name = idToken.getText();
            if (!currentScope.define(new Symbol(name, type, false))) {
                reportError(idToken.getSymbol(), "Identificador ya definido en este ámbito: " + name);
            }
        }
        return null;
    }

    // Declaración de función: maneja el scope interno y el retorno
    @Override
    public Void visitFunctionDecl(InterpreterParser.FunctionDeclContext ctx) {
        String name = ctx.ID().getText();
        if (ctx.type() == null) {
            reportError(ctx.ID().getSymbol(), "Error de sintaxis: la función '" + name + "' no tiene tipo de retorno.");
            return null;
        }

        String returnType = ctx.type().getText().toLowerCase();
        if (!currentScope.define(new Symbol(name, returnType, false))) {
            reportError(ctx.ID().getSymbol(), "Función ya definida: " + name);
        }
        functions.put(name, new Symbol(name, returnType, false));

        // Crear un nuevo scope para la función
        Scope functionScope = new Scope(currentScope);
        Scope previous = currentScope;
        currentScope = functionScope;

        insideFunction = true;
        currentFunctionName = name;
        hasReturnValue = false;

        if (ctx.parameters() != null) {
            visit(ctx.parameters());
        }

        visit(ctx.block());

        if (!hasReturnValue) {
            reportError(ctx.ID().getSymbol(), "La función '" + name + "' no retorna ningún valor.");
        }

        insideFunction = false;
        currentFunctionName = null;
        currentScope = previous;
        return null;
    }

    // Declaración de procedimiento
    @Override
    public Void visitProcedureDecl(InterpreterParser.ProcedureDeclContext ctx) {
        String name = ctx.ID().getText();
        if (!currentScope.define(new Symbol(name, "procedure", false))) {
            reportError(ctx.ID().getSymbol(), "Procedimiento ya definido: " + name);
        }

        // Nuevo scope para el procedimiento
        Scope procedureScope = new Scope(currentScope);
        Scope previous = currentScope;
        currentScope = procedureScope;

        if (ctx.parameters() != null) {
            visit(ctx.parameters());
        }

        visit(ctx.block());
        currentScope = previous;
        return null;
    }

    // Parámetros de función o procedimiento
    @Override
    public Void visitParam(InterpreterParser.ParamContext ctx) {
        boolean isReference = ctx.VAR_KW() != null;
        String type = ctx.type().getText().toLowerCase();
        for (var idToken : ctx.ID()) {
            String name = idToken.getText();
            if (!currentScope.define(new Symbol(name, type, isReference))) {
                reportError(idToken.getSymbol(), "Parámetro duplicado: " + name);
            }
        }
        return null;
    }

    private InterpreterParser.FunctionCallContext findFunctionCall(org.antlr.v4.runtime.tree.ParseTree tree) {
        if (tree instanceof InterpreterParser.FunctionCallContext) {
            return (InterpreterParser.FunctionCallContext) tree;
        }
        for (int i = 0; i < tree.getChildCount(); i++) {
            InterpreterParser.FunctionCallContext result = findFunctionCall(tree.getChild(i));
            if (result != null) return result;
        }
        return null;
    }


    // Inferencia de tipo de una expresión para validación
    private String inferType(InterpreterParser.ExpressionContext expr) {
        // Revisar si contiene una llamada a función
        InterpreterParser.FunctionCallContext call = findFunctionCall(expr);
        if (call != null) {
            String name = call.ID().getText();
            Symbol func = functions.get(name);
            if (func != null) return func.type;
        }

        String text = expr.getText().toLowerCase();
        if (text.equals("true") || text.equals("false")) return "boolean";
        if (text.matches("^\\d+$")) return "integer";
        if (text.matches("^'.'$")) return "char";
        if (text.matches("^'.*'$")) return "string";

        // Identificador simple
        if (expr.getChildCount() == 1 && expr.getChild(0) instanceof TerminalNode) {
            String varName = expr.getText();
            Symbol symbol = currentScope.resolve(varName);
            if (symbol != null) return symbol.type;
        }

        return "unknown";
    }



    // Asignaciones
    @Override
    public Void visitAssignment(InterpreterParser.AssignmentContext ctx) {
        String varName = ctx.getChild(0).getText();
        Symbol symbol = currentScope.resolve(varName);
        if (symbol == null) {
            reportError(ctx.getStart(), "Variable no declarada: " + varName);
        } else {
            String exprType = inferType(ctx.expression());
            if (!exprType.equals(symbol.type)) {
                reportError(ctx.getStart(), "Asignación con tipo incompatible: " + exprType + " a " + symbol.type);
            }
        }

        // ✅ Marcar retorno si se está asignando al nombre de la función
        if (insideFunction && varName.equals(currentFunctionName)) {
            hasReturnValue = true;
        }

        return visit(ctx.expression());
    }


    // Llamadas a funciones
    @Override
    public Void visitFunctionCall(InterpreterParser.FunctionCallContext ctx) {
        String name = ctx.ID().getText();
        Symbol symbol = currentScope.resolve(name);
        if (symbol == null) {
            reportError(ctx.ID().getSymbol(), "Función o procedimiento no declarado: " + name);
        }
        return visitChildren(ctx);
    }

    // Validación de variable en read
    @Override
    public Void visitReadStatement(InterpreterParser.ReadStatementContext ctx) {
        String varName = ctx.ID().getText();
        Symbol symbol = currentScope.resolve(varName);
        if (symbol == null) {
            reportError(ctx.ID().getSymbol(), "Variable no declarada en read: " + varName);
        }
        return null;
    }

    // Validación de variables en write
    @Override
    public Void visitWriteStatement(InterpreterParser.WriteStatementContext ctx) {
        for (var expr : ctx.expression()) {
            if (expr.getChildCount() == 1 && expr.getChild(0).getText().matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                String varName = expr.getText();
                Symbol symbol = currentScope.resolve(varName);
                if (symbol == null) {
                    reportError(expr.getStart(), "Variable no declarada en write: " + varName);
                } else if (symbol.type.equals("boolean")) {
                    reportError(expr.getStart(), "No se permite escribir variables de tipo boolean: " + varName);
                }
            }
        }
        return null;
    }

    // Validación de estructuras de control
    @Override
    public Void visitIfStatement(InterpreterParser.IfStatementContext ctx) {
        visit(ctx.expression());
        visit(ctx.statement(0));
        if (ctx.statement().size() > 1) {
            visit(ctx.statement(1));
        }
        return null;
    }

    @Override
    public Void visitWhileStatement(InterpreterParser.WhileStatementContext ctx) {
        visit(ctx.expression());
        visit(ctx.statement());
        return null;
    }

    @Override
    public Void visitForStatement(InterpreterParser.ForStatementContext ctx) {
        String varName = ctx.ID().getText();
        Symbol symbol = currentScope.resolve(varName);
        if (symbol == null) {
            reportError(ctx.ID().getSymbol(), "Variable de control no declarada: " + varName);
        }
        visit(ctx.expression(0));
        visit(ctx.expression(1));
        visit(ctx.statement());
        return null;
    }

    // Validación del uso de identificadores (ej. en expresiones)
    @Override
    public Void visitFactor(InterpreterParser.FactorContext ctx) {
        String text = ctx.getText().toLowerCase();

        if (text.equals("true") || text.equals("false")) {
            // Constantes booleanas válidas
            return null;
        }

        if (ctx.ID() != null) {
            String name = ctx.ID().getText();
            Symbol symbol = currentScope.resolve(name);
            if (symbol == null) {
                reportError(ctx.ID().getSymbol(), "Identificador no declarado: " + name);
            }
        }

        return visitChildren(ctx);
    }
}
