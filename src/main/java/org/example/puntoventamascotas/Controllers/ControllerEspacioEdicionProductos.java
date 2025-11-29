package org.example.puntoventamascotas.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.ProductosDAO;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Producto;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.io.IOException;
import java.util.List;

public class ControllerEspacioEdicionProductos {
    private ProductosDAO productosDAO;
    @FXML private VBox VboxCompleto;
    @FXML private ScrollPane scrollPaneEdicion;
    @FXML private FlowPane flowPaneEdicionProductos;
    ControladorLoginAdministrador controladorLoginAdministrador;


    public ControllerEspacioEdicionProductos(){
        this.productosDAO = new ProductosDAO(ConexionMsql.getConnection());
        this.controladorLoginAdministrador = new ControladorLoginAdministrador();
    }

    //metodo para cerrar la ventana
    public void cerrarVentanaActual(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    //metodo initialize=======================================================================================================
    @FXML
    public void initialize(){
        ObservableList<Producto> productos = FXCollections.observableArrayList(productosDAO.listarProductos());
        try{
            crearCard(productos);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //metodo para crear el card de manera generica y
    // se le pasa un objeto "item" que decide si es mascota o producto================================================================
    public void crearCard(List<? extends ItemCardInterface> productos) throws IOException {
        //limpiar todo para cargar las interfaces entre el mismo contenedor
        flowPaneEdicionProductos.getChildren().clear();
        //dejar espaciado entre cards
        flowPaneEdicionProductos.setHgap(15);
        flowPaneEdicionProductos.setVgap(15);
        flowPaneEdicionProductos.setPadding(new Insets(15));

        //for(int i = 0; i <= 15; i++) {
            //foreach para recorrer toda la lista de ItemCardInterface que deberia de tener los elemenos de productos gracias al dao
            for (ItemCardInterface item : productos) {
                //cargar el FXMLLoader para ponerle los cards de productos
                FXMLLoader loaderProductos = new FXMLLoader(getClass().getResource("/Views/Card.fxml"));
                VBox cards = loaderProductos.load();
                ControllerCard controllerCardProductos = loaderProductos.getController();
                controllerCardProductos.setData(item, "Administrador");
                //ejecutar la accion del boton de editar
                controllerCardProductos.setOnEditar(itemCard -> {
                    System.out.println(itemCard);
                    try {
                        /*No me muestra el formulario de edicion de productos*/

                        //se carga el fxmloader con la interfaz de formulario
                        FXMLLoader loaderProductosForms = new FXMLLoader(getClass().getResource("/Views/FormularioProductos.fxml"));
                        //esta parte es como decirle "Carga el archivo y muestralo"
                        Parent root = loaderProductosForms.load();
                        //aqui al controlador externo se le aigna este controlador
                        ControllerProductos controllerProductos = loaderProductosForms.getController();
                        System.out.println(itemCard);
                        controllerProductos.setData(itemCard);

                        //crear la nueva escena, cargarla y mostrarla
                        Scene sceneFormProductos = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(sceneFormProductos);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                controllerCardProductos.setOnEliminar(itemCard -> {
                    boolean siBorrar = MensajesVista.mostrarMensajeConfirmacion("¿Seguro?", "Confirma el borrado");
                    if (siBorrar) {
                        productosDAO.eliminarProducto(itemCard.getId());
                        MensajesVista.mostrarMensajeExito("Exito", "Producto eliminado con éxito");
                        //hacer refresh, se me ocurrio asi pero creo no esta bien
                        controladorLoginAdministrador.crearEspacioEdicionProductos();
                    }
                });

                controllerCardProductos.setOnComprar(itemCard -> {
                });

                controllerCardProductos.setOnInfo(itemCard -> {
                });

                flowPaneEdicionProductos.getChildren().add(cards);
            }
        //}
    }

    @FXML
    public void agregarProducto() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FormularioProductos.fxml"));
        Parent root = loader.load();
        ControllerProductos controllerProductos = loader.getController();
        controllerProductos.isRegistrar(true);
        Scene sceneProductos = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(sceneProductos);
        stage.show();
    }
}
