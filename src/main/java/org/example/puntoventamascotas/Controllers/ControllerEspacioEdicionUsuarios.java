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
import org.example.puntoventamascotas.DAO.UsuarioDAO;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Usuario;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.io.IOException;
import java.util.List;

public class ControllerEspacioEdicionUsuarios {
    private UsuarioDAO usuarioDAO;
    @FXML VBox VboxCompleto;
    @FXML private FlowPane flowPaneEdicionUsuarios;
    @FXML ScrollPane scrollPaneEdicion;


    public ControllerEspacioEdicionUsuarios(){
        this.usuarioDAO = new UsuarioDAO(ConexionMsql.getConnection());
    }

    //metodo initialize========================================================================================================
    @FXML public void initialize(){
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(usuarioDAO.listarUsuarios());
        try{
            crearCardUsuarios(usuarios);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //metodo para crear los cards de los usuarios===========================================================================================
    public void crearCardUsuarios(List<? extends ItemCardInterface> usuarios) throws IOException {
        flowPaneEdicionUsuarios.getChildren().clear();
        flowPaneEdicionUsuarios.setHgap(20);
        flowPaneEdicionUsuarios.setVgap(20);
        flowPaneEdicionUsuarios.setPadding(new Insets(20));

        //for(int i = 0; i < 15; i++) {
            for (ItemCardInterface item : usuarios) {

                FXMLLoader loaderUsuarios = new FXMLLoader(getClass().getResource("/Views/Card.fxml"));
                VBox card = loaderUsuarios.load();

                ControllerCard controllerCard = loaderUsuarios.getController();
                controllerCard.setData(item, "Administrador");
                controllerCard.setOnEditar(itemCard -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FormularioUsuarios.fxml"));
                        Parent root = loader.load();
                        ControllerUsuarios controllerUsuarios = loader.getController();
                        controllerUsuarios.setData(itemCard);

                        //crear la escena
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                //accion del boton eliminar
                controllerCard.setOnEliminar(itemCard -> {
                    boolean siBorrar = MensajesVista.mostrarMensajeConfirmacion("Seguro?", "Confirma el borrado");
                    if(siBorrar){
                        usuarioDAO.eliminarUsuario(itemCard.getId());
                        MensajesVista.mostrarMensajeExito("Exito", "Usuario eliminado");
                        //hacer el refresh
                    }
                });

                controllerCard.setOnInfo(itemCard -> {
                });

                flowPaneEdicionUsuarios.getChildren().add(card);
            }
       // }
    }

    //metodo para asirganrle al boton de guardar que esta vez va a registrar
    @FXML
    public void agregarUsuario() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FormularioUsuarios.fxml"));
        System.out.println(loader);
        Parent root = loader.load();
        ControllerUsuarios controllerUsuarios = loader.getController();
        //aqui se manda a llamar diciendo que se va a registrar
        controllerUsuarios.isRegistrar(true);
        //crear la escena
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
