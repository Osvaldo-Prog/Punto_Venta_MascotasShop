package org.example.puntoventamascotas.Models;

public class DetalleVenta {
    //inicializacion de variables
    private int idVenta;
    private int idItem;
    private int idDetalleVenta;
    private int cantidad; //cantidad de producto despues de pagar
    private double precioUnitario;
    private double subTotal;

    //constructor vacio
    public DetalleVenta(){}

    //constructor con parametros

    public DetalleVenta(int idVenta, int idItem, int cantidad, double precioUnitario, double subTotal) {
        this.idVenta = idVenta;
        this.idItem = idItem;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
    }


    //GETTERS Y SETTERS


    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    //get y set del objeto de venta
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int venta) {
        this.idVenta = idVenta;
    }


    //get y set del id del detalle de venta
    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    //get y set de la cantidad
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    //get y set del precio unitario
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    //get y set del subtotal
    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    //metodo toString
    @Override
    public String toString() {
        return "DetalleVenta{" +
                ", venta=" + idVenta +
                ", idDetalleVenta=" + idDetalleVenta +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subTotal=" + subTotal +
                '}';
    }
}
