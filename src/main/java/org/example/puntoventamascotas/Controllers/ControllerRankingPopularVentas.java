package org.example.puntoventamascotas.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.MascotaDAO;
import org.example.puntoventamascotas.DAO.ProductosDAO;
import org.example.puntoventamascotas.Models.Mascota;
import org.example.puntoventamascotas.Models.Producto;
import javafx.scene.image.ImageView;
import java.awt.*;

public class ControllerRankingPopularVentas {
    @FXML Label lblVendidas1;
    @FXML Label lblVendidas2;
    @FXML Label lblVendidas3;
    @FXML Label lblVendidas4;
    @FXML Label lblVendidas5;
    @FXML Label lblTitulo;
    @FXML ImageView img1;
    @FXML ImageView img2;
    @FXML ImageView img3;
    @FXML ImageView img4;
    @FXML ImageView img5;
    //=======================================
    ProductosDAO productosDAO;
    MascotaDAO mascotaDAO;

    public ControllerRankingPopularVentas() {
        productosDAO = new ProductosDAO(ConexionMsql.getConnection());
        mascotaDAO = new MascotaDAO(ConexionMsql.getConnection());
    }

    public void cargarRanking(String tipoRanking){
        if(tipoRanking.equals("Productos")) {
            lblTitulo.setText("Productos Mas Vendidos");
            ObservableList<Producto> observableListProductos = FXCollections.observableArrayList(productosDAO.RankearProductosMasVendidos());
            lblVendidas1.setText(observableListProductos.get(0).getCantidad() + " Unidades");
            try {
                img1.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListProductos.get(0).getImagen())));
            } catch (Exception e) {
                img1.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
            //====================================
            lblVendidas2.setText(observableListProductos.get(1).getCantidad() + " Unidades");
            try {
                img2.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListProductos.get(1).getImagen())));
            } catch (Exception e) {
                img2.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
            //======================================
            lblVendidas3.setText(observableListProductos.get(2).getCantidad() + " Unidades");
            try {
                img3.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListProductos.get(2).getImagen())));
            } catch (Exception e) {
                img3.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
            //======================================
            lblVendidas4.setText(observableListProductos.get(3).getCantidad() + " Unidades");
            try {
                img4.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListProductos.get(3).getImagen())));
            } catch (Exception e) {
                img4.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
            //======================================
            lblVendidas5.setText(observableListProductos.get(4).getCantidad() + " Unidades");
            try {
                img5.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListProductos.get(4).getImagen())));
            } catch (Exception e) {
                img5.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
        }else if (tipoRanking.equals("Mascotas")) {
            lblTitulo.setText("Mascotas Mas Adoptadas");
            ObservableList<Mascota> observableListMascotas = FXCollections.observableArrayList(mascotaDAO.RenkearMascotasMasAdoptadas());
            lblVendidas1.setText(observableListMascotas.get(0).getCantidad() + " Unidades");
            try {
                img1.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListMascotas.get(0).getImagen())));
            } catch (Exception e) {
                img1.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
            //====================================
            lblVendidas2.setText(observableListMascotas.get(1).getCantidad() + " Unidades");
            try {
                img2.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListMascotas.get(1).getImagen())));
            } catch (Exception e) {
                img2.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
            //======================================
            lblVendidas3.setText(observableListMascotas.get(2).getCantidad() + " Unidades");
            try {
                img3.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListMascotas.get(2).getImagen())));
            } catch (Exception e) {
                img3.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
            //======================================
            lblVendidas4.setText(observableListMascotas.get(3).getCantidad() + " Unidades");
            try {
                img4.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListMascotas.get(3).getImagen())));
            } catch (Exception e) {
                img4.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
            //======================================
            lblVendidas5.setText(observableListMascotas.get(4).getCantidad() + " Unidades");
            try {
                img5.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + observableListMascotas.get(4).getImagen())));
            } catch (Exception e) {
                img5.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            }
        }
    }
}
