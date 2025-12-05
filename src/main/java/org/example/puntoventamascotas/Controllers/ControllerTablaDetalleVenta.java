package org.example.puntoventamascotas.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.DetalleVentaDAO;
import org.example.puntoventamascotas.Models.VistaDetalleVenta;

public class ControllerTablaDetalleVenta {
    @FXML private TableView<VistaDetalleVenta> tablaDetalleVenta;
    @FXML private TableColumn<VistaDetalleVenta, String> colIdVenta;
    @FXML private TableColumn<VistaDetalleVenta, String> colNombreItem;
    @FXML private TableColumn<VistaDetalleVenta, String> colCantidad;
    @FXML private TableColumn<VistaDetalleVenta, String> colPrecioUnitario;
    @FXML private TableColumn<VistaDetalleVenta, String> colSubtotal;

    DetalleVentaDAO detalleVentaDAO;

    public ControllerTablaDetalleVenta(){
        this.detalleVentaDAO = new DetalleVentaDAO(ConexionMsql.getConnection());
    }


    @FXML public void initialize(){
        colIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));
        colNombreItem.setCellValueFactory(new PropertyValueFactory<>("nombreItem"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
    }

    public void cargarDetalleVentas(int ventaInfo){
        ObservableList<VistaDetalleVenta> observableList = FXCollections.observableArrayList(detalleVentaDAO.listarDetallesVentaByIdVenta(ventaInfo));
        tablaDetalleVenta.setItems(observableList);
    }
}
