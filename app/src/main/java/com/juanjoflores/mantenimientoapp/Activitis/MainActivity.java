package com.juanjoflores.mantenimientoapp.Activitis;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.juanjoflores.mantenimientoapp.Adapters.CerrarSesionDialogFragment;
import com.juanjoflores.mantenimientoapp.Adapters.MainFragmentPagerAdapter;
import com.juanjoflores.mantenimientoapp.Adapters.NavDrawerListAdapter;
import com.juanjoflores.mantenimientoapp.DB.Roles;
import com.juanjoflores.mantenimientoapp.DB.StatusTareas;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadas;
import com.juanjoflores.mantenimientoapp.DB.TareasAsignadasDbManager;
import com.juanjoflores.mantenimientoapp.DB.Usuarios;
import com.juanjoflores.mantenimientoapp.DB.UsuariosDbManager;
import com.juanjoflores.mantenimientoapp.Fragments.FiltrosListaTareasDialogFragment;
import com.juanjoflores.mantenimientoapp.Fragments.ListaTareasFragment;
import com.juanjoflores.mantenimientoapp.Fragments.ListaTareasTecnicoFragment;
import com.juanjoflores.mantenimientoapp.Interfaces.ISelectedData;
import com.juanjoflores.mantenimientoapp.Items.NavDrawerItem;
import com.juanjoflores.mantenimientoapp.R;
import com.juanjoflores.mantenimientoapp.Utils.SessionManager;
import com.juanjoflores.mantenimientoapp.Views.AutoScaleTextView;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class MainActivity extends BaseActivity implements ISelectedData{
    private MainFragmentPagerAdapter adapter;
    private DrawerLayout drawerLayout;
    private ListView mDrawerList;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private int selectedItemViewPager;
    private FloatingActionButton fabFiltro;

    private ActionBarDrawerToggle mDrawerToggle;

    // Titulo del Drawer
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapterNavDrawer;
    private Context context;

    private Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Context
        context = this;
        //fab filtro
        fabFiltro = (FloatingActionButton) findViewById(R.id.fab);
        fabFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showFragmentFiltrosTareas();

            }
        });

        //Recuperamos los datos del usuario
            UsuariosDbManager usuariosDbManager = new UsuariosDbManager(context);
            usuario = usuariosDbManager.getUsuarioSesion();

        //Adapter para el ViewPAger
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        if(usuario != null){
            TareasAsignadasDbManager tareasAsignadasDbManager = new TareasAsignadasDbManager(context);
            List<TareasAsignadas> listTareas = new ArrayList<>();

            //Cargamos las tareas y configuracione dependiendo del perfil del usuario
            switch (usuario.getRol().getTipo()){
                case ROL_ADMINISTRADOR:
                    fabFiltro.setVisibility(View.VISIBLE);
                    listTareas = tareasAsignadasDbManager.getTareasTodas();
                    if(!listTareas.isEmpty()) {
                        ListaTareasFragment listaTareasFragment = new ListaTareasFragment();
                        listaTareasFragment.setListTareas(listTareas);
                        adapter.addFragment(listaTareasFragment, TITULO_ADIMISTRADOR);
                    }
                    break;
                case ROL_TECNICO:
                    fabFiltro.setVisibility(View.GONE);
                    listTareas = tareasAsignadasDbManager.getTareasTecnico(usuario);
                    if(!listTareas.isEmpty())
                        adapter.addFragment(new ListaTareasTecnicoFragment(listTareas), TITULO_TECNICO);
                    break;
            }
        }

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //actionBar
        final ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.list));
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        //Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        //Llenamos los datos del usuario en el drawer
        AutoScaleTextView txtname = (AutoScaleTextView) findViewById(R.id.textViewHeader);
        TextView txtCorreo = (TextView) findViewById(R.id.textCorreo);
        if(usuario != null) {
            txtname.setText(usuario.getNombre());
            txtCorreo.setText(usuario.getCorreo());
        }

        //view Pager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            viewPager.setAdapter(adapter);
        }

        //tabs
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);



        // Carga Items del Menu Custom
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // Iconos para el Menu Custom
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);


        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // agregar un nuevo item al menu deslizante
        for(int i = 0; i<navMenuTitles.length;i++){
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1), i));
        }

        if(usuario.getRol().getTipo() == ROL_TECNICO){
            navDrawerItems.remove(0);
        }

        //
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // nav drawer list adapter
        adapterNavDrawer = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapterNavDrawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.mipmap.list,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                ab.setTitle(mTitle);

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                ab.setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {

        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        TareasAsignadasDbManager tareasAsignadasDbManager = new TareasAsignadasDbManager(context);
        List<TareasAsignadas> listaTareas = tareasAsignadasDbManager.getTareasTodas();
        try {
            ((ListaTareasFragment)adapter.getFragment(0)).setupRecyclerview(listaTareas);
        }catch (Exception e){

        }

    }

    private void showActivityAgregarTarea() {
        Intent intentAgregarTarea = new Intent(context, AgregarTareaActivity.class);
        startActivity(intentAgregarTarea);
    }

    private void showActivityFrutas() {
        Intent intentFrutas = new Intent(context, VerFrutasActivity.class);
        startActivity(intentFrutas);
    }

    void showFragmentFiltrosTareas() {
        DialogFragment newFragment = FiltrosListaTareasDialogFragment.newInstance();
        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    void showFragmentCerrarSesion() {
       DialogFragment newFragment = CerrarSesionDialogFragment.newInstance(context);
        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);
        newFragment.show(getSupportFragmentManager(), "dialog");

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                break;
        }
        return false;
    }
    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                switch (item.getItemId()){

                }
                return true;
            }
        });

    }

    @Override
    public void onSelectedData(int value) {
        TareasAsignadasDbManager tareasAsignadasDbManager = new TareasAsignadasDbManager(context);
        List<TareasAsignadas> listaTareas ;
        switch (value){
            case R.id.chkbtn_espera:
               listaTareas =tareasAsignadasDbManager.getTareasEstatus(1);
                ((ListaTareasFragment)adapter.getFragment(0)).setupRecyclerview(listaTareas);
                break;
            case R.id.chkbtn_iniciada:
                listaTareas =tareasAsignadasDbManager.getTareasEstatus(2);
                ((ListaTareasFragment)adapter.getFragment(0)).setupRecyclerview(listaTareas);
                break;
            case R.id.chkbtn_terminada:
                listaTareas =tareasAsignadasDbManager.getTareasEstatus(3);
                ((ListaTareasFragment)adapter.getFragment(0)).setupRecyclerview(listaTareas);
                break;
            case R.id.chkbtn_todos:
                listaTareas = tareasAsignadasDbManager.getTareasTodas();
                ((ListaTareasFragment)adapter.getFragment(0)).setupRecyclerview(listaTareas);
                break;
            default:
                break;
        }
    }



    /* */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

     /* Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        int itemId = navDrawerItems.get(position).getId();
        switch (itemId) {
            case 0:
                showActivityAgregarTarea();
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case 1:
                showActivityFrutas();
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case 2:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case 3:
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case 4:
                showFragmentCerrarSesion();
                drawerLayout.closeDrawer(Gravity.START);
                break;

            default:
                drawerLayout.closeDrawer(Gravity.START);
                break;
        }

    }


    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {



        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }else{
            super.onBackPressed();
        }

    }
}


