package org.example.puntoventamascotas.Models;

public class DetalleCarrito {
    //inicializacion de atributos/variables
    private int idUsuario;
    private int idCarrito;
    private int idProducto;
    private int idDetalleCarrito;
    private int cantidad; //cantidad de producto antes de pagar
    private double subTotal;

    //constructor vacio
    public DetalleCarrito(){}

    //constructor con parametros
    public DetalleCarrito(int idUsuario, int idCarrito, int idProducto, int idDetalleCarrito, int cantidad, double subTotal) {
        this.idUsuario = idUsuario;
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.idDetalleCarrito = idDetalleCarrito;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    //GETTERS Y SETTERS

    //get y set del objeto usuario
    public int getUsuario() {
        return idUsuario;
    }

    public void setUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    //get y set del objeto del carrito
    public int getCarrito() {
        return idCarrito;
    }

    public void setCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    //get y set del objeto de producto
    public int getProducto() {
        return idProducto;
    }

    public void setProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    //get y set del id del detalle del carrito
    public int getIdDetalleCarrito() {
        return idDetalleCarrito;
    }

    public void setIdDetalleCarrito(int idDetalleCarrito) {
        this.idDetalleCarrito = idDetalleCarrito;
    }

    //get y set de la cantidad
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
        return "DetalleCarrito{" +
                "usuario=" + idUsuario +
                ", carrito=" + idCarrito +
                ", producto=" + idProducto +
                ", idDetalleCarrito=" + idDetalleCarrito +
                ", cantidad=" + cantidad +
                ", subTotal=" + subTotal +
                '}';
    }
}
