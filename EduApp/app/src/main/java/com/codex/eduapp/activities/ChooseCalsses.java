package com.codex.eduapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

 import com.codex.eduapp.helper.AlertMotInternet;
import com.codex.eduapp.helper.NetworkAvailable;
import com.codex.eduapp.helper.PrefManager;
import com.codex.eduapp.network.models.ClassessModel;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChooseCalsses extends AppCompatActivity implements AllRequestsAPI.OnComplete {

    MaterialSpinner spinner_clasess, spinner_sectionsm;
    TextView t_choose_date, T_Search;
    AllRequestsAPI allRequestsAPI;
    NetworkAvailable networkAvailable;
    List<String> list_classes = new ArrayList<>();
    List<String> list_Sections = new ArrayList<>();
    ClassessModel.Get_Classes get_classes;
    List<String> Classess_Ids = new ArrayList<>();
    List<String> Sections_Ids = new ArrayList<>();
    String Sec_Id = "nll";
    String Classe_id = "nll";
    PrefManager prefManager;
    boolean flage=false;
    boolean flage1=false;
    ImageView img_back;
    String dat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_calsses);
        try {

            InitsViews();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void InitsViews() throws InterruptedException {
        spinner_clasess = findViewById(R.id.spinner_class);
        spinner_sectionsm = findViewById(R.id.spinner_section);
        t_choose_date = findViewById(R.id.choose_date);
        img_back=findViewById(R.id.back);


        T_Search = findViewById(R.id.T_search);
        if (getIntent().getStringExtra("flage").equals("1")){
            spinner_sectionsm.setVisibility(View.VISIBLE);
            t_choose_date.setVisibility(View.GONE);
        }
        else {
            spinner_sectionsm.setVisibility(View.GONE);
        }
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        prefManager=new PrefManager(this);
        networkAvailable = new NetworkAvailable();

        spinner_clasess.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                int pos = list_classes.indexOf(item);
                Classe_id = Classess_Ids.get(pos);
                flage=true;
                list_Sections.clear();
                Sections_Ids.clear();
                for (int z = 0; z < get_classes.getData().get(pos).getSections().size(); z++) {
                    list_Sections.add(get_classes.getData().get(pos).getSections().get(z).getSectionName());
                    Sections_Ids.add(get_classes.getData().get(pos).getSections().get(z).getSectionId() + "");
                }
                spinner_sectionsm.setItems(list_Sections);

            }
        });
        t_choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ChooseCalsses.this);
                dialog.setContentView(R.layout.calender);
                Calendar calendar = Calendar.getInstance();

                MaterialCalendarView calendarView=(MaterialCalendarView)dialog.findViewById(R.id.calendarView);

                calendarView.setSelectedDate(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH)));
                calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                        final SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd", new Locale("en"));
                        dialog.dismiss();
                         dat = calendarDay.getDate()+"";
                        t_choose_date.setText(dat);
                        flage1=true;
                    }
                });






                dialog.show();

            }
        });

        spinner_sectionsm.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                int pos = list_Sections.indexOf(item);
                Sec_Id = Sections_Ids.get(pos);
                flage=true;
            }
        });
        if (!networkAvailable.isNetworkAvailable(
                ChooseCalsses.this)) {
            new AlertMotInternet(ChooseCalsses.this, T_Search);

        } else {

            if (networkAvailable.isNetworkAvailable(ChooseCalsses.this)) {
                allRequestsAPI = new AllRequestsAPI(ChooseCalsses.this);
                allRequestsAPI.GetClasses_Method();


            } else {
                new AlertMotInternet(ChooseCalsses.this, T_Search);

            }

        }
        T_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("flage").equals("1")) {
                    if (flage) {
                        Intent intent = new Intent(ChooseCalsses.this, Students.class);
                        intent.putExtra("sec_id", Sec_Id);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ChooseCalsses.this, getString(R.string.pleaseselectsec), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    if (flage) {
                        if (flage1) {
                            Intent intent = new Intent(ChooseCalsses.this, AttendanceStudent.class);
                            intent.putExtra("class_id",Classe_id);
                            intent.putExtra("date",dat );
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(ChooseCalsses.this, getString(R.string.Pleasedate), Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(ChooseCalsses.this, getString(R.string.pleaseselectsec), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof ClassessModel.Get_Classes) {

            get_classes = (ClassessModel.Get_Classes) object;
            for (int x = 0; x < get_classes.getData().size(); x++) {
                list_classes.add(get_classes.getData().get(x).getClassName());
                Classess_Ids.add(get_classes.getData().get(x).getClassId() + "");


            }
            spinner_clasess.setItems(list_classes);
        }

//                    ClassessModel.Get_Classes obj = gson.fromJson(object, ClassessModel.Get_Classes.class);
        ;
    }
}
