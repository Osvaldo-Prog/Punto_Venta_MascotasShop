package org.example.puntoventamascotas.Controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.MascotaDAO;
import org.example.puntoventamascotas.DAO.ProductosDAO;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Mascota;
import org.example.puntoventamascotas.Models.Producto;

import java.io.IOException;
import java.util.List;

/* llegar a gc2*/


public class ControladorLoginCliente {
    MascotaDAO mascotaDAO;
    ProductosDAO productosDAO;
    String tipoCategoria;
    String tipoArea;

    //inicializacion de los nodos de fxml
    @FXML private Label labelPrecioBeagle;
    @FXML private ComboBox<String> comboBoxMasc_Prod;
    @FXML private ComboBox<String> comboBoxTipoMasc_Prod;
    @FXML private FlowPane flowPaneCard;
    @FXML ScrollPane scrollPaneCards;
    @FXML ScrollPane scrollPaneMenuCliente;
    @FXML AnchorPane anchorPaneClienteMenu;

    public ControladorLoginCliente() {
        this.mascotaDAO = new MascotaDAO(ConexionMsql.getConnection());
        this.productosDAO = new ProductosDAO(ConexionMsql.getConnection());
    }


    //lo que este en este metodo se cargará una vez se carge el fxml loader=====================================================================================
    @FXML
    public void initialize() {
        comboBoxMasc_Prod.getItems().addAll("Mascotas",
                "Productos");
        comboBoxTipoMasc_Prod.getItems().addAll("Perros", "Gatos", "Aves", "Peces");

        /*Aqui se crea un listener con observable para cachar lo que tenga el combobox en tiempo real de la categoria*/
        comboBoxMasc_Prod.valueProperty().addListener((observable, oldValue, newValue) -> {
            tipoCategoria = newValue.toString();
            if (tipoCategoria.equals("Mascotas")){
                try {
                    ObservableList<Mascota> mascotasListaObs = FXCollections.observableArrayList(mascotaDAO.listarMascotasByArea(tipoArea));
                    crearCard(mascotasListaObs);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //lo mismo que para mascotas
            } else if (tipoCategoria.equals("Productos")) {
                try {
                    ObservableList<Producto> productosListaObs = FXCollections.observableArrayList(productosDAO.listarProductosByArea(tipoArea));
                    crearCard(productosListaObs);
                } catch (IOException e) {
                }
            }
        });

        /*Aqui es practicamente lo mismo que el observavble con listener anterior solo que en vez de que sean las mascotas
        * solamente, es la mascota con su tipo de area*/
        comboBoxTipoMasc_Prod.valueProperty().addListener((observable, oldValue, newValue) -> {
            //aqui se asigna a tipoArea el area que se selecciona en el combobox
            tipoArea = newValue.toString();
            //validar que categoria esta en el combobox
            if(tipoCategoria.equals("Mascotas")){
                /*se asigna la lista tipo Observable pero con una consulta a la base de datos dependiendo el tipoArea
                que ya tiene el combobox y se pasa como parametro para que la consulta trabaje*/
                ObservableList<Mascota> mascotasListaObs = FXCollections.observableArrayList(mascotaDAO.listarMascotasByArea(tipoArea));
                try {
                    crearCard(mascotasListaObs);
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
                //exactamente lo mismo para productos
            }else if(tipoCategoria.equals("Productos")){
                ObservableList<Producto> productosListaObs = FXCollections.observableArrayList(productosDAO.listarProductosByArea(tipoArea));
                try {
                    crearCard(productosListaObs);
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });
        /*
        este es para que se genere los cards de mascotas una vez iniciado,
        ya que el listener no se ejecuta hastq que el combobox seleccione algo*/
        try {
            ObservableList<Mascota> mascotasListaObs = FXCollections.observableArrayList(mascotaDAO.listarMascotas());
            tipoCategoria = "Mascotas";
            crearCard(mascotasListaObs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //metodo para cerrar la ventana(simular cerrar seison)=============================================================================
    public void cerrarVentanaInicio(ActionEvent ventanaInicio) {
        //obtener el actual stage con el event
        Stage stage = (Stage) ((Node) ventanaInicio.getSource()).getScene().getWindow();
        stage.close();
    }

    //metodo para crear el card de manera generica y se le pasa un objeto "item" que decide si es mascota o producto=====================================================================================
    public void crearCard(List<? extends ItemCardInterface> items) throws IOException{
        //limpiar todo antes ya que se reemplazaran entre mascota y proudctos
        flowPaneCard.getChildren().clear();
        //dejar espaciado entre cards
        flowPaneCard.setHgap(20);
        flowPaneCard.setVgap(20);
        flowPaneCard.setPadding(new Insets(20));
        //for each para recorrer la lista obsvervable que ya tiene los elementos de la base de datos gracias al dao
        for (ItemCardInterface item : items) {
            //un fxml loader obtener la vista de cards
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Card.fxml"));
            VBox card = loader.load();
            ControllerCard controllerCard = loader.getController();
            controllerCard.setData(item, "Cliente");
            flowPaneCard.getChildren().add(card);
        }
    }
}

