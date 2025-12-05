package org.example.puntoventamascotas.Models;

public class VistaDetalleVenta {
    private int idVenta;
    private String nombreItem;
    private int cantidad;
    private double precioUnitario;
    private double subTotal;

    public VistaDetalleVenta() {
    }

    public VistaDetalleVenta(int idVenta, String nombreItem, int cantidad, double precioUnitario, double subTotal) {
        this.idVenta = idVenta;
        this.nombreItem = nombreItem;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
