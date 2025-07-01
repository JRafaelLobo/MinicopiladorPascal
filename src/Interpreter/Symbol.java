package Interpreter;

/**
 * Esta clase representa un **símbolo** dentro del análisis semántico.
 * Un símbolo puede ser una variable, parámetro, función o procedimiento.

 * Cada símbolo almacena:
 * - `name`: el identificador del símbolo.
 * - `type`: el tipo del símbolo (integer, char, string, boolean, procedure, etc.).
 * - `isReference`: si es un parámetro pasado por referencia (usando `var`).
 */
public class Symbol {
    public String name;         // Nombre del símbolo (identificador)
    public String type;         // Tipo del símbolo (ej. integer, string, procedure...)
    public boolean isReference; // True si es un parámetro por referencia

    // Constructor: inicializa el símbolo con nombre, tipo y modo de paso (por valor o referencia)
    public Symbol(String name, String type, boolean isReference) {
        this.name = name;
        this.type = type;
        this.isReference = isReference;
    }
}
