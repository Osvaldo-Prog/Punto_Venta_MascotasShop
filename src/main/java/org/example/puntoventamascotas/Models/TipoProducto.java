package org.example.puntoventamascotas.Models;

public class TipoProducto {
    //Inicializacion de atributos/variables
    private int idTipoProducto;
    private String nombreTipoProducto;
    private String descripcion;
    private Area area;
    private CategoriaProducto categoriaProducto;

    //constructor vacio
    public TipoProducto(){}

    //constructor con parametros
    public TipoProducto(int idTipoProducto, String nombreTipoProducto, String descripcion, Area area, CategoriaProducto categoriaProducto) {
        this.idTipoProducto = idTipoProducto;
        this.nombreTipoProducto = nombreTipoProducto;
        this.descripcion = descripcion;
        this.area = area;
        this.categoriaProducto = categoriaProducto;
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
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    //get y set del objeto de categoria del producto
    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    //metodo toString
    @Override
    public String toString() {
        return "TipoProducto{" +
                "idTipoProducto=" + idTipoProducto +
                ", nombreTipoProducto='" + nombreTipoProducto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", area=" + area +
                ", categoriaProducto=" + categoriaProducto +
                '}';
    }
}
