package com.codex.eduapp.school.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

 import com.codex.eduapp.school.adapter.StudentAttendanceAdapter;
import com.codex.eduapp.school.helper.AlertMotInternet;
import com.codex.eduapp.school.helper.NetworkAvailable;
import com.codex.eduapp.school.network.Requests.AllRequestsAPI;
import com.codex.eduapp.school.network.models.attendanceStudentModel;

public class GetAttendaceStudent extends AppCompatActivity implements AllRequestsAPI.OnComplete {
    RecyclerView recyclerView;
    NetworkAvailable networkAvailable;
    attendanceStudentModel.GetAddendanceStudent get_students;
    ImageView img_back;
    TextView txt_Titlte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_attendace_student);
        Initsviews();
    }

    public void Initsviews() {
        recyclerView = findViewById(R.id.rec_student);
        img_back = findViewById(R.id.et_back);
        txt_Titlte = findViewById(R.id.title);
        networkAvailable = new NetworkAvailable();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(GetAttendaceStudent.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        txt_Titlte.setText(getIntent().getStringExtra("name"));
        if (!networkAvailable.isNetworkAvailable(GetAttendaceStudent.this)) {
            new AlertMotInternet(GetAttendaceStudent.this, recyclerView);

        } else {

            if (networkAvailable.isNetworkAvailable(GetAttendaceStudent.this)) {
                AllRequestsAPI allRequestsAPI = new AllRequestsAPI(GetAttendaceStudent.this);
                allRequestsAPI.Get_AttendancOFstudent_methods(getIntent().getStringExtra("student_id"));


            } else {
                new AlertMotInternet(GetAttendaceStudent.this, recyclerView);

            }
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GetAttendaceStudent.this, ChooseCalsses.class);
        intent.putExtra("flage", "1");
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof attendanceStudentModel.GetAddendanceStudent) {

            get_students = (attendanceStudentModel.GetAddendanceStudent) object;
            if (get_students.getData().size() > 0) {
                recyclerView.setAdapter(new StudentAttendanceAdapter(get_students.getData(), GetAttendaceStudent.this));
            } else {
                finish();
            }
        }

    }
}