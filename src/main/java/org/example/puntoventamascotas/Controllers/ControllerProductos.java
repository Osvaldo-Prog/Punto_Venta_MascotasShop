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
import org.example.puntoventamascotas.DAO.ProductosDAO;
import org.example.puntoventamascotas.Models.ItemCardInterface;
import org.example.puntoventamascotas.Models.Producto;
import org.example.puntoventamascotas.Models.TipoProducto;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.io.File;

public class ControllerProductos {
    @FXML TextField txtNombre;
    @FXML TextArea txtDescripcion;
    @FXML TextField txtPrecio;
    @FXML TextField txtStock;
    @FXML ImageView imgSubir;
    @FXML ComboBox<String> comboBoxTipoProducto;
    @FXML Button btnGuardar;
    String tipoProducto;
    Producto productos;
    ProductosDAO productosDAO;
    String nombreImagen = "";
    boolean banderaRegistrar = false;


    public ControllerProductos() {
        this.productosDAO = new ProductosDAO(ConexionMsql.getConnection());
    }

    //metodo initialize======================================================================================================
    @FXML
    public void initialize(){
        comboBoxTipoProducto.getItems().addAll("Alimento",
                                                     "Higiene",
                                                     "Accesorio");
        comboBoxTipoProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            tipoProducto = newValue.toString();
            System.out.println(tipoProducto);
        });
    }

    //metodo para cerrar la ventana actual===================================================================================================
    public void cerrarVentanaActual(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    //metodo para asignarle los datos al formulario de productos=============================================================================
    //aqui en el set data no me muestra el tipo de producto diferente de alimento y tampcoo me trae la
    //descripcion de las otras cards
    public void setData(ItemCardInterface item){
        productos = (Producto)item;
        TipoProducto tipoProductoConsulta = productosDAO.obtenerNombreTipoProductoPorId(productos.getIdTipo());
        //asignarle todos los valores que tiene el card al formulario
        txtNombre.setText(productos.getNombre());
        txtDescripcion.setText(productos.getDescripcion());
        txtPrecio.setText("" + productos.getPrecio());
        txtStock.setText("" + productos.getStock());
        //con esta linea se obtiene el nombre del tipo de producto de acuerdo al nombre de tipo producto
        comboBoxTipoProducto.getSelectionModel().select(tipoProductoConsulta.getNombreTipoProducto());
        //pasarle el nombre por la consulta
        tipoProducto = tipoProductoConsulta.getNombreTipoProducto();
        //try catch para cargar tambien la imagen y por si no se encuentra la imagen, muestra una por defecto
        try{
            imgSubir.setImage(new Image(getClass().getResourceAsStream("/Imagenes/" + item.getImagen())));
        }catch(Exception e){
            imgSubir.setImage(new Image(getClass().getResourceAsStream("/Imagenes/notFound.jpg")));
            e.printStackTrace();
        }
    }

    //metodo para hacer que habra el explorador de archivos y cargar una imagen=============================================================================
    @FXML
    public void cargarImagen(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        //el "*.jpg" es para que el expl, de archivos ya no detecte imagenes que no sean jpg
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagen", "*"));
        //aqui el filechoser manda a llamar a cualquier nodo solo para conseguir la escena
        File file = fileChooser.showOpenDialog(txtNombre.getScene().getWindow());
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

    //metodo para actualizar el producto (Lo llama el boton)
    // pero a su vez tiene el registro de uno nuevo
    // esto se da gracias a una bandera=============================================================================
    @FXML
    public void updateProducto(){
        Producto productoModificado;
        //Se necesitó crear un metodo para obtener el id de tipoProducto segun el nombre del tipoProducto (Alimentos)
        TipoProducto idTipoProducto = productosDAO.obtenerTipoProductoByNombre(tipoProducto);
        if(banderaRegistrar){
            //aqui comienza el registro-------------------------------------------------------------------------------
            //aqui no se necesita el id porque es autoincrement entonces se añade solo
            productoModificado = new Producto(
                    txtNombre.getText(),
                    txtDescripcion.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText()),
                    /*Si el usuario no selecciona ninguna imagen entonces vamos a regresar el nombre
                     * de la imagen que ya existe en la DB y si ingresa una nueva imagen, dicho
                     * nombre de imagen se pondrá en la DB*/
                    nombreImagen.equals("") ? productos.getImagen() : nombreImagen,
                    //aqui solo se manda el id al modelo de producto porque es lo que se necesita en la BD
                    idTipoProducto.getIdTipoProducto()
            );
            if(productosDAO.insertarProducto(productoModificado)){
                MensajesVista.mostrarMensajeExito("Exito", "Producto añadido con éxito");
            }else{
                MensajesVista.mostrarMensajeError("Error", "No se pudo añadir el producto");
            }
        //Aqui termina el registro--------------------------------------------------------------------------------------------------
        }else {
            //este else quiere decir que es una actualizacion
            productoModificado = new Producto(
                    productos.getId(),
                    txtNombre.getText(),
                    txtDescripcion.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText()),
                    //aqui solo se manda el id al modelo de producto porque es lo que se necesita en la BD
                    idTipoProducto.getIdTipoProducto(),
                    /*Si el usuario no selecciona ninguna imagen entonces vamos a regresar el nombre
                     * de la imagen que ya existe en la DB y si ingresa una nueva imagen, dicho
                     * nombre de imagen se pondrá en la DB*/
                    nombreImagen.equals("") ? productos.getImagen() : nombreImagen
            );
            if (productosDAO.updateProducto(productoModificado)) {
                MensajesVista.mostrarMensajeExito("Exito", "Producto Modificado correctamente");
            } else {
                MensajesVista.mostrarMensajeError("Error", "Hubo un error en la modificación");
            }
        }
    }

    //con este metodo validara si registrara
    //y lo unico que hace es cambiar el texto del boton de guardar a registrar ya que se usa la misma UI
    public void isRegistrar(boolean vamohRegistrar){
        banderaRegistrar = vamohRegistrar;
        btnGuardar.setText("Registrar");
    }
}
