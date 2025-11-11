module org.example.puntoventamascotas {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.puntoventamascotas to javafx.fxml;
    exports org.example.puntoventamascotas;
}