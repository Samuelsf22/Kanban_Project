package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {
     public String guardarUsuario(Usuario usuario) {
         try {
             Connection conexion = Conexion.getConexion();
             String checkSQL = "select correo FROM usuarios WHERE correo = ?;";
             var check = conexion.prepareStatement(checkSQL);
             check.setString(1,usuario.getCorreo());
             ResultSet rs = check.executeQuery();
             if (rs.next()) {
                 return "El correo ya está usado en otra cuenta";
             }else {
                 String sql = "INSERT INTO usuarios (nombre,apellido, correo, contrasena) VALUES( ?,?,?,?);";
                 var pstms = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                 pstms.setString(1, usuario.getNombre());
                 pstms.setString(2, usuario.getApellido());
                 pstms.setString(3, usuario.getCorreo());
                 pstms.setString(4, usuario.getContrasena());
                 pstms.executeUpdate();
                 conexion.close();
                 return "";
             }
         } catch (SQLException e) {
             e.printStackTrace();
             return "error";
         }
    }

    public String ValidarUsuario(Usuario usuario) {
        try {
            Connection conn = Conexion.getConexion();

            String emailCorrecto = null;
            String passCorrecto = null;

            String consulta = "SELECT correo, contrasena FROM usuarios WHERE correo = ?;";
            var ps = conn.prepareStatement(consulta);
            ps.setString(1, usuario.getCorreo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emailCorrecto = rs.getString(1);
                passCorrecto = rs.getString(2);
            }
            conn.close();

            if (usuario.getCorreo().equals(emailCorrecto) && usuario.getContrasena().equals(passCorrecto)) {
                return "";
            } else if (usuario.getCorreo().equals(emailCorrecto) && !usuario.getContrasena().equals(passCorrecto)) {
                return "Contraseña incorrecta, intente de nuevo";
            }
            if (!usuario.getCorreo().equals(emailCorrecto)) {
                return "El correo no existe en la base de datos";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "error";
    }
    public Usuario verUsuario(String correo) {
        try {
            Connection conn = Conexion.getConexion();
            Usuario usuario = null;
            var pstms = conn.prepareStatement("select id, nombre, apellido from usuarios where correo = ?;");
            pstms.setString(1,correo);
            ResultSet rs = pstms.executeQuery();
            while (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"), correo);
            }
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
