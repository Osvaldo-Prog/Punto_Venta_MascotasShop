package org.example.puntoventamascotas.DAO;

import org.example.puntoventamascotas.Models.CategoriaProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaProductoDAO {
    private Connection conexion;

    public CategoriaProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }


    //=========================================Categoria====================================================================
    public List<CategoriaProducto> obtenerCategorias(){
        List<CategoriaProducto> listaCategorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria_producto";
        PreparedStatement stmtsCategoria = null;
        ResultSet rsCategoria = null;
        try{
            stmtsCategoria = conexion.prepareStatement(sql);
            rsCategoria = stmtsCategoria.executeQuery();
            while(rsCategoria.next()){
                listaCategorias.add(new CategoriaProducto(
                        rsCategoria.getInt("id_categoria"),
                        rsCategoria.getString("nombre"),
                        rsCategoria.getString("descripcion"),
                        rsCategoria.getInt("id_area")
                ));
            }
            return listaCategorias;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public CategoriaProducto obtenerCategoriaProductoByIdArea(int idArea){
        String sql = "SELECT * FROM categoria_producto WHERE id_area = ?;";
        PreparedStatement stmtsCategoria = null;
        ResultSet rsCategoria = null;
        try{
            stmtsCategoria = conexion.prepareStatement(sql);
            stmtsCategoria.setInt(1, idArea);
            rsCategoria = stmtsCategoria.executeQuery();
            if(rsCategoria.next()){
                CategoriaProducto categoriaProducto = new CategoriaProducto(
                        rsCategoria.getInt("id_categoria"),
                        rsCategoria.getString("nombre"),
                        rsCategoria.getString("descripcion"),
                        rsCategoria.getInt("id_area")
                );
                return categoriaProducto;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CategoriaProducto> obtenerCategoriasByArea (int idArea){
        List<CategoriaProducto> listaCategorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria_producto WHERE id_area = ?;";
        PreparedStatement stmtsCategoria = null;
        ResultSet rsCategoria = null;
        try{
            stmtsCategoria = conexion.prepareStatement(sql);
            stmtsCategoria.setInt(1, idArea);
            rsCategoria = stmtsCategoria.executeQuery();
            while(rsCategoria.next()){
                listaCategorias.add(new CategoriaProducto(
                        rsCategoria.getInt("id_categoria"),
                        rsCategoria.getString("nombre"),
                        rsCategoria.getString("descripcion"),
                        rsCategoria.getInt("id_area")
                ));
            }
            return listaCategorias;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public CategoriaProducto obtenerCategoriaProductoByNombreAndIdArea(String nombreCategoria, int idArea){
        String sql = "SELECT * FROM categoria_producto WHERE nombre = ? and id_area = ?;";
        PreparedStatement stmtsCategoria = null;
        ResultSet rsCategoria = null;
        try{
            stmtsCategoria = conexion.prepareStatement(sql);
            stmtsCategoria.setString(1, nombreCategoria);
            stmtsCategoria.setInt(2, idArea);
            rsCategoria = stmtsCategoria.executeQuery();
            if(rsCategoria.next()){
                CategoriaProducto categoriaProducto = new CategoriaProducto(
                        rsCategoria.getInt("id_categoria"),
                        rsCategoria.getString("nombre"),
                        rsCategoria.getString("descripcion"),
                        rsCategoria.getInt("id_area")
                );
                return categoriaProducto;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public CategoriaProducto obtenerCategoriaById(int idCategoria){
        String sql = "SELECT * FROM categoria_producto WHERE id_categoria = ?;";
        PreparedStatement stmtsCategoria = null;
        ResultSet rsCategoria = null;
        try{
            stmtsCategoria = conexion.prepareStatement(sql);
            stmtsCategoria.setInt(1, idCategoria);
            rsCategoria = stmtsCategoria.executeQuery();
            if(rsCategoria.next()){
                CategoriaProducto categoriaProducto = new CategoriaProducto(
                        rsCategoria.getInt("id_categoria"),
                        rsCategoria.getString("nombre"),
                        rsCategoria.getString("descripcion"),
                        rsCategoria.getInt("id_area")
                );
                return categoriaProducto;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
