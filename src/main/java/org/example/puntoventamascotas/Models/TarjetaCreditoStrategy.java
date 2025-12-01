package org.example.puntoventamascotas.Models;

public class TarjetaCreditoStrategy implements IPagoStrategy {
    @Override
    public boolean procesarPago(double monto, String datosTarjeta) {
        return true;
    }
}
