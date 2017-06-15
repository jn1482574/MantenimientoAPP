package com.juanjoflores.mantenimientoapp.DB;

import android.content.Context;

import com.juanjoflores.mantenimientoapp.Activitis.MainActivity;
import com.juanjoflores.mantenimientoapp.Utils.SessionManager;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class UsuariosDbManager {

    Context context;

    public UsuariosDbManager(Context context) {
        this.context = context;
    }

    public Usuarios getUsuarioSesion(){
        String idStr =  SessionManager.getInstace(context).getAccessToken();
        Usuarios usuarioSesion = null;
        if(!idStr.equals("No disponible")) {//si el token esta disponible recuperamos el usuario
            Long id = Long.parseLong(idStr);
            usuarioSesion = Usuarios.findById(Usuarios.class,id);
        }
        return usuarioSesion;
    }


}
