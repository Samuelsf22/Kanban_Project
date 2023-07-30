package Modelo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TareaDAO {
    public boolean anadirTarea (Tarea tareas, JPanel panel){
        try {
            Connection conn = Conexion.getConexion();
            String checkSQL = "select id FROM tareas WHERE id = ?;";
            var check = conn.prepareStatement(checkSQL);
            check.setString(1,tareas.getId());
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(panel,"EL id ya existe", "Error", JOptionPane.ERROR_MESSAGE );
                return false;
            }else {
                String sql = "INSERT INTO tareas (id, nombre, etiqueta_id, categorias_id, fecha_limite, estado) " +
                        "SELECT ?, ?, e.id, c.id, ?, ? " +
                        "FROM etiquetas e " +
                        "JOIN categorias c ON c.nombre = ? " +
                        "WHERE e.prioridad = ?;";
                var pstms = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstms.setString(1,tareas.getId());
                pstms.setString(2,tareas.getNombre());
                pstms.setString(3,tareas.getFechaLimite());
                pstms.setString(4,"PENDIENTE");
                pstms.setString(5,tareas.getTipo());
                pstms.setString(6,tareas.getPrioridad());
                pstms.executeUpdate();
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Tarea> verTareas () {
        Connection conn = Conexion.getConexion();
        var tareas = new ArrayList<Tarea>();
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select t.id, t.nombre, e.prioridad, c.nombre, t.fecha_limite " +
                    "from tareas t " +
                    "inner join categorias c on c.id = t.categorias_id " +
                    "inner join etiquetas e on e.id = t.etiqueta_id " +
                    "where t.estado = 'PENDIENTE';");
            while (rs.next()) {
                var tarea = new Tarea(rs.getString("t.id"), rs.getString("t.nombre"),
                        rs.getString("e.prioridad"), rs.getString("c.nombre"),
                        rs.getString("t.fecha_limite"));

                tareas.add(tarea);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tareas;
    }
    public boolean actualizarTareas (Tarea tareas) {
        Connection conn = Conexion.getConexion();
        String sql = "UPDATE tareas " +
                "SET nombre = ?," +
                "etiqueta_id = (SELECT id FROM etiquetas WHERE prioridad = ?), " +
                "categorias_id = (SELECT id FROM categorias WHERE nombre = ?), " +
                "fecha_limite = ?, estado = ? " +
                "WHERE id = ?;";
        try {
            var pstms = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstms.setString(1, tareas.getNombre());
            pstms.setString(2, tareas.getPrioridad());
            pstms.setString(3, tareas.getTipo());
            pstms.setString(4, tareas.getFechaLimite());
            pstms.setString(5,"PENDIENTE");
            pstms.setString(6, tareas.getId());
            pstms.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean borrarTareas (String id){
        Connection conn = Conexion.getConexion();
        String sql = "delete from tareas where id = ? ;";
        try {
            var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,id);
            pstmt.executeUpdate();
            conn.close();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean elegirTarea (TElegida tElegida){
        try {
            Connection conn = Conexion.getConexion();
            String sql = "INSERT INTO tareaElegida (usuario_id, tarea_id) values (?,?);";
            var pstms = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstms.setInt(1,tElegida.getUsuario_id());
            pstms.setString(2,tElegida.getTarea_id());
            pstms.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean actualizarEstado (Tarea tarea) {
        Connection conn = Conexion.getConexion();
        String sql = "UPDATE tareas SET estado = ? WHERE id = ?;";
        try {
            var pstms = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstms.setString(1, tarea.getEstado());
            pstms.setString(2, tarea.getId());
            pstms.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean quitarTarea (int id){
        Connection conn = Conexion.getConexion();
        String sql = "delete from tareaElegida where id= ?;";
        try {
            var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            conn.close();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<TElegida> verTareasElegidas (int usuario) {
        Connection conn = Conexion.getConexion();
        var tElegidas = new ArrayList<TElegida>();
        try {
            var pstms = conn.prepareStatement("select te.id, t.id, t.nombre, e.prioridad, t.fecha_limite " +
                    "from tareaElegida te " +
                    "inner join tareas t on t.id = te.tarea_id " +
                    "inner join etiquetas e on t.etiqueta_id = e.id " +
                    "where usuario_id = ? and t.estado = ?;");
            pstms.setInt(1,usuario);
            pstms.setString(2,"ELEGIDO");
            ResultSet rs = pstms.executeQuery();
            while (rs.next()) {
                var tElegida = new TElegida(rs.getInt("te.id"), rs.getString("t.id"),rs.getString("t.nombre"),
                        rs.getString("e.prioridad"), rs.getString("t.fecha_limite"));
                tElegidas.add(tElegida);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tElegidas;
    }
    public ArrayList<TElegida> verTareasFinalizadas (int usuario) {
        Connection conn = Conexion.getConexion();
        var tElegidas = new ArrayList<TElegida>();
        try {
            var pstms = conn.prepareStatement("select te.id, t.nombre, c.nombre " +
                    "from tareaElegida te " +
                    "inner join tareas t on t.id = te.tarea_id " +
                    "inner join categorias c ON t.categorias_id = c.id " +
                    "where usuario_id = ? and t.estado = ?;");
            pstms.setInt(1,usuario);
            pstms.setString(2,"FINALIZADO");
            ResultSet rs = pstms.executeQuery();
            while (rs.next()) {
                var tElegida = new TElegida(rs.getInt("te.id"), rs.getString("t.nombre"),
                        rs.getString("c.nombre"));
                tElegidas.add(tElegida);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tElegidas;
    }
}
