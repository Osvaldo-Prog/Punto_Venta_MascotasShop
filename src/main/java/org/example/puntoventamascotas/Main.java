package org.example.puntoventamascotas;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.ConexionMsql;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Ahora inicializas tu VistaPrincipal
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        vistaPrincipal.start(primaryStage);
        // La conexión se hace DENTRO de start, cuando JavaFX ya está inicializado
        Connection conexion = ConexionMsql.getConnection();
        if (conexion != null) {
            vistaPrincipal.mostrarMensajeExito("Éxito en la conexión", "Se ha conectado a la DB");
        } else {
            vistaPrincipal.mostrarMensajeError("Error en la conexión", "Se produjo un error en la conexión a la DB");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}