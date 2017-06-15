package com.juanjoflores.mantenimientoapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.juanjoflores.mantenimientoapp.Adapters.ListaTareasRecyclerViewAdapter;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadas;
import com.juanjoflores.mantenimientoapp.R;

import java.util.List;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class ListaTareasFragment extends Fragment {
    private RecyclerView rv;
    private Animation animationUp, animationDown;
    private List<TareasAsignadas> listTareas;
    private TextView tvNoDisponibles;

    public List<TareasAsignadas> getListTareas() {
        return listTareas;
    }

    public void setListTareas(List<TareasAsignadas> listTareas) {
        this.listTareas = listTareas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        LinearLayout ly =(LinearLayout) inflater.inflate(R.layout.fragment_lista_tareas, container, false);
        rv = (RecyclerView) ly.findViewById(R.id.recyclerView);
        tvNoDisponibles = (TextView) ly.findViewById(R.id.empty_view);
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
            rv.setAdapter(new ListaTareasRecyclerViewAdapter(getContext(),this, animationUp, animationDown, listTareas));
            rv.setVisibility(View.VISIBLE);
            tvNoDisponibles.setVisibility(View.GONE);
        }else{
            rv.setVisibility(View.GONE);
            tvNoDisponibles.setVisibility(View.VISIBLE);

        }

        }

}
