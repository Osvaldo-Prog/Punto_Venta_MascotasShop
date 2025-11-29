package org.example.puntoventamascotas.Models;

public class Producto implements ItemCardInterface{
    //inicializacion de atributos/varables
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String imagen;
    private int idTipoProducto;

    //construcor vacio
    public Producto(){}

    //constructor con para actualizar
    public Producto(int idProducto, String nombre, String descripcion, double precio, int stock, int idTipoProducto, String imagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.idTipoProducto = idTipoProducto;
    }

    //constructor para que registre
    public Producto(String nombre, String descripcion, double precio, int stock, String imagen, int idTipoProducto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
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

    @Override
    public int getId() {
        return idProducto;
    }

    //get y set del nombre del producto
    @Override
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
    @Override
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

    //getter y setter de imagen

    @Override
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //get y set del objeto del tipo de producto al que corresponde

    public int getIdTipo() {
        return idTipoProducto;
    }
    public void setTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    //este metodo es sobre la interface y es para que detecte si es producto al igual uno para mascota, por eso el return con el nombre
    @Override
    public String getTipo() {
        return "Producto";
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
                ", imagen='" + imagen + '\'' +
                ", idTipoProducto=" + idTipoProducto +
                '}';
    }
}
