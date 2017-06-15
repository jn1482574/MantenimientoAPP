package com.juanjoflores.mantenimientoapp.DB;

import com.orm.SugarRecord;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class StatusTareas extends SugarRecord {

    Integer Status;
    String descirpcion;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getDescirpcion() {
        return descirpcion;
    }

    public void setDescirpcion(String descirpcion) {
        this.descirpcion = descirpcion;
    }

    public StatusTareas() {
    }

    public StatusTareas(Integer status, String descirpcion) {
        Status = status;
        this.descirpcion = descirpcion;
    }
}
