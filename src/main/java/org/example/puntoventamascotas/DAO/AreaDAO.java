package org.example.puntoventamascotas.DAO;

import org.example.puntoventamascotas.Models.Area;
import org.example.puntoventamascotas.Models.TipoProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO {
    private Connection conexion;

    public AreaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    //====================================AREA=================================================================
    public List<Area> obtenerAreas() {
        List<Area> listaAreas = new ArrayList<>();
        String sql = "SELECT * FROM area";
        PreparedStatement stmtsArea = null;
        ResultSet rsArea = null;
        try {
            stmtsArea = conexion.prepareStatement(sql);
            rsArea = stmtsArea.executeQuery();
            while (rsArea.next()) {
                listaAreas.add(new Area(
                        rsArea.getInt("id_area"),
                        rsArea.getString("nombre"),
                        rsArea.getString("descripcion")
                ));
            }
            return listaAreas;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Area obtenerAreaByNombre(String nombreArea) {
        String sql = "SELECT * FROM area WHERE nombre = ?;";
        PreparedStatement stmtsArea = null;
        ResultSet rsArea = null;
        try {
            stmtsArea = conexion.prepareStatement(sql);
            stmtsArea.setString(1, nombreArea);
            rsArea = stmtsArea.executeQuery();
            if (rsArea.next()) {
                Area area = new Area(
                        rsArea.getInt("id_area"),
                        rsArea.getString("nombre"),
                        rsArea.getString("descripcion")
                );
                return area;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Area obtenerAreaNombreById(int idArea) {
        String sql = "SELECT *" +
                " FROM area" +
                " WHERE  id_area = ?;";
        PreparedStatement stmtsArea = null;
        ResultSet rsArea = null;
        try {
            stmtsArea = conexion.prepareStatement(sql);
            stmtsArea.setInt(1, idArea);
            rsArea = stmtsArea.executeQuery();
            if (rsArea.next()) {
                Area area = new Area(
                        rsArea.getInt("id_area"),
                        rsArea.getString("nombre"),
                        rsArea.getString("descripcion"));
                return area;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
