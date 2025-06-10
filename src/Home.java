import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
                System.out.println("Compilar");
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
                System.out.println("Cargar");
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

