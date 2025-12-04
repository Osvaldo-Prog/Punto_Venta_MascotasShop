package org.example.puntoventamascotas.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.DetalleVentaDAO;
import org.example.puntoventamascotas.DAO.VentaDAO;
import org.example.puntoventamascotas.Models.DetalleVenta;
import org.example.puntoventamascotas.Models.Venta;
import org.example.puntoventamascotas.Models.VistaDetalleVenta;
import org.example.puntoventamascotas.Models.VistaVentas;

import java.util.Date;

public class ControllerTablaVentasInfo {
    @FXML
    private TableView<VistaVentas> tablaVentas;
    @FXML private TableColumn<VistaVentas, Integer> colIdVenta;
    @FXML private TableColumn<VistaVentas, String> colNombrePersonal;
    @FXML private TableColumn<VistaVentas, String> colMetodoPago;
    @FXML private TableColumn<VistaVentas, Integer> colIdDireccion;
    @FXML private TableColumn<VistaVentas, Date> colFecha;
    @FXML private TableColumn<VistaVentas, Double> colTotal;

    VentaDAO ventaDAO;
    DetalleVentaDAO detalleVentaDAO;
    String ventaInfo;

    public ControllerTablaVentasInfo() {
        this.ventaDAO = new VentaDAO(ConexionMsql.getConnection());
        this.detalleVentaDAO = new DetalleVentaDAO(ConexionMsql.getConnection());
    }

    @FXML
    public void initialize() {
        colIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));
        colNombrePersonal.setCellValueFactory(new PropertyValueFactory<>("nombrePersonal"));
        colMetodoPago.setCellValueFactory(new PropertyValueFactory<>("nombreMetodoPago"));
        colIdDireccion.setCellValueFactory(new PropertyValueFactory<>("idDireccion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tablaVentas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                ventaInfo = ("" + newValue.getIdVenta());
                System.out.println(ventaInfo);
                /*ventaInfo = (newValue.getNombrePersonal());
                ventaInfo = (newValue.getNombreMetodoPago());
                ventaInfo = ("" + newValue.getIdDireccion());
                ventaInfo = ("" + newValue.getFechaVenta());
                ventaInfo = ("" + newValue.getTotal());*/
            }
        });
    }

    public void cargarVentas(){
        ObservableList<VistaVentas> observableList = FXCollections.observableArrayList(ventaDAO.listarVentas());
        tablaVentas.setItems(observableList);
    }

    //metodo para cargar la ventana de detalle de la venta
    @FXML
    public void cargarDetalleVenta(){
        try{
            FXMLLoader loader = new  FXMLLoader(getClass().getResource("/Views/TablaDetalleVentas.fxml"));
            Parent root = loader.load();
            ControllerTablaDetalleVenta controllerTablaDetalleVenta = loader.getController();
            controllerTablaDetalleVenta.cargarDetalleVentas(Integer.parseInt(ventaInfo));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Detalle Ventas");
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
