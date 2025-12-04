package org.example.puntoventamascotas.Controllers;

import com.sun.security.jgss.GSSUtil;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.puntoventamascotas.DAO.ConexionMsql;
import org.example.puntoventamascotas.DAO.MascotaDAO;
import org.example.puntoventamascotas.DAO.ProductosDAO;
import org.example.puntoventamascotas.Models.*;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/* llegar a gc2*/


public class ControladorLoginCliente {
    MascotaDAO mascotaDAO;
    ProductosDAO productosDAO;
    String tipoCategoria;
    String tipoArea;
    Usuario usuario;

    //inicializacion de los nodos de fxml
    @FXML
    private Label labelPrecioBeagle;
    @FXML
    private ComboBox<String> comboBoxMasc_Prod;
    @FXML
    private ComboBox<String> comboBoxTipoMasc_Prod;
    @FXML
    private FlowPane flowPaneCard;
    @FXML
    ScrollPane scrollPaneCards;
    @FXML
    ScrollPane scrollPaneMenuCliente;
    @FXML
    AnchorPane anchorPaneClienteMenu;
    Carrito<Mascota> carritoMascotas = new Carrito();
    Carrito<Producto> carritoProductos = new Carrito();


    public ControladorLoginCliente() {
        this.mascotaDAO = new MascotaDAO(ConexionMsql.getConnection());
        this.productosDAO = new ProductosDAO(ConexionMsql.getConnection());
    }


    //lo que este en este metodo se cargará una vez se carge el fxml loader=====================================================================================
    @FXML
    public void initialize() {
        comboBoxMasc_Prod.getItems().addAll("Mascotas",
                "Productos");
        comboBoxTipoMasc_Prod.getItems().addAll("Perros", "Gatos", "Aves", "Peces");

        /*Aqui se crea un listener con observable para cachar lo que tenga el combobox en tiempo real de la categoria*/
        comboBoxMasc_Prod.valueProperty().addListener((observable, oldValue, newValue) -> {
            tipoCategoria = newValue.toString();
            if (tipoCategoria.equals("Mascotas")) {
                try {
                    ObservableList<Mascota> mascotasListaObs = FXCollections.observableArrayList(mascotaDAO.listarMascotasByArea(tipoArea));
                    crearCard(mascotasListaObs);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //lo mismo que para mascotas
            } else if (tipoCategoria.equals("Productos")) {
                try {
                    ObservableList<Producto> productosListaObs = FXCollections.observableArrayList(productosDAO.listarProductosByArea(tipoArea));
                    System.out.println(productosListaObs);
                    crearCard(productosListaObs);
                } catch (IOException e) {
                }
            }
        });

        /*Aqui es practicamente lo mismo que el observavble con listener anterior solo que en vez de que sean las mascotas
         * solamente, es la mascota con su tipo de area*/
        comboBoxTipoMasc_Prod.valueProperty().addListener((observable, oldValue, newValue) -> {
            //aqui se asigna a tipoArea el area que se selecciona en el combobox
            tipoArea = newValue.toString();
            //validar que categoria esta en el combobox
            if (tipoCategoria.equals("Mascotas")) {
                /*se asigna la lista tipo Observable pero con una consulta a la base de datos dependiendo el tipoArea
                que ya tiene el combobox y se pasa como parametro para que la consulta trabaje*/
                ObservableList<Mascota> mascotasListaObs = FXCollections.observableArrayList(mascotaDAO.listarMascotasByArea(tipoArea));
                try {
                    crearCard(mascotasListaObs);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //exactamente lo mismo para productos
            } else if (tipoCategoria.equals("Productos")) {
                ObservableList<Producto> productosListaObs = FXCollections.observableArrayList(productosDAO.listarProductosByArea(tipoArea));
                try {
                    crearCard(productosListaObs);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        /*
        este es para que se genere los cards de mascotas una vez iniciado,
        ya que el listener no se ejecuta hastq que el combobox seleccione algo*/
        try {
            ObservableList<Mascota> mascotasListaObs = FXCollections.observableArrayList(mascotaDAO.listarMascotas());
            tipoCategoria = "Mascotas";
            crearCard(mascotasListaObs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //metodo para cerrar la ventana(simular cerrar seison)=============================================================================
    public void cerrarVentanaInicio(ActionEvent ventanaInicio) {
        //obtener el actual stage con el event
        Stage stage = (Stage) ((Node) ventanaInicio.getSource()).getScene().getWindow();
        stage.close();
    }

    //metodo para crear el card de manera generica y se le pasa un objeto "item" que decide si es mascota o producto=====================================================================================
    public void crearCard(List<? extends ItemCardInterface> items) throws IOException {
        //limpiar todo antes ya que se reemplazaran entre mascota y proudctos
        flowPaneCard.getChildren().clear();
        //dejar espaciado entre cards
        flowPaneCard.setHgap(20);
        flowPaneCard.setVgap(20);
        flowPaneCard.setPadding(new Insets(20));
        //for each para recorrer la lista obsvervable que ya tiene los elementos de la base de datos gracias al dao
        //for (int i = 0; i <= 5; i++) {
        for (ItemCardInterface item : items) {
            //un fxml loader obtener la vista de cards
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Card.fxml"));
            VBox card = loader.load();
            ControllerCard controllerCard = loader.getController();
            controllerCard.setData(item, "Cliente");
            flowPaneCard.getChildren().add(card);
            if (tipoCategoria.equals("Productos")) {
                //este llamado del metodo es donde asigna la informacion del producto
                controllerCard.setOnInfo(itemCard -> {
                    try {
                        FXMLLoader loaderInfo = new FXMLLoader(getClass().getResource("/Views/InformacionModelo.fxml"));
                        Parent root = loaderInfo.load();
                        ControllerInformacionModelo controllerInformacionModelo = loaderInfo.getController();
                        controllerInformacionModelo.setDataProductoInfo((Producto) itemCard);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else if (tipoCategoria.equals("Mascotas")) {
                //Aqui es lo mismo de arriba pero con mascotas
                controllerCard.setOnInfo(itemCard -> {
                    try {
                        FXMLLoader loaderInfo = new FXMLLoader(getClass().getResource("/Views/InformacionModelo.fxml"));
                        Parent root = loaderInfo.load();
                        ControllerInformacionModelo controllerInformacionModelo = loaderInfo.getController();
                        controllerInformacionModelo.setDataMascotaInfo((Mascota) itemCard);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            //este metodo es para cuando el boton se presiona se agrega al carrito
            controllerCard.setAddItemToCart(itemCard -> {
                if (itemCard.getTipo().equals("Mascota")) {
                    MensajesVista.mostrarMensajeTemporal("Exito", "Mascota añadida al carrito", 550);
                    /*el metodo stream() es de las listas y recorre la lista
                    El anyMatch tiene una condicion y cuando se cumple termina y devuelve true*/
                    Optional<Mascota> mascotaEncontrada = carritoMascotas.obtenerItems().stream()
                            .filter(mascota -> mascota.getId() == itemCard.getId())
                            .findFirst();
                    if (mascotaEncontrada.isPresent()) {
                        Mascota m = mascotaEncontrada.get();
                        m.setCantidad(m.getCantidad() + 1);
                    } else {
                        //Se crea una lista (carrito) generico de tipo mascota
                        //Se castea para que detecte las mascotas
                        carritoMascotas.agregarItem((Mascota) itemCard);
                    }
                } else if (itemCard.getTipo().equals("Producto")) {
                    MensajesVista.mostrarMensajeTemporal("Exito", "Producto añadido al carrito", 550);
                    //lo mismo aqui
                    Optional<Producto> productoEncontrado = carritoProductos.obtenerItems().stream()
                            .filter(producto -> producto.getId() == itemCard.getId())
                            .findFirst();
                    if (productoEncontrado.isPresent()) {
                        Producto p = productoEncontrado.get();
                        p.setCantidad(p.getCantidad() + 1);
                    } else {
                        carritoProductos.agregarItem((Producto) itemCard);
                    }
                }
            });


            controllerCard.setOnComprar(itemCard -> {
                try {
                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Views/FormularioDireccion&MetodoPago.fxml"));
                    Parent root = loader2.load();
                    ControllerFormDireccionMetodoPago controllerFormDireccionMetodoPago = loader2.getController();
                    controllerFormDireccionMetodoPago.setUsuario(usuario);
                    controllerFormDireccionMetodoPago.setData(itemCard.getPrecio());
                    controllerFormDireccionMetodoPago.setItem(itemCard);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        //}
    }

    @FXML
    public void abrirCarrito() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/VerCarrito.fxml"));
        Parent root = loader.load();
        ControllerVerCarrito controllerVerCarrito = loader.getController();
        controllerVerCarrito.crearCard(carritoMascotas.obtenerItems(), carritoProductos.obtenerItems());
        controllerVerCarrito.calcularTotal();
        controllerVerCarrito.setUsuario(usuario);
        carritoMascotas.eliminarItems();
        carritoProductos.eliminarItems();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

