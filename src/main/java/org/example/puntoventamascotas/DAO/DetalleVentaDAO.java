package org.example.puntoventamascotas.DAO;

import org.example.puntoventamascotas.Models.DetalleVenta;
import org.example.puntoventamascotas.Models.Item;

import java.sql.*;

public class DetalleVentaDAO {
    private Connection conexion;

    public DetalleVentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean registrarDetalleVenta(DetalleVenta detalleVenta){
        String sql = "INSERT INTO detalle_venta(id_venta, id_item, cantidad, precio_unitario, subtotal)" +
                     "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmtDetalleVenta = null;
        try{
            stmtDetalleVenta = conexion.prepareStatement(sql);
            stmtDetalleVenta.setInt(1, detalleVenta.getIdVenta());
            stmtDetalleVenta.setInt(2, detalleVenta.getIdItem());
            stmtDetalleVenta.setInt(3, detalleVenta.getCantidad());
            stmtDetalleVenta.setDouble(4, detalleVenta.getPrecioUnitario());
            stmtDetalleVenta.setDouble(5, detalleVenta.getSubTotal());
            int filas = stmtDetalleVenta.executeUpdate();
            return (filas > 0);
        }catch (SQLException E){
            E.printStackTrace();
            return false;
        }
    }

    public int registrarItem(Item item){
        String sql = "INSERT INTO item(nombre, id_producto, id_mascota, tipo)" +
                     " Values(?,?,?,?)";
        PreparedStatement stmtItem = null;
        try{
            stmtItem = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Si es 0, se manda null a la BD, si no si se le asigna el id del item
            stmtItem.setString(1, item.getNombre());
            stmtItem.setObject(2, item.getIdProducto() == 0 ? null : item.getIdProducto());
            stmtItem.setObject(3, item.getIdMascota() == 0 ? null : item.getIdMascota());
            stmtItem.setString(4, item.getTipo());
            int filas = stmtItem.executeUpdate();
            if(filas > 0){
                ResultSet rs = stmtItem.getGeneratedKeys();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
            return -1;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }



}
