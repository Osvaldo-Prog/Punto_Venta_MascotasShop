package org.example.puntoventamascotas.Models;

public interface IPagoStrategy {
    boolean procesarPago(double monto, String datosPago);
}
