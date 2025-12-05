package org.example.puntoventamascotas.Models;

import java.util.ArrayList;
import java.util.List;

public class Carrito <T> {
    private List<T> items;

    //constructor para crear la lista donde se añadirán los items (productos y mascotas)
    public Carrito() {
        items = new ArrayList<>();
    }

    public void agregarItem(T item){
        this.items.add(item);
    }

    public void eliminarItem(T item){
        this.items.remove(item);
    }

    public List<T> obtenerItems(){
        return items;
    }

    public void eliminarItems(){
        items.clear();
    }

}
