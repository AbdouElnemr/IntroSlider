package com.codex.eduapp.school.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

 import com.codex.eduapp.school.helper.AlertMotInternet;
import com.codex.eduapp.school.helper.NetworkAvailable;
import com.codex.eduapp.school.helper.PrefManager;
import com.codex.eduapp.school.network.Requests.AllRequestsAPI;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

public class Login extends AppCompatActivity implements AllRequestsAPI.OnComplete {

    TextView t_ForgetPassword, T_Signup, T_Login;
    PrefManager prefManager;
    NetworkAvailable networkAvailable;
    AllRequestsAPI allRequestsAPI;
    String Phone,Password;
    EditText et_Phone, et_Password;
    Button Rest_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitsViews();
    }

    public void InitsViews() {
        networkAvailable = new NetworkAvailable();

        t_ForgetPassword = findViewById(R.id.forget_pass);
        et_Password = findViewById(R.id.et_password);
        et_Phone = findViewById(R.id.et_phone);

        prefManager = new PrefManager(this);
        new MyFirebaseInstanceIDService().onTokenRefresh();
        T_Signup = findViewById(R.id.et_signup);
        T_Login = findViewById(R.id.bt_login);
        if (prefManager.geRole().equals("parent")) {
            T_Signup.setVisibility(View.VISIBLE);
        }
        t_ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiag();
            }
        });
        T_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });
        T_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!networkAvailable.isNetworkAvailable(Login.this)) {
                    new AlertMotInternet(Login.this, T_Login);

                } else {
                    Phone = et_Phone.getText().toString();
                    Password = et_Password.getText().toString();
                    if (ISVaild()) {
                        if (networkAvailable.isNetworkAvailable(Login.this)) {
                            allRequestsAPI = new AllRequestsAPI(Login.this);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("mobile", Phone);
                            hashMap.put("password", Password);
                            hashMap.put("role", prefManager.geRole());
                            hashMap.put("device_token", prefManager.getToken());
                            allRequestsAPI.Login_Method(hashMap);
                        } else {
                            new AlertMotInternet(Login.this, T_Login);

                        }
                    }


                }


            }
        });

    }

    public boolean ISVaild() {
        boolean Is_Valid = true;
        View focusView = null;
        if (Phone.length() == 0) {
            Is_Valid = false;
            focusView=et_Phone;
            et_Phone.setError(getString(R.string.phonerequired));
        } else if (Phone.length() < 10) {
            Is_Valid = false;
            focusView=et_Phone;
            et_Phone.setError(getString(R.string.phone_number));

        } else if (Password.length() < 6) {
            Is_Valid = false;
            focusView=et_Password;
            et_Password.setError(getString(R.string.passwordmust));
        } else if (Password.length() == 0) {
            Is_Valid = false;
            focusView=et_Password;
            et_Password.setError(getString(R.string.passwordreqiured));
        }
        if (!Is_Valid) {
            focusView.requestFocus();
        }
        return Is_Valid;


    }


    private void showDiag() {

        final View dialogView = View.inflate(this, R.layout.password_dialog, null);

        final Dialog dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        ImageView imageView =   dialog.findViewById(R.id.closeDialogImg);
        final EditText et_phone= dialog.findViewById(R.id.et_phone) ;
        Rest_btn=dialog.findViewById(R.id.bt_login);
        Rest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean Is_Valid = true;
                View focusView = null;
                if (et_phone.getText().toString().length() == 0) {
                    Is_Valid = false;
                    focusView=et_phone;
                    et_phone.setError(getString(R.string.phonerequired));
                } else if (et_phone.getText().toString().length() < 10) {
                    Is_Valid = false;
                    focusView=et_phone;
                    et_phone.setError(getString(R.string.phone_number));

                }
                if (!Is_Valid) {
                    focusView.requestFocus();

                }
                else {
                    Toast.makeText(Login.this,"the Login forget",Toast.LENGTH_LONG).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                revealShow(dialogView, false, dialog);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.root);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (t_ForgetPassword.getX() + (t_ForgetPassword.getWidth() / 2));
        int cy = (int) (t_ForgetPassword.getY()) + t_ForgetPassword.getHeight() + 56;


        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }

    }

    @Override
    public void onSuccess(Object object) {

    }

    public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

        public MyFirebaseInstanceIDService() {
        }


        @Override
        public void onTokenRefresh() {
            super.onTokenRefresh();
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            // Saving reg id to shared preferences
            storeRegIdInPref(refreshedToken);

            // sending reg id to your server
            sendRegistrationToServer(refreshedToken);
            Log.e("LOG", "android id >>" + refreshedToken);


        }

        private void sendRegistrationToServer(final String token) {
            // sending gcm token to server
            //Log.e(TAG, "sendRegistrationToServer: " + token);
        }

        private void storeRegIdInPref(String token) {
            prefManager.settoken(token);
        }
    }


}