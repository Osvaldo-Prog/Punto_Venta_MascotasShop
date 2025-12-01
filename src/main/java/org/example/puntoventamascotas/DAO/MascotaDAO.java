package org.example.puntoventamascotas.DAO;


import org.example.puntoventamascotas.Models.Mascota;
import org.example.puntoventamascotas.Models.TipoMascota;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MascotaDAO {
    private Connection conexion;

    public MascotaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    //metodo para insertar una nueva mascota
    public boolean insertarMascota(Mascota mascota){
        String sql = "INSERT INTO mascota(nombre, descripcion, cuidados, precio, imagen, id_tipo_mascota)" +
                     "VALUES (?,?,?,?,?,?);";
        PreparedStatement stmtMascota = null;
        try{
            stmtMascota = conexion.prepareStatement(sql);
            stmtMascota.setString(1, mascota.getNombre());
            stmtMascota.setString(2, mascota.getDescripcionMascota());
            stmtMascota.setString(3, mascota.getCuidados());
            stmtMascota.setDouble(4, mascota.getPrecio());
            stmtMascota.setString(5, mascota.getImagen());
            stmtMascota.setInt(6, mascota.getIdTipo());
            int filas = stmtMascota.executeUpdate();
            return (filas > 0);
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Metodo para traer todo lo que tenga las mascotas=================================================================================
    public List<Mascota> listarMascotas(){
        List<Mascota> mascotasLista = new ArrayList<>();
        String sqlListaMascotas = "SELECT *" +
                                  " from mascota;";
        PreparedStatement stmtMascota = null;
        ResultSet rsMascota = null;
        try{
            stmtMascota =conexion.prepareStatement(sqlListaMascotas);
            rsMascota = stmtMascota.executeQuery();
            while(rsMascota.next()){
                mascotasLista.add(new Mascota(
                        rsMascota.getInt("id_mascota"),
                        rsMascota.getString("nombre"),
                        rsMascota.getString("cuidados"),
                        rsMascota.getString("descripcion"),
                        rsMascota.getDouble("precio"),
                        rsMascota.getInt("id_tipo_mascota"),
                        rsMascota.getString("imagen")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return mascotasLista;
    }

    //Metodo para filtrar de acuerdo al area===========================================================================================
    public List<Mascota> listarMascotasByArea(String area){
        List<Mascota> mascotasLista = new ArrayList<>();
        String sqlListaMascotas = "SELECT *" +
                                 " from mascota" +
                                 " INNER JOIN tipo_mascota on tipo_mascota.id_tipo_mascota = mascota.id_tipo_mascota" +
                                 " Inner join area on area.id_area = tipo_mascota.id_area" +
                                 " WHERE area.nombre = ?;";
        PreparedStatement stmtMascota = null;
        ResultSet rsMascota = null;
        try{
            stmtMascota = conexion.prepareStatement(sqlListaMascotas);
            stmtMascota.setString(1, area);
            rsMascota = stmtMascota.executeQuery();
            while(rsMascota.next()){
                mascotasLista.add(new Mascota(
                        rsMascota.getInt("id_mascota"),
                        rsMascota.getString("nombre"),
                        rsMascota.getString("descripcion"),
                        rsMascota.getString("cuidados"),
                        rsMascota.getDouble("precio"),
                        rsMascota.getInt("id_tipo_mascota"),
                        rsMascota.getString("imagen")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return mascotasLista;
    }


    //Metodo para hacer el update de mascotas
    public boolean updateMascota(Mascota mascota){
        String sql = "UPDATE mascota" +
                     " SET nombre = ?, cuidados = ?, descripcion = ?, precio = ?, imagen = ?, id_tipo_mascota = ?" +
                     " WHERE id_mascota = ?;";
        PreparedStatement stmtUpdateMascota = null;
        try{
            stmtUpdateMascota = conexion.prepareStatement(sql);
            stmtUpdateMascota.setString(1, mascota.getNombre());
            stmtUpdateMascota.setString(2, mascota.getCuidados());
            stmtUpdateMascota.setString(3, mascota.getDescripcionMascota());
            stmtUpdateMascota.setDouble(4, mascota.getPrecio());
            stmtUpdateMascota.setString(5, mascota.getImagen());
            stmtUpdateMascota.setInt(6, mascota.getIdTipo());
            stmtUpdateMascota.setInt(7, mascota.getId());
            int rows = stmtUpdateMascota.executeUpdate();
            return rows > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Este metodo me retorna null y no se por que
    //metodo para obtener el id del tipo de mascota segun el nombre que se usa en el insert de administrador
    public TipoMascota obtenerTipoMascotaByNombre(String nombre){
        String sql = "SELECT * FROM tipo_mascota" +
                     " WHERE nombre = ?;";
        PreparedStatement stmtTipoMascota = null;
        ResultSet rsTipoMascota = null;
        try{
            stmtTipoMascota = conexion.prepareStatement(sql);
            stmtTipoMascota.setString(1, nombre);
            rsTipoMascota = stmtTipoMascota.executeQuery();
            if(rsTipoMascota.next()){
                TipoMascota tipoMascota = new TipoMascota();
                tipoMascota.setIdTipoMascota(rsTipoMascota.getInt("id_tipo_mascota"));
                tipoMascota.setNombreTipoMascota(rsTipoMascota.getString("nombre"));
                tipoMascota.setDescripcion(rsTipoMascota.getString("descripcion"));
                tipoMascota.setArea(rsTipoMascota.getInt("id_area"));
                return tipoMascota;
            }
            return null;
        }catch(SQLException e ){
            e.printStackTrace();
            return null;
        }
    }

    //metodo para obtener el id de tipo de mascota pero pasandole el idtipoMascota como parametro
    //se usa en el setdata de mascotas para llenar el formulario de actualizacion
    public TipoMascota obtenerNombreTipoMascotaPorId(int idTipoMascota){
        String sql = "SELECT * " +
                     "FROM tipo_mascota " +
                     "WHERE id_tipo_mascota = ?;";
        PreparedStatement stmtTipoMascota = null;
        ResultSet rsTipoMascota = null;
        try{
            stmtTipoMascota = conexion.prepareStatement(sql);
            stmtTipoMascota.setInt(1, idTipoMascota);
            rsTipoMascota = stmtTipoMascota.executeQuery();
            if(rsTipoMascota.next()){
                TipoMascota tipoMascota = new TipoMascota();
                tipoMascota.setIdTipoMascota(rsTipoMascota.getInt("id_tipo_mascota"));
                tipoMascota.setNombreTipoMascota(rsTipoMascota.getString("nombre"));
                tipoMascota.setDescripcion(rsTipoMascota.getString("descripcion"));
                tipoMascota.setArea(rsTipoMascota.getInt("id_area"));
                return tipoMascota;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }


    public boolean eliminarMascota(int idMascota){
        String sql = "DELETE FROM mascota" +
                     " WHERE id_mascota = ?;";
        PreparedStatement stmtMascota = null;
        try{
            stmtMascota = conexion.prepareStatement(sql);
            stmtMascota.setInt(1, idMascota);
            int filas  = stmtMascota.executeUpdate();
            return filas > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
