package org.example.puntoventamascotas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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

    @FXML
    TextField txtNombre;
    @FXML
    TextArea txtDescripcion;
    @FXML
    TextField txtCuidados;
    @FXML
    TextField txtPrecio;
    @FXML
    ImageView imgSubir;
    @FXML
    ComboBox<String> comboBoxTipoMascota;
    @FXML
    Button btnGuardar;
    Mascota mascota;
    String tipoMascota;
    String nombreImagen = "";
    boolean banderaRegistrar;
    MascotaDAO mascotaDAO;


    public ControllerMascotas() {
        this.mascotaDAO = new MascotaDAO(ConexionMsql.getConnection());
    }

    //el metodo initialize siempre se ejecuta antes de todo al cargar la interfaz grafica==========================================================
    @FXML
    public void initialize() {
        comboBoxTipoMascota.getItems().addAll("Caninos",
                "Felinos",
                "Aereos",
                "Marinos");
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
    public void setData(ItemCardInterface item) {
        mascota = (Mascota) item;
        /*Este es importante ya que es revoltoso, para que al combobox de tipo de mascota
        * tenga el tipo correspondiente, se hace una consulta en la base de datos
        * con el metodo de abajo se le esta pasando el id de tipo de mascota que se
        * supone tiene asignado y gracias a ese metodo trae el nombre del tipo de mascota*/
        TipoMascota tipoMascotaConsulta = mascotaDAO.obtenerNombreTipoMascotaPorId(mascota.getIdTipo());
        txtNombre.setText(mascota.getNombre());
        txtDescripcion.setText(mascota.getDescripcionMascota());
        txtCuidados.setText(mascota.getCuidados());
        txtPrecio.setText("" + mascota.getPrecio());
        //aqui es donde se le va a asignar en el combobox
        comboBoxTipoMascota.getSelectionModel().select(tipoMascotaConsulta.getNombreTipoMascota());
        tipoMascota = tipoMascotaConsulta.getNombreTipoMascota();
        //try catch para cargar tambien la imagen y por si no se encuentra la imagen, muestra una por defecto
        try {
            //pare este paso ocurre primero el metodo de cargar imagen de abajo
            System.out.println(item.getImagen());
            imgSubir.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + item.getImagen())));
        } catch (Exception e) {
            e.printStackTrace();
            imgSubir.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
        }
    }

    //metodo para hacer que habra el explorador de archivos y cargar una imagen=============================================================================
    @FXML
    public void cargarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        //el "*.jpg" es para que el expl, de archivos ya no detecte imagenes que no sean jpg
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagen", "*"));
        //aqui el filechoser manda a llamar a cualquier nodo solo para conseguir la escena
        File file = fileChooser.showOpenDialog(txtCuidados.getScene().getWindow());
        if (file != null) {
            try {
                nombreImagen = file.getName();
                Image image = new Image(file.toURI().toString());
                imgSubir.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //metodo para actualizar la mascota pero a su vez tiene el registro de una nueva
    //esto se da gracias a una bandera===================================================================================================================
    @FXML
    public void updateMascota() {
        Mascota mascotaModificada;
        //aqui se sabra el id del tipo de mascota mediante el nombre que se ponga en el combobox seleccionado
        TipoMascota idTipoMascota = mascotaDAO.obtenerTipoMascotaByNombre(tipoMascota);
        //aqui empieza el registro---------------------------------------------------------------
        if (banderaRegistrar) {
            boolean sonCamposVacios = sonVacios(txtNombre, txtPrecio);
            if (sonCamposVacios) {
                MensajesVista.mostrarMensajeError("Error", "El nombre, precio, tipo de mascota e imagen son obligatorios");
            }else {
                mascotaModificada = new Mascota(
                        txtNombre.getText(),
                        txtDescripcion.getText(),
                        txtCuidados.getText(),
                        Double.parseDouble(txtPrecio.getText()),
                        nombreImagen.equals("") ? mascota.getImagen() : nombreImagen,
                        idTipoMascota.getIdTipoMascota()
                );
                if (mascotaDAO.insertarMascota(mascotaModificada)) {
                    MensajesVista.mostrarMensajeExito("Exito", "Se registró la mascota correctamente");
                    limpiarCampos();
                } else {
                    MensajesVista.mostrarMensajeError("Error", "Ocurrió un error en el registro");
                }
            }
            //aqui termina el registro-------------------------------------------------------------
        } else {
            //en este else quiere decir que es una actualizacion
            mascotaModificada = new Mascota(
                    mascota.getId(),
                    txtNombre.getText(),
                    txtDescripcion.getText(),
                    txtCuidados.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    //aqui solo se manda el id porque lo neceista la DB
                    //me lo esta dando vacío y aun no se por que
                    idTipoMascota.getIdTipoMascota(),
                    //esto quiere decir que si el nombre de la imagen es vacio(No se modificó),
                    // le asigne lo que ya tiene la mascota, pero si si me modificó que le añada la nueva
                    nombreImagen.equals("") ? mascota.getImagen() : nombreImagen
            );
            if (mascotaDAO.updateMascota(mascotaModificada)) {
                MensajesVista.mostrarMensajeExito("Exito", "Mascota modificada correctamente");
            } else {
                MensajesVista.mostrarMensajeError("Error", "Ocurrio un error en la modificacion de la mascota");
            }
        }
    }

    //metodo para validar si se va a registrar o no y que solo cambio el texto del boton
    public void isRegistrar(boolean vamohRegistrar) {
        banderaRegistrar = vamohRegistrar;
        btnGuardar.setText("Registrar");
    }

    public boolean sonVacios(TextField txtNombre, TextField txtPrecio){
        if(txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()
           || comboBoxTipoMascota.getValue() == null || imgSubir.getImage() == null){
            return true;
        }
        return false;
    }
    //metodo para cerrar la venta cuando se registre nueva mascota
    public void cerrarVenatanActual() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow(); // btnGuardar o cualquier nodo de tu ventana
        stage.close();
    }

    //metodo para limpiar campos
    public void limpiarCampos(){
        txtNombre.clear();
        txtPrecio.clear();
        txtDescripcion.clear();
        txtCuidados.clear();
        comboBoxTipoMascota.setValue(null);
        imgSubir.setImage(null);
    }

}
