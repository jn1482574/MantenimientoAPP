package com.juanjoflores.mantenimientoapp.DB;

import com.orm.SugarRecord;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class TareasPorUsuario extends SugarRecord<TareasPorUsuario> {

    Usuarios idUsuario;
    TipoTareas idTarea;

    public TareasPorUsuario() {
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoTareas getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(TipoTareas idTarea) {
        this.idTarea = idTarea;
    }

    public TareasPorUsuario(Usuarios idUsuario, TipoTareas idTarea) {
        this.idUsuario = idUsuario;
        this.idTarea = idTarea;
    }
}
