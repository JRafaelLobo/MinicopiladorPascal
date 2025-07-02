package RepresentacionIntermedia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;

public class LLVMGenerator {

    private int tempVarCounter = 0;
    private final Set<String> variables = new LinkedHashSet<>();
    private final Map<String, String> varTypes = new HashMap<>();
    private boolean endsWithBranch = false;

    public List<String> generar(List<Cuadruplo> cuadruplos) {
        tempVarCounter = 0;
        variables.clear();
        varTypes.clear();
        endsWithBranch = false;

        List<String> llvmCode = new ArrayList<>();
        llvmCode.add("declare i32 @printf(i8*, ...)");
        llvmCode.add("@.str = private constant [4 x i8] c\"%d\\0A\\00\"");

        llvmCode.add("define i32 @main() {");
        llvmCode.add("entry:");

        // Detectar variables y asignar tipo (por convención 't_bool' es booleano)
        for (Cuadruplo c : cuadruplos) {
            agregarVariableSiEsNecesaria(c.getResultado());
            agregarVariableSiEsNecesaria(c.getArg1());
            agregarVariableSiEsNecesaria(c.getArg2());
        }

        // Reservar memoria para variables
        for (String var : variables) {
            String tipo = varTypes.getOrDefault(var, "i32");
            llvmCode.add("  %" + var + " = alloca " + tipo);
        }

        // Generar instrucciones LLVM
        for (Cuadruplo c : cuadruplos) {
            if ("label".equals(c.getOp())) {
                if (!endsWithBranch) {
                    llvmCode.add("  br label %" + c.getResultado());
                }
                llvmCode.add(c.getResultado() + ":");
                endsWithBranch = false;
                continue;
            }

            switch (c.getOp()) {
                case "=" -> emitAssignment(llvmCode, c);
                case "+" -> emitBinaryOp(llvmCode, "add", c);
                case "-" -> emitBinaryOp(llvmCode, "sub", c);
                case "*" -> emitBinaryOp(llvmCode, "mul", c);
                case "/" -> emitBinaryOp(llvmCode, "sdiv", c);
                case ">=" -> emitComparison(llvmCode, "sge", c);
                case "<=" -> emitComparison(llvmCode, "sle", c);
                case ">" -> emitComparison(llvmCode, "sgt", c);
                case "<" -> emitComparison(llvmCode, "slt", c);
                case "==" -> emitComparison(llvmCode, "eq", c);
                case "!=" -> emitComparison(llvmCode, "ne", c);
                case "write" -> emitWrite(llvmCode, c);
                case "ifFalse" -> {
                    emitBranchFalse(llvmCode, c);
                    endsWithBranch = true;
                }
                case "goto" -> {
                    llvmCode.add("  br label %" + c.getResultado());
                    endsWithBranch = true;
                }
                default -> llvmCode.add("  ; No manejado: " + c);
            }

            if (!c.getOp().equals("goto") && !c.getOp().equals("ifFalse")) {
                endsWithBranch = false;
            }
        }

        llvmCode.add("  ret i32 0");
        llvmCode.add("}");
        return llvmCode;
    }

    private void agregarVariableSiEsNecesaria(String name) {
        if (name == null || name.isEmpty()) return;
        if (name.matches("\\d+")) return;       // Literal numérico
        if (name.matches("L\\d+")) return;      // Etiquetas
        if (name.startsWith("tmp")) return;     // Temporales LLVM
        variables.add(name);

        // Solo registrar tipo si aún no se ha registrado
        if (!varTypes.containsKey(name)) {
            if (name.startsWith("t_bool") || name.startsWith("t") && Character.isDigit(name.charAt(1))) {
                // Heurística para temporales booleanos
                varTypes.put(name, "i1");
            } else {
                varTypes.put(name, "i32");
            }
        }
    }

    private void emitAssignment(List<String> code, Cuadruplo c) {
        String val = loadValue(code, c.getArg1());
        String destType = varTypes.getOrDefault(c.getResultado(), "i32");
        String sourceType = getLLVMTypeOf(val);

        // Si el tipo fuente y destino no coinciden, convertir si es posible
        if (!destType.equals(sourceType)) {
            String tmp = newTemp();
            if ("i32".equals(sourceType) && "i1".equals(destType)) {
                // truncar de i32 a i1
                code.add("  %" + tmp + " = trunc i32 " + val + " to i1");
            } else if ("i1".equals(sourceType) && "i32".equals(destType)) {
                // extender de i1 a i32
                code.add("  %" + tmp + " = zext i1 " + val + " to i32");
            } else {
                code.add("  ; No se puede convertir de " + sourceType + " a " + destType);
                tmp = val;
            }
            val = "%" + tmp;
        }

        code.add("  store " + destType + " " + val + ", " + destType + "* %" + c.getResultado());
    }

    private void emitBinaryOp(List<String> code, String llvmOp, Cuadruplo c) {
        String left = loadValue(code, c.getArg1());
        String right = loadValue(code, c.getArg2());
        String temp = newTemp();
        code.add("  %" + temp + " = " + llvmOp + " i32 " + left + ", " + right);

        // ✅ Asegurar que se registre el tipo correcto
        varTypes.put(c.getResultado(), "i32");

        code.add("  store i32 %" + temp + ", i32* %" + c.getResultado());
    }

    private void emitComparison(List<String> code, String cond, Cuadruplo c) {
        String left = loadValue(code, c.getArg1());
        String right = loadValue(code, c.getArg2());
        String tmp = newTemp();
        code.add("  %" + tmp + " = icmp " + cond + " i32 " + left + ", " + right);
        code.add("  store i1 %" + tmp + ", i1* %" + c.getResultado());
        // Registrar variable booleana si no está
        agregarVariableSiEsNecesaria(c.getResultado());
    }

    private void emitBranchFalse(List<String> code, Cuadruplo c) {
        // Condición que es variable booleana
        String condVar = loadValue(code, c.getArg1());
        String tempCond = newTemp();
        // La condición es i1, así que no necesitamos comparar con 0
        code.add("  br i1 " + condVar + ", label %continue" + tempCond + ", label %" + c.getResultado());
        code.add("continue" + tempCond + ":");
    }

    private String loadValue(List<String> code, String val) {
        if (val == null || val.isEmpty()) return "0";
        if (val.matches("-?\\d+")) return val; // Literal numérico
        if (variables.contains(val)) {
            String tipo = varTypes.containsKey(val) ? varTypes.get(val) : "i32";
            String temp = newTemp();
            code.add("  %" + temp + " = load " + tipo + ", " + tipo + "* %" + val);
            return "%" + temp;
        }
        if (val.startsWith("%")) return val;
        return "%" + val;
    }

    private void emitWrite(List<String> code, Cuadruplo c) {
        String varName = c.getArg1();
        if (varName == null || varName.isEmpty()) return;

        String tipo = varTypes.getOrDefault(varName, "i32");

        String loaded = loadValue(code, varName);  // obtiene el valor cargado (por ejemplo, %tmp5)

        if ("i32".equals(tipo)) {
            code.add("  call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 " + loaded + ")");
        } else if ("i1".equals(tipo)) {
            // Convertir booleano a entero (0 o 1)
            String tmpInt = newTemp();
            code.add("  %" + tmpInt + " = zext i1 " + loaded + " to i32");
            code.add("  call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i32 0, i32 0), i32 %" + tmpInt + ")");
        } else {
            code.add("  ; No se puede imprimir tipo: " + tipo);
        }
    }

    private String newTemp() {
        return "tmp" + (tempVarCounter++);
    }

    private String getLLVMTypeOf(String val) {
        if (val.matches("%tmp\\d+")) {
            // Heurística simple: buscar la variable que originó ese temp
            // No es perfecto, pero ayuda en casos básicos
            return "i32";  // por defecto asumimos i32 si no sabemos
        }
        if (variables.contains(val)) {
            return varTypes.getOrDefault(val, "i32");
        }
        if (val.matches("\\d+")) return "i32";
        return "i32";  // por defecto
    }

}