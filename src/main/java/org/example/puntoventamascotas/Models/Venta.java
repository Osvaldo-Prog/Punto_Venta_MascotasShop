package org.example.puntoventamascotas.Models;

import java.time.LocalDateTime;

public class Venta {
    //inicialiacion de atributos/variables
    private int idUsuario;
    private int idMetodoPago;
    private int idVenta;
    private int idDireccionEnvio;
    private LocalDateTime fechaVenta;
    private double total;

    //constructor vacio
    public Venta(){}

    //constructor con param.
    public Venta(int idUsuario, int idMetodoPago, int idDireccionEnvio, LocalDateTime fechaVenta, double total) {
        this.idUsuario = idUsuario;
        this.idMetodoPago = idMetodoPago;
        this.idDireccionEnvio = idDireccionEnvio;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    //GETTERS Y SETTERS


    public int getIdDireccionEnvio() {
        return idDireccionEnvio;
    }

    public void setIdDireccionEnvio(int idDireccionEnvio) {
        this.idDireccionEnvio = idDireccionEnvio;
    }

    //get y set del objeto usuario
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    //get y set del objeto del metodo de pago
    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
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
    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
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
