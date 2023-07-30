package Controlador;

import Modelo.*;
import Vista.VentanaAnadirEditar;
import Vista.VentanaLogin;
import Vista.VentanaTareas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTareas extends JFrame implements ActionListener {
    private static VentanaTareas frmTareas = new VentanaTareas();
    private final UsuarioDAO usuarioDAO;

    //ventanaAñadir
    private final TareaDAO tareasDAO = new TareaDAO() ;
    private final VentanaAnadirEditar frmAnadirEditar = new VentanaAnadirEditar();
    private final EtiquetaDAO etiquetaDAO = new EtiquetaDAO();
    private final CategoriaDAO categoriaDAO= new CategoriaDAO();
    private final Eventos eventos = new Eventos();
    private final ControladorAnadir crtlAnadir = new ControladorAnadir(this, tareasDAO, frmAnadirEditar, etiquetaDAO, categoriaDAO, eventos);

    private int id_cliente;

    public ControladorTareas(UsuarioDAO usuarioDAO, VentanaTareas frmTareas) {
        this.usuarioDAO = usuarioDAO;
        ControladorTareas.frmTareas = frmTareas;
        ControladorTareas.frmTareas.btnAñadir.addActionListener(this);
        ControladorTareas.frmTareas.btnEditar.addActionListener(this);
        ControladorTareas.frmTareas.btnEliminar.addActionListener(this);
        ControladorTareas.frmTareas.btnPrincipal.addActionListener(this);
        ControladorTareas.frmTareas.btnEscogidas.addActionListener(this);
        ControladorTareas.frmTareas.btnFinalizadas.addActionListener(this);
        ControladorTareas.frmTareas.btnSingOut.addActionListener(this);

        ControladorTareas.frmTareas.btnElegir.addActionListener(this);
        ControladorTareas.frmTareas.btnQuitarElegido.addActionListener(this);
        ControladorTareas.frmTareas.btnCompletarElegido.addActionListener(this);

        actualizarTareas();
        cambioVentanas(true, false, false);
        verFormuIario();
    }
    public void verFormuIario() {
        setContentPane(frmTareas.LayoutPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//cierra la ejecucion
        //setUndecorated(true);//para que no muestre el title bar
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
    public void setUsuario(String correo){
        Usuario cliente = usuarioDAO.verUsuario(correo);
        id_cliente = cliente.getId();
        //
        actualizarSeleccion(id_cliente);
        actualizarFinalizados(id_cliente);
    }
    public void notAdmin(){
        frmTareas.btnAñadir.setVisible(false);
        frmTareas.btnEliminar.setVisible(false);
        frmTareas.btnEditar.setVisible(false);
    }
    public void isAdmin(){
        frmTareas.btnElegir.setVisible(false);
        frmTareas.btnEscogidas.setVisible(false);
        frmTareas.btnFinalizadas.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmTareas.btnAñadir){
            frmAnadirEditar.btnEditar.setVisible(false);
            frmAnadirEditar.btnAgregar.setVisible(true);
            frmAnadirEditar.txtIdTarea.setEditable(true);
            crtlAnadir.setVisible(true);
        }
        if (e.getSource() == frmTareas.btnEditar){
            int resgistro = frmTareas.tableTareas.getSelectedRow();//el resgistro seleccionado
            if (resgistro>-1){
                frmAnadirEditar.txtIdTarea.setText(frmTareas.tableTareas.getValueAt(resgistro, 0).toString());
                frmAnadirEditar.txtNombre.setText(frmTareas.tableTareas.getValueAt(resgistro, 1).toString());
                frmAnadirEditar.cbPrioridad.setSelectedItem(frmTareas.tableTareas.getValueAt(resgistro, 2).toString());
                frmAnadirEditar.cbTipo.setSelectedItem(frmTareas.tableTareas.getValueAt(resgistro, 3).toString());
                frmAnadirEditar.txtFechaLimite.setText(frmTareas.tableTareas.getValueAt(resgistro, 4).toString());
                frmAnadirEditar.txtIdTarea.setEditable(false);
                frmAnadirEditar.btnEditar.setVisible(true);
                frmAnadirEditar.btnAgregar.setVisible(false);
                crtlAnadir.setVisible(true);
            }else {
                JOptionPane.showMessageDialog(frmTareas.LayoutPrincipal, "Seleccione una fila a editar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == frmTareas.btnEliminar){
            int resgistro = frmTareas.tableTareas.getSelectedRow();//el resgistro seleccionado
            if (resgistro>-1){
                var id = frmTareas.tableTareas.getValueAt(resgistro, 0).toString();
                if (tareasDAO.borrarTareas(id)){
                    actualizarTareas();
                }
            }else {
                JOptionPane.showMessageDialog(frmTareas.LayoutPrincipal, "Seleccione una tarea a eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == frmTareas.btnSingOut){
            var ventanaLogin = new VentanaLogin();
            new ControladorLogin(usuarioDAO, ventanaLogin).setVisible(true);
            dispose();
        }
        if (e.getSource() == frmTareas.btnPrincipal){
            cambioVentanas(true, false, false);
        }
        if (e.getSource() == frmTareas.btnEscogidas){
            cambioVentanas(false, true, false);
        }
        if (e.getSource() == frmTareas.btnFinalizadas){
            cambioVentanas(false, false, true);
        }
        if (e.getSource() == frmTareas.btnElegir){
            int resgistro = frmTareas.tableTareas.getSelectedRow();//el resgistro seleccionado
            if (resgistro>-1){
                var id = frmTareas.tableTareas.getValueAt(resgistro, 0).toString();
                var tElegida = new TElegida(id_cliente, id);
                var tarea = new Tarea(id, "ELEGIDO");
                if (tareasDAO.elegirTarea(tElegida)){
                    tareasDAO.actualizarEstado(tarea);
                    actualizarSeleccion(id_cliente);
                    actualizarTareas();

                }
            }else {
                JOptionPane.showMessageDialog(frmTareas.LayoutPrincipal, "Seleccione una tarea", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == frmTareas.btnQuitarElegido){
            int resgistro = frmTareas.tableEscogidas.getSelectedRow();//el resgistro seleccionado
            if (resgistro>-1){
                var id = Integer.parseInt(frmTareas.tableEscogidas.getValueAt(resgistro, 0).toString());
                var id_tarea = frmTareas.tableEscogidas.getValueAt(resgistro, 1).toString();
                var tarea = new Tarea(id_tarea, "PENDIENTE");
                if (tareasDAO.quitarTarea(id)){
                    tareasDAO.actualizarEstado(tarea);
                    actualizarSeleccion(id_cliente);
                    actualizarTareas();
                }
            }else {
                JOptionPane.showMessageDialog(frmTareas.LayoutPrincipal, "Seleccione una tarea a quitar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == frmTareas.btnCompletarElegido){
            int resgistro = frmTareas.tableEscogidas.getSelectedRow();//el resgistro seleccionado
            if (resgistro>-1){
                var id_tarea = frmTareas.tableEscogidas.getValueAt(resgistro, 1).toString();
                var tarea = new Tarea(id_tarea, "FINALIZADO");
                if (tareasDAO.actualizarEstado(tarea)){
                    actualizarSeleccion(id_cliente);
                    actualizarFinalizados(id_cliente);
                    actualizarTareas();
                }
            }else {
                JOptionPane.showMessageDialog(frmTareas.LayoutPrincipal, "Seleccione una tarea", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public void actualizarTareas(){
        var tareas = tareasDAO.verTareas();
        var modT = new DefaultTableModel(new String[]{"ID","Nombre","Prioridad","Categoría","Fecha Limite"}, tareas.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < tareas.size(); i++) {
            var registerPizza = tareas.get(i);
            modT.setValueAt(registerPizza.getId(),i,0);
            modT.setValueAt(registerPizza.getNombre(),i,1);
            modT.setValueAt(registerPizza.getPrioridad(),i,2);
            modT.setValueAt(registerPizza.getTipo(),i,3);
            modT.setValueAt(registerPizza.getFechaLimite(),i,4);

        }
        frmTareas.tableTareas.setModel(modT);

        Tablas c = new Tablas();
        c.colorResaltado( new Color(12, 97, 225));
        for (int i = 0; i <  frmTareas.tableTareas.getColumnCount(); i++) {
            frmTareas.tableTareas.getColumnModel().getColumn(i).setCellRenderer(c);
        }
    }
    private void actualizarSeleccion(int id_cliente) {
        var tElegidas = tareasDAO.verTareasElegidas(id_cliente);
        var modT = new DefaultTableModel(new String[]{"ID", "Tarea ID","Prioridad", "Nombre", "Fecha Limite"}, tElegidas.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < tElegidas.size(); i++) {
            var tElegida = tElegidas.get(i);
            modT.setValueAt(tElegida.getId(),i,0);
            modT.setValueAt(tElegida.getTarea_id(),i,1);
            modT.setValueAt(tElegida.getPrioridad(),i,2);
            modT.setValueAt(tElegida.getNombre(),i,3);
            modT.setValueAt(tElegida.getFechaLimite(),i,4);

        }
        frmTareas.tableEscogidas.setModel(modT);
        //para pintar el resaltado, y las filas
        Tablas c = new Tablas();
        c.colorResaltado( new Color(16, 70, 210));
        for (int i = 0; i <  frmTareas.tableEscogidas.getColumnCount(); i++) {
            frmTareas.tableEscogidas.getColumnModel().getColumn(i).setCellRenderer(c);
        }
    }
    private void actualizarFinalizados(int id_cliente) {
        var tElegidas = tareasDAO.verTareasFinalizadas(id_cliente);
        var modT = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoria"}, tElegidas.size()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < tElegidas.size(); i++) {
            var tElegida = tElegidas.get(i);
            modT.setValueAt(tElegida.getId(),i,0);
            modT.setValueAt(tElegida.getNombre(),i,1);
            modT.setValueAt(tElegida.getCategoria(),i,2);
        }
        frmTareas.tableFinalizadas.setModel(modT);
        //para pintar el resaltado, y las filas
        Tablas c = new Tablas();
        c.colorResaltado( new Color(16, 70, 210));
        for (int i = 0; i < frmTareas.tableFinalizadas.getColumnCount(); i++) {
            frmTareas.tableFinalizadas.getColumnModel().getColumn(i).setCellRenderer(c);
        }
    }
    public void cambioVentanas(boolean tableroKB, boolean escogidas, boolean finalizadas){
        frmTareas.LayoutTableroKB.setVisible(tableroKB);
        frmTareas.LayoutEscogidas.setVisible(escogidas);
        frmTareas.LayoutFinalizadas.setVisible(finalizadas);
    }
}
