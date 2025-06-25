package RepresentacionIntermedia;

import java.util.ArrayList;
import java.util.List;

public class CodigoIntermedio {
    private final List<Cuadruplo> cuadruplos = new ArrayList<>();

    public void agregar(String op, String arg1, String arg2, String resultado) {
        cuadruplos.add(new Cuadruplo(op, arg1, arg2, resultado));
    }

    public void agregar(Cuadruplo c) {
        cuadruplos.add(c);
    }

    public List<Cuadruplo> getCuadruplos() {
        return cuadruplos;
    }

    public void imprimir() {
        for (Cuadruplo c : cuadruplos) {
            System.out.println(c);
        }
    }

    public void limpiar() {
        cuadruplos.clear();
    }

    public int size() {
        return cuadruplos.size();
    }

    public Cuadruplo get(int index) {
        return cuadruplos.get(index);
    }
}
