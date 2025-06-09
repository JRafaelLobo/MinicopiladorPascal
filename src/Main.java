import Interpreter.InterpreterLexer;
import Interpreter.InterpreterParser;
import Interpreter.SemanticVisitor;
import Interpreter.VerboseListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Lee el archivo fuente Mini-Pascal (.pas) como flujo de caracteres
            CharStream input = CharStreams.fromFileName("tests/testStrongTyping.pas");

            // Instancia el analizador léxico generado por ANTLR
            InterpreterLexer lexer = new InterpreterLexer(input);
            // Quita los manejadores de errores por defecto del lexer
            lexer.removeErrorListeners();
            // Agrega un listener personalizado para reportar errores léxicos
            lexer.addErrorListener(new VerboseListener());

            // Crea el flujo de tokens a partir del lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // Instancia el parser generado por ANTLR
            InterpreterParser parser = new InterpreterParser(tokens);
            // Quita los manejadores de errores por defecto del parser
            parser.removeErrorListeners();
            // Agrega un listener personalizado para errores sintácticos
            parser.addErrorListener(new VerboseListener());

            // Comienza el análisis sintáctico desde la regla principal "program"
            ParseTree tree = parser.program();

            // Imprime el árbol sintáctico generado
            System.out.println("Árbol sintáctico:");
            System.out.println(tree.toStringTree(parser));

            // Realiza el análisis semántico sobre el árbol
            SemanticVisitor semanticVisitor = new SemanticVisitor();
            semanticVisitor.visit(tree);

            // Muestra los errores semánticos recolectados (si existen)
            System.out.println("Errores semánticos:");
            for (String error : semanticVisitor.getErrors()) {
                System.err.println(error);
            }

        } catch (IOException e) {
            // Manejo de error si el archivo no puede abrirse
            System.err.println(e.getMessage());
        }
    }
}
