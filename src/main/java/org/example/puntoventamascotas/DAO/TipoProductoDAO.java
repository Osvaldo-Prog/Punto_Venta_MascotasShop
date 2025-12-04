package org.example.puntoventamascotas.DAO;

import org.example.puntoventamascotas.Models.TipoProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoProductoDAO {
    private Connection conexion;
    public TipoProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    //este metodo es para traer el id del tipo de producto mediante el id que ya tiene seleccionado el producto
    //se usa en el setData para asignarle los datos al formulario
    public TipoProducto obtenerNombreTipoProductoPorId(int idTipoProducto){
        String sql = "SELECT *" +
                " FROM tipo_producto" +
                " WHERE  id_tipo_producto = ?;";
        PreparedStatement stmtsTipoProducto = null;
        ResultSet rsTipoProducto = null;
        try{
            stmtsTipoProducto = conexion.prepareStatement(sql);
            stmtsTipoProducto.setInt(1, idTipoProducto);
            rsTipoProducto = stmtsTipoProducto.executeQuery();
            if(rsTipoProducto.next()){
                TipoProducto tipoProducto = new TipoProducto();
                tipoProducto.setIdTipoProducto(rsTipoProducto.getInt("id_tipo_producto"));
                tipoProducto.setNombreTipoProducto(rsTipoProducto.getString("nombre"));
                tipoProducto.setDescripcion(rsTipoProducto.getString("descripcion"));
                tipoProducto.setCategoriaProducto(rsTipoProducto.getInt("id_categoria"));
                return tipoProducto;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //=======================================TipoProductos=========================================
    public List<TipoProducto> obtenerTiposProductoByIdTipoProducto (int idCategoria){
        List<TipoProducto> listaTipoProductos = new ArrayList<>();
        String sql = "SELECT * FROM tipo_producto WHERE id_categoria = ?;";
        PreparedStatement stmtsCategoria = null;
        ResultSet rsCategoria = null;
        try{
            stmtsCategoria = conexion.prepareStatement(sql);
            stmtsCategoria.setInt(1, idCategoria);
            rsCategoria = stmtsCategoria.executeQuery();
            while(rsCategoria.next()){
                listaTipoProductos.add(new TipoProducto(
                        rsCategoria.getInt("id_tipo_producto"),
                        rsCategoria.getString("nombre"),
                        rsCategoria.getString("descripcion"),
                        rsCategoria.getInt("id_categoria")
                ));
            }
            return listaTipoProductos;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
