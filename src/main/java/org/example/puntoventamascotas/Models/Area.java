package org.example.puntoventamascotas.Models;

public class Area {
    //inicalizacion de variables/atributos
    private int idArea;
    private String nombreArea;
    private String descripcion;

    //constructor vacio
    public Area(){}

    //constructor con parametos
    public Area(int idArea, String nombreArea, String descripcion) {
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.descripcion = descripcion;
    }

    //GETTERS Y SETTERS=====================================================

    //GET Y SET DE IdArea
    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    //get y set de nombreArea
    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    //get y set de descripcion
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Metodo toString
    @Override
    public String toString() {
        return "Area{" +
                "idArea=" + idArea +
                ", nombreArea='" + nombreArea + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
