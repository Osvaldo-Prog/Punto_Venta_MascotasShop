package org.example.puntoventamascotas.Models;

public class Item {
    private int idItem;
    private String nombre;
    private int idProducto;
    private int idMascota;
    private String tipo;

    public Item(String nombre, int idProducto, int idMascota, String tipo) {
        this.nombre = nombre;
        this.idProducto = idProducto;
        this.idMascota = idMascota;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
