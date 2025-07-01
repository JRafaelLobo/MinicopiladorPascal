import Interpreter.*;
import RepresentacionIntermedia.CodigoIntermedio;
import RepresentacionIntermedia.Cuadruplo;
import RepresentacionIntermedia.LLVMGenerator;
import RepresentacionIntermedia.LLVMWriter;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Lee el archivo fuente Mini-Pascal (.pas) como flujo de caracteres
            CharStream input = CharStreams.fromFileName("tests/test1.pas");

            // Instancia el analizador léxico generado por ANTLR
            InterpreterLexer lexer = new InterpreterLexer(input);
            // Quita los manejadores de errores por defecto del lexer
            lexer.removeErrorListeners();
            // Agrega un listener personalizado para reportar errores léxicos
            lexer.addErrorListener(new VerboseListener());

            // Crea el flujo de tokens a partir del lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // Instancia el parser generado por ANTLR
            InterpreterParser parser = new InterpreterParser(tokens);
            // Quita los manejadores de errores por defecto del parser
            parser.removeErrorListeners();
            // Agrega un listener personalizado para errores sintácticos
            parser.addErrorListener(new VerboseListener());

            // Comienza el análisis sintáctico desde la regla principal "program"
            ParseTree tree = parser.program();

            // Imprime el árbol sintáctico generado
            System.out.println("Árbol sintáctico:");
            System.out.println(tree.toStringTree(parser));

            // Realiza el análisis semántico sobre el árbol
            SemanticVisitor semanticVisitor = new SemanticVisitor();
            semanticVisitor.visit(tree);

            // Muestra los errores semánticos recolectados (si existen)
            System.out.println("Errores semánticos:");
            for (String error : semanticVisitor.getErrors()) {
                System.err.println(error);
            }

            // IR - Cuadruplos
            IRGeneratorVisitor irGen = new IRGeneratorVisitor();
            irGen.visit(tree);

            System.out.println("\nCódigo intermedio de tres direcciones:");
            CodigoIntermedio codigo = irGen.getCodigo();

            for (Cuadruplo c : codigo.getCuadruplos()) {
                System.out.println(c);
            }

            //IR - LLVM
            LLVMGenerator llvmGen = new LLVMGenerator();
            List<String> instruccionesLLVM = llvmGen.generar(codigo.getCuadruplos());
            LLVMWriter.escribirArchivo("salida.ll", instruccionesLLVM);

            // Mostrar en consola
            System.out.println("\nCódigo LLVM generado:");
            for (String linea : instruccionesLLVM) {
                System.out.println(linea);
            }
            System.out.println();

            // Guardar en archivo salida.ll
            LLVMWriter.escribirArchivo("salida.ll", instruccionesLLVM);

        } catch (IOException e) {
            // Manejo de error si el archivo no puede abrirse
            System.err.println(e.getMessage());
        }
    }
}
