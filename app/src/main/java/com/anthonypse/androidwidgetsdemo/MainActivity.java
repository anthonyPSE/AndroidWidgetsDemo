package com.anthonypse.androidwidgetsdemo;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.anthonypse.androidutils.InfoUtil;
import com.anthonypse.androidwidgetsdemo.gui.pages.DeviceSettings;
import com.anthonypse.androidwidgetsdemo.gui.pages.InternalHardware;
import com.anthonypse.androidwidgetsdemo.gui.pages.IntroPage;
import com.anthonypse.androidwidgetsdemo.gui.pages.Networks;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private InfoUtil mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInfo = new InfoUtil(this, getClass().getSimpleName());
        bind();
        initNavDrawer();

        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        placeFragmentInFrame(new IntroPage());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Bind UI elements to data members.
     */
    private void bind(){
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Sets up the navigation drawer's on-click event.
     */
    private void initNavDrawer(){
        Log.i(TAG, "initNavDrawer");

        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeader = navigationView.getHeaderView(0);
        if(navHeader != null){
            navHeader.findViewById(R.id.textUrl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, "AnthonyPSE@Gmail.com");
                        startActivity(Intent.createChooser(intent, "Send Email"));
                    } catch (ActivityNotFoundException e){
                        mInfo.logError("Couldn't Launch email");
                    } catch (Exception e){
                        mInfo.logError("Unhandled exception when trying to open email intent");
                    }

                }
            });
        }
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return handleNavDrawerItemSelect(menuItem.getItemId());
                    }
                });
    }

    private boolean handleNavDrawerItemSelect(int menuItem) {
        Log.i(TAG, "handleNavDrawerItemSelect");

        switch (menuItem) {
            case R.id.nav_device_settings:
                placeFragmentInFrame(new DeviceSettings());
                return true;
            case R.id.nav_networks:
                placeFragmentInFrame(new Networks());
                return true;
            case R.id.nav_peripherals:
                placeFragmentInFrame(new InternalHardware());
                return true;
            default:
                placeFragmentInFrame(new IntroPage());
                return true;
        }
    }

    private void placeFragmentInFrame(Fragment newFrag){
        Log.i(TAG, "placeFragmentInFrame");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, newFrag);
        fragmentTransaction.commit();
    }

}
