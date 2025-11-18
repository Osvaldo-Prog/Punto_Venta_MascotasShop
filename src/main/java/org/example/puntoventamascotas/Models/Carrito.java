package org.example.puntoventamascotas.Models;

public class Carrito {
    //inicializacion de atributos/variables
    private int idUsuario;
    private int idCarrito;
    private String fechaCreacion;
    private boolean estado;

    //constructor vacio
    public Carrito(){}

    //constructor con parametros
    public Carrito(int idUsuario, int idCarrito, String fechaCreacion,  boolean estado) {
        this.idUsuario = idUsuario;
        this.idCarrito = idCarrito;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    //Getters y setters

    //get y set del objeto de usuario
    public int getUsuario() {
        return idUsuario;
    }

    public void setUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    //get y set del id del carrito
    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    //get y set de la fecha de creacion
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    //get y set del estado del carrito
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    //metodo toString
    @Override
    public String toString() {
        return "Carrito{" +
                "usuario=" + idUsuario +
                ", idCarrito=" + idCarrito +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", estado=" + estado +
                '}';
    }
}
