package com.codex.eduapp.school.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

 import com.codex.eduapp.school.fragments.Announcement;
import com.codex.eduapp.school.fragments.Leaderboard;
import com.codex.eduapp.school.helper.PrefManager;

import java.util.Locale;

public class HomePage extends AppCompatActivity {
    DrawerLayout drawer;
    ImageView immenu;
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        InitsViews();

    }

    public void InitsViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        drawer = findViewById(R.id.drawer_layout);
        immenu = findViewById(R.id.et_back);
        prefManager=new PrefManager(this);
        String selected_language = prefManager.GetLanguage();
        Locale locale = new Locale(selected_language);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        tabLayout =   findViewById(R.id.tabLayout);
        frameLayout =   findViewById(R.id.frameLayout);

        fragment = new Announcement();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();


        NavigationView navigationView =   findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image1);
        navigationView.getMenu().getItem(2).setActionView(R.layout.menu_image2);
        navigationView.getMenu().getItem(3).setActionView(R.layout.appointments);
        navigationView.getMenu().getItem(4).setActionView(R.layout.homewrk);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_camera:
                        startActivity(new Intent(HomePage.this, HomePage.class));
                        finish();
                        break;

                    case R.id.nav_gallery:
                        Intent intent = new Intent(HomePage.this, ChooseCalsses.class);
                        intent.putExtra("flage", "0");
                        startActivity(intent);
                        break;
                    case R.id.nav_slideshow:
                        Intent intent1 = new Intent(HomePage.this, ChooseCalsses.class);
                        intent1.putExtra("flage", "1");
                        startActivity(intent1);
                        break;
                    case R.id.nav_manage:
                        startActivity(new Intent(HomePage.this, Schedules.class));

                        break;
                    case R.id.nav_howwork:
                        startActivity(new Intent(HomePage.this, HomeWork.class));

                        break;





                }
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }
        });






        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new Announcement();
                        break;
                    case 1:
                        fragment = new Leaderboard();
                        break;

                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        immenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.START);


            }

        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
