package org.example.puntoventamascotas.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.MascotaDAO;
import org.example.puntoventamascotas.Models.Mascota;

public class ControladorLoginCliente {
    MascotaDAO mascotaDAO;

    //inicializacion de los nodos de fxml
    @FXML private Label labelPrecioBeagle;
    @FXML private ComboBox<String> comboBoxMasc_Prod;
    @FXML private ComboBox<String> comboBoxTipoMasc_Prod;
    @FXML private TableView<Mascota> tableViewMascotas;
    @FXML private TableColumn<Mascota,Integer> columnaIdMascota;
    @FXML private TableColumn<Mascota,String> columnaImagMascota;
    @FXML private TableColumn<Mascota,String> columnaNombreMascota;
    @FXML private TableColumn<Mascota,Double> columnaPrecioMascota;
    @FXML private TableColumn<Mascota,String> columnaInfoMascota;
    @FXML private TableColumn<Mascota,String> columnaCuidados;
    @FXML private TableColumn<Mascota,String> columnaidTipoMascota;
    //esta columna debe de ser void porque tendrá un boton de acción
    @FXML private TableColumn<Mascota,Void> columnaComprar;

    public ControladorLoginCliente(){
        this.mascotaDAO = new MascotaDAO(ConexionMsql.getConnection());
    }


    //lo que este en este metodo se cargará una vez se carge el fxml loader
    @FXML public void initialize(){
        comboBoxMasc_Prod.getItems().addAll("Mascotas",
                                                  "Productos");
        comboBoxTipoMasc_Prod.getItems().addAll("Terrestre", "Aerea", "Marina");

        //columnas simples
        columnaidTipoMascota.setCellValueFactory(new PropertyValueFactory<>("idTipoMascota"));
        columnaIdMascota.setCellValueFactory(new PropertyValueFactory<>("idMascota"));
        columnaNombreMascota.setCellValueFactory(new PropertyValueFactory<>("nombreMascota"));
        columnaPrecioMascota.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaInfoMascota.setCellValueFactory(new PropertyValueFactory<>("descripcionMascota"));
        columnaCuidados.setCellValueFactory(new PropertyValueFactory<>("cuidados"));
        columnaImagMascota.setCellValueFactory(new PropertyValueFactory<>("imagen"));

        //columnas mas complejas que son la imagen y la del boton de comprar



        //con este se accede a la base de datos mediante el dao y al tableview se le asignan los items que trajo
        ObservableList<Mascota> mascotas = FXCollections.observableArrayList(mascotaDAO.listarMascotas());
        tableViewMascotas.setItems(mascotas);
        tableViewMascotas.setFixedCellSize(100);

    }





    //metodo para cerrar la ventana(simular cerrar seison)=============================================================================
    public void cerrarVentanaInicio(ActionEvent ventanaInicio){
        //obtener el actual stage con el event
        Stage stage = (Stage) ((Node) ventanaInicio.getSource()).getScene().getWindow();
        stage.close();
        //labelPrecioBeagle.setText(mascotasDao.obtenerPrecio);
    }


}
