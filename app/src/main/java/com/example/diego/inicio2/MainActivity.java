package com.example.diego.inicio2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.media.MediaDescriptionCompatApi21;

import android.support.v4.widget.DrawerLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.example.diego.inicio2.Manejadores.ManejadorNotificaciones;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.Servicios.Solicitudes;
import com.example.diego.inicio2.vistas.Amigos;
import com.example.diego.inicio2.vistas.CamaraGaleria;
import com.example.diego.inicio2.vistas.Inicio;
import com.example.diego.inicio2.vistas.Loggin;
import com.example.diego.inicio2.vistas.NavDrawerItem;
import com.example.diego.inicio2.vistas.NavDrawerListAdapter;
import com.example.diego.inicio2.vistas.Perfil;
import com.example.diego.inicio2.vistas.Sugerencia;
import com.example.diego.inicio2.vistas.Videos;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;


public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private static ArrayList<NavDrawerItem> navDrawerItems;
    private static NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent servicio = new Intent(this, Solicitudes.class);
        startService(servicio);

        //seteo la variable que sabe el estado del menu
        menuAct = false;

        setContentView(R.layout.activity_main);
        // PANTALLA EN VERTICAL
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // agregar un nuevo item al menu deslizante
        // Perfil
        navDrawerItems.add(new NavDrawerItem(ManejadorUsuario.usuario.getNombre()+" "+ManejadorUsuario.usuario.getApellido(), navMenuIcons.getResourceId(0, -1),ManejadorUsuario.usuario.getMail()));
        // Inicio
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1), true, "+99"));
        // Grabar
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Contactos
        int solicitudes = ManejadorUsuario.cuentaSolicitudes();
        if (solicitudes != 0){
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, Integer.toString(solicitudes)));
        }else
        {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        }


        //ver Videos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // cerrar sesion
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(1);
        }
    }

    /**
     * Slide menu item click listener
     * */
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //variable que sabe cuando esta abierto el menu
    private boolean menuAct;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(menuAct) {
          mDrawerLayout.closeDrawer(mDrawerList);
        }else {
            this.moveTaskToBack(true);
        }
        return true;
    }


    public static void modificaAmigosSolicitud(int cant){
        NavDrawerItem aux = navDrawerItems.get(3);
        aux.setCount(Integer.toString(cant));
        aux.setCounterVisibility(true);
        Log.i("diegoooooooooooo", "la en main cantidad es: "+cant);
        navDrawerItems.set(3,aux);
        adapter = new NavDrawerListAdapter(ApplicationContextProvider.getContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menuAct = drawerOpen;
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Intent intent = null;
        AlertDialog.Builder alert = null;
        switch (position) {
            case 0:
                fragment = new Perfil();
                break;
            case 1:
                fragment = new Inicio();
                break;
            case 2:
                intent = new Intent(MainActivity.this, CamaraGaleria.class);
                break;
            case 3:
                intent = new Intent(MainActivity.this, Amigos.class);
                break;
            case 4:
                fragment = new Videos();
                break;
            case 5:
                fragment = new Sugerencia();
                break;
            case 6:
                //aca va el alert info mati
                alert = new AlertDialog.Builder(this);
                Log.i("diegooooooooooooo", "paso");
                alert.setIcon(R.drawable.ic_launcher_diego_web);
                alert.setTitle("Remembering");
                alert.setMessage("aca ponemos la informacion que queramos :)");
                alert.setCancelable(true);
                alert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                break;
            case 7:
            {
                alert = new AlertDialog.Builder(this);
                Log.i("diegooooooooooooo", "paso");
                alert.setIcon(R.drawable.ic_launcher_diego_web);
                alert.setTitle("Cerrar Session");
                alert.setMessage("Deseas cerrar la sesion?");
                alert.setCancelable(true);
                alert.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            Intent intent;

                            public void onClick(DialogInterface dialog, int id) {
                                ManejadorUsuario.usuario = null;
                                intent = new Intent(getApplicationContext(), Loggin.class);
                                startActivity(intent);
                                Intent servicio = new Intent(getApplicationContext(), Solicitudes.class);
                                stopService(servicio);
                            }
                        });
                alert.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
            }


            default:
                break;
        }

        if(alert!=null){
            AlertDialog alert11 = alert.create();
            alert11.show();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        if(intent!=null){
            startActivity(intent);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
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

}
