package Interpreter;

public class Symbol {
    public String name;
    public String type;
    public boolean isReference;

    public Symbol(String name, String type, boolean isReference) {
        this.name = name;
        this.type = type;
        this.isReference = isReference;
    }
}
