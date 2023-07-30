package Controlador;

import Modelo.*;
import Vista.VentanaAnadirEditar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class ControladorAnadir extends  JFrame implements ActionListener, KeyListener {
    private static VentanaAnadirEditar frmAnadirEditar = new VentanaAnadirEditar();
    private final ControladorTareas crtlTareas;
    private final TareaDAO tareaDAO;
    private final EtiquetaDAO etiquetaDAO;
    private final CategoriaDAO categoriaDAO;
    private final Eventos eventos;

    public ControladorAnadir(ControladorTareas crtlTareas, TareaDAO tareaDAO, VentanaAnadirEditar frmAnadirEditar, EtiquetaDAO etiquetaDAO, CategoriaDAO categoriaDAO, Eventos eventos) {
        this.crtlTareas = crtlTareas;
        this.tareaDAO = tareaDAO;
        this.etiquetaDAO = etiquetaDAO;
        this.categoriaDAO = categoriaDAO;
        this.eventos = eventos;

        ControladorAnadir.frmAnadirEditar = frmAnadirEditar;
        ControladorAnadir.frmAnadirEditar.btnAgregar.addActionListener(this);
        ControladorAnadir.frmAnadirEditar.btnCancelar.addActionListener(this);
        ControladorAnadir.frmAnadirEditar.btnEditar.addActionListener(this);

        ControladorAnadir.frmAnadirEditar.txtIdTarea.addKeyListener(this);
        ControladorAnadir.frmAnadirEditar.txtNombre.addKeyListener(this);
        verFormuIario();
    }
    public void verFormuIario() {
        setContentPane(frmAnadirEditar.LayoutPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//cierra la ventana pero no la ejecucion
        setUndecorated(true);
        setAlwaysOnTop(true);
        pack();
        setVisible(false);
        setLocationRelativeTo(null);

        frmAnadirEditar.txtIdTarea.setBorder(new LineBorder(Color.BLACK,0));
        frmAnadirEditar.txtNombre.setBorder(new LineBorder(Color.BLACK,0));
        frmAnadirEditar.txtFechaLimite.setBorder(new LineBorder(Color.BLACK,0));
        frmAnadirEditar.cbPrioridad.setBorder(new LineBorder(Color.BLACK,0));
        frmAnadirEditar.cbTipo.setBorder(new LineBorder(Color.BLACK,0));
        verPrioridad();
        verCategoria();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        crtlTareas.setVisible(true);
        if (e.getSource() == frmAnadirEditar.btnCancelar) {
            limpiar();
            dispose();
        }

        if (e.getSource() == frmAnadirEditar.btnAgregar){
            if (verificar()){
                var tareas = datosTXT();
                if (tareaDAO.anadirTarea(tareas, frmAnadirEditar.LayoutPrincipal)){
                    crtlTareas.actualizarTareas();
                    JOptionPane.showMessageDialog(frmAnadirEditar.LayoutPrincipal, "Tarea añadida", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(frmAnadirEditar.LayoutPrincipal, "No se pudo añadir la tarea", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == frmAnadirEditar.btnEditar){
            if (verificar()){
                var tareas = datosTXT();
                if (tareaDAO.actualizarTareas(tareas)){
                    crtlTareas.actualizarTareas();
                    JOptionPane.showMessageDialog(frmAnadirEditar.LayoutPrincipal, "Tarea actualizada", "Actualización", JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(frmAnadirEditar.LayoutPrincipal, "No se pudo actualizar la tarea", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean verificar(){
        String mensajeError = "";
        if (frmAnadirEditar.txtIdTarea.getText().length()<10){
            mensajeError += "Rellene el ID (10 dígitos)\n";
        }
        if (frmAnadirEditar.txtNombre.getText().isEmpty()){
            mensajeError += "Rellene el nombre de la tarea\n";
        }
        if (Objects.equals(frmAnadirEditar.cbTipo.getSelectedItem(), "Seleccionar")){
            mensajeError += "Seleccione un tipo\n";
        }
        if (Objects.equals(frmAnadirEditar.cbPrioridad.getSelectedItem(), "Seleccionar")){
            mensajeError += "Seleccione la prioridad\n";
        }
        if (frmAnadirEditar.txtFechaLimite.getText().isEmpty()){
            mensajeError += "Rellene la fecha limite";
        }


        if (mensajeError.isEmpty()){
            return true;
        }else {
            JOptionPane.showMessageDialog(frmAnadirEditar.LayoutPrincipal, mensajeError);
            return false;
        }
    }
    private Tarea datosTXT(){
        var id          = frmAnadirEditar.txtIdTarea.getText();
        var nombre      = frmAnadirEditar.txtNombre.getText();
        var prioridad   = (String) frmAnadirEditar.cbPrioridad.getSelectedItem();
        var tipo        = (String) frmAnadirEditar.cbTipo.getSelectedItem();
        var fecha       = frmAnadirEditar.txtFechaLimite.getText();
        return new Tarea(id, nombre, prioridad, tipo, fecha);
    }
    private void limpiar(){
        frmAnadirEditar.txtIdTarea.setText("");
        frmAnadirEditar.txtNombre.setText("");
        frmAnadirEditar.cbTipo.setSelectedIndex(0);
        frmAnadirEditar.cbPrioridad.setSelectedIndex(0);
        frmAnadirEditar.txtFechaLimite.setText("");
    }
    private void verPrioridad(){
        var prioridades = etiquetaDAO.verEtiquetas();
        frmAnadirEditar.cbPrioridad.addItem("Seleccionar");
        for (Etiqueta prioridad: prioridades) {
            frmAnadirEditar.cbPrioridad.addItem(prioridad.getPrioridad());
        }
    }
    private void verCategoria(){
        var categorias = categoriaDAO.verCategorias();
        frmAnadirEditar.cbTipo.addItem("Seleccionar");
        for (Categoria etiqueta: categorias) {
            frmAnadirEditar.cbTipo.addItem(etiqueta.getNombre());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == frmAnadirEditar.txtNombre) eventos.isLetterOrDigit(e,255);
        if (e.getSource() == frmAnadirEditar.txtIdTarea) eventos.isUpperCaseOrDigit(e,10);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
