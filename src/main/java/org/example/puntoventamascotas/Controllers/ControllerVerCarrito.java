package org.example.puntoventamascotas.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Mascota;
import org.example.puntoventamascotas.Models.Producto;
import org.example.puntoventamascotas.Models.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerVerCarrito {
    //Atributos de la vista
    @FXML FlowPane flowPane;
    @FXML Label lblTotal;


    //Atributos
    Usuario usuario;
    List<Mascota> listaMascotas;
    List<Producto> listaProductos;
    double totalPagar = 0;

    //metodo para crear el card de manera generica y se le pasa un objeto "item" que decide si es mascota o producto=====================================================================================
    public void crearCard(List<Mascota> mascotas, List<Producto> productos) throws IOException {
        this.listaMascotas = mascotas;
        this.listaProductos = productos;
        //limpiar todo antes ya que se reemplazaran entre mascota y proudctos
        flowPane.getChildren().clear();
        //dejar espaciado entre cards
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setPadding(new Insets(20));
        List<ItemCardInterface> itemCards = new ArrayList<>();
        for(Mascota mascota: mascotas){
            itemCards.add(mascota);
        }
        for(Producto producto: productos){
            itemCards.add(producto);
        }
        //for each para recorrer la lista obsvervable que ya tiene los elementos de la base de datos gracias al dao
        for (ItemCardInterface item : itemCards) {
            //un fxml loader obtener la vista de cards
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Card.fxml"));
            VBox card = loader.load();
            ControllerCard controllerCard = loader.getController();
            controllerCard.setData(item, "Carrito");
            flowPane.getChildren().add(card);
        }
    }

    public void calcularTotal(){
        /*Se recorren las listas del carrito que se añidieron y despues se le suma el total
        * a pagar para despues asignarselo al label*/
        for(Mascota mascota: listaMascotas){
            //el total a pagar se le añade el precio de la mascota pero multiplicado por cuantas veces se añidió
            totalPagar += mascota.getPrecio() * mascota.getCantidad();
        }
        for (Producto producto: listaProductos){
            totalPagar += producto.getPrecio() * producto.getCantidad();
        }
        lblTotal.setText("Total a pagar $" + totalPagar);
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    @FXML
    public void pagar() throws IOException {
        List<ItemCardInterface> itemCards = new ArrayList<>();
        for(Mascota mascota: listaMascotas){
            itemCards.add(mascota);
        }
        for(Producto producto: listaProductos){
            itemCards.add(producto);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FormularioDireccion&MetodoPago.fxml"));
        Parent root = loader.load();
        ControllerFormDireccionMetodoPago controllerFormDireccionMetodoPago = loader.getController();
        controllerFormDireccionMetodoPago.setUsuario(usuario);
        controllerFormDireccionMetodoPago.setData(totalPagar);
        controllerFormDireccionMetodoPago.setListaItems(itemCards);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
