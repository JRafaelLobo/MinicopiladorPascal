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
            // Cargar archivo .pas
            CharStream input = CharStreams.fromFileName("tests/testStrongTyping.pas");

            // Crear el lexer
            InterpreterLexer lexer = new InterpreterLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new VerboseListener());



            // Crear el parser
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            InterpreterParser parser = new InterpreterParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new VerboseListener());

            // Iniciar análisis desde la regla "program"
            ParseTree tree = parser.program();

            // Mostrar el árbol sintáctico
            System.out.println("Árbol sintáctico:");
            System.out.println(tree.toStringTree(parser));

            // Ejecutar análisis semántico
            SemanticVisitor semanticVisitor = new SemanticVisitor();
            semanticVisitor.visit(tree);

            // Reportar errores semánticos
            System.out.println("Errores semánticos:");
            for (String error : semanticVisitor.getErrors()) {
                System.err.println(error);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}