package Interpreter;

import org.antlr.v4.runtime.*;

public class VerboseListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        System.err.printf("Error sintáctico en línea %d, columna %d: %s%n", line, charPositionInLine, msg);
    }
}