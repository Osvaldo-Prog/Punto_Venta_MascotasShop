package org.example.puntoventamascotas.Models;

import java.time.LocalDateTime;

public class VistaVentas {
    private int idVenta;
    private String nombrePersonal;
    private String nombreMetodoPago;
    private int idDireccion;
    private LocalDateTime fechaVenta;
    private double total;

    public VistaVentas(){
    }

    public VistaVentas(int idVenta, String nombrePersonal, String nombreMetodoPago, int idDireccion, LocalDateTime fechaVenta, double total) {
        this.idVenta = idVenta;
        this.nombrePersonal = nombrePersonal;
        this.nombreMetodoPago = nombreMetodoPago;
        this.idDireccion = idDireccion;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombreUsuario) {
        this.nombrePersonal = nombreUsuario;
    }

    public String getNombreMetodoPago() {
        return nombreMetodoPago;
    }

    public void setNombreMetodoPago(String nombreMetodoPago) {
        this.nombreMetodoPago = nombreMetodoPago;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
