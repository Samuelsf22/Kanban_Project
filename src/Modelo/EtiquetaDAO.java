package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EtiquetaDAO {
    public ArrayList<Etiqueta> verEtiquetas () {
        Connection conn = Conexion.getConexion();
        var etiquetas = new ArrayList<Etiqueta>();
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select prioridad from etiquetas;");
            while (rs.next()) {
                var etiqueta = new Etiqueta(rs.getString("prioridad"));
                etiquetas.add(etiqueta);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etiquetas;
    }
}
