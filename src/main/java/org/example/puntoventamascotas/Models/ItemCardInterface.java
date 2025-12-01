package org.example.puntoventamascotas.Models;

//interface para hacerlas genericas
public interface ItemCardInterface {
    int getId();
    String getNombre();
    double getPrecio();
    String getImagen();
    //para saber si es mascota o producto
    String getTipo();
}
