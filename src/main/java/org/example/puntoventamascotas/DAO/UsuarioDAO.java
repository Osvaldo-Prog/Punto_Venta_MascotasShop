package org.example.puntoventamascotas.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.puntoventamascotas.Models.Usuario;

public class UsuarioDAO {
    private Connection conexion;

    //constructor para inicializar la conexion
    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }


    //Metodo para insertar el usuaro=============================================================================================
    public boolean insertarUsuario(Usuario usuario, int rol){
        String sqlInsertar = "INSERT INTO usuario (nombre, edad, nombre_usuario, telefono, correo, contraseña, id_rol)" +
                                            " VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stmtsUsuario = null;
        try{
            stmtsUsuario = conexion.prepareStatement(sqlInsertar);
            stmtsUsuario.setString(1, usuario.getNombre());
            stmtsUsuario.setInt(2, usuario.getEdad());
            stmtsUsuario.setString(3, usuario.getNombreUsuario());
            stmtsUsuario.setString(4, usuario.getTelefono());
            stmtsUsuario.setString(5, usuario.getCorreo());
            stmtsUsuario.setString(6, usuario.getContraseña());
            stmtsUsuario.setInt(7, rol);

            int filas = stmtsUsuario.executeUpdate();
            return (filas > 0);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Metodo para verificar si el nombre de usuario existe en la base de datos========================================================================
    public boolean nombreUsuarioExiste(String nombre_usuario){
        //consulta de cuantos nombres de usuario existen comparado con el que se ingresó
        String sqlNombreUsuario = "SELECT COUNT(*) " +
                                  "from usuario " +
                                  "WHERE nombre_usuario = ?";
        //inicializar el PreparedStatement y el ResulSet
        PreparedStatement stmtExisteNombreUsuario = null;
        ResultSet rsConteoUsuarios = null;
        try {
            stmtExisteNombreUsuario = conexion.prepareStatement(sqlNombreUsuario);
            stmtExisteNombreUsuario.setString(1, nombre_usuario);
            rsConteoUsuarios = stmtExisteNombreUsuario.executeQuery();
            if(rsConteoUsuarios.next()) {
                return rsConteoUsuarios.getInt(1) > 0; //esto quiere decir que si, ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; //esto quiere decir que no existe o que hubo un error
    }

    //Verificar si el correo existe en la base de datos=======================================================================================================================
    public boolean correoExistente(String correoUsuario){
        //consulta para ver si existe el correo
        String sqlCorreoUsuario = "SELECT COUNT(*) " +
                                  "from usuario " +
                                  "WHERE correo = ?";
        PreparedStatement stmtExisteCorreo = null;
        ResultSet rsCorreo = null;
        try{
            stmtExisteCorreo = conexion.prepareStatement(sqlCorreoUsuario);
            stmtExisteCorreo.setString(1, correoUsuario);
            rsCorreo = stmtExisteCorreo.executeQuery();
            if(rsCorreo.next()) {
                return rsCorreo.getInt(1) > 0; // quiere decir que si existe correo
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false; // quiere decir que no hay correo registrado
    }

    //Metodo para verificar si ya existe el numero de telefono=============================================================================
    public boolean existeNumeroTelefono(String numero_telefono){
        String sqlTelefonoUsuario = "SELECT COUNT(*) " +
                                    "from usuario " +
                                    "WHERE telefono = ?";
        PreparedStatement stmtTelefonoUsuario = null;
        ResultSet rsTelefonoUsuario = null;
        try{
            stmtTelefonoUsuario = conexion.prepareStatement(sqlTelefonoUsuario);
            stmtTelefonoUsuario.setString(1, numero_telefono);
            rsTelefonoUsuario = stmtTelefonoUsuario.executeQuery();
            if(rsTelefonoUsuario.next()){
                return rsTelefonoUsuario.getInt(1) > 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Metodo para listar todos los usuarios========================================================================================
    public List<Usuario> listarUsuarios(){
        List<Usuario> usuariosLista = new ArrayList<>();
        String sqlListaUsuarios = "SELECT * FROM usuario";
        PreparedStatement stmtListaUsuarios = null;
        ResultSet rsListaUsuarios = null;
        try{
            stmtListaUsuarios = conexion.prepareStatement(sqlListaUsuarios);
            rsListaUsuarios = stmtListaUsuarios.executeQuery();
            while (rsListaUsuarios.next()) {
                usuariosLista.add(new Usuario(rsListaUsuarios.getInt("id_usuario"),
                        rsListaUsuarios.getString("nombre"),
                        rsListaUsuarios.getInt("edad"),
                        rsListaUsuarios.getString("nombre_usuario"),
                        rsListaUsuarios.getString("telefono"),
                        rsListaUsuarios.getString("correo"),
                        rsListaUsuarios.getString("contraseña"),
                        rsListaUsuarios.getInt("id_rol")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return usuariosLista;
    }


    //Metodo para obtener el id de rol de acuerdo al nombre=======================================================================================
    public int obtenerIdRolPorNombre(String nombre){
        String sqlRolUsuario = "SELECT id_rol" +
                                " from roles" +
                                " WHERE nombre = ?";
        PreparedStatement stmtRolUsuario = null;
        ResultSet rsRolUsuario = null;
        try{
            stmtRolUsuario = conexion.prepareStatement(sqlRolUsuario);
            stmtRolUsuario.setString(1, nombre);
            rsRolUsuario = stmtRolUsuario.executeQuery();

            if(rsRolUsuario.next()){
                return rsRolUsuario.getInt("id_rol");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Metodo para darle el nombre de acuerdo el id=======================================================================
    public String obtenerNombreRolPorId(int idRol){
        String sql = "Select nombre" +
                     " from roles" +
                     " where id_rol = ?;";
        PreparedStatement stmtRolUsuario = null;
        ResultSet rsRolUsuario = null;
        try{
            stmtRolUsuario = conexion.prepareStatement(sql);
            stmtRolUsuario.setInt(1, idRol);
            rsRolUsuario = stmtRolUsuario.executeQuery();

            if(rsRolUsuario.next()){
                return rsRolUsuario.getString("nombre");
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }


    //Metodo para actualizar los usuarios=================================================================================================
    public boolean updateUsuario(Usuario usuario){
        String sql = "UPDATE usuario" +
                     " Set nombre = ?, nombre_usuario = ?, edad = ?, telefono = ?, correo = ?, id_rol = ?" +
                     " WHERE id_usuario = ?;";
        PreparedStatement stmtUpdateUsuario = null;
        try{
            stmtUpdateUsuario = conexion.prepareStatement(sql);
            stmtUpdateUsuario.setString(1, usuario.getNombre());
            stmtUpdateUsuario.setString(2, usuario.getNombreUsuario());
            stmtUpdateUsuario.setInt(3, usuario.getEdad());
            stmtUpdateUsuario.setString(4, usuario.getTelefono());
            stmtUpdateUsuario.setString(5, usuario.getCorreo());
            stmtUpdateUsuario.setInt(6, usuario.getIdRol());
            //este ultimo es para traer el id del usuario y se pueda hacer en el where
            stmtUpdateUsuario.setInt(7, usuario.getIdUsuario());
            //asignar las columnas afectadas
            int rows = stmtUpdateUsuario.executeUpdate();
            //retornar las columnas afectadas
            return rows > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //este es por si quiere actualizar la contraseña
    public boolean updateUsuarioContraseña(Usuario usuario){
        String sql = "UPDATE usuario" +
                " Set nombre = ?, nombre_usuario = ?, edad = ?, contraseña = ?, telefono = ?, correo = ?, id_rol = ?" +
                " WHERE id_usuario = ?;";
        PreparedStatement stmtUpdateUsuario = null;
        try{
            stmtUpdateUsuario = conexion.prepareStatement(sql);
            stmtUpdateUsuario.setString(1, usuario.getNombre());
            stmtUpdateUsuario.setString(2, usuario.getNombreUsuario());
            stmtUpdateUsuario.setInt(3, usuario.getEdad());
            stmtUpdateUsuario.setString(4, usuario.getContraseña());
            stmtUpdateUsuario.setString(5, usuario.getTelefono());
            stmtUpdateUsuario.setString(6, usuario.getCorreo());
            stmtUpdateUsuario.setInt(7, usuario.getIdRol());
            //este ultimo es para traer el id del usuario y se pueda hacer en el where
            stmtUpdateUsuario.setInt(8, usuario.getIdUsuario());
            //asignar las columnas afectadas
            int rows = stmtUpdateUsuario.executeUpdate();
            //retornar las columnas afectadas
            return rows > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //metodo para eliminar usuarios
    public boolean eliminarUsuario(int idUsuario){
        String sql = "DELETE FROM usuario" +
                    " WHERE id_usuario = ?;";
        PreparedStatement stmtEliminarUsuario = null;
        try{
            stmtEliminarUsuario = conexion.prepareStatement(sql);
            stmtEliminarUsuario.setInt(1, idUsuario);
            int rows = stmtEliminarUsuario.executeUpdate();
            return rows > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
