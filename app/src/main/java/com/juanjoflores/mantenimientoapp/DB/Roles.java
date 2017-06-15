package com.juanjoflores.mantenimientoapp.DB;

import com.orm.SugarRecord;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class Roles extends SugarRecord<Roles> {

    public int Tipo;
    public String Descripcion;

    public Roles() {
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Roles(int tipo, String descripcion) {
        Tipo = tipo;
        Descripcion = descripcion;
    }
}
