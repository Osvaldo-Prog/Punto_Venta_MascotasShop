package org.example.puntoventamascotas.Models;

public class Venta {
    //inicialiacion de atributos/variables
    private Usuario usuario;
    private MetodoPago metodoPago;
    private int idVenta;
    private String fechaVenta;
    private double total;

    //constructor vacio
    public Venta(){}

    //constructor con param.
    public Venta(Usuario usuario, MetodoPago metodoPago, int idVenta, String fechaVenta, double total) {
        this.usuario = usuario;
        this.metodoPago = metodoPago;
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    //GETTERS Y SETTERS

    //get y set del objeto usuario
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //get y set del objeto del metodo de pago
    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    //get y set del id de venta
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    //get y set de fecha de la venta
    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    //get y set del total
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    //metodo toString
    @Override
    public String toString() {
        return "Venta{" +
                "usuario=" + usuario +
                ", metodoPago=" + metodoPago +
                ", idVenta=" + idVenta +
                ", fechaVenta='" + fechaVenta + '\'' +
                ", total=" + total +
                '}';
    }
}
