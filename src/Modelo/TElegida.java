package Modelo;

public class TElegida {
    int id;
    int usuario_id;
    String tarea_id;
    String nombre;
    String prioridad;
    String fechaLimite;
    String categoria;

    public TElegida(int id, String nombre, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
    }
    public TElegida(int id, int usuario_id, String tarea_id) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.tarea_id = tarea_id;
    }

    public TElegida(int usuario_id, String tarea_id) {
        this.usuario_id = usuario_id;
        this.tarea_id = tarea_id;
    }

    public TElegida(int id, String tarea_id, String nombre, String prioridad, String fechaLimite) {
        this.id = id;
        this.tarea_id = tarea_id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.fechaLimite = fechaLimite;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getTarea_id() {
        return tarea_id;
    }

    public void setTarea_id(String tarea_id) {
        this.tarea_id = tarea_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
