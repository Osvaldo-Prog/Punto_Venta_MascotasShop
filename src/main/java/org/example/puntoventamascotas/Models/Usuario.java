package org.example.puntoventamascotas.Models;

import java.io.Serializable;

public class Usuario implements ItemCardInterface{
    //inicializacion de atributos/variables
    private int idUsuario;
    private String nombre;
    private int edad;
    private String nombreUsuario;
    private String telefono;
    private String correo;
    private String contraseña;
    private int idRol;

    //constructor vacio para el ingreso de datos
    public Usuario(){}

    //constructor con parametros aunque no creo que se vaya a usar igual se declarará
    public Usuario(int idUsuario, String nombre, int edad, String nombreUsuario, String telefono, String correo, String contraseña, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.edad = edad;
        this.nombreUsuario = nombreUsuario;
        this.telefono = telefono;
        this.correo = correo;
        this.contraseña = contraseña;
        this.idRol = idRol;
    }

    //este constructor es para que al registrar un usuario nuevo no se añáda un id ya que es autoincrement en la BD
    public Usuario(String nombre, int edad, String nombreUsuario, String telefono, String correo, String contraseña, int idRol) {
        this.nombre = nombre;
        this.edad = edad;
        this.nombreUsuario = nombreUsuario;
        this.telefono = telefono;
        this.correo = correo;
        this.contraseña = contraseña;
        this.idRol = idRol;
    }

    //este constructor es para cuando no quiere cambiar la contraseña el formulario de edicion
    public Usuario(int idUsuario, String nombre, int edad, String nombreUsuario, String telefono, String correo, int idRol) {
        this.idRol = idRol;
        this.correo = correo;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
        this.edad = edad;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
    }

    //GETTERS Y SETTER

    // get y set id usuario
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int getId() {
        return idUsuario;
    }

    //get y set de nombre
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return 0;
    }

    @Override
    public String getImagen() {
        return "";
    }

    @Override
    public String getTipo() {
        return "Usuario";
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

    //get y set de idRol usuario
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
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
