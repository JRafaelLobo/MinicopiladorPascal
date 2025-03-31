import Interpreter.InterpreterLexer;
import Interpreter.InterpreterParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Cargar archivo .pas
            CharStream input = CharStreams.fromFileName("tests/test.pas");

            // Crear el lexer
            InterpreterLexer lexer = new InterpreterLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // Crear el parser
            InterpreterParser parser = new InterpreterParser(tokens);

            // Iniciar análisis desde la regla "program"
            ParseTree tree = parser.program();

            // Mostrar el árbol sintáctico
            System.out.println(tree.toStringTree(parser));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}