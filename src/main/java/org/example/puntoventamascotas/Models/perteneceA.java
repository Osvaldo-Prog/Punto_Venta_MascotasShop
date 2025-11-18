package org.example.puntoventamascotas.Models;

public class perteneceA {
    //inicializacion de variables/atributos
    /*Como esta es una tabla de relacion de muchos a muchos
    * se pasó como variables objetos de las tablas relacionadas
    * dando a entender que son las llaves foraneas en el modelo relacional*/
    private int idUsuario;
    private int idRol;

    //constructor vacio
    public perteneceA() {}

    //constructor con param.
    public perteneceA(int idUsuario, int idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    //GETTER Y SETTER

    //get y set de Usuario
    public int getUsuario() {
        return idUsuario;
    }

    public void setUsuario(int usuario) {
        this.idUsuario = idUsuario;
    }

    //get y set de RolUsuario
    public int getRolUsuario() {
        return idRol;
    }

    public void setRolUsuario(int idRol) {
        this.idRol = idRol;
    }

    //toString para solo mandar llamar y mostrar resultados
    @Override
    public String toString() {
        return "perteneceA{" +
                "usuario=" + idUsuario +
                ", rolUsuario=" + idRol +
                '}';
    }
}
