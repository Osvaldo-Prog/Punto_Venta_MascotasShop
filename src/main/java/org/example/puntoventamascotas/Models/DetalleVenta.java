package org.example.puntoventamascotas.Models;

public class DetalleVenta {
    //inicializacion de variables
    private int idUsuario;
    private int idMetodoPago;
    private int idVenta;
    private int idProducto;
    private int idDetalleVenta;
    private int cantidad; //cantidad de producto despues de pagar
    private double precioUnitario;
    private double subTotal;

    //constructor vacio
    public DetalleVenta(){}

    //constructor con parametros
    public DetalleVenta(int idUsuario, int idMetodoPago, int idVenta, int idProducto, int idDetalleVenta, int cantidad, double precioUnitario, double subTotal) {
        this.idUsuario = idUsuario;
        this.idMetodoPago = idMetodoPago;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.idDetalleVenta = idDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
    }

    //GETTERS Y SETTERS

    //get y set del objeto de usuario
    public int getUsuario() {
        return idUsuario;
    }

    public void setUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    //get y set del objeto de venta
    public int getVenta() {
        return idVenta;
    }

    public void setVenta(int venta) {
        this.idVenta = idVenta;
    }

    //get y set del objeto producto
    public int getProducto() {
        return idProducto;
    }

    public void setProducto(int producto) {
        this.idProducto = idProducto;
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
                "usuario=" + idUsuario +
                "Metodo de pago= " + idMetodoPago +
                ", venta=" + idVenta +
                ", producto=" + idProducto +
                ", idDetalleVenta=" + idDetalleVenta +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subTotal=" + subTotal +
                '}';
    }
}
