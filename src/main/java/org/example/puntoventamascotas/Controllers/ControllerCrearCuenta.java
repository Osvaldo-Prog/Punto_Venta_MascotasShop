package org.example.puntoventamascotas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.UsuarioDAO;
import org.example.puntoventamascotas.Models.Usuario;
import org.example.puntoventamascotas.VistaPrincipal;
import org.mindrot.jbcrypt.BCrypt;
import org.example.puntoventamascotas.Util.MensajesVista;

public class ControllerCrearCuenta {
    //iniciar la conexion
    private UsuarioDAO usuarioDAO;
    private VistaPrincipal vistaPrincipal;


    //constructor para establecer la conexion
    public ControllerCrearCuenta() {
        this.usuarioDAO = new UsuarioDAO(ConexionMsql.getConnection());
        this.vistaPrincipal = new VistaPrincipal();
    }

    //inicializar los componentes con el @FXML ya que vienen de scene builder (archivo VentanaCrearCuenta.fxml)
    @FXML
    private TextField textFieldNombreCompleto;
    @FXML
    private TextField textFieldNombreUsuario;
    @FXML
    private PasswordField passFieldContraseña;
    @FXML
    private PasswordField passFieldConfirContraseña;
    @FXML
    private TextField textFieldCorreoElectronico;
    @FXML
    private TextField textFieldEdad;
    @FXML
    private TextField textFieldNumero;
    @FXML
    private Button btonCancelar;
    @FXML
    private Button botonRegistrarse;


    @FXML
    private void cerrarVentana(ActionEvent actionEvent) {
        //obtener el stage actual para cerrar
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    //================================================metodo para registrar un usuario====================================================================================
    @FXML
    private void registrarUsuario(ActionEvent actionEvent){
        try{
            //Crear el objeto de usuario e insertarle todo lo que tengan los textfield
            Usuario usuario = new Usuario();
            usuario.setNombre(textFieldNombreCompleto.getText().trim());
            usuario.setNombreUsuario(textFieldNombreUsuario.getText().trim());
            //Hashear la contraseña (PENDIENTE)
            usuario.setContraseña(passFieldContraseña.getText().trim());
            usuario.setCorreo(textFieldCorreoElectronico.getText().trim());
            usuario.setEdad(Integer.parseInt(textFieldEdad.getText().trim()));
            usuario.setTelefono(textFieldNumero.getText().trim());

            //llamar al metodo de insercion a la base de datos del dao
            boolean exito = usuarioDAO.insertarUsuario(usuario);

            if(exito){
                MensajesVista.mostrarMensajeExito("Exito en la inserción", "Se ha registrado con éxito el usuario cliente");
            }else{
                MensajesVista.mostrarMensajeError("Error al registrar usuario", "Ocurrió un error al registrar el usuario");
            }
        }catch(Exception e){
            MensajesVista.mostrarMensajeError("Error desconcido", "Ha ocurrido un error desconocido");
            e.printStackTrace();
        }
    }


    //metodo para hashear la contraseña
    public String passwordHash(PasswordField passFieldContraseña) {
        return BCrypt.hashpw(passFieldContraseña.getText().trim(), BCrypt.gensalt());
    }
}
