package org.example.puntoventamascotas.Models;

public class CategoriaProducto {
    //inicializacion de atributos/variables
    private int idCategoriaProducto;
    private String nombreCategoria;
    private String descripcion;

    //constructor vacio
    public CategoriaProducto(){}

    //constructor con parametros
    public CategoriaProducto(int idCategoriaProducto, String nombreCategoria, String descripcion) {
        this.idCategoriaProducto = idCategoriaProducto;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
    }

    //GETTERS Y SETTERS

    //get y set de id de la categoria
    public int getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    public void setIdCategoriaProducto(int idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    //get y set del nombre de la categoria
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    //get y set de la descripcion
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //metodo toString
    @Override
    public String toString() {
        return "CategoriaProducto{" +
                "idCategoriaProducto=" + idCategoriaProducto +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
