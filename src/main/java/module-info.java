module org.example.puntoventamascotas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires jbcrypt;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;
    requires jdk.security.jgss;

    // Agrega estas líneas:
    opens org.example.puntoventamascotas.Controllers to javafx.fxml;
    opens org.example.puntoventamascotas.Models to javafx.base, javafx.fxml;
    exports org.example.puntoventamascotas.Controllers;

    exports org.example.puntoventamascotas;
    exports org.example.puntoventamascotas.Util;
}