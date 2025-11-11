package org.example.puntoventamascotas.Models;

public class perteneceA {
    //inicializacion de variables/atributos
    /*Como esta es una tabla de relacion de muchos a muchos
    * se pasó como variables objetos de las tablas relacionadas
    * dando a entender que son las llaves foraneas en el modelo relacional*/
    private Usuario usuario;
    private RolUsuario rolUsuario;

    //constructor vacio
    public perteneceA() {}

    //constructor con param.
    public perteneceA(Usuario usuario, RolUsuario rolUsuario) {
        this.usuario = usuario;
        this.rolUsuario = rolUsuario;
    }

    //GETTER Y SETTER

    //get y set de Usuario
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //get y set de RolUsuario
    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    //toString para solo mandar llamar y mostrar resultados
    @Override
    public String toString() {
        return "perteneceA{" +
                "usuario=" + usuario +
                ", rolUsuario=" + rolUsuario +
                '}';
    }
}
