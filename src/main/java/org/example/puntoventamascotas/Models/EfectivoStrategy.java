package org.example.puntoventamascotas.Models;

public class EfectivoStrategy implements IPagoStrategy{
    @Override
    public boolean procesarPago(double monto, String detalles) {
        return true;
    }
}
