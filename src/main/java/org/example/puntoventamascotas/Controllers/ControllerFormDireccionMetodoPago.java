package org.example.puntoventamascotas.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.puntoventamascotas.DAO.*;
import org.example.puntoventamascotas.Models.*;
import org.example.puntoventamascotas.Util.MensajesVista;

import java.time.LocalDateTime;
import java.util.List;

public class ControllerFormDireccionMetodoPago {
    @FXML
    ComboBox<String> comboxMetodoPago;
    @FXML
    TextField txtCalle;
    @FXML
    TextField txtNumExt;
    @FXML
    TextField txtNumInter;
    @FXML
    TextField txtColonia;
    @FXML
    TextField txtCP;
    @FXML
    TextField txtCiudad;
    @FXML
    TextField txtEstado;
    @FXML
    TextField txtPais;

    String tipoMetodoPago;
    MetodoPagoDAO metodoPagoDAO;
    DireccionEnvioDAO direccionEnvioDAO;
    DetalleVentaDAO detalleVentaDAO;
    VentaDAO ventaDAO;
    ProductosDAO productosDAO;
    double total;
    boolean ticketExitoso;
    Usuario usuario;
    List<ItemCardInterface> listaItems;
    ItemCardInterface itemCard;

    public ControllerFormDireccionMetodoPago() {
        metodoPagoDAO = new MetodoPagoDAO(ConexionMsql.getConnection());
        direccionEnvioDAO = new DireccionEnvioDAO(ConexionMsql.getConnection());
        ventaDAO = new VentaDAO(ConexionMsql.getConnection());
        detalleVentaDAO = new DetalleVentaDAO(ConexionMsql.getConnection());
        productosDAO = new ProductosDAO(ConexionMsql.getConnection());
    }

    @FXML
    public void initialize() {
        comboxMetodoPago.getItems().addAll("Tarjeta",
                "Transferencia",
                "Efectivo");
        comboxMetodoPago.valueProperty().addListener((observable, oldValue, newValue) -> {
            tipoMetodoPago = newValue.toString();
        });
    }

    //metodo para cerrar la ventana actual=========================================================================
    @FXML
    private void cerrarVentana(ActionEvent actionEvent) {
        //obtener el stage actual para cerrar
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    //metodo para el boton de pagar del formulario de la direccion, aqui se implementa strategy
    public void pagar() {
        ControllerCard controllerCard = new ControllerCard();
        ProcesadorVenta procesadorVenta = new ProcesadorVenta();
        MetodoPago metodoPago = new MetodoPago();
        /*aqui primero a la variable metodoPago se le estan asignando todos los metosos de pago dados
        de alta en la bd*/
        metodoPago = metodoPagoDAO.obtenerMetodoPagoByNombre(tipoMetodoPago);
        /*Aqui se pregunta que tipo de metodo de pago es por el nombre
        * y si es uno u otro crea el metodod e pago de strategy*/
        if (metodoPago.getNombreMetodoPago().equals("Tarjeta")) {
            procesadorVenta.setPagoStrategy(new TarjetaCreditoStrategy());
        } else if (metodoPago.getNombreMetodoPago().equals("Transferencia")) {
            procesadorVenta.setPagoStrategy(new TransferenciaStrategy());
        } else if (metodoPago.getNombreMetodoPago().equals("Efectivo")) {
            procesadorVenta.setPagoStrategy(new EfectivoStrategy());
        }
        boolean exito = procesadorVenta.ejecutarPago(total, tipoMetodoPago);
        //si el procesar venta regresa true entra aqui
        if (exito) {
            //se crea el modelo/objeto de la direccion de envio para registrarla en la bd
            DireccionEnvio direccionEnvio = new DireccionEnvio(
                    txtCalle.getText(),
                    txtNumExt.getText(),
                    txtNumInter.getText(),
                    txtColonia.getText(),
                    txtCP.getText(),
                    txtCiudad.getText(),
                    txtEstado.getText(),
                    txtPais.getText(),
                    usuario.getId()
            );
            //obtenemos su id con la consulta del dao para ponerlo en la venta
            int idRegistroDireccion = direccionEnvioDAO.registrarDireccionEnvio(direccionEnvio);
            //si el id es difernete de -1 creará la venta
            if (idRegistroDireccion != -1) {
                Venta venta = new Venta(
                        usuario.getId(),
                        metodoPago.getIdMetodoPago(),
                        idRegistroDireccion,
                        LocalDateTime.now(),
                        total //este total ya lo tenemos gracias al metodo de hasta abajo
                );
                //pasa lo mismo, necesitamos el id de la venta creada, para ello una consulta, y se asigna al detalle venta
                int idVenta = ventaDAO.registrarVenta(venta);
                if (idVenta != -1) {
                    MensajesVista.mostrarMensajeExito("Exito", "Se ha realizado la compra");
                    /*En todo este paso se estan creando los items pero el mismo item sabra si es
                     * de mascota o de producto, ademas este solo funciona para el carrito*/
                    //Esto quiere decir que si la lista contiene algo inserte la lista al detalle venta
                    if (listaItems != null) {
                        //recorre la lista creada de carrito, este listaItems lo traemos gracias al metodo de hasta abajo
                        for (ItemCardInterface itemLista : listaItems) {
                            //creamos un item(Aun no sabe que es, si mascota o producto)
                            Item cosa = null;
                            DetalleVenta detalleVenta = null;
                            //si el item es de tipo Mascota entra aqui
                            if (itemLista.getTipo().equals("Mascota")) {
                                //Se castea hacia mascota el itemLista
                                Mascota mascota = (Mascota) itemLista;
                                //se crea el objeto/modelo Item
                                cosa = new Item(
                                        mascota.getNombre(),
                                        0,
                                        mascota.getId(),
                                        mascota.getTipo()
                                );
                                /*Pasa lo mismo, cachamos el id el item del detalle venta registrada
                                para mostrar en el detalle venta*/
                                int idItem = detalleVentaDAO.registrarItem(cosa);
                                detalleVenta = new DetalleVenta(
                                        idVenta,
                                        idItem,
                                        mascota.getCantidad(),
                                        mascota.getPrecio(),
                                        mascota.getPrecio() * mascota.getCantidad()
                                );
                                /*Ahora preguntamos si e siguiente elemeno de la lista
                                * es producto entra aqui*/
                            } else if (itemLista.getTipo().equals("Producto")) {
                                //Igual que arriba se castea pero hacia producto
                                Producto producto = (Producto) itemLista;
                                //se crea el modelo/objeto Item
                                cosa = new Item(
                                        producto.getNombre(),
                                        producto.getId(),
                                        0,
                                        producto.getTipo()
                                );
                                /*aqui solamente actualizamos el stock del producto pero como se debe actualizar
                                * tambien en la BD hacemos el update abajo*/
                                int nuevoStock = producto.getStock() - producto.getCantidad();
                                producto.setStock(nuevoStock);
                                productosDAO.updateStock(producto.getId(), nuevoStock);
                                //aqui cahamos de nuevo el id de item del detalle venta para mostrar en el detalle venta
                                int idItem = detalleVentaDAO.registrarItem(cosa);
                                detalleVenta = new DetalleVenta(
                                        idVenta,
                                        idItem,
                                        producto.getCantidad(),
                                        producto.getPrecio(),
                                        producto.getPrecio() * producto.getCantidad()
                                );
                            }
                            ticketExitoso = detalleVentaDAO.registrarDetalleVenta(detalleVenta);
                            System.out.println(ticketExitoso);
                        }
                        //este quiere decir que la lista está vacía y no insertará la lista si no el item individual
                    } else {
                        /*Esta parte es solamente para cuando se compra directamente un producto o mascota unitaria
                        * es decir dandole click directamente al boton comprar/adoptar
                        * literal hace el mismo procedimiento que arriba solo con las siguientes diferencias
                        * la venta sigue igual porque ya se hizo, es mas el detalle de venta lo que cambia*/
                        if (itemCard.getTipo().equals("Mascota")) {
                            //igual lo del casteo
                            Mascota mascota = (Mascota) itemCard;
                            /*Aqui se crea el modelo/objeto y no se le pasa id de producto si no de mascota
                             * y como es de una cantidad pues mas abajo en el detalle venta se le pasa 1*/
                            Item cosaMascota = new Item(
                                    mascota.getNombre(),
                                    0,
                                    mascota.getId(),
                                    mascota.getTipo()
                            );
                            /*Se está creando un detalle de venta individual si compro mascota o producto
                            * en este caso es mascota y se registra la mascota al detalle venta*/
                            int idItem = detalleVentaDAO.registrarItem(cosaMascota);
                            DetalleVenta detalleVenta = new DetalleVenta(
                                    idVenta,
                                    idItem,
                                    1,
                                    mascota.getPrecio(),
                                    mascota.getPrecio()
                            );
                            /*Aqui ya esta registrando el detalle de la venta por separado*/
                            ticketExitoso = detalleVentaDAO.registrarDetalleVenta(detalleVenta);
                        } else if (itemCard.getTipo().equals("Producto")) {
                            //igual lo del casteo
                            Producto producto = (Producto) itemCard;
                            Item cosa = new Item(
                                    producto.getNombre(),
                                    producto.getId(),
                                    0,
                                    producto.getTipo()
                            );
                            /*Aqui como el unico que tiene stock es el producto se lo actualizamos igual que arriba*/
                            int nuevoStock = producto.getStock() - producto.getCantidad();
                            producto.setStock(nuevoStock);
                            productosDAO.updateStock(producto.getId(), nuevoStock);
                            //Se necesita el id del item para insertarlo en el detalle de la venta
                            int idItem = detalleVentaDAO.registrarItem(cosa);
                            /*Iual que arriba se está creando un detalle de venta individual si compro mascota o producto
                             * en este caso es mascota y se registra la mascota al detalle venta*/
                            DetalleVenta detalleVenta = new DetalleVenta(
                                    idVenta,
                                    idItem,
                                    1,
                                    producto.getPrecio(),
                                    producto.getPrecio()
                            );
                            ticketExitoso = detalleVentaDAO.registrarDetalleVenta(detalleVenta);
                        }
                    }
                    if (ticketExitoso) {
                        MensajesVista.mostrarMensajeExito("Exito", "Se ha guardado tu ticket");
                    } else {
                        MensajesVista.mostrarMensajeError("Error", "Ocurrio un error con tu ticket");
                    }
                }
            }
        } else {
            MensajesVista.mostrarMensajeError("Error", "Venta fallida");
        }
    }

    public void setData(double totalPagar) {
        this.total = totalPagar;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setListaItems(List<ItemCardInterface> listaItems) {
        this.listaItems = listaItems;
    }

    public void setItem(ItemCardInterface itemCard) {
        this.itemCard = itemCard;
    }

}
