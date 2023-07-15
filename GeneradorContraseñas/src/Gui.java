import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Gui extends JFrame {
    private final JCheckBox mayus;
    private final JCheckBox num;
    private final JCheckBox simb;
    private final JTextField contraseniaGenerada;
    private final JTextArea sitioWeb;
    private Contrasenia objContrasenia;

    public Gui() {
        JPanel panelPrincipal = new JPanel(new GridLayout(5, 0));
        Servidor servidor = Servidor.obtenerInstancia();
        setSize(800, 600);
        setTitle("Generador de contraseñas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mayus = new JCheckBox("Mayúsculas o no");
        num = new JCheckBox("Números o no");
        simb = new JCheckBox("Símbolos o no");
        JButton boton = new JButton("Generar");
        JTextField web = new JTextField("Sitio web");
        JButton mostrar = new JButton("Mostrar Contraseñas");
        web.setEditable(false);
        sitioWeb = new JTextArea();
        Font webFont = sitioWeb.getFont();
        Font grande = webFont.deriveFont(webFont.getSize() + 10f);
        sitioWeb.setFont(grande);

        JButton guardar = new JButton("Guardar en BBDD");

        contraseniaGenerada = new JTextField();
        contraseniaGenerada.setEditable(false);
        Font currentFont = contraseniaGenerada.getFont();
        Font biggerFont = currentFont.deriveFont(currentFont.getSize() + 10f);
        contraseniaGenerada.setFont(biggerFont);

        boton.addActionListener(e -> {
            objContrasenia = new Contrasenia();
            objContrasenia.generarContrasenia(mayus.isSelected(), num.isSelected(), simb.isSelected());
            contraseniaGenerada.setText(objContrasenia.toString());
        });

        guardar.addActionListener(e -> {
            try {
                String query = "INSERT INTO tContraseña(cContraseña,cNombre) values(?,?)";
                PreparedStatement pstmt = servidor.conectarBaseDatos().prepareStatement(query);
                pstmt.setString(1,contraseniaGenerada.getText());
                pstmt.setString(2,sitioWeb.getText());
                pstmt.executeUpdate();
            } catch (SQLException i){
                JFrame error = new JFrame("Error al insertar");
                error.add(new JLabel(i.getMessage()));
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                error.setSize(400,400);
                error.setVisible(true);
            }
        });

        mostrar.addActionListener(e-> {
            new VistaPasswords();
        });

        JPanel panelCheck = new JPanel(new GridLayout(1,3));
        panelCheck.add(mayus,0);
        panelCheck.add(num,1);
        panelCheck.add(simb,2);

        JPanel panelContra = new JPanel(new GridLayout(1,2));
        panelContra.add(boton,0);
        panelContra.add(contraseniaGenerada,1);

        JPanel panelWeb = new JPanel(new GridLayout(1,2));
        panelWeb.add(web,0);
        panelWeb.add(sitioWeb,1);

        JPanel panelGuardar = new JPanel(new GridLayout(1,1));
        panelGuardar.add(guardar,0);

        panelPrincipal.add(panelCheck,0);
        panelPrincipal.add(panelContra,1);
        panelPrincipal.add(panelWeb,2);
        panelPrincipal.add(panelGuardar,3);
        panelPrincipal.add(mostrar,4);

        add(panelPrincipal);
        setVisible(true);
    }
}