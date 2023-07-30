package Controlador;

import Modelo.UsuarioDAO;
import Modelo.Usuario;
import Vista.VentanaLogin;
import Vista.VentanaRegistrar;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class ControladorRegistrar extends JFrame implements ActionListener {
    private static VentanaRegistrar frmRegistrar = new VentanaRegistrar() ;
    private final UsuarioDAO consultaUsuario;

    public ControladorRegistrar(UsuarioDAO consultaUsuario, VentanaRegistrar frmRegistrar) {
        this.consultaUsuario = consultaUsuario;
        ControladorRegistrar.frmRegistrar = frmRegistrar;
        ControladorRegistrar.frmRegistrar.btnVolver.addActionListener(this);
        ControladorRegistrar.frmRegistrar.btnRegistrar.addActionListener(this);
        verFormuIario();
    }
    public void verFormuIario() {
        setContentPane(frmRegistrar.LayoutPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//cierra la ventana pero no la ejecucion
        setUndecorated(true);
        //setSize(500, 360);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == frmRegistrar.btnVolver){
            dispose();
            var ventanaLogin = new VentanaLogin();
            new ControladorLogin(consultaUsuario, ventanaLogin).setVisible(true);
        }
        if (e.getSource() == frmRegistrar.btnRegistrar){
            if (verificarRegister()){
                var usuario = new Usuario(frmRegistrar.txtNombre.getText(), frmRegistrar.txtApellidos.getText(),
                        frmRegistrar.txtEmail.getText(), frmRegistrar.txtContrasenia.getText());
                var mensaje = consultaUsuario.guardarUsuario(usuario);
                if (mensaje.isEmpty()){
                    var ventanaLogin = new VentanaLogin();
                    new ControladorLogin(consultaUsuario, ventanaLogin).setVisible(true);
                    dispose();
                    JOptionPane.showMessageDialog(null, "Usuario guardado correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(frmRegistrar.LayoutPrincipal, mensaje);
                }
            }
        }

    }
    private boolean verificarRegister() {
        if (!frmRegistrar.txtNombre.getText().isEmpty() && !frmRegistrar.txtApellidos.getText().isEmpty() &&
                !frmRegistrar.txtEmail.getText().isEmpty() && !frmRegistrar.txtContrasenia.getText().isEmpty()) {
            if (!esCorreo(frmRegistrar.txtEmail.getText())) {
                JOptionPane.showMessageDialog(frmRegistrar.LayoutPrincipal,"El correo es invalido");
                return false;
            }
            if (frmRegistrar.txtContrasenia.getText().length() < 8) {
                JOptionPane.showMessageDialog(frmRegistrar.LayoutPrincipal,"La contraseña debe contener mínimo 8 caracteres");
                return false;
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(frmRegistrar.LayoutPrincipal,"Por favor rellene el o los campos restantes");
            return false;
        }
    }
    private boolean esCorreo(String correo) {
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher comparar = patron.matcher(correo);
        //FIND dice si la comparacion es verdadera o falsa
        return comparar.find();
    }
}
