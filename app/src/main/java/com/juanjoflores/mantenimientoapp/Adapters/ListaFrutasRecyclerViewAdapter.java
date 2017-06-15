package com.juanjoflores.mantenimientoapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juanjoflores.mantenimientoapp.DB.Frutas;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadas;
import com.juanjoflores.mantenimientoapp.Interfaces.RecyclerViewOnSelectedItem;
import com.juanjoflores.mantenimientoapp.R;

import java.util.List;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class ListaFrutasRecyclerViewAdapter extends RecyclerView.Adapter<ListaFrutasRecyclerViewAdapter.ViewHolder>{



    private final TypedValue typedValue = new TypedValue();
    private int back;
    private RecyclerViewOnSelectedItem mCallback;
    private Animation animationUp, animationDown;;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    private List<Frutas> listFrutas;


    public ListaFrutasRecyclerViewAdapter(Context context, List<Frutas> listFrutas) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        this.animationDown = animationDown;
        this.animationUp = animationUp;
        back = typedValue.resourceId;
        this.listFrutas = listFrutas;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frutas, parent, false);
        view.setBackgroundResource(back);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //llenamos el item de la lista
        holder.tvNombre.setText(listFrutas.get(position).getFarm_name());
        holder.tvArticulo.setText(listFrutas.get(position).getItem());
        holder.tvCodigoPostal.setText(listFrutas.get(position).getZipcode());
        holder.tvIdentificador.setText((listFrutas.get(position).getFarmer_id()));


    }



    @Override
    public int getItemCount() {
        return listFrutas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString, mID, mImage;
        public View view;
        CardView cv;
        public TextView tvNombre, tvArticulo, tvCodigoPostal, tvIdentificador;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            tvNombre = (TextView) this.view.findViewById(R.id.txt_nombre);
            tvArticulo = (TextView) this.view.findViewById(R.id.txt_articulo);
            tvCodigoPostal = (TextView) this.view.findViewById(R.id.txt_codigo_postal);
            tvIdentificador = (TextView) this.view.findViewById(R.id.txt_granja_id);

            cv = (CardView) this.view.findViewById(R.id.cardview);


        }

        @Override
        public String toString() {
            return super.toString() + "  " ;
        }

    }

}