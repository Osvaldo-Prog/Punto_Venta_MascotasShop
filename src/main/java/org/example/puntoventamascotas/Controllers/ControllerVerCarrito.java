package org.example.puntoventamascotas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import org.example.puntoventamascotas.Util.MensajesVista;

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
    List<ItemCardInterface> itemCards = new ArrayList<>();

    //metodo para cerrar la ventana actual==========================================================================================
    public void cerrarVenatanActual(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    //metodo para crear el card de manera generica y se le pasa un objeto "item" que decide si es mascota o producto=====================================================================================
    public void crearCard(List<Mascota> mascotas, List<Producto> productos) throws IOException {
        this.listaMascotas = mascotas;
        this.listaProductos = productos;
        //limpiar todo antes ya que se reemplazaran entre mascota y proudctos
        flowPane.getChildren().clear();
        itemCards.clear();
        //dejar espaciado entre cards
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setPadding(new Insets(20));
        for(Mascota mascota: mascotas){
            itemCards.add(mascota);
        }
        for(Producto producto: productos){
            itemCards.add(producto);
        }
        //for each para recorrer la lista obsvervable que ya tiene los elementos de la base de datos gracias al dao
        //Este for crea los cards de la lista del carrito
        for (ItemCardInterface item : itemCards) {
            //un fxml loader obtener la vista de cards
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Card.fxml"));
            VBox card = loader.load();
            ControllerCard controllerCard = loader.getController();
            controllerCard.setOnEliminarDeCarrito(itemCard -> {
                itemCards.removeIf(itemRemove -> itemRemove.getId() == itemCard.getId());
                calcularTotal();
                List<Mascota> listaMascotas = new ArrayList<>();
                List<Producto> listaProductos = new ArrayList<>();
                for(ItemCardInterface itemsTotales : itemCards){
                    if(itemsTotales.getTipo().equals("Mascota")){
                        listaMascotas.add((Mascota) itemsTotales);
                    }else if (itemsTotales.getTipo().equals("Producto")){
                        listaProductos.add((Producto) itemsTotales);
                    }
                }
                try {
                    crearCard(listaMascotas, listaProductos);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                MensajesVista.mostrarMensajeTemporal("Borrado", "Item borrado del carrito", 650);
            });
            controllerCard.setData(item, "Carrito");
            flowPane.getChildren().add(card);
        }
    }

    public void calcularTotal(){
        /*Se recorren las listas del carrito que se añidieron y despues se le suma el total
        * a pagar para despues asignarselo al label*/
        totalPagar = 0;
        for(ItemCardInterface itemsTotales : itemCards){
            if(itemsTotales.getTipo().equals("Mascota")){
                Mascota mascota = (Mascota) itemsTotales;
                totalPagar += mascota.getPrecio() * mascota.getCantidad();
            }else if (itemsTotales.getTipo().equals("Producto")){
                Producto producto = (Producto) itemsTotales;
                totalPagar += producto.getPrecio() * producto.getCantidad();
            }
        }
        lblTotal.setText("Total a pagar $" + totalPagar);
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    @FXML
    public void pagar() throws IOException {
        for(Mascota mascota: listaMascotas){
            this.itemCards.add(mascota);
        }
        for(Producto producto: listaProductos){
            this.itemCards.add(producto);
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
