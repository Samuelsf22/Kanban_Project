package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Tablas extends DefaultTableCellRenderer {
    private Color color = null;
    public void colorResaltado(Color color) {
        this.color = color;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(SwingConstants.CENTER);//para centrar los datos de las columnas
        if (isSelected){//cuando selecciona una fila
            setBackground(color);
            setForeground(new Color(215, 215, 215));
        }else {
            switch (table.getValueAt(row, 2).toString()) {
                case "ALTA" -> {
                    setBackground(new Color(255, 174, 174));
                    setForeground(Color.BLACK);
                }
                case "MEDIA" -> {
                    setBackground(new Color(255, 226, 158));
                    setForeground(Color.BLACK);
                }
                case "BAJA" -> {
                    setBackground(new Color(195, 255, 199));
                    setForeground(Color.BLACK);
                }
                default -> {
                    setBackground(Color.white);
                    setForeground(Color.black);
                }
            }
        }

        return this;
    }
}
