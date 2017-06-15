package com.juanjoflores.mantenimientoapp.DB;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class TareasAsignadasDbManager {
    Context context;

    public TareasAsignadasDbManager(Context context) {
        this.context = context;
    }

    public List<TareasAsignadas> getTareasTodas(){
        List<TareasAsignadas> tareas = TareasAsignadas.listAll(TareasAsignadas.class);;

        return tareas;
    }

    public List<TareasAsignadas> getTareasTecnico(Usuarios usuario){
        return Select.from(TareasAsignadas.class).where(Condition.prop("tecnico").eq(usuario.getId())).list();
    }

    public List<TareasAsignadas> getTareasEstatus(int status){
       return Select.from(TareasAsignadas.class)
                .where(Condition.prop("estatus").eq(status)).list();
    }

    public void updateTarea(TareasAsignadas tarea){
        tarea.save();
    }
}
