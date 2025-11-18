package org.example.puntoventamascotas.Models;

public class TipoProducto {
    //Inicializacion de atributos/variables
    private int idTipoProducto;
    private String nombreTipoProducto;
    private String descripcion;
    private int idArea;
    private int idCategoriaProducto;

    //constructor vacio
    public TipoProducto(){}

    //constructor con parametros
    public TipoProducto(int idTipoProducto, String nombreTipoProducto, String descripcion, int idArea, int idCategoriaProducto) {
        this.idTipoProducto = idTipoProducto;
        this.nombreTipoProducto = nombreTipoProducto;
        this.descripcion = descripcion;
        this.idArea = idArea;
        this.idCategoriaProducto = idCategoriaProducto;
    }

    //GETTERS Y SETTERS

    //get y set de id del tipo de producto
    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    //get y set del nombre de tipo de producto
    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setNombreTipoProducto(String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }

    //get y set de descripcion
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //get y set del objeto de area
    public int getArea() {
        return idArea;
    }

    public void setArea(int idArea) {
        this.idArea = idArea;
    }

    //get y set del objeto de categoria del producto
    public int getCategoriaProducto() {
        return idCategoriaProducto;
    }

    public void setCategoriaProducto(int idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    //metodo toString
    @Override
    public String toString() {
        return "TipoProducto{" +
                "idTipoProducto=" + idTipoProducto +
                ", nombreTipoProducto='" + nombreTipoProducto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", area=" + idArea +
                ", categoriaProducto=" + idCategoriaProducto +
                '}';
    }
}
