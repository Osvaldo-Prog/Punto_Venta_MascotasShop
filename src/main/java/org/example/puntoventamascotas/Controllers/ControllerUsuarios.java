package org.example.puntoventamascotas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.UsuarioDAO;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Usuario;
import org.example.puntoventamascotas.Util.MensajesVista;
import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Pattern;

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
    //variable de expresion regular para que el correo sea correcto
    final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";
    //este es para corroborar que el correo tenga los caracteres que son
    final Pattern EMAIL_PATTERN = Pattern.compile(emailRegex);
    //String que guarda lo que debe llevar la contraseña, igual con expresión regular
    final String contraseñaRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
    final Pattern contraseñaPattern = Pattern.compile(contraseñaRegex);


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
            boolean sonCamposVacios = sonVacios(txtNombre, txtNombreUsuario, pswContraseña, txtEdad, txtCorreo, txtTelefono);
            boolean existeUsuario = existeNombreUsuario(txtNombreUsuario.getText());
            boolean existeCorreo = existeCorreoElectronico(txtCorreo.getText());
            boolean emailValido = validarEmail(txtCorreo);
            boolean esContraseñaSegura = validarFortalezaContraseña(pswContraseña);
            boolean existeTelefono = existeTelefonoUsuario(txtTelefono.getText());
            if (sonCamposVacios) {
                MensajesVista.mostrarMensajeError("Error", "Todos los campos son obligatorios");
            } else if (existeUsuario) {
                MensajesVista.mostrarMensajeError("Error", "El nombre de usuario existe");
            } else if (!esContraseñaSegura) {
                MensajesVista.mostrarMensajeError("Error", "La contraseña no es segura");
            } else if (!emailValido) {
                MensajesVista.mostrarMensajeError("Error", "El correo no tiene la estructura válida");
            } else if (existeCorreo) {
                MensajesVista.mostrarMensajeError("Error", "El correo ya está registrado");
            } else if (existeTelefono) {
                MensajesVista.mostrarMensajeError("Error", "El teléfono ya está registrado");
            } else {

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
                    MensajesVista.mostrarMensajeExito("Exito", "Se registró correctamente");
                } else {
                    MensajesVista.mostrarMensajeError("Exito", "Hubo un error en el registro");
                }
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

    //Metodo para validar que los campos no estan vacios========================================================================
    /*Usado en:
     * Metodo de procesarRegistro();*/
    public boolean sonVacios(TextField txtNombre, TextField txtNombreUsuario,
                             PasswordField pswContraseña, TextField txtCorreo,
                             TextField txtEdad, TextField txtTelefono) {
        //Verifica si al menos un campo esta vacio
        if (txtNombre.getText().trim().isEmpty() || txtNombreUsuario.getText().trim().isEmpty()
                || pswContraseña.getText().trim().isEmpty() || txtEdad.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty()
                || txtCorreo.getText().trim().isEmpty() || comboBoxTipoUsuario.getValue() == null) {
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
    public boolean existeCorreoElectronico(String textFieldCorreoElectronico) {
        return usuarioDAO.correoExistente(textFieldCorreoElectronico);
    }

    //metodo para validar el email============================================================================================================
    public boolean validarEmail(TextField email) {
        if (email.getText() == null) {
            return false;
        }
        /*este return es para que con el matcher valide lo que tenia el textfield del email
        el EMAIL_PATTERN es la variable que tiene ya guardado el patron de la exp regular
        el matches es un metodo boleano del matcher y si coincide lo comparado retorna true
        matcher -> es un objeto que compara la expresión regular con un texto específico.
         */
        return EMAIL_PATTERN.matcher(email.getText()).matches();
    }

    //Metodo para validar la fortaleza de la contraseña===========================================================================================
    public boolean validarFortalezaContraseña(TextField password) {
        if (password.getText() == null) {
            return false;
        }
        return contraseñaPattern.matcher(password.getText().trim()).matches();
    }

    //Metodo para verificar si el numero de telefono existe=========================================================================================
    public boolean existeTelefonoUsuario(String textFieldTelefonoUsuario) {
        return usuarioDAO.existeNumeroTelefono(textFieldTelefonoUsuario);
    }
}
