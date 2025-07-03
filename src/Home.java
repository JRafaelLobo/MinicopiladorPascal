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
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
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
                consola.setText("");
                //System.out.println(textBox.getText());
                String texto = textBox.getText();
                Compilar(texto);

            }
        });
        mostrarIRButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Muestra el resultado en una ventana emergente

                JTextArea textArea = new JTextArea(IR);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 400));

                JOptionPane.showMessageDialog(null, scrollPane, "Código LLVM IR", JOptionPane.INFORMATION_MESSAGE);

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

    private void Compilar(String entrada) {
        consola.append("Compilando..." + "\n");

        try {
            // Capturar errores de System.err temporalmente
            ByteArrayOutputStream errorBuffer = new ByteArrayOutputStream();
            PrintStream originalErr = System.err;
            System.setErr(new PrintStream(errorBuffer));

            // 1. Análisis léxico
            CharStream input = CharStreams.fromString(entrada);
            InterpreterLexer lexer = new InterpreterLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new VerboseListener(consola));

            // 2. Tokens
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 3. Análisis sintáctico
            InterpreterParser parser = new InterpreterParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new VerboseListener(consola));

            // 4. Parse
            ParseTree tree = parser.program();

            // Restaurar System.err
            System.err.flush();
            System.setErr(originalErr);

            // Verifica errores sintácticos/léxicos capturados
            String erroresCapturados = errorBuffer.toString();
            if (!erroresCapturados.isEmpty()) {
                consola.append("\n❌ Compilación detenida por errores léxicos o sintácticos.\n");
                JOptionPane.showMessageDialog(null, "Error de sintaxis. Revisa el código fuente.");
                return;
            }

            // 5. Análisis semántico
            SemanticVisitor semanticVisitor = new SemanticVisitor();
            semanticVisitor.visit(tree);
            for (String error : semanticVisitor.getErrors()) {
                consola.append(error + "\n");
            }

            // Si hay errores semánticos, detener compilación
            if (!semanticVisitor.getErrors().isEmpty()) {
                consola.append("\n❌ Compilación detenida por errores semánticos.\n");
                JOptionPane.showMessageDialog(null, "Error semántico. Revisa el código fuente.");
                return;
            }

            // 6. Generación de código intermedio
            IRGeneratorVisitor irGen = new IRGeneratorVisitor();
            irGen.visit(tree);
            CodigoIntermedio codigo = irGen.getCodigo();

            // 7. LLVM IR
            LLVMGenerator llvmGen = new LLVMGenerator();
            List<String> instruccionesLLVM = llvmGen.generar(codigo.getCuadruplos());

            // 8. Guardar archivo
            consola.append("\n");
            consola.append(LLVMWriter.escribirArchivo("salida.ll", instruccionesLLVM) + "\n\n");

            // Mostrar LLVM en consola
            IR = "";
            for (String linea : instruccionesLLVM) {
                IR += (linea + "\n");
            }

            JOptionPane.showMessageDialog(null, "✅ Compilación Exitosa!");

        } catch (Exception e) {
            consola.append("⚠️ Error durante la compilación: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }


    private String IR;

}