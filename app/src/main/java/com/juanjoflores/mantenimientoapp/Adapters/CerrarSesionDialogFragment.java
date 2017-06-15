package com.juanjoflores.mantenimientoapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.juanjoflores.mantenimientoapp.Activitis.LoginActivity;
import com.juanjoflores.mantenimientoapp.Interfaces.ISelectedData;
import com.juanjoflores.mantenimientoapp.R;
import com.juanjoflores.mantenimientoapp.Utils.SessionManager;


/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class CerrarSesionDialogFragment extends DialogFragment {



    TextView tvCancelar, tvAplicar;
    TextView tvTitulo, tvTexto;
    String strTitulo = "";
    String strTexto = "";
    public static Animation animAlpha;
    Context context;

    public static CerrarSesionDialogFragment newInstance(Context context) {

        CerrarSesionDialogFragment alertDialogFragment = new CerrarSesionDialogFragment();
        alertDialogFragment.setContext(context);
        alertDialogFragment.setCancelable(false);
        animAlpha = AnimationUtils.loadAnimation(context, R.anim.anim);
        return alertDialogFragment;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cerrar_sesion_dialogfragment, container, false);
        tvAplicar = (TextView) v.findViewById(R.id.btnAplicar);
        tvCancelar = (TextView) v.findViewById(R.id.btnCancelar);
        tvTitulo = (TextView) v.findViewById(R.id.titulo);
        tvTexto = (TextView) v.findViewById(R.id.text);

        strTitulo = getString(R.string.str_titulo_cerrar_sesion);
        strTexto = getString(R.string.str_texto_cerrar_sesion);

        if(tvTitulo!=null) {
            this.tvTitulo.setText(this.strTitulo);
        }

        if(tvTexto!=null) {
            this.tvTexto.setText(this.strTexto);
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                SessionManager.getInstace(getContext()).setAccessToken(SessionManager.TOKEN_NO_DISPONIBLE);
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                ((Activity)getContext()).finish();
            }
        });

        tvCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
              dismiss();
            }
        });

    }


    public String getStrTitulo() {
        return strTitulo;
    }

    public void setStrTitulo(String Titulo) {
        this.strTitulo = Titulo;
    }

    public String getStrTexto() {
        return strTexto;
    }

    public void setStrTexto(String Texto) {
        this.strTexto = Texto;
    }

}
