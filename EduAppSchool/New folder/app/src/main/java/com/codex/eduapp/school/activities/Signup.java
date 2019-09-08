package com.codex.eduapp.school.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

 import com.codex.eduapp.school.helper.AlertMotInternet;
import com.codex.eduapp.school.helper.Helper;
import com.codex.eduapp.school.helper.NetworkAvailable;
import com.codex.eduapp.school.helper.PrefManager;
import com.codex.eduapp.school.network.Requests.AllRequestsAPI;

import java.util.HashMap;

public class Signup extends AppCompatActivity implements AllRequestsAPI.OnComplete {
    public static EditText et_Email, et_username, et_password, et_Phone;
    String email, username, password, phone;
    Button bt_signup;
    NetworkAvailable networkAvailable;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        InitsViews();
    }
    public void InitsViews(){
        et_Email=findViewById(R.id.et_email);
        networkAvailable=new NetworkAvailable();
        prefManager=new PrefManager(this);
        et_username=findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        et_Phone=findViewById(R.id.et_phone);
        bt_signup=findViewById(R.id.bt_login);
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=et_username.getText().toString();
                email=et_Email.getText().toString();
                phone=et_Phone.getText().toString();
                password=et_password.getText().toString();
                if (IsValid()){
                    if (networkAvailable.isNetworkAvailable(Signup.this)){

                        AllRequestsAPI   allRequestsAPI = new AllRequestsAPI(Signup.this);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("mobile", phone);
                        hashMap.put("password", password);
                        hashMap.put("name", username);
                        hashMap.put("email",email);
                        hashMap.put("device_token", prefManager.getToken());
                        allRequestsAPI.Signup_Method(hashMap);
                    }
                    else {
                        new AlertMotInternet(Signup.this,bt_signup);
                    }

                }

            }
        });


    }

    public boolean IsValid() {
        boolean Is_Valid = true;
        if (username.length() == 0) {
            Is_Valid = false;
            et_username.setError(getString(R.string.userreuired));
        } else if (username.length() < 3) {
            Is_Valid = false;
            et_username.setError(getString(R.string.usermust));
        } else if (phone.length() == 0) {
            Is_Valid = false;
            et_Phone.setError(getString(R.string.phonerequired));
        } else if (phone.length() < 10) {
            Is_Valid = false;
            et_Phone.setError(getString(R.string.phone_number));
        } else if (email.length() == 0) {
            Is_Valid = false;
            et_Email.setError(getString(R.string.emalireuired));
        } else if (!new Helper().emailValidator(email)) {
            Is_Valid = false;
            et_Email.setError(getString(R.string.emailsmustbe));
        } else if (password.length() < 6) {
            Is_Valid = false;
            et_password.setError(getString(R.string.passwordmust));
        } else if (password.length() == 0) {
            Is_Valid = false;
            et_password.setError(getString(R.string.passwordreqiured));
        }
        return Is_Valid;
    }

    @Override
    public void onSuccess(Object object) {

    }
}
