module org.example.puntoventamascotas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires jbcrypt;
    requires javafx.graphics;

    // Agrega estas líneas:
    opens org.example.puntoventamascotas.Controllers to javafx.fxml;
    exports org.example.puntoventamascotas.Controllers;

    exports org.example.puntoventamascotas;
    exports org.example.puntoventamascotas.Util;
}