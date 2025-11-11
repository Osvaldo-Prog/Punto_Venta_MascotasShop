package org.example.puntoventamascotas.Models;

public class DetalleCarrito {
    //inicializacion de atributos/variables
    private Usuario usuario;
    private Carrito carrito;
    private Producto producto;
    private int idDetalleCarrito;
    private int cantidad; //cantidad de producto antes de pagar
    private double subTotal;

    //constructor vacio
    public DetalleCarrito(){}

    //constructor con parametros
    public DetalleCarrito(Usuario usuario, Carrito carrito, Producto producto, int idDetalleCarrito, int cantidad, double subTotal) {
        this.usuario = usuario;
        this.carrito = carrito;
        this.producto = producto;
        this.idDetalleCarrito = idDetalleCarrito;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    //GETTERS Y SETTERS

    //get y set del objeto usuario
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //get y set del objeto del carrito
    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    //get y set del objeto de producto
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
                "usuario=" + usuario +
                ", carrito=" + carrito +
                ", producto=" + producto +
                ", idDetalleCarrito=" + idDetalleCarrito +
                ", cantidad=" + cantidad +
                ", subTotal=" + subTotal +
                '}';
    }
}
