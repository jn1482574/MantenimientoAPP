package com.juanjoflores.mantenimientoapp.DB;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Contabilidad 5 on 14/06/2017.
 */

public class TipoTareasDbManager {

    Context context;

    public TipoTareasDbManager(Context context) {
        this.context = context;
    }

    public List<TipoTareas> getTipoTareasTodas(){
        return TipoTareas.listAll(TipoTareas.class);
    }

    public TipoTareas getTipoTareasPorId(Long id){
        return TipoTareas.findById(TipoTareas.class, id);
    }
}
