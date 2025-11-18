package org.example.puntoventamascotas.Models;

public class Venta {
    //inicialiacion de atributos/variables
    private int idUsuario;
    private int idMetodoPago;
    private int idVenta;
    private String fechaVenta;
    private double total;

    //constructor vacio
    public Venta(){}

    //constructor con param.
    public Venta(int idUsuario, int idMetodoPago, int idVenta, String fechaVenta, double total) {
        this.idUsuario = idUsuario;
        this.idMetodoPago = idMetodoPago;
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    //GETTERS Y SETTERS

    //get y set del objeto usuario
    public int getUsuario() {
        return idUsuario;
    }

    public void setUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    //get y set del objeto del metodo de pago
    public int getMetodoPago() {
        return idMetodoPago;
    }

    public void setMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
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
                "usuario=" + idUsuario +
                ", metodoPago=" + idMetodoPago +
                ", idVenta=" + idVenta +
                ", fechaVenta='" + fechaVenta + '\'' +
                ", total=" + total +
                '}';
    }
}
