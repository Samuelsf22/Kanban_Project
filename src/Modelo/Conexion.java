package Modelo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    /*public  boolean esCorreo(String correo) {
        try {
            InternetAddress internetAddress = new InternetAddress(correo);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }*/
    public static Connection getConexion(){
        Connection conn = null;
        try{
            String driver ="com.mysql.cj.jdbc.Driver";
            String user=   "";
            String pass=   "";
            String url =   "jdbc:";
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
        }catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        return conn;
    }  
}
