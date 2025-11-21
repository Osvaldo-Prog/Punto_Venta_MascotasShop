package org.example.puntoventamascotas.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.UsuarioDAO;
import org.example.puntoventamascotas.Models.Usuario;
import org.example.puntoventamascotas.Util.MensajesVista;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class MainController {
    @FXML private ImageView imagenMascotasPrincipal;
    @FXML private Label labelLogin;
    @FXML private Button btonCerrar;
    @FXML private TextField textFieldUsername;
    @FXML private TextField passwFieldContraseña;
    private UsuarioDAO usuarioDAO;

    public MainController(){
        this.usuarioDAO = new UsuarioDAO(ConexionMsql.getConnection());
    }

    //metodo para abrir la ventana de crear una cuenta======================================================================================
    @FXML protected void abrirVentanaCrearCuenta(){
        try{
            //Se crea un FXMLoader para cargar la ventana enlazada
            FXMLLoader loaderVentanaCrearCuenta = new FXMLLoader(getClass().getResource("/Views/VentanaCrearCuenta.fxml"));
            //Es como decir: "Carga mi archivo FXML y dame el panel principal"
            Parent root = loaderVentanaCrearCuenta.load();

            //crear la nueva escena y poner lo que ya se cargo dentro
            Scene sceneVentanaCrearCuenta = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Crear Cuenta");
            stage.setScene(sceneVentanaCrearCuenta);

            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //metodo para cerrar la ventana actual=====================================================================
    public void cerrarVentanaActual(ActionEvent event) {
        // Obtener el stage actual desde el evento
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //METODOS DEL INICIO DE SESION----------------------------------------------------------------------------------------------------
    //como el logeo esta en la misma ventana principal
    // pues todo su control sera desde este controlador========================================================

    //metodo para validar si existe el usuario y de el acceso
    // es boleano para de aqui ver si se abre la nueva ventana dependiendo quien inice sesion
    public String procesarLogeo(){
            //listamos todos los usarios llamando la consulta con el dao
            List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();
            //validamos usuario y contraseña
            for (Usuario usuario : listaUsuarios) {
                //validamos que coinciden
                boolean userNameValido = usuario.getNombreUsuario().equals(textFieldUsername.getText().trim());
                boolean passwValido = BCrypt.checkpw(passwFieldContraseña.getText(), usuario.getContraseña());
                //validamos que existen
                if(userNameValido && passwValido){
                    String rolUsuario = usuarioDAO.obtenerRolPorUsuario(usuario.getIdUsuario());
                    //este sout me trae el rol del usuario que inicio sesion
                    System.out.println(rolUsuario);

                    //si el usuario no tiene rol retorna nulo
                    if(rolUsuario == null){
                        return "Sin Rol";
                    }
                    return rolUsuario;
                }
            }
        return "Nombre de usuario o contraseña incorrectos";
    }


    //metodo para validar quien inicia sesion y ver que interfaz de abrirá==========================================================================
    //********aun falta validar quien inicio sesion********
    public void abrirVentanaDependiente(){
        boolean sonVacios = camposVacios(textFieldUsername, passwFieldContraseña);
        String rolUsuario = procesarLogeo();
        if(sonVacios){
            MensajesVista.mostrarMensajeError("Error", "No se pudo iniciar sesión, todos los campos son obligatorios");
        } else if(rolUsuario.equals("Nombre de usuario o contraseña incorrectos")){
            MensajesVista.mostrarMensajeError("Error", "Nombre de usuario o contraseña incorrectos");
        } else if(rolUsuario.equals("Sin Rol")){
            MensajesVista.mostrarMensajeError("Error", "Usuario Sin Rol");
        }else if(rolUsuario.equalsIgnoreCase("Cliente")){
            try{
                //Se crea un FXMLoader para cargar la ventana enlazada
                FXMLLoader loaderVentanaCliente = new FXMLLoader(getClass().getResource("/Views/VentanaLoginCliente.fxml"));
                //Es como decir: "Carga mi archivo FXML y dame el panel principal"
                Parent root = loaderVentanaCliente.load();

                //Se inicializa la ventana
                Scene sceneVentanaCliente = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Menu de productos/mascotas como cliente");
                stage.setScene(sceneVentanaCliente);
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(rolUsuario.equalsIgnoreCase("Administrador")){
            try{
                //se crea el fxml para cargar la ventana
                FXMLLoader loaderVentanaAdministrador = new FXMLLoader(getClass().getResource("/Views/VentanaLoginAdministrador.fxml"));
                //para que el root cargue bien la ventana
                Parent root = loaderVentanaAdministrador.load();

                //inicializar la ventana
                Scene sceneVentanaAdministrador = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Menu de productos/mascotas como administrador");
                stage.setScene(sceneVentanaAdministrador);
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }

        }else{
            MensajesVista.mostrarMensajeError("Error", "No se pudo iniciar sesión, verifica tu nombre de usuario o contraseña");
        }
    }



    //metodo para validar campos vacios===========================================================================
    private boolean camposVacios(TextField textFieldUsername, TextField passwFieldContraseña){
        if(textFieldUsername.getText().isEmpty() || passwFieldContraseña.getText().isEmpty()){
            return true;
        }
        return false;
    }

    //Metodo sencillo para validar si existe el nombre de usuario=====================================================================================
    public boolean existeNombreUsuario(String textFieldNombreUsuario) {
        return usuarioDAO.nombreUsuarioExiste(textFieldNombreUsuario);
    }


    //metodo para validar coincidencia de las contraseñas=====================================================================================
    public boolean sonIgualesContraseñas(String passFieldContraseña, String passFieldConfirContraseña){
        if(passFieldContraseña.equals(passFieldConfirContraseña)){
            return true;
        }
        return false;
    }

}
