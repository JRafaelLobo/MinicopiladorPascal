package Interpreter;

import java.util.ArrayList;
import java.util.List;

public class IRGeneratorVisitor extends InterpreterBaseVisitor<String> {
    private int tempCount = 0;
    private int labelCount = 0;

    private final List<String> funcCode = new ArrayList<>();
    private final List<String> mainCode = new ArrayList<>();

    private boolean inFunction = false;
    private String currentFunctionName = null;

    private String newTemp() {
        return "t" + (tempCount++);
    }

    private String newLabel() {
        return "L" + (labelCount++);
    }

    public List<String> getCode() {
        List<String> all = new ArrayList<>();
        all.addAll(funcCode);
        all.addAll(mainCode);
        return all;
    }

    private void addCode(String line) {
        if (inFunction) {
            funcCode.add(line);
        } else {
            mainCode.add(line);
        }
    }

    @Override
    public String visitAssignment(InterpreterParser.AssignmentContext ctx) {
        String expr = visit(ctx.expression());
        String id = ctx.ID() != null ? ctx.ID().getText() : ctx.arrayAccess().getText();

        addCode(id + " = " + expr);

        if (inFunction && id.equals(currentFunctionName)) {
            addCode("return " + expr);
        }

        return null;
    }

    @Override
    public String visitArithmeticExpr(InterpreterParser.ArithmeticExprContext ctx) {
        if (ctx.term().size() == 1) {
            return visit(ctx.term(0));
        }

        String left = visit(ctx.term(0));
        for (int i = 1; i < ctx.term().size(); i++) {
            String right = visit(ctx.term(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String temp = newTemp();
            addCode(temp + " = " + left + " " + op + " " + right);
            left = temp;
        }
        return left;
    }

    @Override
    public String visitTerm(InterpreterParser.TermContext ctx) {
        if (ctx.factor().size() == 1) {
            return visit(ctx.factor(0));
        }

        String left = visit(ctx.factor(0));
        for (int i = 1; i < ctx.factor().size(); i++) {
            String right = visit(ctx.factor(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String temp = newTemp();
            addCode(temp + " = " + left + " " + op + " " + right);
            left = temp;
        }
        return left;
    }

    @Override
    public String visitFactor(InterpreterParser.FactorContext ctx) {
        if (ctx.ID() != null) {
            String idText = ctx.ID().getText();
            // Detecta true/false aunque no sean tokens especiales
            if (idText.equalsIgnoreCase("true")) return "true";
            if (idText.equalsIgnoreCase("false")) return "false";
            return idText;
        }
        if (ctx.INT_CONST() != null) return ctx.INT_CONST().getText();

        if (ctx.expression() != null) return visit(ctx.expression());
        if (ctx.functionCall() != null) return visit(ctx.functionCall());

        if (ctx.NOT() != null) {
            String operand = visit(ctx.factor());
            String temp = newTemp();
            addCode(temp + " = not " + operand);
            return temp;
        }

        return "";
    }

    @Override
    public String visitFunctionCall(InterpreterParser.FunctionCallContext ctx) {
        List<String> args = new ArrayList<>();
        if (ctx.expression().size() > 0) {
            for (InterpreterParser.ExpressionContext expr : ctx.expression()) {
                args.add(visit(expr));
            }
        }
        for (String arg : args) {
            addCode("param " + arg);
        }
        String temp = newTemp();
        addCode(temp + " = call " + ctx.ID().getText() + ", " + args.size());
        return temp;
    }

    @Override
    public String visitIfStatement(InterpreterParser.IfStatementContext ctx) {
        String condition = visit(ctx.expression());
        String labelEnd = newLabel();

        if (ctx.statement().size() == 1) {
            addCode("ifFalse " + condition + " goto " + labelEnd);
            visit(ctx.statement(0));
            addCode(labelEnd + ":");
        } else {
            String labelElse = newLabel();
            addCode("ifFalse " + condition + " goto " + labelElse);
            visit(ctx.statement(0));
            addCode("goto " + labelEnd);
            addCode(labelElse + ":");
            visit(ctx.statement(1));
            addCode(labelEnd + ":");
        }

        return null;
    }

    @Override
    public String visitComparisonExpr(InterpreterParser.ComparisonExprContext ctx) {
        String left = visit(ctx.arithmeticExpr(0));

        if (ctx.arithmeticExpr().size() > 1) {
            String right = visit(ctx.arithmeticExpr(1));
            String op = ctx.getChild(1).getText();
            String temp = newTemp();
            addCode(temp + " = " + left + " " + op + " " + right);
            return temp;
        }

        return left;
    }

    @Override
    public String visitWhileStatement(InterpreterParser.WhileStatementContext ctx) {
        String startLabel = newLabel();
        String endLabel = newLabel();

        addCode(startLabel + ":");
        String condition = visit(ctx.expression());
        addCode("ifFalse " + condition + " goto " + endLabel);

        visit(ctx.statement());

        addCode("goto " + startLabel);
        addCode(endLabel + ":");

        return null;
    }

    @Override
    public String visitRepeatStatement(InterpreterParser.RepeatStatementContext ctx) {
        String startLabel = newLabel();

        addCode(startLabel + ":");

        visit(ctx.statements());

        String condition = visit(ctx.expression());

        addCode("ifFalse " + condition + " goto " + startLabel);

        return null;
    }

    @Override
    public String visitFunctionDecl(InterpreterParser.FunctionDeclContext ctx) {
        inFunction = true;
        currentFunctionName = ctx.ID().getText();

        addCode(currentFunctionName + ":");

        visit(ctx.block());

        addCode("end " + currentFunctionName);

        currentFunctionName = null;
        inFunction = false;

        return null;
    }

    @Override
    public String visitLogicalExpr(InterpreterParser.LogicalExprContext ctx) {
        if (ctx.comparisonExpr().size() == 1) {
            // Caso base: solo una comparación
            return visit(ctx.comparisonExpr(0));
        }

        String left = visit(ctx.comparisonExpr(0));
        for (int i = 1; i < ctx.comparisonExpr().size(); i++) {
            String right = visit(ctx.comparisonExpr(i));
            String op = ctx.getChild(2 * i - 1).getText().toLowerCase(); // AND, OR

            String temp = newTemp();
            // Generar código para operación lógica
            addCode(temp + " = " + left + " " + op + " " + right);
            left = temp;
        }
        return left;
    }
}