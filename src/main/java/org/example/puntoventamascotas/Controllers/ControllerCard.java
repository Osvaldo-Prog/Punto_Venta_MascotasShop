package org.example.puntoventamascotas.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Mascota;
import org.example.puntoventamascotas.Models.Producto;
import org.example.puntoventamascotas.Models.Usuario;

import java.util.function.Consumer;

/*En este controlador se controla el card, dado el caso
 * se controlan sus botones directemente desde aquí
 * con los Consumer*/
public class ControllerCard {
    @FXML
    private VBox VboxCard;
    @FXML
    private Label labelNombre;
    @FXML
    private ImageView imageView;
    @FXML
    private Button botonComprar;
    @FXML
    private Button botonInfo;
    @FXML
    private Label labelPrecio;
    @FXML
    private Label lblTelefono;
    @FXML
    private Label labelStockProducto;
    @FXML
    private Button btnEdicion;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEliminarDeCarrito;
    @FXML
    private Button btnAgregarToCarrito;
    @FXML
    private HBox hBoxBtnEdicion;
    /* Estos Consumer son para delegador de acciones o funciones, en otras palabras
    * mantiene el contexto de los objetos que se le pasan
    * estos nos ayudan para guardar la informacion del card seleccionado*/
    private Consumer<ItemCardInterface> onEdicion;
    private Consumer<ItemCardInterface> onComprar;
    private Consumer<ItemCardInterface> onInfo;
    private Consumer<ItemCardInterface> onEliminar;
    private Consumer<ItemCardInterface> onAddItemToCart;
    private Consumer<ItemCardInterface> onEliminarDeCarrito;
    private ItemCardInterface itemCard;


    //metodo para darle getIdTipoProducto datos a la card(de forma generica entre mascotas, productos y usuarios)========================================================
    public void setData(ItemCardInterface item, String tipoUsuario) {
        this.itemCard = item;
        lblTelefono.setVisible(false);
        labelNombre.setText(item.getNombre());
        //try catch por si no se encuentra la imagen y muestra una por defecto
        try {
            imageView.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + item.getImagen())));
        } catch (Exception e) {
            imageView.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
        }
        labelPrecio.setText("$" + item.getPrecio());
        //con este if se valida si es producto o mascota gracias al metodo getTipo de cada clase en los modelos
        if (item.getTipo().equals("Producto")) {
            /*para solucionar el problema de que no mostraba el stock
             * hice que los items aqui fueran productos para acceder a su metodo getStock*/
            Producto productos = (Producto) item;
            botonComprar.setText("Comprar");
            labelStockProducto.setVisible(true);
            labelStockProducto.setText(productos.getStock() + " Disponibles");
        } else if (item.getTipo().equals("Mascota")) {
            botonComprar.setText("Adoptar");
            labelStockProducto.setVisible(false);
        }

        //pasa lo mismo que en el caso de arriba
        if (tipoUsuario.equals("Administrador")) {
            if (item.getTipo().equals("Usuario")) {
                Usuario usuario = (Usuario) item;
                btnAgregarToCarrito.setVisible(false);
                lblTelefono.setVisible(true);
                botonComprar.setVisible(false);
                botonInfo.setVisible(false);
                labelNombre.setText(usuario.getNombreUsuario());
                labelPrecio.setText(usuario.getEdad() + " años");
                lblTelefono.setText("Celular: " + usuario.getTelefono());
                labelStockProducto.setVisible(true);
                //se le asigna una imagen por defecto solo para aue aparezcan en el card ya que no se cargan imagenes pa usuario
                imageView.setImage(new Image(getClass().getResourceAsStream("/Imagenes/user.png")));
                labelStockProducto.setText(usuario.getCorreo());
            }
            btnAgregarToCarrito.setVisible(false);
            btnEliminarDeCarrito.setVisible(false);
            botonComprar.setVisible(false);
            hBoxBtnEdicion.setVisible(true);
            btnEdicion.setVisible(true);
            btnEliminar.setVisible(true);
        } else if (tipoUsuario.equals("Cliente")) {
            btnAgregarToCarrito.setVisible(true);
            btnEdicion.setVisible(false);
            btnEliminarDeCarrito.setVisible(false);
            btnEliminar.setVisible(false);
        } else if (tipoUsuario.equals("Carrito")) {
            if (item.getTipo().equals("Mascota")) {
                Mascota mascota = (Mascota) item;
                lblTelefono.setText("Cantidad: " + mascota.getCantidad());
                btnEliminarDeCarrito.setVisible(true);
            } else if (item.getTipo().equals("Producto")) {
                Producto producto = (Producto) item;
                lblTelefono.setText("Cantidad: " + producto.getCantidad());
            }
            btnEdicion.setVisible(false);
            btnEliminar.setVisible(false);
            btnAgregarToCarrito.setVisible(false);
            botonComprar.setVisible(false);
            botonInfo.setVisible(false);
            lblTelefono.setVisible(true);
            labelStockProducto.setVisible(false);
        }
    }

    //este metodo se llama desde controllerEspacioEdicionMascotas y de productos
    // ya que este dara la informacion de lo que contienen los cards======================================================================================
    public void setOnEditar(Consumer<ItemCardInterface> onEdicion) {
        this.onEdicion = onEdicion;
    }

    //lo mismo================================================================================================================
    public void setOnEliminar(Consumer<ItemCardInterface> onEliminar) {
        this.onEliminar = onEliminar;
    }

    //lo mismo con este otro===============================================================================================================================
    public void setOnComprar(Consumer<ItemCardInterface> onComprar) {
        this.onComprar = onComprar;
    }

    //lo mismo que el anterior=================================================================================================================
    public void setOnInfo(Consumer<ItemCardInterface> onInfo) {
        this.onInfo = onInfo;
    }

    //lo mismo que lo anterior
    public void setAddItemToCart(Consumer<ItemCardInterface> onAddItemToCart) {
        this.onAddItemToCart = onAddItemToCart;
    }

    //lo mismo que el anterior
    public boolean setOnEliminarDeCarrito(Consumer<ItemCardInterface> onEliminarDeCarrito) {
        this.onEliminarDeCarrito = onEliminarDeCarrito;
        return true;
    }

    //===========================================================================================================================================
    @FXML
    public void initialize() {
        //aqui esta el boton edicion y al momento de presionarlo trae la info de cada card creado
        btnEdicion.setOnAction(e -> {
            if (onEdicion != null) {
                //el .accept ejecuta el customer, es como el onAction de boton
                onEdicion.accept(this.itemCard);
            }
        });

        btnEliminar.setOnAction(e -> {
            if (onEliminar != null) {
                onEliminar.accept(this.itemCard);
            }
        });

        botonComprar.setOnAction(e -> {
            if (onComprar != null) {
                onComprar.accept(this.itemCard);
            }
        });

        botonInfo.setOnAction(e -> {
            if (onInfo != null) {
                onInfo.accept(this.itemCard);
            }
        });

        btnAgregarToCarrito.setOnAction(e -> {
            if (onAddItemToCart != null) {
                onAddItemToCart.accept(this.itemCard);
            }
        });

        btnEliminarDeCarrito.setOnAction(e -> {
            System.out.println("Funciona el boton");
            if (onEliminarDeCarrito != null) {
                onEliminarDeCarrito.accept(this.itemCard);
            }
        });
    }
}
