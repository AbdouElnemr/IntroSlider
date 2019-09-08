package com.codex.eduapp.school.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

 import com.codex.eduapp.school.adapter.SchedulesAdapter;
import com.codex.eduapp.school.helper.AlertMotInternet;
import com.codex.eduapp.school.helper.NetworkAvailable;
import com.codex.eduapp.school.helper.PrefManager;
import com.codex.eduapp.school.network.Requests.AllRequestsAPI;
import com.codex.eduapp.school.network.models.GeTShechules;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Schedules extends AppCompatActivity implements AllRequestsAPI.OnComplete {
    RecyclerView recyclerView;
    NetworkAvailable networkAvailable;
    GeTShechules.Get_schudel get_students;
    ImageView img_back;
    MaterialSpinner materialSpinner_days;
    List<String>list_Days=new ArrayList<>();
    String appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules);
        Initsviews();
    }

    public void Initsviews() {
        recyclerView = findViewById(R.id.rec_student);
        img_back = findViewById(R.id.et_back);
        materialSpinner_days=findViewById(R.id.spineer);
        list_Days.add(getString(R.string.Saturday));
        list_Days.add(getString(R.string.Sunday));
        list_Days.add(getString(R.string.Monday));
        list_Days.add(getString(R.string.Tuesday));
        list_Days.add(getString(R.string.Wednesday));
        list_Days.add(getString(R.string.Thursday));
        list_Days.add(getString(R.string.Friday));
        materialSpinner_days.setItems(list_Days);
        networkAvailable = new NetworkAvailable();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        materialSpinner_days.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Configuration conf = getResources().getConfiguration();
                conf.locale = new Locale("en"); // locale I used here is Arabic
                Resources resources = new Resources(getAssets(), getResources().getDisplayMetrics(), conf);
                /* get localized string */
                int x=list_Days.indexOf(item);
                String[] menuArray=resources.getStringArray(R.array.aee);
                 appName = menuArray[x];
                if (networkAvailable.isNetworkAvailable(Schedules.this)) {
                    AllRequestsAPI allRequestsAPI = new AllRequestsAPI(Schedules.this);
                    allRequestsAPI.Schedules_methods(appName);

                } else {
                    new AlertMotInternet(Schedules.this, recyclerView);

                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(Schedules.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


    }



    @Override
    public void onSuccess(Object object) {
        if (object instanceof GeTShechules.Get_schudel) {
            Log.e(":ef", "ahmed al;i");

            get_students = (GeTShechules.Get_schudel) object;
            try {


                if (get_students.getData().size() > 0) {

                    recyclerView.setAdapter(new SchedulesAdapter(get_students.getData(), Schedules.this));
                    PrefManager prefManager=new PrefManager(this);
                    String selected_language = prefManager.GetLanguage();
                    Locale locale = new Locale(selected_language);
                    Log.e("lang", selected_language);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                } else {

                    finish();
                }
            }
            catch (Exception x){
                recyclerView.setAdapter(null);
            }
        }

    }
}