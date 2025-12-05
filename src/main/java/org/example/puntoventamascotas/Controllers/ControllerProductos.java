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
import org.example.puntoventamascotas.DAO.*;
import org.example.puntoventamascotas.Models.*;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.io.File;

public class ControllerProductos {
    @FXML TextField txtNombre;
    @FXML TextArea txtDescripcion;
    @FXML TextField txtPrecio;
    @FXML TextField txtStock;
    @FXML ImageView imgSubir;
    @FXML ComboBox<String> comboBoxTipoProducto;
    @FXML ComboBox<String> comboBoxCategoriaProducto;
    @FXML ComboBox<String> comboBoxAreaProducto;
    @FXML Button btnGuardar;
    String tipoProductoCombo;
    String areaCombo;
    String categoriaCombo;
    Producto productos;
    ProductosDAO productosDAO;
    CategoriaProductoDAO categoriaProductoDAO;
    TipoProductoDAO tipoProductoDAO;
    AreaDAO areaDAO;
    String nombreImagen = "";
    boolean banderaRegistrar = false;
    Area area;
    CategoriaProducto categoriaProducto;
    TipoProducto tipoProducto;



    public ControllerProductos() {
        this.productosDAO = new ProductosDAO(ConexionMsql.getConnection());
        this.areaDAO = new AreaDAO(ConexionMsql.getConnection());
        this.categoriaProductoDAO = new CategoriaProductoDAO(ConexionMsql.getConnection());
        this.tipoProductoDAO = new TipoProductoDAO(ConexionMsql.getConnection());
    }

    //metodo initialize======================================================================================================
    @FXML
    public void initialize(){
        /*Aqui se empieza a leer los combobox para el llenado se usa una consulta a la base de datos y se
        * asigna al atrubito de cada "= newValue.toString" lo puse asi ya que se repite en los 3 combobox*/
        for (Area a : areaDAO.obtenerAreas()) {
            comboBoxAreaProducto.getItems().add(a.getNombreArea());
        }
        comboBoxAreaProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            areaCombo = newValue.toString();
            area = areaDAO.obtenerAreaByNombre(areaCombo);
            comboBoxCategoriaProducto.getItems().clear();
            for(CategoriaProducto c : categoriaProductoDAO.obtenerCategoriasByArea(area.getIdArea())) {
                comboBoxCategoriaProducto.getItems().add(c.getNombreCategoria());
            }
        });

        comboBoxCategoriaProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            categoriaCombo = newValue.toString();
            categoriaProducto = categoriaProductoDAO.obtenerCategoriaProductoByNombreAndIdArea(categoriaCombo, area.getIdArea());
            comboBoxTipoProducto.getItems().clear();
            for(TipoProducto tipProd : tipoProductoDAO.obtenerTiposProductoByIdTipoProducto(categoriaProducto.getIdCategoriaProducto())){
                comboBoxTipoProducto.getItems().add(tipProd.getNombreTipoProducto());
            }
        });
        comboBoxTipoProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            tipoProductoCombo = newValue.toString();
            tipoProducto = productosDAO.obtenerTipoProductoByNombreAndIdCategoria(tipoProductoCombo, categoriaProducto.getIdCategoriaProducto());
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
        tipoProducto = tipoProductoDAO.obtenerNombreTipoProductoPorId(productos.getIdTipo());
        categoriaProducto = categoriaProductoDAO.obtenerCategoriaById(tipoProducto.getCategoriaProducto());
        area = areaDAO.obtenerAreaNombreById(categoriaProducto.getIdArea());
        //asignarle todos los valores que tiene el card al formulario
        txtNombre.setText(productos.getNombre());
        txtDescripcion.setText(productos.getDescripcion());
        txtPrecio.setText("" + productos.getPrecio());
        txtStock.setText("" + productos.getStock());
        //con esta linea se obtiene el nombre del tipo de producto de acuerdo al nombre de tipo producto
        tipoProductoCombo = tipoProducto.getNombreTipoProducto();
        areaCombo = area.getNombreArea();
        categoriaCombo = categoriaProducto.getNombreCategoria();
        /*categoriaProducto = categoriaProductoDAO.obtenerCategoriaProductoByNombreAndIdArea(categoriaCombo, area.getIdArea());
        comboBoxTipoProducto.getItems().clear();
        for(TipoProducto tipProd : tipoProductoDAO.obtenerTiposProductoByIdTipoProducto(categoriaProducto.getIdCategoriaProducto())){
            comboBoxTipoProducto.getItems().add(tipProd.getNombreTipoProducto());
        }*/
        comboBoxTipoProducto.getSelectionModel().select(tipoProducto.getNombreTipoProducto());
        comboBoxCategoriaProducto.getSelectionModel().select(categoriaProducto.getNombreCategoria());
        comboBoxAreaProducto.getSelectionModel().select(area.getNombreArea());
        //pasarle el nombre por la consulta
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
        if(banderaRegistrar){
            boolean sonCamposVacios = sonVacios(txtNombre, txtPrecio, txtStock);
            if(sonCamposVacios){
                MensajesVista.mostrarMensajeError("Error", "Los campos de nombre, precio, stock, tipo de producto e imagen son obligatorios");
            }else {
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
                        tipoProducto.getIdTipoProducto()
                );
                if (productosDAO.insertarProducto(productoModificado)) {
                    MensajesVista.mostrarMensajeExito("Exito", "Producto añadido con éxito");
                    limpiarCampos();
                } else {
                    MensajesVista.mostrarMensajeError("Error", "No se pudo añadir el producto");
                }
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
                    tipoProducto.getIdTipoProducto(),
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

    public boolean sonVacios(TextField txtNombre, TextField txtPrecio, TextField txtStock){
        if(txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty() || txtStock.getText().isEmpty()
           || comboBoxTipoProducto.getValue() == null || imgSubir.getImage() == null) {
            return true;
        }
        return false;
    }

    //metodo para cerrar la venta actual despues de guardar una mascota
    public void cerrarVenatanActual() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow(); // btnGuardar o cualquier nodo de tu ventana
        stage.close();
    }

    //metodo para limpiar los campos despues de registrar
    public void limpiarCampos(){
        txtNombre.clear();
        txtPrecio.clear();
        txtPrecio.clear();
        txtStock.clear();
        comboBoxTipoProducto.getSelectionModel().clearSelection();
        imgSubir.setImage(null);
    }

}
