package Vista;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class VentanaRegistrar {
    public JPanel LayoutPrincipal;
    public JLabel lblLayoutFondo;
    public JButton btnVolver;
    public JButton btnRegistrar;
    public JTextField txtNombre;
    public JTextField txtApellidos;
    public JTextField txtEmail;
    public JPasswordField txtContrasenia;
    public VentanaRegistrar(){
        txtNombre.setBorder(new LineBorder(Color.BLACK,0));
        txtApellidos.setBorder(new LineBorder(Color.BLACK,0));
        txtEmail.setBorder(new LineBorder(Color.BLACK,0));
        txtContrasenia.setBorder(new LineBorder(Color.BLACK,0));
    }
}
