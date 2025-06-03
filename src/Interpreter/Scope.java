package Interpreter;

import java.util.*;

public class Scope {
    private final Map<String, Symbol> symbols = new HashMap<>();
    private final Scope parent;

    public Scope(Scope parent) {
        this.parent = parent;
    }

    public boolean define(Symbol symbol) {
        if (symbols.containsKey(symbol.name)) return false;
        symbols.put(symbol.name, symbol);
        return true;
    }

    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);
        if (s != null) return s;
        return (parent != null) ? parent.resolve(name) : null;
    }

    public boolean existsInCurrentScope(String name) {
        return symbols.containsKey(name);
    }
}
