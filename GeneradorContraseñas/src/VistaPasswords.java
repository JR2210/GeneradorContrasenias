import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VistaPasswords extends JFrame {

    public VistaPasswords() {
        JPanel panelContras = new JPanel(new GridLayout(numeroContrasenias(),4));
        String contrasenia = "SELECT cContraseña,cNombre FROM tContraseña";
        try {
            PreparedStatement pstmt = Servidor.obtenerInstancia().conectarBaseDatos().prepareStatement(contrasenia);
            try (ResultSet r = pstmt.executeQuery()){
                while(r.next()) {
                    Mostrar mostrar = new Mostrar();
                    String contrasenias = r.getString(1);
                    JButton copiar = new JButton("Copiar");

                    JPasswordField contras = new JPasswordField(20);
                    Font fuenteContras = contras.getFont();
                    Font grandeC = fuenteContras.deriveFont(fuenteContras.getSize() + 10f);
                    contras.setFont(grandeC);
                    contras.setEditable(false);
                    contras.setEchoChar('*'); // Mostrar caracteres ocultos
                    contras.setText("***********");
                    mostrar.setText("Mostrar");

                    JTextField nombres = new JTextField(r.getString(2));
                    Font fuenteNombres = nombres.getFont();
                    Font grandeN = fuenteNombres.deriveFont(fuenteNombres.getSize() + 10f);
                    nombres.setFont(grandeN);
                    nombres.setEditable(false);

                    panelContras.add(contras);
                    panelContras.add(nombres);

                    copiar.addActionListener(e -> {
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        StringSelection selection = new StringSelection(contrasenias);
                        clipboard.setContents(selection, null);
                        System.out.println("Contraseña copiada al portapapeles");
                    });

                    mostrar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (mostrar.getText().equals("Mostrar")) {
                                contras.setEchoChar((char) 0); // Mostrar caracteres normales
                                contras.setText(contrasenias);
                                mostrar.setText("Ocultar");
                            } else {
                                contras.setEchoChar('*'); // Mostrar caracteres ocultos
                                contras.setText("***********");
                                mostrar.setText("Mostrar");
                            }
                        }
                    });
                    panelContras.add(mostrar);
                    panelContras.add(copiar);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        setTitle("Listado de contraseñas");
        setSize(1280,720);
        setDefaultCloseOperation(2);
        add(panelContras);
        setVisible(true);
    }

    private int numeroContrasenias() {
        int numero;
        try {
            Servidor servidor = Servidor.obtenerInstancia();
            String query = "SELECT COUNT(*) FROM tContraseña";
            PreparedStatement pstmt = servidor.conectarBaseDatos().prepareStatement(query);
            ResultSet r = pstmt.executeQuery();
            r.next();
            numero = r.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            numero = 0;
        }
        return numero;
    }
}
