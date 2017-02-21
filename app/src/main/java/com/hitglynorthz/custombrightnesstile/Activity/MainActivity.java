package com.hitglynorthz.custombrightnesstile.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hitglynorthz.custombrightnesstile.Nav.NavHome;
import com.hitglynorthz.custombrightnesstile.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initToolbar();
        initDrawer();
        initFABS();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initDrawer() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //init home fragment
        fragmentManager.beginTransaction().replace(R.id.content_frame, new NavHome(), "NHOME").commit();
        navigationView.setCheckedItem(R.id.nav_home);
        setTitle(getString(R.string.nav_home));
    }

    private void initFABS() {
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //back to home
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);
            if (!currentFragment.getTag().equals("NHOME")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new NavHome(), "NHOME").commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }
            if (currentFragment.getTag().equals("NHOME")) {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new NavHome(), "NHOME").commit();
            setTitle(getString(R.string.nav_home));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
