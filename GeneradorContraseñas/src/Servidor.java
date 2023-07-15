import java.sql.*;

public class Servidor {
    private static Servidor instancia = null;
    private final Connection conexionServidor;

    private Servidor() {
        try {
            conexionServidor = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/contraseñas", "root", "1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Servidor obtenerInstancia() {
        if(instancia == null) {
            instancia = new Servidor();
        }
        return instancia;
    }

    public Connection conectarBaseDatos() {
        return conexionServidor;
    }
}