package org.example.puntoventamascotas.DAO;

import org.example.puntoventamascotas.Models.Venta;
import org.example.puntoventamascotas.Models.VistaVentas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    private Connection conexion;

    public VentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public int registrarVenta(Venta venta){
        String sql = "INSERT INTO venta(id_usuario, id_metodo_pago, id_direccion, fecha_venta, total)" +
                     " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmtVenta = null;
        try{
            stmtVenta = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmtVenta.setInt(1, venta.getIdUsuario());
            stmtVenta.setInt(2, venta.getIdMetodoPago());
            stmtVenta.setInt(3, venta.getIdDireccionEnvio());
            stmtVenta.setTimestamp(4, Timestamp.valueOf(venta.getFechaVenta()));
            stmtVenta.setDouble(5, venta.getTotal());
            int filas = stmtVenta.executeUpdate();
            if (filas > 0){
                ResultSet rs = stmtVenta.getGeneratedKeys();
                if (rs.next()){
                    return rs.getInt(1);
                }
            }
            return -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    //Metodo para listar las ventas y mostrarlas al admin====================================
    public List<VistaVentas> listarVentas(){
        List<VistaVentas> listaVentas = new ArrayList<>();
        String sql = "SELECT * FROM vista_ventas" +
                     " Order By id_venta";
        PreparedStatement stmtVenta = null;
        ResultSet rsVenta = null;
        try{
            stmtVenta = conexion.prepareStatement(sql);
            rsVenta = stmtVenta.executeQuery();
            while (rsVenta.next()){
                VistaVentas vistaVentas = new  VistaVentas(
                        rsVenta.getInt("id_venta"),
                        rsVenta.getString("nombre_personal"),
                        rsVenta.getString("nombre_metodo_pago"),
                        rsVenta.getInt("id_direccion"),
                        rsVenta.getTimestamp("fecha_venta").toLocalDateTime(),
                        rsVenta.getDouble("total")
                );
                listaVentas.add(vistaVentas);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaVentas;
    }
}
