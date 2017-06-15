package com.juanjoflores.mantenimientoapp.DB;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Contabilidad 5 on 14/06/2017.
 */

public class TareasPorUsuarioDbManager {
    Context context;

    public TareasPorUsuarioDbManager(Context context) {
        this.context = context;
    }

    public List<TareasPorUsuario> getTareasPorusuarioTodas(){
        List<TareasPorUsuario> lista = TareasPorUsuario.listAll(TareasPorUsuario.class);
        return  lista;
    }

    public List<TareasPorUsuario> getTareasPorTipo (TipoTareas tipo){

        return  Select.from(TareasPorUsuario.class).where(Condition.prop("id_Tarea").eq(tipo.getId())).list();
    }
 }
