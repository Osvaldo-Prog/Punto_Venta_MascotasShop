package org.example.puntoventamascotas.Models;

import org.example.puntoventamascotas.Util.MensajesVista;

//esta clase es el contexto
public class ProcesadorVenta {
    private IPagoStrategy pagoStrategy;


    public void setPagoStrategy(IPagoStrategy pagoStrategy){
        this.pagoStrategy = pagoStrategy;
    }

    public boolean ejecutarPago(double monto, String detalles){
        if(pagoStrategy == null){
            MensajesVista.mostrarMensajeError("Error", "No se ha establecido una estrategia de pago");
            return false;
        }
        return this.pagoStrategy.procesarPago(monto, detalles);
    }
}
