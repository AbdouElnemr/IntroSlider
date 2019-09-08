package com.codex.eduapp.school.activities;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.codex.eduapp.school.R;
import com.codex.eduapp.school.helper.PrefManager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.victor.loading.newton.NewtonCradleLoading;

public class Splash extends AppCompatActivity {
    private NewtonCradleLoading newtonCradleLoading;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitViews();
    }

    public void InitViews() {
        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();
        prefManager=new PrefManager(this);

        YoYo.with(Techniques.ZoomIn)
                .duration(700)
                .repeat(1)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        YoYo.with(Techniques.FadeIn)
                                .duration(700)
                                .onEnd(new YoYo.AnimatorCallback() {
                                    @Override
                                    public void call(Animator animator) {
                                        YoYo.with(Techniques.FadeOut)
                                                .duration(700)
                                                .repeat(1)
                                                .onEnd(new YoYo.AnimatorCallback() {
                                                    @Override
                                                    public void call(Animator animator) {
                                                        YoYo.with(Techniques.ZoomOutUp)
                                                                .duration(700)
                                                                .onEnd(new YoYo.AnimatorCallback() {
                                                                    @Override
                                                                    public void call(Animator animator) {
                                                                        if(prefManager.islogin()){
                                                                            startActivity(new Intent(Splash.this, HomePage.class));
                                                                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                                                            finish();
                                                                        }
                                                                        else {


                                                                            startActivity(new Intent(Splash.this, Choose_Type.class));
                                                                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                                                            finish();
                                                                        }
                                                                    }
                                                                })
                                                                .playOn(findViewById(R.id.logo));
                                                    }
                                                })
                                                .playOn(findViewById(R.id.logo));
                                    }
                                })
                                .playOn(findViewById(R.id.logo));
                    }
                })
                .playOn(findViewById(R.id.logo));


    }
}
