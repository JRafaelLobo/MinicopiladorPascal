import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
}

