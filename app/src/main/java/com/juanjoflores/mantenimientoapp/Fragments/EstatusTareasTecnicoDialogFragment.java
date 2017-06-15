package com.juanjoflores.mantenimientoapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.juanjoflores.mantenimientoapp.Interfaces.ISelectedData;
import com.juanjoflores.mantenimientoapp.Interfaces.RecyclerViewOnSelectedItem;
import com.juanjoflores.mantenimientoapp.R;


/**
 * Created by DesarrolloMovil on 14/10/16.
 */

public class EstatusTareasTecnicoDialogFragment extends DialogFragment {



    TextView tvCancelar, tvAplicar;
    private ISelectedData mCallback;
    private RadioGroup rgpFiltro;
    Fragment mFragment;

    public Fragment getmFragment() {
        return mFragment;
    }

    public void setmFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public static EstatusTareasTecnicoDialogFragment newInstance(Fragment mFragment) {

        EstatusTareasTecnicoDialogFragment filtrosListaPinesDialogFragment = new EstatusTareasTecnicoDialogFragment();
        filtrosListaPinesDialogFragment.setmFragment(mFragment);
        filtrosListaPinesDialogFragment.setCancelable(false);

        return filtrosListaPinesDialogFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_estatus_tareas_tenico, container, false);
        tvAplicar = (TextView) v.findViewById(R.id.btnAplicar);
        tvCancelar = (TextView) v.findViewById(R.id.btnCancelar);
        rgpFiltro = (RadioGroup) v.findViewById(R.id.rgp_filtro);
        try{
            mCallback = (ISelectedData) mFragment;
        }catch(ClassCastException ex){
            //.. should log the error or throw and exception
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }
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




}
