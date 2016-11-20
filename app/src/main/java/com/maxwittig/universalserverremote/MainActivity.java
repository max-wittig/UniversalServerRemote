package com.maxwittig.universalserverremote;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.maxwittig.universalserverremote.fragments.AlertFragment;
import com.maxwittig.universalserverremote.fragments.SettingsFragment;
import com.maxwittig.universalserverremote.fragments.VolumeControlFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private PostSender postSender;
    private Settings settings;
    private String url = "192.168.1.104";
    private int port = 8000;

    public MainActivity()
    {
        settings = new Settings();
        postSender = new PostSender(this, settings);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadSharedPreferences();
        openLastFragment();
    }

    private void openLastFragment()
    {

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void loadSharedPreferences()
    {
        SharedPreferences settings = getSharedPreferences("main", 0);
        if(settings != null)
        {
            postSender.getSettings().setPort(settings.getInt("hostPort", 8000));
            postSender.getSettings().setUrl(settings.getString("hostIP", "127.0.0.1"));
            try
            {
                String classString = "com.maxwittig.universalserverremote.fragments." + settings.getString("currentOpenFragmentName", "SettingsFragment");
                Class className = Class.forName(classString);
                Fragment fragment = (Fragment)className.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void saveCurrentFragment(String currentOpenFragmentName)
    {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences("main", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("currentOpenFragmentName", currentOpenFragmentName);
        editor.apply();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment chosenFragment = null;
        Bundle bundle = new Bundle();
        bundle.putParcelable("PostSender", postSender);

        if (id == R.id.nav_alert)
        {
            chosenFragment = new AlertFragment();
            saveCurrentFragment("AlertFragment");
        }
        else if (id == R.id.nav_volume)
        {
            chosenFragment = new VolumeControlFragment();
            saveCurrentFragment("VolumeControlFragment");

        }
        else if (id == R.id.nav_settings)
        {
            chosenFragment = new SettingsFragment();
            saveCurrentFragment("SettingsFragment");
        }

        if(chosenFragment != null)
        {
            chosenFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.content_main, chosenFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
