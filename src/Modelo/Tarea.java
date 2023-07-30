package Modelo;

public class Tarea {
    String id;
    String nombre;
    String prioridad;
    String tipo;
    String fechaLimite;
    String estado;

    public Tarea(String id, String nombre, String prioridad, String tipo, String fechaLimite) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.tipo = tipo;
        this.fechaLimite = fechaLimite;
    }

    public Tarea(String id, String nombre, String prioridad, String tipo, String fechaLimite, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.tipo = tipo;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
    }

    public Tarea(String id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
