package org.example.puntoventamascotas.DAO;

import org.example.puntoventamascotas.Models.DireccionEnvio;

import java.sql.*;

public class DireccionEnvioDAO {
    private Connection conexion;

    public DireccionEnvioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public int registrarDireccionEnvio(DireccionEnvio direccionEnvio){
        String sql = "INSERT INTO direccion_envio(calle, numero_exterior, numero_interior, colonia, codigo_postal, ciudad, estado, pais, id_usuario) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmtDireccion = null;
        try{
            stmtDireccion = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmtDireccion.setString(1, direccionEnvio.getCalle());
            stmtDireccion.setString(2, direccionEnvio.getNumeroExterior());
            stmtDireccion.setString(3, direccionEnvio.getNumeroInterior());
            stmtDireccion.setString(4, direccionEnvio.getColonia());
            stmtDireccion.setString(5, direccionEnvio.getCodigoPostal());
            stmtDireccion.setString(6, direccionEnvio.getCiudad());
            stmtDireccion.setString(7, direccionEnvio.getEstado());
            stmtDireccion.setString(8, direccionEnvio.getPais());
            stmtDireccion.setInt(9, direccionEnvio.getIdUsuario());
            int filas = stmtDireccion.executeUpdate();
            if (filas > 0){
                //Leer el ID generado
                ResultSet rs = stmtDireccion.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);  //devuelve el id generado
                }
            }
            return -1;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}
