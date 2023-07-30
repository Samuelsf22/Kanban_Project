package Modelo;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Eventos {
    public void isLetter(KeyEvent e) {
        char caracter = e.getKeyChar();
        if (!Character.isLetter(caracter) && !(caracter == KeyEvent.VK_SPACE)) {
            e.consume();
        }
    }

    public void isDigit(KeyEvent e, int digitos) {
        char caracter = e.getKeyChar();
        if (!Character.isDigit(caracter)) {
            e.consume();
        }else if (((JTextField)e.getSource()).getText().length() >= digitos) {
            e.consume();
        }
    }
    public void isLetterOrDigit(KeyEvent e, int limite) {
        char caracter = e.getKeyChar();
        if (!Character.isLetterOrDigit(caracter) && !(caracter == KeyEvent.VK_SPACE)) {
            e.consume();
        }else if (((JTextField)e.getSource()).getText().length() >= limite) {
            e.consume();
        }
    }

    public void isUpperCaseOrDigit(KeyEvent e, int limite){
        char caracter = e.getKeyChar();
        if (!Character.isUpperCase(caracter) && !Character.isDigit(caracter)) {
            e.consume();
        }else if (((JTextField)e.getSource()).getText().length() >= limite) {
            e.consume();
        }
    }
}
