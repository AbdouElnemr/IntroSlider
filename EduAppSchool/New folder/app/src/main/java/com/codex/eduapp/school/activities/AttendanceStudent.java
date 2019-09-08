package com.codex.eduapp.school.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

 import com.codex.eduapp.school.adapter.AttendanceALLStudent;
import com.codex.eduapp.school.helper.AlertMotInternet;
import com.codex.eduapp.school.helper.NetworkAvailable;
import com.codex.eduapp.school.network.Requests.AllRequestsAPI;
import com.codex.eduapp.school.network.models.AddendaceModel;

import org.json.JSONArray;

public class AttendanceStudent extends AppCompatActivity implements AllRequestsAPI.OnComplete {
    RecyclerView recyclerView;
    NetworkAvailable networkAvailable;
    AddendaceModel.GetAddendanceModel get_students;
    ImageView img_back;
    TextView txt_Save, attendantData;
    CheckBox attendanceCheckBox, attendance_checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);
        Initsviews();
    }

    public void Initsviews() {
        recyclerView = findViewById(R.id.rec_student);
        img_back = findViewById(R.id.et_back);
        txt_Save = findViewById(R.id.wait_approve_txt);
        attendantData = findViewById(R.id.attend_data);
        networkAvailable = new NetworkAvailable();
        attendanceCheckBox = findViewById(R.id.attendanceStudent);
        attendance_checkBox = findViewById(R.id.attendance_checkBox);

        attendanceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    attendance_checkBox.setChecked(true);
                    //  attendantData.setText(android.R.string.Present);
                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(AttendanceStudent.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if (!networkAvailable.isNetworkAvailable(AttendanceStudent.this)) {
            new AlertMotInternet(AttendanceStudent.this, recyclerView);

        } else {

            if (networkAvailable.isNetworkAvailable(AttendanceStudent.this)) {
                AllRequestsAPI allRequestsAPI = new AllRequestsAPI(AttendanceStudent.this);
                allRequestsAPI.Get_Attendanc_methods(getIntent().getStringExtra("class_id"), getIntent().getStringExtra("date"));


            } else {
                new AlertMotInternet(AttendanceStudent.this, recyclerView);

            }
        }
        txt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AttendanceALLStudent.list_Stundtent.size() > 0
                        && attendance_checkBox.isChecked()) {
                    JSONArray j1 = null, j2 = null;
                    j1 = new JSONArray();
                    j2 = new JSONArray();
                    for (int x = 0; x < AttendanceALLStudent.list_Stundtent.size(); x++) {
                        j1.put(AttendanceALLStudent.list_Stundtent.get(x));
                        j2.put(AttendanceALLStudent.list_Absents.get(x));
                    }
                    Log.e("dd", j1 + "");

                    if (networkAvailable.isNetworkAvailable(AttendanceStudent.this)) {
                        AllRequestsAPI allRequestsAPI = new AllRequestsAPI(AttendanceStudent.this);
                        allRequestsAPI.AddAttendence_methods(j1, j2, getIntent().getStringExtra("date"));


                    } else {
                        new AlertMotInternet(AttendanceStudent.this, txt_Save);

                    }
                } else {
                    Toast.makeText(AttendanceStudent.this, getString(R.string.selectstudent), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AttendanceStudent.this, ChooseCalsses.class);
        intent.putExtra("flage", "0");
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof AddendaceModel.GetAddendanceModel) {

            get_students = (AddendaceModel.GetAddendanceModel) object;
            if (get_students.getData().size() > 0) {
                Toast.makeText(AttendanceStudent.this, get_students.getMessage(), Toast.LENGTH_LONG).show();
                recyclerView.setAdapter(new AttendanceALLStudent(get_students.getData(), AttendanceStudent.this));
            } else {
                finish();
            }
        }

    }
}
