package org.example.puntoventamascotas.DAO;

import org.example.puntoventamascotas.Models.MetodoPago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {
    private Connection conexion;

    public MetodoPagoDAO(Connection conexion) {
        this.conexion=conexion;
    }

    public List<MetodoPago> listarMetodosPago(){
        List<MetodoPago> metodosPagoLista = new ArrayList<>();
        String sql = "Select * from metodo_pago";
        PreparedStatement stmtsMetodosPago = null;
        ResultSet rsMetodosPago = null;
        try{
            stmtsMetodosPago = conexion.prepareStatement(sql);
            rsMetodosPago = stmtsMetodosPago.executeQuery();
            while(rsMetodosPago.next()){
                metodosPagoLista.add(new MetodoPago(
                        rsMetodosPago.getInt("id_metodo_pago"),
                        rsMetodosPago.getString("nombre"),
                        rsMetodosPago.getString("descripcion")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return metodosPagoLista;
    }

    public MetodoPago obtenerMetodoPagoByNombre(String nombreMetodoPago){
        String sql = "Select * from metodo_pago" +
                     " WHERE nombre = ?";
        PreparedStatement stmtsMetodosPago = null;
        ResultSet rsMetodosPago = null;
        try{
            stmtsMetodosPago = conexion.prepareStatement(sql);
            stmtsMetodosPago.setString(1, nombreMetodoPago);
            rsMetodosPago = stmtsMetodosPago.executeQuery();
            if(rsMetodosPago.next()){
                    return new MetodoPago(
                        rsMetodosPago.getInt("id_metodo_pago"),
                        rsMetodosPago.getString("nombre"),
                        rsMetodosPago.getString("descripcion")
                    );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
