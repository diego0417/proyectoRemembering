package com.example.diego.inicio2.vistas;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.diego.inicio2.R;
import com.example.diego.inicio2.vistas.amigos.AgregarAmigo;
import com.example.diego.inicio2.vistas.amigos.MisAmigos;
import com.example.diego.inicio2.vistas.amigos.Solicitudes;

/**
 * Created by diego on 20/08/2015.
 */
public class  Amigos extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_amigos);
        // PANTALLA EN VERTICAL
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab().setText("Solicitudes").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Mis Amigos").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Agregar").setTabListener(this);
        actionBar.addTab(tab);
    }



    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    return new Solicitudes();
                case 1:
                    return new MisAmigos();
                case 2:
                    return new AgregarAmigo();
                default:
                    return null;
            }
        }
        public int getCount() {
            return 3;
        }
    }





    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }





}
