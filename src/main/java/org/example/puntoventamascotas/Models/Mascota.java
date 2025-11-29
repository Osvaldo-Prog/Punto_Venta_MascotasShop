package org.example.puntoventamascotas.Models;

public class Mascota implements ItemCardInterface{
    //inicializacion de atributos/variables
    /*Misma situacion de atributo con FK = objeto*/
    private int idMascota;
    private String nombreMascota;
    private String descripcionMascota;
    private String cuidados;
    private double precio;
    private String imagen;
    private int idTipoMascota;

    //constructor vacio
    public Mascota(){}

    //constructor sin id para la inserción a la DB
    public Mascota(String nombreMascota, String descripcionMascota, String cuidados, double precio, String imagen, int idTipoMascota) {
        this.nombreMascota = nombreMascota;
        this.descripcionMascota = descripcionMascota;
        this.cuidados = cuidados;
        this.precio = precio;
        this.imagen = imagen;
        this.idTipoMascota = idTipoMascota;
    }

    //constructor con para.
    public Mascota(int idMascota, String nombreMascota, String descripcionMascota, String cuidados, double precio, int idTipoMascota, String imagen) {
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.descripcionMascota = descripcionMascota;
        this.cuidados = cuidados;
        this.precio = precio;
        this.imagen = imagen;
        this.idTipoMascota = idTipoMascota;


    }

    //GETTERS Y SETTERS

    //get y set de id de la mascota
    @Override
    public int getId() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    //get y set del nombre de la mascota
    @Override
    public String getNombre() {
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

    //get y set de precio

    @Override
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //getter y set de imagen

    @Override
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //get y set del de tipo de mascota

    public int getIdTipo() {
        return idTipoMascota;
    }

    public void setTipoMascota(int idTipoMascota) {
        this.idTipoMascota = idTipoMascota;
    }

    @Override
    public String getTipo() {
        return "Mascota";
    }



    //metodo toString

    @Override
    public String toString() {
        return "Mascota{" +
                "idMascota=" + idMascota +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", descripcionMascota='" + descripcionMascota + '\'' +
                ", cuidados='" + cuidados + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                ", idTipoMascota=" + idTipoMascota +
                '}';
    }
}
