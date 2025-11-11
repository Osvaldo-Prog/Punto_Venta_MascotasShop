package org.example.puntoventamascotas.Models;

public class DetalleVenta {
    //inicializacion de variables
    private Usuario usuario;
    private Venta venta;
    private Producto producto;
    private int idDetalleVenta;
    private int cantidad; //cantidad de producto despues de pagar
    private double precioUnitario;
    private double subTotal;

    //constructor vacio
    public DetalleVenta(){}

    //constructor con parametros
    public DetalleVenta(Usuario usuario, Venta venta, Producto producto, int idDetalleVenta, int cantidad, double precioUnitario, double subTotal) {
        this.usuario = usuario;
        this.venta = venta;
        this.producto = producto;
        this.idDetalleVenta = idDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
    }

    //GETTERS Y SETTERS

    //get y set del objeto de usuario
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //get y set del objeto de venta
    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    //get y set del objeto producto
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
                "usuario=" + usuario +
                ", venta=" + venta +
                ", producto=" + producto +
                ", idDetalleVenta=" + idDetalleVenta +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subTotal=" + subTotal +
                '}';
    }
}
