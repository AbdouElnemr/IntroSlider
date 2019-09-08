package com.codex.eduapp.school.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codex.eduapp.school.R;
import com.codex.eduapp.school.helper.PrefManager;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.Locale;

public class Choose_Type extends AppCompatActivity {
    ImageView img_student,img_parent,img_stuff;
    public static String type;
    PrefManager prefManager;
    TextView t_Change_lang;
    Dialog b;
    String selected_language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager=new PrefManager(this);
        String selected_language = prefManager.GetLanguage();
        Locale locale = new Locale(selected_language);
        Log.e("lang", selected_language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_choose__type);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitsViews();

    }
    public void InitsViews(){

        prefManager=new PrefManager(this);
        img_parent=findViewById(R.id.parent);
        img_student=findViewById(R.id.et_student);
        img_stuff=findViewById(R.id.staff);
        t_Change_lang=findViewById(R.id.t_change);
        if (prefManager.GetLanguage().equals("en")) {
            t_Change_lang.setText("ENG");
        }
        else {
            t_Change_lang.setText("AR");

        }
        t_Change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Choose_Type.this);
                LayoutInflater inflater = Choose_Type.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                dialogBuilder.setView(dialogView);

                final int arrayId = R.array.language_items;
                String[] values = Choose_Type.this.getResources().getStringArray(arrayId);

                ListView listView1 = (ListView) dialogView.findViewById(R.id.lsl);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Choose_Type.this,
                        R.layout.textcenter, values);
                listView1.setAdapter(adapter);
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        doFilterList(position);

                    }
                });
                b = dialogBuilder.create();
                b.show();

            }
        });



        img_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="parent";
                prefManager.setrole(type);
                img_parent.setBackgroundColor(Color.parseColor("#4D000000"));
                startActivity(new Intent(Choose_Type.this,Login.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        });

        img_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="student";
                prefManager.setrole(type);
                img_student.setBackgroundColor(Color.parseColor("#4D000000"));
                startActivity(new Intent(Choose_Type.this,Login.class));
                finish();
            }
        });
        img_stuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="teacher";
                prefManager.setrole(type);
                img_stuff.setBackgroundColor(Color.parseColor("#4D000000"));
                startActivity(new Intent(Choose_Type.this,Login.class));
                finish();
            }
        });


    }

    private void doFilterList(int position) {
        b.dismiss();

        if (position == 0) {
            String languageToLoad = "en";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            t_Change_lang.setText("ENG");


            selected_language = "en";
            prefManager.setLanguage(selected_language);
            Intent refresh = new Intent(Choose_Type.this, Splash.class);
            startActivity(refresh);
            finish();
        } else {
            Resources res2 = getApplicationContext().getResources();
            DisplayMetrics dm2 = res2.getDisplayMetrics();
            android.content.res.Configuration conf2 = res2.getConfiguration();
            conf2.locale = new Locale("ar");
            res2.updateConfiguration(conf2, dm2);
            t_Change_lang.setText("AR");
            selected_language = "ar";
            prefManager.setLanguage(selected_language);
            Intent refresh = new Intent(Choose_Type.this, Splash.class);
            startActivity(refresh);
            finish();
        }
    }

}
