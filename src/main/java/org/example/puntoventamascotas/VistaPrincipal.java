package org.example.puntoventamascotas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaPrincipal extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/VentanaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }


}
