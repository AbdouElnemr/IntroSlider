package com.codex.eduapp.school.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

 import com.codex.eduapp.school.adapter.HomeWorkAdapter;
import com.codex.eduapp.school.helper.AlertMotInternet;
import com.codex.eduapp.school.helper.NetworkAvailable;
import com.codex.eduapp.school.network.Requests.AllRequestsAPI;
import com.codex.eduapp.school.network.models.AssigmnetsModel;
import com.codex.eduapp.school.network.models.ClassessModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class HomeWork extends AppCompatActivity implements AllRequestsAPI.OnComplete {
    ImageView img_add_homework,img_back;
    NetworkAvailable networkAvailable;
    List<String> list_Sections_ids = new ArrayList<>();
    List<String> list_Sections = new ArrayList<>();
    MaterialSpinner spiner_sections, spinner_classes;
    ClassessModel.Get_Classes get_classes;
    List<String> list_classes_ids = new ArrayList<>();
    List<String> list_classes = new ArrayList<>();
    RecyclerView recyclerView;
    String Sec_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        InitsViews();
    }

    public void InitsViews() {
        img_add_homework = findViewById(R.id.add_home);
        spinner_classes = findViewById(R.id.spineer);
        spiner_sections = findViewById(R.id.spineer1);
        recyclerView=findViewById(R.id.rec_student);
        img_back=findViewById(R.id.et_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeWork.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        networkAvailable = new NetworkAvailable();
        img_add_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeWork.this, ADDHomework.class));
            }
        });
        if (networkAvailable.isNetworkAvailable(HomeWork.this)) {
            AllRequestsAPI allRequestsAPI = new AllRequestsAPI(HomeWork.this);
            allRequestsAPI.GetClasses_Method();

        } else {
            new AlertMotInternet(HomeWork.this, img_add_homework);

        }
        spinner_classes.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                list_Sections.clear();
                list_Sections_ids.clear();
                for (int z = 0; z < get_classes.getData().get(position).getSections().size(); z++) {
                    list_Sections.add(get_classes.getData().get(position).getSections().get(z).getSectionName());
                    list_Sections_ids.add(get_classes.getData().get(position).getSections().get(z).getSectionId() + "");

                }
                spiner_sections.setItems(list_Sections);
            }
        });
        spiner_sections.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                int pos = list_Sections.indexOf(item);
                Sec_Id = list_Sections_ids.get(pos);
                if (networkAvailable.isNetworkAvailable(HomeWork.this)) {
                    AllRequestsAPI allRequestsAPI = new AllRequestsAPI(HomeWork.this);
                    allRequestsAPI.Assignments_methods(Sec_Id);

                } else {
                    new AlertMotInternet(HomeWork.this, img_add_homework);

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
                list_classes_ids.add(get_classes.getData().get(x).getClassId() + "");
//                for (int i=0;i<get_classes.getData().get(x).getSections().size();i++){
//                    list_Sections_ids.add(get_classes.getData().get(x).getSections().get(i).getSectionId()+"");
//                    list_Sections.add(get_classes.getData().get(x).getSections().get(i).getSectionName());
//                }
            }
            spinner_classes.setItems(list_classes);
        }
        if (object instanceof AssigmnetsModel.GetAssigent) {
            recyclerView.setAdapter(new HomeWorkAdapter(((AssigmnetsModel.GetAssigent) object).getData(), HomeWork.this));

        }
    }
}
