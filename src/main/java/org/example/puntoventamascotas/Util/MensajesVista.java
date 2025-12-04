package org.example.puntoventamascotas.Util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MensajesVista {
    public static void mostrarMensajeError(String titulo, String mensaje) {
        Platform.runLater(() -> {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle(titulo);
            alertError.setHeaderText(null);
            alertError.setContentText(mensaje);
            alertError.showAndWait();
        });
    }

    public static void mostrarMensajeExito(String titulo, String mensaje) {
        Platform.runLater(() -> {
            Alert alertExito = new Alert(Alert.AlertType.CONFIRMATION);
            alertExito.setTitle(titulo);
            alertExito.setHeaderText(null);
            alertExito.setContentText(mensaje);
            alertExito.showAndWait();
        });
    }

    public static boolean mostrarMensajeConfirmacion(String titulo, String mensaje) {
        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmacion.setTitle(titulo);
        alertConfirmacion.setHeaderText(null);
        alertConfirmacion.setContentText(mensaje);
        Optional<ButtonType> resultado = alertConfirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public static void mostrarMensajeTemporal(String titulo, String mensaje, int milisegundos) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);

            alert.show(); // <- No bloquea

            // Cerrar después del tiempo indicado
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(
                    javafx.util.Duration.millis(milisegundos)
            );
            delay.setOnFinished(event -> alert.close());
            delay.play();
        });
    }

}
