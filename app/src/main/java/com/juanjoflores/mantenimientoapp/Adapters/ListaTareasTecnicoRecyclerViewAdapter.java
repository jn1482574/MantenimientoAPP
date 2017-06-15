package com.juanjoflores.mantenimientoapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juanjoflores.mantenimientoapp.DB.TareasAsignadas;
import com.juanjoflores.mantenimientoapp.Interfaces.RecyclerViewOnSelectedItem;
import com.juanjoflores.mantenimientoapp.R;

import java.util.List;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class ListaTareasTecnicoRecyclerViewAdapter extends RecyclerView.Adapter<ListaTareasTecnicoRecyclerViewAdapter.ViewHolder> {

    private final TypedValue typedValue = new TypedValue();
    private int back;
    private RecyclerViewOnSelectedItem mCallback;
    private Animation animationUp, animationDown;
    private Fragment mFragment;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    private List<TareasAsignadas> listTareas;


    public ListaTareasTecnicoRecyclerViewAdapter(Context context, Fragment fragment, Animation animationUp, Animation animationDown, List<TareasAsignadas> listTareas) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        this.animationDown = animationDown;
        this.animationUp = animationUp;
        mFragment = fragment;
        back = typedValue.resourceId;
        this.listTareas = listTareas;

        try{
            mCallback = (RecyclerViewOnSelectedItem) mFragment;
        }catch(ClassCastException ex){
            Log.e("ListTareasTecnicoApter","no se implemento el callback", ex);
        }

    }

    @Override
    public ListaTareasTecnicoRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tareas, parent, false);
        view.setBackgroundResource(back);
        return new ListaTareasTecnicoRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListaTareasTecnicoRecyclerViewAdapter.ViewHolder holder, final int position) {

        holder.resumen.setBackgroundColor((position%2 == 0) ? holder.view.getContext().getResources().getColor(R.color.list_item_gray) : Color.argb(0,0,0,0));
        holder.contenido.setBackgroundColor((position%2 == 0) ? holder.view.getContext().getResources().getColor(R.color.list_item_gray) : Color.argb(0,0,0,0));

        //llenamos el item de la lista
        holder.tvFecha.setText(listTareas.get(position).getFecha_alta());
        holder.tvTarea.setText(listTareas.get(position).getNombreTarea());
        holder.tvEstado.setText(listTareas.get(position).getEstatus().getDescirpcion());
        holder.tvDescripcion.setText((listTareas.get(position).getDescripcion()));
        holder.tvTipo.setText("Tipo d Tarea: "+listTareas.get(position).getTipo_tarea().getTarea() + "\nDuracion: "+listTareas.get(position).getDuracion()+" min");
        holder.tvAsignado.setText("Asignado a: "+(listTareas.get(position).getTecnico().getNombre()));
       // holder.contenido.setVisibility(View.VISIBLE);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                if (holder.contenido.isShown()) {
                    holder.contenido.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {}

                        @Override
                        public void onFinish() {
                            holder.contenido.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();

                } else {
                    holder.contenido.setVisibility(View.VISIBLE);
                    holder.contenido.startAnimation(animationDown);

                }

            }
        });

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Context context = v.getContext();
                if(mCallback != null){
                    mCallback.RecyclerViewOnSelectedItem(position);
                }
                return true;
            }
        });
    }

    public TareasAsignadas getValueAt(int position) {
        return listTareas.get(position);
    }

    @Override
    public int getItemCount() {
        return listTareas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString, mID, mImage;
        public View view;
        public LinearLayout contenido, resumen;
        CardView cv;
        public TextView tvFecha, tvTarea, tvEstado, tvDescripcion, tvTipo, tvAsignado;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            contenido = (LinearLayout) this.view.findViewById(R.id.ll_historial_contenido);
            resumen = (LinearLayout) this.view.findViewById(R.id.ll_historial_resumen);
            tvFecha = (TextView) this.view.findViewById(R.id.tv_fecha);
            tvTarea = (TextView) this.view.findViewById(R.id.tv_nombre);
            tvEstado = (TextView) this.view.findViewById(R.id.tv_estado);
            tvDescripcion = (TextView) this.view.findViewById(R.id.tv_descripcion);
            tvTipo = (TextView) this.view.findViewById(R.id.tv_tipo_tarea);
            tvAsignado = (TextView) this.view.findViewById(R.id.tv_asignado);
            cv = (CardView) this.view.findViewById(R.id.cardview);
            contenido.setVisibility(View.GONE);
        }

        @Override
        public String toString() {
            return super.toString() + "  " ;
        }

    }

}