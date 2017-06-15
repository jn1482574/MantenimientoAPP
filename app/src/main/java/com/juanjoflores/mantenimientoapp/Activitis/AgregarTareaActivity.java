package com.juanjoflores.mantenimientoapp.Activitis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.juanjoflores.mantenimientoapp.DB.StatusTareas;
import com.juanjoflores.mantenimientoapp.DB.StatusTareasDbManager;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadas;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadasDbManager;
import com.juanjoflores.mantenimientoapp.DB.TareasPorUsuario;
import com.juanjoflores.mantenimientoapp.DB.TareasPorUsuarioDbManager;
import com.juanjoflores.mantenimientoapp.DB.TipoTareas;
import com.juanjoflores.mantenimientoapp.DB.TipoTareasDbManager;
import com.juanjoflores.mantenimientoapp.DB.Usuarios;
import com.juanjoflores.mantenimientoapp.R;
import com.juanjoflores.mantenimientoapp.Utils.Util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class AgregarTareaActivity extends MainActivity {

    Context context;
    private Toolbar toolbar;
    private EditText etTarea, etDescripcion;
    private Spinner spTipo, spDuracion;
    private Button btnAceptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);

        context = this;
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //actionBar
        final ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.str_titulo_activity_agregar_tarea));

        BitmapDrawable f1 = (BitmapDrawable)getResources()
                .getDrawable(R.mipmap.back
                );
        Bitmap bm = Bitmap.createScaledBitmap(f1.getBitmap(),80,80,false);

        ab.setHomeAsUpIndicator(new BitmapDrawable(bm));
        ab.setDisplayHomeAsUpEnabled(true);

        etTarea = (EditText) findViewById(R.id.et_tarea);
        etDescripcion = (EditText) findViewById(R.id.et_descripcion);
        spTipo = (Spinner) findViewById(R.id.sp_tipo);
        spDuracion = (Spinner) findViewById(R.id.sp_duracion);
        btnAceptar = (Button) findViewById(R.id.btn_aceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tareaNombre = etTarea.getText().toString();
                String tareaDescripcion = etDescripcion.getText().toString();
                if(tareaNombre.isEmpty() || tareaDescripcion.isEmpty()){
                    Snackbar.make(v, "Debes llenar todos los campos", Snackbar.LENGTH_LONG).show();
                }else{
                    VerificarAsignacion(tareaNombre, tareaDescripcion);
                }
            }
        });

    }



    public void VerificarAsignacion(String tarea_nombre, String Descripcion){
        TipoTareasDbManager tipoTareasDbManager = new TipoTareasDbManager(context);
        TareasPorUsuarioDbManager tareasPorUsuarioDbManager = new TareasPorUsuarioDbManager(context);
        int id = spTipo.getSelectedItemPosition()+1;
        TipoTareas tipo = tipoTareasDbManager.getTipoTareasPorId(((long) id));
        tareasPorUsuarioDbManager.getTareasPorusuarioTodas();
        List<TareasPorUsuario> tareasporUsuario = tareasPorUsuarioDbManager.getTareasPorTipo(tipo);
        Usuarios usuario_a_Asignar = null;
        if(!tareasporUsuario.isEmpty()){
            for (TareasPorUsuario tareas: tareasporUsuario) {
                Usuarios usuario = tareas.getIdUsuario();
                if(usuario_a_Asignar == null){
                    usuario_a_Asignar = usuario;
                }else{
                    TareasAsignadasDbManager tareasAsignadasDbManager = new TareasAsignadasDbManager(context);
                    int totalAsignadoUsuarioA = 0;
                    List<TareasAsignadas> tareasA = tareasAsignadasDbManager.getTareasTecnico(usuario);
                    for (TareasAsignadas tarea:
                         tareasA) {
                        totalAsignadoUsuarioA = totalAsignadoUsuarioA +Integer.parseInt(tarea.getDuracion());
                    }

                    int totalAsignadoUsuarioB = 0;
                    List<TareasAsignadas> tareasB = tareasAsignadasDbManager.getTareasTecnico(usuario_a_Asignar);
                    for (TareasAsignadas tarea:
                            tareasB) {
                        totalAsignadoUsuarioB= totalAsignadoUsuarioB +Integer.parseInt(tarea.getDuracion());
                    }

                    if(totalAsignadoUsuarioB >= totalAsignadoUsuarioA){
                        usuario_a_Asignar = usuario;
                    }
                }
            }
        }
        String duracion = spDuracion.getSelectedItem().toString();
        agregarTarea(tarea_nombre, Descripcion, duracion, usuario_a_Asignar, tipo);

    }

    public void agregarTarea(String tarea, String Descripcion, String Duracion, Usuarios usuario, TipoTareas tipoTareas){
        StatusTareasDbManager statusTareasDbManager = new StatusTareasDbManager(context);
        StatusTareas statusTareas = statusTareasDbManager.getStatusPorId(StatusTareasDbManager.STATUS_EN_ESPERA);
        String Fecha = Util.getFechaActual();
        TareasAsignadas tareas = new TareasAsignadas(tarea, Descripcion, Duracion, Fecha, usuario, tipoTareas, statusTareas);
        tareas.save();
        Util.ShowConfirmCloseDialogAcept(this,context,getString(R.string.str_tarea_asignada ),getString(R.string.str_tarea_asignada_a)+usuario.getNombre());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //regresamos
                finish();
                break;
        }
        return false;
    }

}

