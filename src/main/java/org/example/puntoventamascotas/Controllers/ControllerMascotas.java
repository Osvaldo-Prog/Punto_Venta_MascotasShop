package org.example.puntoventamascotas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.MascotaDAO;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Mascota;
import org.example.puntoventamascotas.Models.TipoMascota;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.io.File;

//
public class ControllerMascotas {

    @FXML TextField txtNombre;
    @FXML TextField txtDescripcion;
    @FXML TextField txtCuidados;
    @FXML TextField txtPrecio;
    @FXML ImageView imgSubir;
    @FXML ComboBox<String> comboBoxTipoMascota;
    @FXML Button btnGuardar;
    Mascota mascota;
    String tipoMascota;
    String nombreImagen = "";
    boolean banderaRegistrar;
    MascotaDAO mascotaDAO;


    public ControllerMascotas(){
        this.mascotaDAO = new MascotaDAO(ConexionMsql.getConnection());
    }

    //el metodo initialize siempre se ejecuta antes de todo al cargar la interfaz grafica==========================================================
    @FXML
    public void initialize(){
        comboBoxTipoMascota.getItems().addAll("Caninos",
                                                    "Felino",
                                                    "Aerea",
                                                    "Marino");
        comboBoxTipoMascota.valueProperty().addListener((observable, oldValue, newValue) -> {
            tipoMascota = newValue.toString();
            System.out.println(tipoMascota);
        });
    }

    //metodo para cerrar la ventana actual==========================================================================================
    public void cerrarVenatanActual(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    //dar los datos al formulario de mascotas=====================================================================================================
    public void setData(ItemCardInterface item){
        Mascota mascota = (Mascota)item;
        System.out.println(item);
        txtNombre.setText(mascota.getNombre());
        txtDescripcion.setText(mascota.getDescripcionMascota());
        txtCuidados.setText(mascota.getCuidados());
        txtPrecio.setText("" + mascota.getPrecio());
        //try catch para cargar tambien la imagen y por si no se encuentra la imagen, muestra una por defecto
        try {
            System.out.println(item.getImagen());
            imgSubir.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + item.getImagen())));
        }catch(Exception e){
            e.printStackTrace();
            imgSubir.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
        }
        tipoMascota = mascota.getIdTipo() == 2 ? "Caninos" : mascota.getIdTipo() == 3 ? "Felinos" : mascota.getIdTipo() == 4 ? "Aereos" : "Marinos";
    }

    //metodo para hacer que habra el explorador de archivos y cargar una imagen=============================================================================
    @FXML
    public void cargarImagen(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        //el "*.jpg" es para que el expl, de archivos ya no detecte imagenes que no sean jpg
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagen", "*"));
        //aqui el filechoser manda a llamar a cualquier nodo solo para conseguir la escena
        File file = fileChooser.showOpenDialog(txtCuidados.getScene().getWindow());
        if(file != null){
            try{
                nombreImagen = file.getName();
                Image image = new Image(file.toURI().toString());
                imgSubir.setImage(image);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //metodo para actualizar la mascota pero a su vez tiene el registro de una nueva
    //esto se da gracias a una bandera===================================================================================================================
    @FXML
    public void updateMascota(){
        Mascota mascotaModificada;
        TipoMascota idTipoMascota = mascotaDAO.obtenerTipoMascota(tipoMascota);
        //aqui empieza el registro---------------------------------------------------------------
        if(banderaRegistrar){
            mascotaModificada = new Mascota(
                    txtNombre.getText(),
                    txtDescripcion.getText(),
                    txtCuidados.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    nombreImagen.equals("") ? mascota.getImagen() : nombreImagen,
                    idTipoMascota.getIdTipoMascota()
            );
            if(mascotaDAO.insertarMascota(mascotaModificada)){
                MensajesVista.mostrarMensajeExito("Exito", "Se registró la mascota correctamente");
            }else{
                MensajesVista.mostrarMensajeError("Error", "Ocurrió un error en el registro");
            }
        //aqui termina el registro-------------------------------------------------------------
        }else{
            //en este else quiere decir que es una actualizacion
            mascotaModificada = new Mascota(
                mascota.getId(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                txtCuidados.getText(),
                Double.parseDouble(txtPrecio.getId()),
                //aqui solo se manda el id porque lo neceista la DB
                idTipoMascota.getIdTipoMascota(),
                //esto quiere decir que si el nombre de la imagen es vacio(No se modificó),
                // le asigne lo que ya tiene la mascota, pero si si me modificó que le añada la nueva
                nombreImagen.equals("") ? mascota.getImagen() : nombreImagen
            );
            if(mascotaDAO.updateMascota(mascotaModificada)){
                MensajesVista.mostrarMensajeExito("Exito", "Mascota modificada correctamente");
            }else{
                MensajesVista.mostrarMensajeError("Error", "Ocurrio un error en la modificacion de la mascota");
            }
        }
    }

    //metodo para validar si se va a registrar o no y que solo cambio el texto del boton
    public void isRegistrar(boolean vamohRegistrar){
        banderaRegistrar = vamohRegistrar;
        btnGuardar.setText("Registrar");
    }
}
