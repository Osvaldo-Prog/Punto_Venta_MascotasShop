package org.example.puntoventamascotas.DAO;

import java.sql.*;

import org.example.puntoventamascotas.Models.Usuario;

public class UsuarioDAO {
    private Connection conexion;

    //constructor para inicializar la conexion
    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    //===================================Metoto para insertar usuario automáticamente cliente====================================================
    public boolean insertarUsuario(Usuario usuario) {
        //insert para la base de datos en la tabla de usuario
        String sqlUsuario = "INSERT INTO usuario (nombre, edad, nombre_usuario, telefono, correo, contraseña) VALUES (?,?,?,?,?,?)";

        //inicalizar PreparedStatement diferentes para el usuario y su rol
        PreparedStatement stmtUsuario = null;
        PreparedStatement stmtRolUsuario = null;
        //hacer un try catch por cualquier error y asignarle la conexion
        try{
            // Iniciar transacción
            conexion.setAutoCommit(false);

            //Insrtar los valores obtenidos a la tabla
            //Statement.RETURN_GENERATED_KEYS -> sirve para devolver el id que acaba de crear con el autoincrement
            stmtUsuario = conexion.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            //concatenar todos los valores
            stmtUsuario.setString(1, usuario.getNombre());
            stmtUsuario.setInt(2, usuario.getEdad());
            stmtUsuario.setString(3, usuario.getNombreUsuario());
            stmtUsuario.setString(4, usuario.getTelefono());
            stmtUsuario.setString(5, usuario.getCorreo());
            stmtUsuario.setString(6, usuario.getContraseña());

            //incrementar las filas si se inserta correctamente
            int filas = stmtUsuario.executeUpdate();
            if(filas == 0) {
                return false;
            }

            //Obtener el id del usuario recién insertado
            int idUsuario;
            //es como preguntar: "Base de datos, ¿que id generaste para el usuario que acabo de insertar?"
            try (ResultSet generatedKeys = stmtUsuario.getGeneratedKeys()) {
                // Pregunta: "¿Hay al menos un id en los resultados?"
                if (generatedKeys.next()){
                    // Acción: "Dame el primer valor (columna 1) como entero"
                    idUsuario = generatedKeys.getInt(1);
                // Si no hay ID generado: "Algo salió mal, deshaz todo y reporta error"
                }else{
                    conexion.rollback(); // Cancela la inserción del usuario
                    return false;  // Retorna error
                }
            }

            //Insertar el usuario recién a la tabla perteneceA
            String sqlRol = "INSERT INTO pertenece (id_usuario, id_rol) VALUES (?,?)";
            stmtRolUsuario = conexion.prepareStatement(sqlRol);
            stmtRolUsuario.setInt(1, idUsuario);
            stmtRolUsuario.setInt(2, 2); //el segundo valor es el id de la tupla cliente que es de la tabla perteceneA

            //ejecutar la inserción
            stmtRolUsuario.executeUpdate();

            //confirmar transaccion
            conexion.commit();
            return true;

        }catch (SQLException e){
            try{
                if(conexion != null){
                    conexion.rollback(); //revertir en caso de error
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        // SIEMPRE, sea exito o no: Limpiar/ cerrar todo
        } finally{
            //cerrar recursos
            try{
                if(stmtUsuario != null) stmtUsuario.close();
                if(stmtRolUsuario != null) stmtRolUsuario.close();
                if(conexion != null) conexion.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }


}
