package org.example.puntoventamascotas.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainController {
    @FXML private ImageView imagenMascotasPrincipal;
    @FXML private Label labelLogin;
    @FXML private Button btonCerrar;

    @FXML
    protected void abrirVentanaCrearCuenta(){
        try{
            //Se crea un FXMLoader para cargar la ventana enlazada
            FXMLLoader loaderVentanaCrearCuenta = new FXMLLoader(getClass().getResource("/Views/VentanaCrearCuenta.fxml"));
            //Es como decir: "Carga mi archivo FXML y dame el panel principal"
            Parent root = loaderVentanaCrearCuenta.load();

            //crear la nueva escena y poner lo que ya se cargo dentro
            Scene sceneVentanaCrearCuenta = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Crear Cuenta");
            stage.setScene(sceneVentanaCrearCuenta);

            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void cerrarVentanaActual(ActionEvent event) {
        // Obtener el stage actual desde el evento
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
