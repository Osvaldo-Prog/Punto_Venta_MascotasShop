package org.example.puntoventamascotas.Models;

public class TransferenciaStrategy implements IPagoStrategy {
    @Override
    public boolean procesarPago(double monto, String datos){
        return true;
    }
}
