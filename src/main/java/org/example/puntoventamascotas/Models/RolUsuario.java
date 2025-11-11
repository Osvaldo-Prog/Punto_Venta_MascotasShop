package org.example.puntoventamascotas.Models;

public class RolUsuario {
    //inicializacion de atributos/variables
    private int idRol;
    private String nombreRol;
    private String descripcionRol;

    //constructor vacio
    public RolUsuario() {}

    //constructor con parametros
    public RolUsuario(int idRol, String nombreRol, String descripcionRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcionRol = descripcionRol;
    }

    //getters y setters

    //get y set de IdRol
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    //get y set de nombreRol
    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    //get y set de descripcion
    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    //metodo toString
    @Override
    public String toString() {
        return "RolUsuario{" +
                "idRol=" + idRol +
                ", nombreRol='" + nombreRol + '\'' +
                ", descripcionRol='" + descripcionRol + '\'' +
                '}';
    }
}
