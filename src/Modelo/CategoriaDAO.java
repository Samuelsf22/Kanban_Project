package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoriaDAO {
    public ArrayList<Categoria> verCategorias () {
        Connection conn = Conexion.getConexion();
        var categorias = new ArrayList<Categoria>();
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select nombre from categorias;");
            while (rs.next()) {
                var categoria = new Categoria(rs.getString("nombre"));
                categorias.add(categoria);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
}
