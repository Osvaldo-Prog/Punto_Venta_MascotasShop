package org.example.puntoventamascotas.Models;

public class Mascota {
    //inicializacion de atributos/variables
    /*Misma situacion de atributo con FK = objeto*/
    private int idMascota;
    private String nombreMascota;
    private String descripcionMascota;
    private String cuidados;
    private int idTipoMascota;

    //constructor vacio
    public Mascota(){}

    //constructor con para.
    public Mascota(int idMascota, String nombreMascota, String descripcionMascota, String cuidados, int idTipoMascota) {
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.descripcionMascota = descripcionMascota;
        this.cuidados = cuidados;
        this.idTipoMascota = idTipoMascota;
    }

    //GETTERS Y SETTERS

    //get y set de id de la mascota
    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    //get y set del nombre de la mascota
    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    //get y set de la descripcion
    public String getDescripcionMascota() {
        return descripcionMascota;
    }

    public void setDescripcionMascota(String descripcionMascota) {
        this.descripcionMascota = descripcionMascota;
    }

    //get y set de los cuidados
    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    //get y set del objeto de tipo de mascota
    public int getTipoMascota() {
        return idTipoMascota;
    }

    public void setTipoMascota(int idTipoMascota) {
        this.idTipoMascota = idTipoMascota;
    }

    //metodo toString
    @Override
    public String toString() {
        return "Mascota{" +
                "idMascota=" + idMascota +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", descripcionMascota='" + descripcionMascota + '\'' +
                ", cuidados='" + cuidados + '\'' +
                ", tipoMascota=" + idTipoMascota +
                '}';
    }
}
