package org.example.puntoventamascotas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorLoginAdministrador {
    @FXML Button botonSalir;
    @FXML AnchorPane anchorPaneEdicion;



//metodo para cerrar la ventana actual============================================================================================================================
    public void cerrarVentanaActual(ActionEvent actionEvent) {
        //obtener el stage actual
        Stage stage = (Stage) ((Node)  actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    /*En este metodo asigna una vista a la vita principal del administrador que ya esta hecha
    * es decir, se asigna la vista de mascotas ============================================================================================================================*/
    public void crearEspacioEdicionMascotas(){
        anchorPaneEdicion.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/EspacioEdicionMascotas.fxml"));
            VBox pane = loader.load();
            ControllerEspacioEdicionMascotas controllerEspacioEdicionMascotas = loader.getController();
            //asignarle al anchorpane el vbox
            anchorPaneEdicion.getChildren().add(pane);
            //hacer que el vbox ocupe todo el espacio del anchorpane
            anchorPaneEdicion.setTopAnchor(pane, 0.0);
            anchorPaneEdicion.setLeftAnchor(pane, 0.0);
            anchorPaneEdicion.setRightAnchor(pane, 0.0);
            anchorPaneEdicion.setBottomAnchor(pane, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /*En este metodo asigna una vista a la vita principal del administrador que ya esta hecha
     * es decir, se asigna la vista de productos ============================================================================================================================*/
    public void crearEspacioEdicionProductos(){
        anchorPaneEdicion.getChildren().clear();
        try{
            FXMLLoader loaderProductos = new FXMLLoader(getClass().getResource("/Views/EspacioEdicionProductos.fxml"));
            VBox pane = loaderProductos.load();
            ControllerEspacioEdicionProductos controllerEspacioEdicionProductos = loaderProductos.getController();
            //asignarle al anchorpane el vbox
            anchorPaneEdicion.getChildren().add(pane);
            //hacer que el vbox ocupe todo el espacio del anchorpane
            anchorPaneEdicion.setTopAnchor(pane, 0.0);
            anchorPaneEdicion.setLeftAnchor(pane, 0.0);
            anchorPaneEdicion.setRightAnchor(pane, 0.0);
            anchorPaneEdicion.setBottomAnchor(pane, 0.0);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /*metodo para crear el espacio de los usuarios en la ventana de admin===================================================================================================================================================================*/
    public void crearEspacioEdicionUsuarios(){
        anchorPaneEdicion.getChildren().clear();
        try{
            FXMLLoader loaderUsuarios = new FXMLLoader(getClass().getResource("/Views/EspacioEdicionUsuarios.fxml"));
            VBox pane = loaderUsuarios.load();
            ControllerEspacioEdicionUsuarios controllerEspacioEdicionUsuarios = loaderUsuarios.getController();
            //asignarle al anchorpane el vbox
            anchorPaneEdicion.getChildren().add(pane);
            //hacer que el vbox ocupe todo el espacio del anchorpane
            anchorPaneEdicion.setTopAnchor(pane, 0.0);
            anchorPaneEdicion.setLeftAnchor(pane, 0.0);
            anchorPaneEdicion.setRightAnchor(pane, 0.0);
            anchorPaneEdicion.setBottomAnchor(pane, 0.0);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
