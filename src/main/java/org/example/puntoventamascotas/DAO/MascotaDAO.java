package org.example.puntoventamascotas.DAO;


import org.example.puntoventamascotas.Models.Mascota;

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

    //Metodo para traer todo lo que tenga las mascotas y que se use en tableView
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
                        rsMascota.getString("descripcion"),
                        rsMascota.getString("cuidados"),
                        rsMascota.getInt("id_tipo_mascota"),
                        rsMascota.getDouble("precio"),
                        rsMascota.getString("imagen")));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return mascotasLista;
    }
}
