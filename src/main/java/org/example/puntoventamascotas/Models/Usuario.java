package org.example.puntoventamascotas.Models;

public class Usuario {
    //inicializacion de atributos/variables
    private int idUsuario;
    private String nombre;
    private int edad;
    private String nombreUsuario;
    private String telefono;
    private String correo;
    private String contraseña;

    //constructor vacio para el ingreso de datos
    public Usuario(){}

    //constructor con parametros aunque no creo que se vaya a usar igual se declarará
    public Usuario(int idUsuario, String nombre, int edad, String nombreUsuario, String telefono, String correo, String contraseña){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.edad = edad;
        this.nombreUsuario = nombreUsuario;
        this.telefono = telefono;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    //GETTERS Y SETTER

    // get y set id usuario
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    //get y set de nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //get y set de edad
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    //get y set de nombreUsuario
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    //get y set de telefono
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //get y set de correo
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    //get y set de contraseña
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    //metodo toString
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}
