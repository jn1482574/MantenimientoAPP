package com.juanjoflores.mantenimientoapp.DB;

import android.content.Context;

import java.security.PublicKey;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class StatusTareasDbManager {
    Context context;
    public static Long STATUS_EN_ESPERA = 1L;
    public static Long STATUS_INICIADA = 2L;
    public static Long STATUS_TERMINADA = 3L;

    public StatusTareasDbManager(Context context) {
        this.context = context;
    }

    public StatusTareas getStatusPorId(Long id){
        return StatusTareas.findById(StatusTareas.class, id);
    }
}
