package org.example.puntoventamascotas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.UsuarioDAO;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Usuario;
import org.example.puntoventamascotas.Util.MensajesVista;
import org.mindrot.jbcrypt.BCrypt;

public class ControllerUsuarios {

    @FXML
    TextField txtNombre;
    @FXML
    TextField txtNombreUsuario;
    @FXML
    TextField txtEdad;
    @FXML
    TextField txtTelefono;
    @FXML
    TextField txtCorreo;
    @FXML
    PasswordField pswContraseña;
    @FXML
    ComboBox<String> comboBoxTipoUsuario;
    @FXML
    Button btnGuardar;
    String tipoUsuario;
    Usuario usuario;
    UsuarioDAO usuarioDAO;
    boolean banderaRegistrar = false;


    public ControllerUsuarios() {
        this.usuarioDAO = new UsuarioDAO(ConexionMsql.getConnection());
    }

    //metodo initialize=================================================================================================================
    @FXML
    public void initialize() {
        comboBoxTipoUsuario.getItems().addAll("Administrador", "Cliente");
        comboBoxTipoUsuario.valueProperty().addListener((observable, oldValue, newValue) -> {
            tipoUsuario = newValue.toString();
            System.out.println(tipoUsuario);
        });
    }

    //metodo para cerrar la ventana actual===================================================================================================
    public void cerrarVentanaActual(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //metodo para darle los datos al formulario de editar=====================================================================================
    public void setData(ItemCardInterface item) {
        usuario = (Usuario) item;
        txtNombre.setText(usuario.getNombre());
        txtNombreUsuario.setText(usuario.getNombreUsuario());
        txtEdad.setText(String.valueOf(usuario.getEdad()));
        txtTelefono.setText(usuario.getTelefono());
        txtCorreo.setText(usuario.getCorreo());
        pswContraseña.setPromptText("Para cambiar la contraseña ingresa la nueva aqui...");
        String nombreTipoUsuario = usuarioDAO.obtenerNombreRolPorId(usuario.getIdRol());
        //obtiene el modelo, es decir, los items del checkbox,
        // el .select es para seleccionar uno de los elementos de acuerdo al índice y se le asigna de una al combobox
        comboBoxTipoUsuario.getSelectionModel().select(nombreTipoUsuario);
        tipoUsuario = nombreTipoUsuario;
    }

    //metodo para el boton de guardar========================================================================================
    @FXML
    public void updateUsuario() {
        int idRol = usuarioDAO.obtenerIdRolPorNombre(tipoUsuario);
        Usuario usuarioModificado;
        System.out.println(usuario);
        //en el if va a registrar, aqui empiea el registro---------------------------------------------------
        if (banderaRegistrar) {
            String contraseñaNormalRegistro;
            String contraseñaHasheadaRegistro;
            contraseñaNormalRegistro = pswContraseña.getText();
            contraseñaHasheadaRegistro = passwordHash(contraseñaNormalRegistro);

            usuarioModificado = new Usuario(
                    txtNombre.getText(),
                    Integer.parseInt(txtEdad.getText()),
                    txtNombreUsuario.getText(),
                    txtTelefono.getText(),
                    txtCorreo.getText(),
                    contraseñaHasheadaRegistro,
                    idRol
            );
            if (usuarioDAO.insertarUsuario(usuarioModificado, idRol)) {
                MensajesVista.mostrarMensajeExito("Exito", "Simon registro");
            } else {
                MensajesVista.mostrarMensajeError("Exito", "Nel registro");
            }
            //aqui termina el registro--------------------------------------------------------------
        } else {
            //y en este else quiere decir que va a actualizar
            //este if es para si el usuario registro nueva contraseña
            if (!pswContraseña.getText().equals("")) {
                //añadí estas dos variables para hashear la contraseña cuando se registre el nuevo usuairo desde el admin
                String contraseñaNormal;
                String contraseñaHasheada;
                contraseñaNormal = pswContraseña.getText();
                contraseñaHasheada = passwordHash(contraseñaNormal);
                usuarioModificado = new Usuario(
                        usuario.getIdUsuario(),
                        txtNombre.getText(),
                        Integer.parseInt(txtEdad.getText()),
                        txtNombreUsuario.getText(),
                        txtTelefono.getText(),
                        txtCorreo.getText(),
                        contraseñaHasheada,
                        idRol
                );
                if (usuarioDAO.updateUsuarioContraseña(usuarioModificado)) {
                    MensajesVista.mostrarMensajeExito("Exito", "Usuario modificado exitosamente");
                } else {
                    MensajesVista.mostrarMensajeError("Error", "Usuario no modificado, ocurrio un error");
                }
            } else {
                //si no modificó la contraseña entra aqui
                usuarioModificado = new Usuario(
                        usuario.getIdUsuario(),
                        txtNombre.getText(),
                        Integer.parseInt(txtEdad.getText()),
                        txtNombreUsuario.getText(),
                        txtTelefono.getText(),
                        txtCorreo.getText(),
                        idRol
                );
                if (usuarioDAO.updateUsuario(usuarioModificado)) {
                    MensajesVista.mostrarMensajeExito("Exito", "Usuario modificado exitosamente");
                } else {
                    MensajesVista.mostrarMensajeError("Error", "Usuario no modificado, ocurrio un error");
                }
            }
        }
    }

    //este metodo es para que el boton de guardar sepa si es de registro o no
    //ya que se carga la misma interfaz que para el update
    public void isRegistrar(boolean vamohAregistrar) {
        banderaRegistrar = vamohAregistrar;
        btnGuardar.setText("Registrar");
        pswContraseña.setPromptText("Ingresa la contraseña");
    }

    //metodo para hashear la contraseña con ByCrypt==========================================================================
    public String passwordHash(String psw) {
        return BCrypt.hashpw(psw.trim(), BCrypt.gensalt());
    }
}
