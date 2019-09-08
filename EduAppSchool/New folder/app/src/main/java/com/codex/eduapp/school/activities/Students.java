package com.codex.eduapp.school.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

 import com.codex.eduapp.school.adapter.Student;
import com.codex.eduapp.school.helper.AlertMotInternet;
import com.codex.eduapp.school.helper.NetworkAvailable;
import com.codex.eduapp.school.network.Requests.AllRequestsAPI;
import com.codex.eduapp.school.network.models.GetStudents;

public class Students extends AppCompatActivity implements AllRequestsAPI.OnComplete {
    RecyclerView recyclerView;
    NetworkAvailable networkAvailable;
    GetStudents.Get_Students get_students;
    ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        Initsviews();
    }

    public void Initsviews() {
        recyclerView = findViewById(R.id.rec_student);
        img_back=findViewById(R.id.et_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        networkAvailable=new NetworkAvailable();
        LinearLayoutManager layoutManager = new LinearLayoutManager(Students.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        if (!networkAvailable.isNetworkAvailable(Students.this)) {
            new AlertMotInternet(Students.this, recyclerView);

        } else {

            if (networkAvailable.isNetworkAvailable(Students.this)) {
                AllRequestsAPI allRequestsAPI = new AllRequestsAPI(Students.this);
                allRequestsAPI.Get_Students_methods(getIntent().getStringExtra("sec_id"));


            } else {
                new AlertMotInternet(Students.this, recyclerView);

            }
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Students.this,ChooseCalsses.class);
        intent.putExtra("flage","1");
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof GetStudents.Get_Students){
            get_students=(GetStudents.Get_Students)object;
            recyclerView.setAdapter(new Student(get_students.getData(),Students.this));

        }

    }
}
