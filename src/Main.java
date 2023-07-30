import Controlador.ControladorLogin;
import Modelo.UsuarioDAO;
import Vista.VentanaLogin;

public class Main {
    public static void main(String[] args) {
        var consultaUsuario = new UsuarioDAO();
        var ventanaLogin = new VentanaLogin();
        new ControladorLogin(consultaUsuario, ventanaLogin).setVisible(true);

    }
}
