package com.juanjoflores.mantenimientoapp.DB;

import com.orm.SugarRecord;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class Usuarios extends SugarRecord {

    private Long id ;
    public String nombre;
    public String correo;
    public String pass;
    public Roles rol;



    public Usuarios() {
    }

    public Usuarios(String nombre, String correo, String pass, Roles rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }
}
