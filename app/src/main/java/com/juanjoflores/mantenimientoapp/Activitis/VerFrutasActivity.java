package com.juanjoflores.mantenimientoapp.Activitis;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.juanjoflores.mantenimientoapp.Adapters.ListaFrutasRecyclerViewAdapter;
import com.juanjoflores.mantenimientoapp.Adapters.ListaTareasRecyclerViewAdapter;
import com.juanjoflores.mantenimientoapp.DB.Frutas;
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
import com.juanjoflores.mantenimientoapp.Utils.MantenimientoAppClient;
import com.juanjoflores.mantenimientoapp.Utils.Util;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class VerFrutasActivity extends BaseActivity {
    private static final String TAG = "VerFrutasActivity";
    private RecyclerView rv;
    private Context context;
    private Toolbar toolbar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_frutas);

        context = this;
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //actionBar
        final ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.str_titulo_activity_ver_frutas));

        BitmapDrawable f1 = (BitmapDrawable)getResources()
                .getDrawable(R.mipmap.back
                );
        Bitmap bm = Bitmap.createScaledBitmap(f1.getBitmap(),80,80,false);

        ab.setHomeAsUpIndicator(new BitmapDrawable(bm));
        ab.setDisplayHomeAsUpEnabled(true);

        //recycler view
        rv = (RecyclerView) findViewById(R.id.rv);
        if(rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        }

        //buscamos datos en la base de datos
        List<Frutas>listaFrutas = Frutas.listAll(Frutas.class);
        setupRecyclerview(listaFrutas);
        //Hacemos la Peticion de los datos
        try {
            sendGetFrutas(rv);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void sendGetFrutas(final View v) throws JSONException, UnsupportedEncodingException {

        // Formacion de Parametros a Enviar
        RequestParams datos = new RequestParams();
        datos.put("category", "Fruit");
        datos.put("item", "Peaches");


        mProgressDialog = Util.CreateProgressDialog(context);

        // Inicia la Peticion al Api URL_BASE + Controller + Action(Nombre
        // del Metodo) + Parametros

        MantenimientoAppClient mantenimientoAppClient = new MantenimientoAppClient();
        mantenimientoAppClient.addHeader("Content-Type ","application/json");
        mantenimientoAppClient.get(MantenimientoAppClient.BASE_URL,"",
                "", datos,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onFinish() {
                        mProgressDialog.dismiss();
                        super.onFinish();
                    }

                    @Override
                    public void onFailure(Throwable e,
                                          JSONObject errorResponse) {
                        mProgressDialog.dismiss();
                        Snackbar.make(v,"Oops! fallo tu conexión a internet, por favor vuelve a intentarlo.", Snackbar.LENGTH_LONG).show();
                        Log.e(TAG, "Problema de Conexión");

                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        super.onSuccess(response);
                    }

                    @Override
                    public void onSuccess(int statusCode,
                                          JSONObject response) {
                        super.onSuccess(statusCode, response);
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        // Recibimos peticion exitosa con un JSONObject de Respuesta


                            LoadJsonaResponse(response);
                        super.onSuccess(statusCode, response);
                    }
                });

    }

    private void LoadJsonaResponse(JSONArray response) {
        //eliminamos los datos de la tabla para actualizar la lista
        Frutas.deleteAll(Frutas.class);
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject frutas = response.getJSONObject(i);
                String zipcode = frutas.getString("zipcode");
                String item = frutas.getString("item");
                String business = frutas.getString("business");
                String farmer_id = frutas.getString("farmer_id");
                String category = frutas.getString("category");
                String l = frutas.getString("l");
                String farm_name = frutas.getString("farm_name");
                String phone1 = "";
                Frutas fruta = new Frutas(zipcode, item, business, farmer_id, category, l, farm_name, phone1);
                fruta.save();
        }catch(Exception e){
                e.printStackTrace();
        }
    }
        List<Frutas>listaFrutas = Frutas.listAll(Frutas.class);
        setupRecyclerview(listaFrutas);

    }


    public void setupRecyclerview(List<Frutas> listaFrutas) {
        if(!listaFrutas.isEmpty()) {
            rv.setAdapter(new ListaFrutasRecyclerViewAdapter(context, listaFrutas));
            rv.setVisibility(View.VISIBLE);
        }else{
            rv.setVisibility(View.GONE);
        }

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

