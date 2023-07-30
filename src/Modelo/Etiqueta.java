package Modelo;

public class Etiqueta {
    int id;
    String estado;
    String prioridad;

    public Etiqueta(String prioridad) {
        this.prioridad = prioridad;
    }

    public Etiqueta(int id, String estado, String prioridad) {
        this.id = id;
        this.estado = estado;
        this.prioridad = prioridad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
