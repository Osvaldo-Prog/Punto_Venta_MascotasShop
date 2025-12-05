package org.example.puntoventamascotas.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Mascota;
import org.example.puntoventamascotas.Models.Producto;

import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

public class ControllerInformacionModelo {
    @FXML TextArea txtAreaInfo;
    private ItemCardInterface itemCard;
    private Producto producto;
    private Mascota mascota;/*
    ControllerProductos controllerProductos;
*/
    public ControllerInformacionModelo(){

    }
    /*public void setData(ItemCardInterface item){
        this.itemCard = item;
        txtAreaInfo.setText("Nombre: " + item.getNombre() + "\n");
        txtAreaInfo.setText("Descripción", item.get);
    }*/
    //Este metodo asiga na informacion al boton de informacion
    public void setDataProductoInfo(Producto producto) throws IOException {
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setText("Nombre: " + producto.getNombre() + "\n" +
        "Descripción: " + producto.getDescripcion() + "\n" +
        "Precio: $" + producto.getPrecio() + "\n" +
        "Stock: " + producto.getStock() + " disponibe(s)\n");
    }

    public void setDataMascotaInfo(Mascota  mascota) throws IOException {
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setText("Nombre: " + mascota.getNombre() + "\n"+
        "Cuidados: " + mascota.getCuidados() + "\n" +
        "Descripción: " + mascota.getDescripcionMascota() + "\n" +
        "Precio: $" + mascota.getPrecio());
    }
}
