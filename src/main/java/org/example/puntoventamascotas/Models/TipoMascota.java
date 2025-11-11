package org.example.puntoventamascotas.Models;

public class TipoMascota {
    /*Inicializacion de atributos/variables
    * en este caso tiene como FK el area entonces se pasa un objeto de tipo area
    * dando a entender que es la FK*/
    private int idTipoMascota;
    private String nombreTipoMascota;
    private String descripcion;
    private Area area;

    //constructor vacio
    public TipoMascota(){}

    //constrcutor con parametros
    public TipoMascota(int idTipoMascota,  String nombreTipoMascota, String descripcion, Area area){
        this.idTipoMascota = idTipoMascota;
        this.nombreTipoMascota = nombreTipoMascota;
        this.descripcion = descripcion;
        this.area = area;
    }

    //GETTERS Y SETTERS

    //get y set de idTipoMascota
    public int getIdTipoMascota() {
        return idTipoMascota;
    }

    public void setIdTipoMascota(int idTipoMascota) {
        this.idTipoMascota = idTipoMascota;
    }

    //get y set de nombre de tipo de mascota
    public String getNombreTipoMascota() {
        return nombreTipoMascota;
    }

    public void setNombreTipoMascota(String nombreTipoMascota) {
        this.nombreTipoMascota = nombreTipoMascota;
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

    //metodo toString
    @Override
    public String toString() {
        return "TipoMascota{" +
                "idTipoMascota=" + idTipoMascota +
                ", nombreTipoMascota='" + nombreTipoMascota + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", area=" + area +
                '}';
    }
}
