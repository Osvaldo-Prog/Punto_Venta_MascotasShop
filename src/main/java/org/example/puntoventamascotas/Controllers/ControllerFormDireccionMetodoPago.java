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
    public void pagar() {
        ControllerCard controllerCard = new ControllerCard();
        ProcesadorVenta procesadorVenta = new ProcesadorVenta();
        MetodoPago metodoPago = new MetodoPago();
        metodoPago = metodoPagoDAO.obtenerMetodoPagoByNombre(tipoMetodoPago);
        if (metodoPago.getNombreMetodoPago().equals("Tarjeta")) {
            procesadorVenta.setPagoStrategy(new TarjetaCreditoStrategy());
        } else if (metodoPago.getNombreMetodoPago().equals("Transferencia")) {
            procesadorVenta.setPagoStrategy(new TransferenciaStrategy());
        } else if (metodoPago.getNombreMetodoPago().equals("Efectivo")) {
            procesadorVenta.setPagoStrategy(new EfectivoStrategy());
        }
        boolean exito = procesadorVenta.ejecutarPago(total, tipoMetodoPago);
        if (exito) {
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
            int idRegistroDireccion = direccionEnvioDAO.registrarDireccionEnvio(direccionEnvio);
            if (idRegistroDireccion != -1) {
                Venta venta = new Venta(
                        usuario.getId(),
                        metodoPago.getIdMetodoPago(),
                        idRegistroDireccion,
                        LocalDateTime.now(),
                        total
                );
                int idVenta = ventaDAO.registrarVenta(venta);
                if (idVenta != -1) {
                    MensajesVista.mostrarMensajeExito("Exito", "Se ha realizado la compra");
                    /*En todo este paso se estan creando los items pero el mismo item sabra si es
                     * de mascota o de producto, ademas este solo funciona para el carrito*/
                    //Esto quiere decir que si la lista contiene algo inserte la lista al detalle venta
                    if (listaItems != null) {
                        for (ItemCardInterface item : listaItems) {
                            Item cosa = null;
                            DetalleVenta detalleVenta = null;
                            if (item.getTipo().equals("Mascota")) {
                                Mascota mascota = (Mascota) item;
                                cosa = new Item(
                                        mascota.getNombre(),
                                        0,
                                        mascota.getId(),
                                        mascota.getTipo()
                                );
                                int idItem = detalleVentaDAO.registrarItem(cosa);
                                detalleVenta = new DetalleVenta(
                                        idVenta,
                                        idItem,
                                        mascota.getCantidad(),
                                        mascota.getPrecio(),
                                        mascota.getPrecio() * mascota.getCantidad()
                                );
                            } else if (item.getTipo().equals("Producto")) {
                                Producto producto = (Producto) item;
                                cosa = new Item(
                                        producto.getNombre(),
                                        producto.getId(),
                                        0,
                                        producto.getTipo()
                                );
                                int nuevoStock = producto.getStock() - producto.getCantidad();
                                producto.setStock(nuevoStock);
                                productosDAO.updateStock(producto.getId(), nuevoStock);
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
                        if (itemCard.getTipo().equals("Mascota")) {
                            Mascota mascota = (Mascota) itemCard;
                            Item cosa = new Item(
                                    mascota.getNombre(),
                                    0,
                                    mascota.getId(),
                                    mascota.getTipo()
                            );
                            int idItem = detalleVentaDAO.registrarItem(cosa);
                            DetalleVenta detalleVenta = new DetalleVenta(
                                    idVenta,
                                    idItem,
                                    1,
                                    mascota.getPrecio(),
                                    mascota.getPrecio()
                            );
                            ticketExitoso = detalleVentaDAO.registrarDetalleVenta(detalleVenta);
                        } else if (itemCard.getTipo().equals("Producto")) {
                            Producto producto = (Producto) itemCard;
                            Item cosa = new Item(
                                    producto.getNombre(),
                                    producto.getId(),
                                    0,
                                    producto.getTipo()
                            );
                            int nuevoStock = producto.getStock() - producto.getCantidad();
                            producto.setStock(nuevoStock);
                            productosDAO.updateStock(producto.getId(), nuevoStock);
                            int idItem = detalleVentaDAO.registrarItem(cosa);
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
