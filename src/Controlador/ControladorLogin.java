package Controlador;

import Modelo.UsuarioDAO;
import Modelo.Usuario;
import Vista.VentanaLogin;
import Vista.VentanaRegistrar;
import Vista.VentanaTareas;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorLogin extends JFrame implements ActionListener {
    private static VentanaLogin frmLogin = new VentanaLogin() ;
    private final UsuarioDAO usuarioDAO;

    public ControladorLogin(UsuarioDAO usuarioDAO, VentanaLogin frmLogin) {
        this.usuarioDAO = usuarioDAO;
        ControladorLogin.frmLogin = frmLogin;
        ControladorLogin.frmLogin.btnLogin.addActionListener(this);
        ControladorLogin.frmLogin.btnRegistrar.addActionListener(this);
        ControladorLogin.frmLogin.btnCerrar.addActionListener(this);
        verFormuIario();
    }
    public void verFormuIario() {
        setContentPane(frmLogin.LayoutPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//cierra la ventana pero no la ejecucion
        setUndecorated(true);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        frmLogin.txtEmail.setBorder(new LineBorder(Color.BLACK,0));
        frmLogin.txtContrasenia.setBorder(new LineBorder(Color.BLACK,0));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmLogin.btnLogin){
            if (frmLogin.txtEmail.getText().equals("admin") && frmLogin.txtContrasenia.getText().equals("admin")){
                VentanaTareas ventanaTareas = new VentanaTareas();
                var crtlTareas = new ControladorTareas(usuarioDAO, ventanaTareas);
                crtlTareas.isAdmin();
                crtlTareas.setVisible(true);
                dispose();
            }else if (verificarLogin()){
                var usuario = new Usuario(frmLogin.txtEmail.getText(), frmLogin.txtContrasenia.getText());
                var mensaje = usuarioDAO.ValidarUsuario(usuario);
                if (mensaje.isEmpty()){
                    VentanaTareas ventanaTareas = new VentanaTareas();
                    var crtlTareas = new ControladorTareas(usuarioDAO, ventanaTareas);
                    crtlTareas.setUsuario(frmLogin.txtEmail.getText());
                    crtlTareas.notAdmin();
                    crtlTareas.setVisible(true);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(frmLogin.LayoutPrincipal, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        if (e.getSource() == frmLogin.btnRegistrar){
            dispose();
            var ventanaRegistrar = new VentanaRegistrar();
            new ControladorRegistrar(usuarioDAO, ventanaRegistrar).setVisible(true);
        }
        if (e.getSource() == frmLogin.btnCerrar){
            System.exit(0);
        }
    }
    private boolean verificarLogin() {
        if (!frmLogin.txtEmail.getText().isEmpty() && !frmLogin.txtContrasenia.getText().isEmpty()) {
            if (!esCorreo(frmLogin.txtEmail.getText())) {
                JOptionPane.showMessageDialog(frmLogin.LayoutPrincipal,"El correo es invalido");
                return false;
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(frmLogin.LayoutPrincipal,"Por favor rellene el o los campos restantes");
            return false;
        }
    }
    private boolean esCorreo(String correo) {
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher comparar = patron.matcher(correo);
        return comparar.find();
    }
}
