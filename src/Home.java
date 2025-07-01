import Interpreter.*;
import RepresentacionIntermedia.CodigoIntermedio;
import RepresentacionIntermedia.Cuadruplo;
import RepresentacionIntermedia.LLVMGenerator;
import RepresentacionIntermedia.LLVMWriter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Home extends JFrame {
    private JButton compilarButton;
    private JButton cargarButton;
    private JButton mostrarIRButton;
    private JPanel home;
    private JTextArea textBox;
    private JTextArea consola;

    public Home() {
        compilarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(textBox.getText());
                String texto = textBox.getText();
                Copilar(texto);

            }
        });
        mostrarIRButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mostrar IR");
            }
        });
        cargarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                // Filtro para archivos .pas
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".pas");
                    }

                    @Override
                    public String getDescription() {
                        return "Archivos Pascal (*.pas)";
                    }
                });

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                        textBox.setText(""); // limpiar antes de cargar
                        String line;
                        while ((line = br.readLine()) != null) {
                            textBox.append(line + "\n");
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        Home home = new Home();
        home.setTitle("Compilador MiniPascal");
        home.setSize(800, 600);
        home.setContentPane(home.home);
        home.setLocationRelativeTo(null);
        home.setVisible(true);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void Copilar(String entrada) {
        consola.append("Copilando..." + "\n");

        // Lee el archivo fuente Mini-Pascal (.pas) como flujo de caracteres
        CharStream input = CharStreams.fromString(entrada);

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
        parser.addErrorListener(new VerboseListener(consola));

        // Comienza el análisis sintáctico desde la regla principal "program"
        ParseTree tree = parser.program();

        // Imprime el árbol sintáctico generado
        consola.append("Árbol sintáctico:" + "\n");
        consola.append(tree.toStringTree(parser) + "\n");

        // Realiza el análisis semántico sobre el árbol
        SemanticVisitor semanticVisitor = new SemanticVisitor();
        semanticVisitor.visit(tree);

        // Muestra los errores semánticos recolectados (si existen)
        consola.append(tree.toStringTree(parser) + "\n");
        for (String error : semanticVisitor.getErrors()) {
            consola.append(error + "\n");
        }

        // IR - Cuadruplos
        IRGeneratorVisitor irGen = new IRGeneratorVisitor();
        irGen.visit(tree);

        consola.append("\nCódigo intermedio de tres direcciones:\n");
        CodigoIntermedio codigo = irGen.getCodigo();

        for (Cuadruplo c : codigo.getCuadruplos()) {
            consola.append(String.valueOf(c) + "\n");
        }

        //IR - LLVM
        LLVMGenerator llvmGen = new LLVMGenerator();
        List<String> instruccionesLLVM = llvmGen.generar(codigo.getCuadruplos());
        LLVMWriter.escribirArchivo("salida.ll", instruccionesLLVM);

        // Mostrar en consola
        consola.append("\nCódigo LLVM generado:");
        for (String linea : instruccionesLLVM) {
            consola.append(linea + "\n");
        }
        consola.append("\n");

        // Guardar en archivo salida.ll
        consola.append(LLVMWriter.escribirArchivo("salida.ll", instruccionesLLVM)+ "\n\n");
    }
}


