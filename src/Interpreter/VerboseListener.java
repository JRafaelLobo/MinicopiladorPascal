package Interpreter;

import org.antlr.v4.runtime.*;

/**
 * `VerboseListener` es un listener personalizado para **reportar errores sintácticos**.

 * Se utiliza en el análisis léxico y sintáctico para capturar y mostrar errores
 * de forma clara, incluyendo la línea, columna y mensaje del error.
 */
public class VerboseListener extends BaseErrorListener {

    /**
     * Este método se ejecuta automáticamente cuando ANTLR encuentra un error sintáctico.
     *
     * recognizer          El parser o lexer que detectó el error.
     * offendingSymbol     El símbolo que causó el error.
     * line                Línea donde ocurrió el error.
     * charPositionInLine  Posición (columna) del error en la línea.
     * msg                 Mensaje descriptivo del error.
     * e                   Excepción con información adicional del error (puede ser null).
     */
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        System.err.printf("Error sintáctico en línea %d, columna %d: %s%n", line, charPositionInLine, msg);
    }
}
