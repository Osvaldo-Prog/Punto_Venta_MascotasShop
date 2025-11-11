package org.example.puntoventamascotas.Models;

public class MetodoPago {
    //inicializacion de los atributos/variables
    private int idMetodoPago;
    private String nombreMetodoPago;
    private String descripcion;

    //constructor vacio
    public MetodoPago(){}

    //constructor con param.
    public MetodoPago(int idMetodoPago, String nombreMetodoPago, String descripcion) {
        this.idMetodoPago = idMetodoPago;
        this.nombreMetodoPago = nombreMetodoPago;
        this.descripcion = descripcion;
    }

    //GETTERS Y SETTERS

    //get y set de id de metodo de pago
    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    //get y set del nombre del metodo de pago
    public String getNombreMetodoPago() {
        return nombreMetodoPago;
    }

    public void setNombreMetodoPago(String nombreMetodoPago) {
        this.nombreMetodoPago = nombreMetodoPago;
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
        return "MetodoPago{" +
                "idMetodoPago=" + idMetodoPago +
                ", nombreMetodoPago='" + nombreMetodoPago + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
