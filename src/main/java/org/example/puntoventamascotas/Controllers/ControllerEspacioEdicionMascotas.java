package org.example.puntoventamascotas.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.MascotaDAO;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Mascota;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.io.IOException;
import java.util.List;


public class ControllerEspacioEdicionMascotas {
    @FXML private FlowPane flowPaneEdicionMascotas;
    @FXML ScrollPane scrollPaneEdicion;
    private MascotaDAO mascotaDAO;

    public ControllerEspacioEdicionMascotas(){
        this.mascotaDAO = new MascotaDAO(ConexionMsql.getConnection());
    }

    //metodo initialize=========================================================================================================
    @FXML
    public void initialize(){
        ObservableList<Mascota> mascotas = FXCollections.observableArrayList(mascotaDAO.listarMascotas());
        try {
            crearCard(mascotas);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //metodo para crear el card de manera generica y
    // se le pasa un objeto "item" que decide si es mascota o producto================================================================
    public void crearCard(List<? extends ItemCardInterface> mascotas) throws IOException {
        //limpiar todo antes ya que se reemplazaran entre mascota y proudctos
        flowPaneEdicionMascotas.getChildren().clear();
        //dejar espaciado entre cards
        flowPaneEdicionMascotas.setHgap(20);
        flowPaneEdicionMascotas.setVgap(20);
        //flowPaneEdicionMascotas.prefWrapLengthProperty().bind(scrollPaneEdicion.widthProperty());
        flowPaneEdicionMascotas.setPadding(new Insets(20));
        //for each para recorrer la lista obsvervable que ya tiene los elementos de la base de datos gracias al dao
        //for(int i = 0; i < 10; i++) {
            for (ItemCardInterface item : mascotas) {
                //un fxml loader obtener la vista de cards
                FXMLLoader loaderMascotas = new FXMLLoader(getClass().getResource("/Views/Card.fxml"));
                VBox card = loaderMascotas.load();
                ControllerCard controllerCardMascotas = loaderMascotas.getController();
                controllerCardMascotas.setData(item, "Administrador");
                //ejecutar la accion del boton editar
                controllerCardMascotas.setOnEditar(itemCard -> {
                    System.out.println(itemCard);
                    try {
                        //Se crea un FXMLoader para cargar la ventana enlazada de formulario para editar
                        FXMLLoader loaderFormMascota = new FXMLLoader(getClass().getResource("/Views/FormularioMascota.fxml"));
                        //Es como decir: "Carga mi archivo FXML y dame el panel principal"
                        Parent root = loaderFormMascota.load();
                        //aqui al controlador externo se le aigna este controlador
                        ControllerMascotas controllerMascotas = loaderFormMascota.getController();
                        controllerMascotas.setData(itemCard);

                        //crear la nueva escena y poner lo que ya se cargo dentro
                        Scene sceneFormularioMascota = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(sceneFormularioMascota);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                controllerCardMascotas.setOnEliminar(itemCard -> {
                    boolean siBorrar = MensajesVista.mostrarMensajeConfirmacion("¿Seguro?", "¿Estas seguro de que quiere borrar la mascota?");
                    if(siBorrar){
                        mascotaDAO.eliminarMascota(itemCard.getId());
                        MensajesVista.mostrarMensajeExito("Exito", "Mascota eliminada con éxito");
                    }
                });

                controllerCardMascotas.setOnComprar(itemCard -> {
                });

                controllerCardMascotas.setOnInfo(itemCard -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/InformacionModelo.fxml"));
                        Parent root = loader.load();
                        ControllerInformacionModelo controllerInformacionModelo = loader.getController();
                        //se llama al metodo pero se concatena como mascota ya que se usa la misma interfaz para el producto
                        controllerInformacionModelo.setDataMascotaInfo((Mascota) itemCard);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                });
                //se le asigna la informaicon del modelo (mascota) al card
                flowPaneEdicionMascotas.getChildren().add(card);
            }
        //}
    }

    //metodo que se asigna al boton cuando presiona Añadir y este manda el mismo fomrulario que el de update
    //pero con la diferencia de que este insertara y el boton dice registrar, no guardar
    @FXML
    public void agregarMascota() throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/FormularioMascota.fxml"));
        Parent root = loader.load();
        //este controller es para que se sepa si se va a registrar mandando a llamar al siguiente metodo
        ControllerMascotas controllerMascotas = loader.getController();
        controllerMascotas.isRegistrar(true);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
