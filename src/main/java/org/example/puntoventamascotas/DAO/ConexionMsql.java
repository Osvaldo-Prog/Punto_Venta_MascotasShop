package org.example.puntoventamascotas.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMsql {
    //INSTANCIA UNICA PARA SINGLETON
    private static final ConexionMsql INSTANCE = new ConexionMsql();

    //CONSTRUCTOR VACIO PRIVADO PARA SINGLETON
    private ConexionMsql(){}

    //datos de la conexion
    public static Connection conexion = null;
    private final String url = "jdbc:mysql://localhost:3306/punto_venta_mascotas";
    private final String user = "root";
    private final String password = "OsvaldoMySQL2408";

    //metodo publico para obtener la conexion
    public static Connection getConnection(){
        try{
            if(conexion == null || conexion.isClosed()){
                conexion = DriverManager.getConnection(INSTANCE.url, INSTANCE.user, INSTANCE.password);
            }
            return conexion;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //metodo para cerrar la conexion
    public static void cerrarConexion(){
        if(conexion != null){
            try{
                conexion.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
