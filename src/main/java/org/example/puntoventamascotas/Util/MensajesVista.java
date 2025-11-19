package org.example.puntoventamascotas.Util;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MensajesVista {
    public static void mostrarMensajeError(String titulo, String mensaje){
        Platform.runLater(()->{
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle(titulo);
            alertError.setHeaderText(null);
            alertError.setContentText(mensaje);
            alertError.showAndWait();
        });
    }

    public static void mostrarMensajeExito(String titulo, String mensaje){
        Platform.runLater(()->{
            Alert alertExito = new Alert(Alert.AlertType.CONFIRMATION);
            alertExito.setTitle(titulo);
            alertExito.setHeaderText(null);
            alertExito.setContentText(mensaje);
            alertExito.showAndWait();
        });
    }
}
