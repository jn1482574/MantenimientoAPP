package com.juanjoflores.mantenimientoapp.Activitis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.juanjoflores.mantenimientoapp.DB.Usuarios;
import com.juanjoflores.mantenimientoapp.R;
import com.juanjoflores.mantenimientoapp.Utils.SessionManager;
import com.juanjoflores.mantenimientoapp.Utils.Util;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class LoginActivity extends BaseActivity {

    private EditText etUsuario;
    private EditText etPass;
    private Button btnAceptar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializamos controles
        etUsuario = (EditText) findViewById(R.id.et_usuario);
        etPass = (EditText) findViewById(R.id.et_pass);
        btnAceptar = (Button) findViewById(R.id.btn_aceptar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                //Validamos Los Campos
                validarCampos(v,usuario, pass);
            }
        });

        //verificamos si existe una sesion activa
        ContinuarSesion();
    }

    public void ContinuarSesion() {
        // Se envia la Informacion a la Session y se abre el Menu Principal
        String TOken = SessionManager.getInstace(LoginActivity.this).getAccessToken();
        if(!TOken.equals("No disponible")) {//si el token esta disponible abrimos el Login
            Usuarios usuarioTB  = Usuarios.findById(Usuarios.class, Long.parseLong(TOken));
            if(usuarioTB == null){
                Snackbar.make(btnAceptar, "Error al iniciar Sesion",Snackbar.LENGTH_LONG).show();
            }else{
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }
    }

    private void validarCampos(View v, String usuario, String pass) {
        //si los campos estan vacios
        if(usuario.isEmpty() || pass.isEmpty()){
            Snackbar.make(v, "Debes llenar todos los campos",Snackbar.LENGTH_LONG).show();
            //si el correo es incorrecto
        }else if(!Util.isValidEmail(usuario)){
            Snackbar.make(v, "Formato de usuario incorrecto",Snackbar.LENGTH_LONG).show();
        }
        else{
            //enviamos los datos para e login (Buscar en Base de datos)
            enviarLogin(v,usuario,pass);
        }

    }

    private void enviarLogin(View v,String usuario, String pass) {
        // List<Usuarios> usuarioTB = Usuarios.findWithQuery(Usuarios.class,"Select * from Usuarios where correo = ? and pass = ?", usuario.toString(), pass.toString());
        List<Usuarios> usuarioTB  = Select.from(Usuarios.class)
                .where(Condition.prop("correo").eq(usuario),
                        Condition.prop("pass").eq(pass))
                .list();

        if(usuarioTB.isEmpty()){
            Snackbar.make(v, "usuario y/o contraseña incorrectos",Snackbar.LENGTH_LONG).show();
        }else{
            SessionManager.getInstace(LoginActivity.this).setAccessToken(usuarioTB.get(0).getId().toString());
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}
