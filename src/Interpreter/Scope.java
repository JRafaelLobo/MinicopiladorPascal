package Interpreter;

import java.util.*;

/**
 * La clase Scope representa un **ámbito léxico** en el análisis semántico.

 * Cada Scope puede tener un Scope padre (permitiendo anidamiento de ámbitos).
 * Sirve para gestionar las **tablas de símbolos** en diferentes bloques de código,
 * como funciones, procedimientos o el programa principal.
 */
public class Scope {
    // Mapa que almacena los símbolos definidos en este ámbito (nombre -> símbolo)
    private final Map<String, Symbol> symbols = new HashMap<>();

    // Ámbito padre (puede ser null si es el ámbito global)
    private final Scope parent;

    // Constructor: crea un nuevo Scope con un posible Scope padre
    public Scope(Scope parent) {
        this.parent = parent;
    }

    /**
     * Define un nuevo símbolo en el ámbito actual.
     * Retorna false si ya existe un símbolo con ese nombre en este mismo Scope.
     */
    public boolean define(Symbol symbol) {
        if (symbols.containsKey(symbol.name)) return false; // Ya está definido
        symbols.put(symbol.name, symbol); // Agrega el nuevo símbolo
        return true;
    }

    /**
     * Busca un símbolo por nombre en el Scope actual o en los padres (de forma recursiva).
     * Retorna null si no se encuentra.
     */
    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);
        if (s != null) return s; // Encontrado en el Scope actual
        return (parent != null) ? parent.resolve(name) : null; // Buscar en padres
    }

    /**
     * Verifica si un símbolo está definido solo en el Scope actual (sin buscar en padres).
     */
    public boolean existsInCurrentScope(String name) {
        return symbols.containsKey(name);
    }
}
