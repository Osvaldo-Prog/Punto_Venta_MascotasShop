package org.example.puntoventamascotas.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

    @FXML
    public void pagar() {
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
                     * de mascota o de producto*/
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

}
