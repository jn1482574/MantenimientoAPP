package com.juanjoflores.mantenimientoapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.juanjoflores.mantenimientoapp.Interfaces.ISelectedData;
import com.juanjoflores.mantenimientoapp.R;


/**
 * Created by DesarrolloMovil on 14/10/16.
 */

public class FiltrosListaTareasDialogFragment extends DialogFragment {



    TextView tvCancelar, tvAplicar;
    private ISelectedData mCallback;
    private RadioGroup rgpFiltro;

    public static FiltrosListaTareasDialogFragment newInstance() {

        FiltrosListaTareasDialogFragment filtrosListaPinesDialogFragment = new FiltrosListaTareasDialogFragment();
        filtrosListaPinesDialogFragment.setCancelable(false);

        return filtrosListaPinesDialogFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_filtros_tareas, container, false);
        tvAplicar = (TextView) v.findViewById(R.id.btnAplicar);
        tvCancelar = (TextView) v.findViewById(R.id.btnCancelar);
        rgpFiltro = (RadioGroup) v.findViewById(R.id.rgp_filtro);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onSelectedData(rgpFiltro.getCheckedRadioButtonId());
                dismiss();
            }
        });

        tvCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (ISelectedData) activity;
        }
        catch (ClassCastException e) {
            Log.d("Filtro", "Activity no implemento la interface");
        }
    }

}
