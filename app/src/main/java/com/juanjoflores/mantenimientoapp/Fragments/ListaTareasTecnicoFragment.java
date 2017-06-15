package com.juanjoflores.mantenimientoapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juanjoflores.mantenimientoapp.Adapters.ListaTareasRecyclerViewAdapter;
import com.juanjoflores.mantenimientoapp.Adapters.ListaTareasTecnicoRecyclerViewAdapter;
import com.juanjoflores.mantenimientoapp.DB.StatusTareas;
import com.juanjoflores.mantenimientoapp.DB.StatusTareasDbManager;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadas;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadasDbManager;
import com.juanjoflores.mantenimientoapp.DB.Usuarios;
import com.juanjoflores.mantenimientoapp.DB.UsuariosDbManager;
import com.juanjoflores.mantenimientoapp.Interfaces.ISelectedData;
import com.juanjoflores.mantenimientoapp.Interfaces.RecyclerViewOnSelectedItem;
import com.juanjoflores.mantenimientoapp.R;

import java.util.List;

/**
 * Created by Contabilidad 5 on 23/02/2017.
 */

public class ListaTareasTecnicoFragment extends Fragment implements ISelectedData, RecyclerViewOnSelectedItem{
    RecyclerView rv;
    private Animation animationUp, animationDown;
    List<TareasAsignadas> listTareas;
    TextView tvNoDisponibles;
    int position;
    Context context;
    Usuarios usuarioSesion;

    public ListaTareasTecnicoFragment(List<TareasAsignadas> listTareas) {
        this.listTareas = listTareas;
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        LinearLayout ly =(LinearLayout) inflater.inflate(R.layout.fragment_lista_tareas, container, false);
        rv = (RecyclerView) ly.findViewById(R.id.recyclerView);
        tvNoDisponibles = (TextView) ly.findViewById(R.id.empty_view);
        UsuariosDbManager usuariosDbManager = new UsuariosDbManager(context);
        usuarioSesion = usuariosDbManager.getUsuarioSesion();
        return ly;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        setupRecyclerview(listTareas);
    }


    public void setupRecyclerview(List<TareasAsignadas> listTareas) {
        if(!listTareas.isEmpty()) {
            animationUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
            animationDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
            rv.setAdapter(new ListaTareasTecnicoRecyclerViewAdapter(getContext(),this, animationUp, animationDown, listTareas));
            rv.setVisibility(View.VISIBLE);
            tvNoDisponibles.setVisibility(View.GONE);
        }else{
            rv.setVisibility(View.GONE);
            tvNoDisponibles.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onSelectedData(int value) {
        ListaTareasTecnicoRecyclerViewAdapter adapter = ((ListaTareasTecnicoRecyclerViewAdapter)rv.getAdapter());
        TareasAsignadas tarea = adapter.getValueAt(position);

        switch (value){
            case R.id.chkbtn_espera://en Espera
                cambiarStatus(tarea, StatusTareasDbManager.STATUS_EN_ESPERA);
                break;
            case R.id.chkbtn_iniciada: //Iniciada
                cambiarStatus(tarea, StatusTareasDbManager.STATUS_INICIADA);
                break;
            case R.id.chkbtn_terminada://Terminada
                cambiarStatus(tarea, StatusTareasDbManager.STATUS_TERMINADA);
                break;

        }
    }

    private void cambiarStatus(TareasAsignadas tarea, Long status){
        StatusTareasDbManager statusTareasDbManager = new StatusTareasDbManager(context);
        TareasAsignadasDbManager tareasAsignadasDbManager = new TareasAsignadasDbManager(context);
        tarea.setEstatus(statusTareasDbManager.getStatusPorId(status));
        tareasAsignadasDbManager.updateTarea(tarea);
        actualizarFragment();
    }

    private void actualizarFragment(){
        this.listTareas.clear();
        TareasAsignadasDbManager tareasAsignadasDbManager = new TareasAsignadasDbManager(context);
        this.listTareas = tareasAsignadasDbManager.getTareasTecnico(usuarioSesion);
        setupRecyclerview(listTareas);
    }

    @Override
    public void RecyclerViewOnSelectedItem(int position) {
      this.position = position;
        showFragmentFiltrosTareas();
    }

    void showFragmentFiltrosTareas() {
        DialogFragment newFragment = EstatusTareasTecnicoDialogFragment.newInstance(this);
        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);
        newFragment.show(getFragmentManager(), "dialog");
    }
}
