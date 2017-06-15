package com.juanjoflores.mantenimientoapp.Activitis;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.juanjoflores.mantenimientoapp.R;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public static Animation anim;
    public static final int ROL_ADMINISTRADOR = 1;
    public static final int ROL_TECNICO = 2;

    public static String TITULO_ADIMISTRADOR ;
    public static String TITULO_TECNICO ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Restringe la Orientacion del dispositivo a PORTRAIT
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Convertimos a Full Screen todos los activities que hereden de BaseActivity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.tint_status_bar));
        }
        // Cargar animacion global para botones
        anim = AnimationUtils.loadAnimation(this, R.anim.anim);

        //Tituos
        TITULO_ADIMISTRADOR = getString(R.string.str_titulo_administrador);
        TITULO_TECNICO = getString(R.string.str_titulo_tecnico);
    }
}
