package RepresentacionIntermedia;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LLVMWriter {

    public static String escribirArchivo(String nombreArchivo, List<String> instruccionesLLVM) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            for (String instruccion : instruccionesLLVM) {
                writer.write(instruccion + System.lineSeparator());
            }
            return "Archivo LLVM generado correctamente: " + nombreArchivo;
        } catch (IOException e) {
            return "Error al escribir el archivo LLVM: " + e.getMessage();
        }
    }
}