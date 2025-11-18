package org.example.puntoventamascotas.Models;

public class Producto {
    //inicializacion de atributos/varables
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int idTipoProducto;

    //construcor vacio
    public Producto(){}

    //constructor con para.
    public Producto(int idProducto, String nombre, String descripcion, double precio, int stock, int idTipoProducto) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.idTipoProducto = idTipoProducto;
    }

    //GETTERS Y SETTERS

    //get y set del id del producto
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    //get y set del nombre del producto
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //get y set de la descripcion
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //get y set del precio
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //get y set del stock
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    //get y set del objeto del tipo de producto al que corresponde
    public int getTipoProducto() {
        return idTipoProducto;
    }

    public void setTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    //metodo toString
    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", tipoProducto=" + idTipoProducto +
                '}';
    }
}
