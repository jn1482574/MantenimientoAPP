package com.juanjoflores.mantenimientoapp.DB;

import com.orm.SugarRecord;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class TipoTareas extends SugarRecord<TipoTareas> {

    public static int TIPO_MANTENIMIENTO_I = 1;
    public static int TIPO_REPARACION_I = 2;
    public static int TIPO_LIMPIEZA_I = 3;


    public static String TIPO_MANTENIMIENTO_STR = "Mantenimiento";
    public static String TIPO_REPARACION_STR = "Reparacion";
    public static String TIPO_LIMPIEZA_STR = "Limpieza";


    int Tipo;
    String Tarea;

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }

    public String getTarea() {
        return Tarea;
    }

    public void setTarea(String tarea) {
        Tarea = tarea;
    }

    public TipoTareas() {
    }

    public TipoTareas(int tipo, String tarea) {
        Tipo = tipo;
        Tarea = tarea;
    }
}
