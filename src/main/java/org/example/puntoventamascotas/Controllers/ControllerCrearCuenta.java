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
//este import es una manera de hashear la contraseña
import org.mindrot.jbcrypt.BCrypt;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.util.regex.Pattern;

public class ControllerCrearCuenta {
    //iniciar la conexion
    private UsuarioDAO usuarioDAO;
    //variable de expresion regular para que el correo sea correcto
    final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";
    //este es para corroborar que el correo tenga los caracteres que son
    final Pattern EMAIL_PATTERN = Pattern.compile(emailRegex);
    //String que guarda lo que debe llevar la contraseña, igual con expresión regular
    final String contraseñaRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
    final Pattern contraseñaPattern = Pattern.compile(contraseñaRegex);



    //constructor para establecer la conexion
    public ControllerCrearCuenta() {
        this.usuarioDAO = new UsuarioDAO(ConexionMsql.getConnection());
    }

    //inicializar los componentes con el @FXML ya que vienen de scene builder (archivo VentanaCrearCuenta.fxml)
    @FXML private TextField textFieldNombreCompleto;
    @FXML private TextField textFieldNombreUsuario;
    @FXML private PasswordField passFieldContraseña;
    @FXML private PasswordField passFieldConfirContraseña;
    @FXML private TextField textFieldCorreoElectronico;
    @FXML private TextField textFieldEdad;
    @FXML private TextField textFieldNumero;
    @FXML private Button btonCancelar;
    @FXML private Button botonRegistrarse;


    //metodo para cerrar la ventana actual=========================================================================
    @FXML
    private void cerrarVentana(ActionEvent actionEvent) {
        //obtener el stage actual para cerrar
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    //================================================metodo para registrar un usuario====================================================================================
    /*Y este no se usa directamente, este se manda a llamar en el metodo de procesarRegistro(); ya que ese tiene las validaciones*/
    @FXML
    private boolean registrarUsuario(){
        //este solo inserta el usuario
        try {
            //Crear el objeto de usuario e insertarle todo lo que tengan los textfield
            Usuario usuario = new Usuario();
            usuario.setNombre(textFieldNombreCompleto.getText().trim());
            usuario.setNombreUsuario(textFieldNombreUsuario.getText().trim());
            //Contraseña hasheada con ByCrypt
            String contraseñaNormal = passFieldContraseña.getText().trim();
            String contraseñaHasheada = passwordHash(contraseñaNormal);
            usuario.setContraseña(contraseñaHasheada.trim());
            usuario.setCorreo(textFieldCorreoElectronico.getText().trim());
            usuario.setEdad(Integer.parseInt(textFieldEdad.getText().trim()));
            usuario.setTelefono(textFieldNumero.getText().trim());

            //llamar al metodo de insercion a la base de datos del dao
            boolean exito = usuarioDAO.insertarUsuario(usuario);
            if (exito) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            MensajesVista.mostrarMensajeError("Error desconcido", "Ha ocurrido un error desconocido");
            e.printStackTrace();
            return false;
        }
    }


    //metodo para hashear la contraseña con ByCrypt==========================================================================
    public String passwordHash(String passFieldContraseña) {
        return BCrypt.hashpw(passFieldContraseña.trim(), BCrypt.gensalt());
    }


    //Metodo para limpiar los campos solamente====================================================================================
    public void limpiarCamposCrearCuenta(TextField textFieldNombreCompleto, TextField textFieldNombreUsuario,
                                         PasswordField passFieldContraseña, PasswordField passFieldConfirContraseña, TextField textFieldCorreoElectronico,
                                         TextField textFieldEdad, TextField textFieldNumero) {
        textFieldNombreCompleto.clear();
        textFieldNombreUsuario.clear();
        passFieldContraseña.clear();
        passFieldConfirContraseña.clear();
        textFieldCorreoElectronico.clear();
        textFieldEdad.clear();
        textFieldNumero.clear();
    }

    //Metodo que procesa el registro, este se creó para hacer las validaciones totales del registro================================================================
    public void procesarRegistro() {
        //hacer todas las validaciones para concatenarle una variable***************************************************************
        boolean sonCamposVacios = sonVacios(textFieldNombreCompleto, textFieldNombreUsuario,
                passFieldContraseña, passFieldConfirContraseña, textFieldCorreoElectronico,
                textFieldEdad, textFieldNumero);
        boolean existeNombreUsuario = existeNombreUsuario(textFieldNombreUsuario.getText());
        boolean sonIguales = sonIgualesContraseñas(passFieldContraseña.getText(), passFieldConfirContraseña.getText());
        boolean esCorreoValido = validarEmail(textFieldCorreoElectronico);
        boolean esContraseñaSegura = validarFortalezaContraseña(passFieldContraseña);
        boolean existeCorreo = existeCorreoElectronico(textFieldCorreoElectronico.getText());
        boolean existeTelefono = existeTelefonoUsuario(textFieldNumero.getText());

        //hacer las validaciones
        if (sonCamposVacios) {
            MensajesVista.mostrarMensajeError("Error", "Todos los campos son obligatorios");
        }else if (existeNombreUsuario) {
            MensajesVista.mostrarMensajeError("Error", "El nombre de usuario existe");
        }else if(!sonIguales) {
            MensajesVista.mostrarMensajeError("Error","Las contraseñas no coinciden");
        }else if(!esContraseñaSegura){
            MensajesVista.mostrarMensajeError("Error", "La contraseña no es segura");
        }else if(!esCorreoValido) {
            MensajesVista.mostrarMensajeError("Error", "El correo no tiene la estructura válida");
        }else if(existeCorreo){
            MensajesVista.mostrarMensajeError("Error", "El correo ya está registrado");
        }else if(existeTelefono){
            MensajesVista.mostrarMensajeError("Error", "El teléfono ya está registrado");
        }else{
            //Despues de validar todo ahora si hace la insersción
            boolean registroExitoso = registrarUsuario();
            if (registroExitoso) {
                MensajesVista.mostrarMensajeExito("Exito en la inserción", "Se registró el usuario como cliente");
                //llamar al metodo del controlador para limpiar los campos una vez ya se insertó un usuario
                limpiarCamposCrearCuenta(textFieldNombreCompleto, textFieldNombreUsuario, passFieldContraseña, passFieldConfirContraseña, textFieldCorreoElectronico, textFieldEdad, textFieldNumero);
            } else {
                MensajesVista.mostrarMensajeError("Error de registro", "Ocurrió un error en el registro");
            }
        }
    }

    //Metodo para validar que los campos no estan vacios========================================================================
    /*Usado en:
     * Metodo de procesarRegistro();*/
    public boolean sonVacios(TextField textFieldNombreCompleto, TextField textFieldNombreUsuario,
                             PasswordField passFieldContraseña, PasswordField passFieldConfirContraseña, TextField textFieldCorreoElectronico,
                             TextField textFieldEdad, TextField textFieldNumero) {
        //Verifica si al menos un campo esta vacio
        if (textFieldNombreCompleto.getText().trim().isEmpty() || textFieldNombreUsuario.getText().trim().isEmpty()
                || passFieldContraseña.getText().trim().isEmpty() || passFieldConfirContraseña.getText().trim().isEmpty()
                || textFieldEdad.getText().trim().isEmpty() || textFieldNumero.getText().trim().isEmpty()) {
            //retornar verdadero de que son vacios
            return true;
        }
        //y falso porque no son vacios
        return false;
    }

    //Metodo sencillo para validar si existe el nombre de usuario=====================================================================================
    public boolean existeNombreUsuario(String textFieldNombreUsuario) {
        return usuarioDAO.nombreUsuarioExiste(textFieldNombreUsuario);
    }

    //Metodo sencillo para validar si existe el email=================================================================================================
    public boolean existeCorreoElectronico(String textFieldCorreoElectronico){
        return  usuarioDAO.correoExistente(textFieldCorreoElectronico);
    }

    //metodo para validar coincidencia de las contraseñas=====================================================================================
    public boolean sonIgualesContraseñas(String passFieldContraseña, String passFieldConfirContraseña){
        if(passFieldContraseña.equals(passFieldConfirContraseña)){
            return true;
        }
        return false;
    }

    //metodo para validar el email============================================================================================================
    public boolean validarEmail(TextField email) {
        if (email.getText() == null){
            return false;
        }
        /*este return es para que con el matcher valide lo que tenia el textfield del email
        el EMAIL_PATTERN es la variable que tiene ya guardado el patron de la exp regular
        el matches es un metodo boleano del matcher y si coincide lo comparado reyorna true
        matcher -> es un objeto que compara la expresión regular con un texto específico.
         */
        return EMAIL_PATTERN.matcher(email.getText()).matches();
    }

    //Metodo para validar la fortaleza de la contraseña===========================================================================================
    public boolean validarFortalezaContraseña(TextField password) {
        if (password.getText() == null){
            return false;
        }
        return contraseñaPattern.matcher(password.getText().trim()).matches();
    }

    //Metodo para verificar si el numero de telefono existe=========================================================================================
    public boolean existeTelefonoUsuario(String textFieldTelefonoUsuario) {
        return usuarioDAO.existeNumeroTelefono(textFieldTelefonoUsuario);
    }

}

