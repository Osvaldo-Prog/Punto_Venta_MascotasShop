package org.example.puntoventamascotas.DAO;

import org.example.puntoventamascotas.Models.Area;
import org.example.puntoventamascotas.Models.CategoriaProducto;
import org.example.puntoventamascotas.Models.Producto;
import org.example.puntoventamascotas.Models.TipoProducto;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class ProductosDAO {
    private Connection conexion;

    public ProductosDAO(Connection conexion){
        this.conexion = conexion;
    }

    //metodo lista para capturar los productos de la base de datos======================================================================
    public List<Producto> listarProductos(){
        List<Producto> productosLista = new ArrayList<>();
        String sqlListar = "SELECT * FROM producto;";
        PreparedStatement stmtsProductos = null;
        ResultSet rsProductos = null;

        try{
            stmtsProductos = conexion.prepareStatement(sqlListar);
            rsProductos = stmtsProductos.executeQuery();

            while(rsProductos.next()){
                productosLista.add(new Producto(
                        rsProductos.getInt("id_producto"),
                        rsProductos.getString("nombre"),
                        rsProductos.getString("descripcion"),
                        rsProductos.getDouble("precio"),
                        rsProductos.getInt("stock"),
                        rsProductos.getInt("id_tipo_producto"),
                        rsProductos.getString("imagen")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return productosLista;
    }

    //metodo para obtener el id del tipo de producto segun el nombre
    public TipoProducto obtenerTipoProductoByNombreAndIdCategoria(String nombre, int idCategoria){
        String sqlListar = "SELECT * FROM tipo_producto" +
                           " WHERE nombre = ?" +
                           " and id_categoria = ?;";
        PreparedStatement stmtsTipoProducto = null;
        ResultSet rsTipoProducto = null;
        try{
            stmtsTipoProducto = conexion.prepareStatement(sqlListar);
            stmtsTipoProducto.setString(1, nombre);
            stmtsTipoProducto.setInt(2, idCategoria);
            rsTipoProducto = stmtsTipoProducto.executeQuery();
            if(rsTipoProducto.next()){
                TipoProducto tipoProducto = new TipoProducto();
                tipoProducto.setIdTipoProducto(rsTipoProducto.getInt("id_tipo_producto"));
                tipoProducto.setNombreTipoProducto(rsTipoProducto.getString("nombre"));
                tipoProducto.setDescripcion(rsTipoProducto.getString("descripcion"));
                tipoProducto.setCategoriaProducto(rsTipoProducto.getInt("id_categoria"));
                return tipoProducto;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //listar productos por el area==========================================================================================
    public List<Producto> listarProductosByArea(String area){
        List<Producto> productosLista = new ArrayList<>();
        String sqlListar = "SELECT * " +
                           " FROM producto" +
                           " INNER JOIN tipo_producto on tipo_producto.id_tipo_producto = producto.id_tipo_producto" +
                           " Inner Join categoria_producto on categoria_producto.id_categoria = tipo_producto.id_categoria" +
                           " INNER JOIN area on area.id_area = categoria_producto.id_area" +
                           " WHERE area.nombre = ?" +
                           " ";

        PreparedStatement stmtsProductos = null;
        ResultSet rsProductos = null;

        try{
            stmtsProductos = conexion.prepareStatement(sqlListar);
            stmtsProductos.setString(1, area);
            rsProductos = stmtsProductos.executeQuery();

            while(rsProductos.next()){
                productosLista.add(new Producto(
                        rsProductos.getInt("id_producto"),
                        rsProductos.getString("nombre"),
                        rsProductos.getString("descripcion"),
                        rsProductos.getDouble("precio"),
                        rsProductos.getInt("stock"),
                        rsProductos.getInt("id_tipo_producto"),
                        rsProductos.getString("imagen")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return productosLista;
    }


    //Metodo para insertar un producto
    //creo que aqui es donde se debe insertar la categoria de producto tambien
    // pero se debe cambiar de nombre en la base de datos o mejor la eliminamos
    public boolean insertarProducto(Producto producto){
        String sql = "INSERT INTO producto(nombre, precio, descripcion, stock, imagen, id_tipo_producto)" +
                     " VALUES(?,?,?,?,?,?) ";
        PreparedStatement stmtsProducto = null;
        try{
            stmtsProducto = conexion.prepareStatement(sql);
            stmtsProducto.setString(1, producto.getNombre());
            stmtsProducto.setDouble(2, producto.getPrecio());
            stmtsProducto.setString(3, producto.getDescripcion());
            stmtsProducto.setInt(4, producto.getStock());
            stmtsProducto.setString(5, producto.getImagen());
            stmtsProducto.setInt(6, producto.getIdTipo());
            int filas =  stmtsProducto.executeUpdate();
            return (filas > 0);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //metodo para actualizar el producto==================================================================================
    public boolean updateProducto(Producto producto){
        String sql = "UPDATE producto" +
                     " SET nombre = ?,  descripcion = ?, precio = ?, stock = ?, imagen = ?, id_tipo_producto = ?" +
                     " WHERE  id_producto = ?;";
        PreparedStatement stmtUpdateProducto = null;
        try {
            stmtUpdateProducto = conexion.prepareStatement(sql);
            stmtUpdateProducto.setString(1, producto.getNombre());
            stmtUpdateProducto.setString(2, producto.getDescripcion());
            stmtUpdateProducto.setDouble(3, producto.getPrecio());
            stmtUpdateProducto.setInt(4, producto.getStock());
            stmtUpdateProducto.setString(5, producto.getImagen());
            stmtUpdateProducto.setInt(6, producto.getIdTipo());
            //este ultimo es para traer el id del producto y se pueda hacer en el where
            stmtUpdateProducto.setInt(7, producto.getIdProducto());
            //asignar las columnas afectadas
            int rows = stmtUpdateProducto.executeUpdate();
            //retornar las columnas afectadas
            return (rows > 0);
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Metodo para borrar el producto=========================================
    public boolean eliminarProducto(int idProducto){
        String sql = "DELETE FROM producto" +
                     " WHERE id_producto = ?;";
        PreparedStatement stmtsProducto = null;
        try{
            stmtsProducto = conexion.prepareStatement(sql);
            stmtsProducto.setInt(1, idProducto);
            int filas =  stmtsProducto.executeUpdate();
            return (filas > 0);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

  /*  //Metodo para obtener el la info del producto mediante el id que se selecciono en el card
    public Producto obtenerInfoProducto(int idProducto){
        String sql = "SELECT * FROM producto" +
                     " WHERE id_producto = ?;";
        PreparedStatement stmtsProducto = null;
        ResultSet rsProducto = null;
        try{
            stmtsProducto = conexion.prepareStatement(sql);
            stmtsProducto.setInt(1, idProducto);
            rsProducto = stmtsProducto.executeQuery();
            if(rsProducto.next()){
                //aqui se usara un constructor sin el id de producto y sin el de tipo de producto
                // porque solo mostrara info que el cliente pueda ver y entender
                Producto producto = new Producto(
                        rsProducto.getInt("id_producto"),
                        rsProducto.getString("nombre"),
                        rsProducto.getString("descripcion"),
                        rsProducto.getDouble("precio"),
                        rsProducto.getInt("stock"),
                        rsProducto.getString("imagen"),
                        rsProducto.getInt("id_tipo_producto")
                );
                return producto;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
*/
    public boolean updateStock(int idProducto, int nuevoStock){
        String sql = "UPDATE producto" +
                     " SET stock = ?" +
                     " WHERE id_producto = ?;";
        PreparedStatement stmtUpdateStock = null;
        try{
            stmtUpdateStock = conexion.prepareStatement(sql);
            stmtUpdateStock.setInt(1, nuevoStock);
            stmtUpdateStock.setInt(2, idProducto);
            int filas =  stmtUpdateStock.executeUpdate();
            return (filas > 0);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    //Metodo para rankear los productos mas vendidos================================================================
    public List<Producto> RankearProductosMasVendidos(){
        List<Producto> productosRankeadosLista = new ArrayList<>();
        String sql = "SELECT producto.imagen, SUM(detalle_venta.cantidad) as Total_Vendido_Productos" +
                     " from detalle_venta" +
                     " INNER JOIN item on item.id_item = detalle_venta.id_item" +
                     " INNER JOIN producto on item.id_producto = producto.id_producto" +
                     " WHERE item.id_producto IS NOT NULL" +
                     " GROUP BY producto.imagen" +
                     " ORDER BY Total_Vendido_Productos desc;";
        PreparedStatement stmtsProducto = null;
        ResultSet rsProductos = null;
        try{
            stmtsProducto = conexion.prepareStatement(sql);
            rsProductos = stmtsProducto.executeQuery();
            while (rsProductos.next()){
                productosRankeadosLista.add(new Producto(
                        rsProductos.getString("imagen"),
                        rsProductos.getInt("Total_Vendido_Productos")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productosRankeadosLista;
    }
}
