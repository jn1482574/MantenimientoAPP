package com.juanjoflores.mantenimientoapp.Activitis;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.juanjoflores.mantenimientoapp.DB.Roles;
import com.juanjoflores.mantenimientoapp.DB.StatusTareas;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadas;
import com.juanjoflores.mantenimientoapp.DB.TareasPorUsuario;
import com.juanjoflores.mantenimientoapp.DB.TipoTareas;
import com.juanjoflores.mantenimientoapp.DB.Usuarios;
import com.juanjoflores.mantenimientoapp.R;

import java.util.List;


/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       IniciaConteoSplashScreen(3);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    CargarDatosDB();
                    break;
                }
            }
        }
    };

    private void IniciaConteoSplashScreen(int segundos) {

        new CountDownTimer((segundos * 1000), 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                handler.sendEmptyMessage(0);
            }
        }.start();
    }


    private void CargarDatosDB(){
        //Cargamos los roles
        List<Roles> listRoles = Roles.listAll(Roles.class);
        if(listRoles.isEmpty()) {
            Roles roles = new Roles(1, "Administrador");
            roles.save();
            roles = new Roles(2, "Tecnico");
            roles.save();
        }

        //Cargamos los tipos de tareas
        List<TipoTareas> listTipoTareas = TipoTareas.listAll(TipoTareas.class);
        if(listTipoTareas.isEmpty()){
            TipoTareas tipoTareas = new TipoTareas(TipoTareas.TIPO_MANTENIMIENTO_I, TipoTareas.TIPO_MANTENIMIENTO_STR);
            tipoTareas.save();
            tipoTareas = new TipoTareas(TipoTareas.TIPO_REPARACION_I, TipoTareas.TIPO_REPARACION_STR);
            tipoTareas.save();
            tipoTareas = new TipoTareas(TipoTareas.TIPO_LIMPIEZA_I, TipoTareas.TIPO_LIMPIEZA_STR);
            tipoTareas.save();
        }

        //Cargamos los estados de las tareas
        List<StatusTareas> listStatus = StatusTareas.listAll(StatusTareas.class);
        if(listStatus.isEmpty()){
            StatusTareas status = new StatusTareas(0,"En espera");
            status.save();
            status = new StatusTareas(1,"Iniciada");
            status.save();
            status = new StatusTareas(2,"Terminada");
            status.save();
        }
        //Cargamos Los Usuarios
        List<Usuarios> listUsuarios = Usuarios.listAll(Usuarios.class);
        if(listUsuarios.isEmpty()){
            List<Roles> roles = Roles.listAll(Roles.class);
            if(!roles.isEmpty()) {
                Usuarios usuarios = new Usuarios("Administrador", "administrador@mail.com", "123456", roles.get(0));
                usuarios.save();
                usuarios = new Usuarios("Tecnico1", "tecnico1@mail.com", "123456", roles.get(1));
                usuarios.save();
                usuarios = new Usuarios("Tecnico2", "tecnico2@mail.com", "123456", roles.get(1));
                usuarios.save();
                usuarios = new Usuarios("Tecnico3", "tecnico3@mail.com", "123456", roles.get(1));
                usuarios.save();
            }
        }

        //Cargamos las tareas por Usuario
        List<TareasPorUsuario> ListtareasUsuario = TareasPorUsuario.listAll(TareasPorUsuario.class);
        if(ListtareasUsuario.isEmpty()){
            List<Usuarios> ListUsuarios = Usuarios.listAll(Usuarios.class);
                if(!ListUsuarios.isEmpty()){
                    //Tipos de tareas
                    List<TipoTareas> tiposTareas = TipoTareas.listAll(TipoTareas.class);
                    //usuario tecnico1(1)
                    TareasPorUsuario tareasUsuario = new TareasPorUsuario(ListUsuarios.get(1), tiposTareas.get(0));
                    tareasUsuario.save();
                    tareasUsuario = new TareasPorUsuario(ListUsuarios.get(1), tiposTareas.get(1));
                    tareasUsuario.save();
                    tareasUsuario =new TareasPorUsuario(ListUsuarios.get(1), tiposTareas.get(2));
                    tareasUsuario.save();
                    //usuario tecnico2(2)
                    tareasUsuario =new TareasPorUsuario(ListUsuarios.get(2), tiposTareas.get(0));
                    tareasUsuario.save();
                    tareasUsuario =new TareasPorUsuario(ListUsuarios.get(2), tiposTareas.get(2));
                    tareasUsuario.save();
                    //usuario tecnico3(3)
                    tareasUsuario =new TareasPorUsuario(ListUsuarios.get(3), tiposTareas.get(1));
                    tareasUsuario.save();


                }
            }

            //Cargamos Tareas a los tecnicos
        List<TareasAsignadas> tareasAsignadas = TareasAsignadas.listAll(TareasAsignadas.class);
        if(tareasAsignadas.isEmpty()){
            List<Usuarios> ListUsuarios = Usuarios.listAll(Usuarios.class);
            if(!ListUsuarios.isEmpty()) {
                List<TipoTareas> tiposTareas = TipoTareas.listAll(TipoTareas.class);
                if(!tiposTareas.isEmpty()) {
                    List<StatusTareas> ListStatus = StatusTareas.listAll(StatusTareas.class);
                    if(!ListStatus.isEmpty()) {
                        TareasAsignadas tareas = new TareasAsignadas("Limpieza de mquina A", "Hacer Limpieza en la maqina A de produccion", "30", "13/06/207", ListUsuarios.get(1), tiposTareas.get(2), ListStatus.get(0));
                        tareas.save();
                        tareas = new TareasAsignadas("Limpieza de mquina B", "Hacer Limpieza en la maqina B de produccion", "60", "13/06/207", ListUsuarios.get(1), tiposTareas.get(2), ListStatus.get(2));
                        tareas.save();
                        tareas = new TareasAsignadas("Mantenimiento maquina C", "Hacer matenimento en la maqina C de entregas", "90", "13/06/207", ListUsuarios.get(1), tiposTareas.get(0), ListStatus.get(0));
                        tareas.save();
                        tareas = new TareasAsignadas("Mantenimiento preventivo", "Hacer mantenimiento en maquina de hielo", "60", "13/06/207", ListUsuarios.get(2), tiposTareas.get(0), ListStatus.get(1));
                        tareas.save();
                    }
                }
            }
        }

        //Iniciamos el Login()
        GotoLogin();

    }



    private void GotoLogin() {
        Intent intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

}
