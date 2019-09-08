package com.codex.eduapp.school.helper;import android.animation.Animator;import android.animation.AnimatorListenerAdapter;import android.app.Dialog;import android.content.Context;import android.content.DialogInterface;import android.graphics.drawable.ColorDrawable;import android.os.Build;import android.support.annotation.RequiresApi;import android.view.KeyEvent;import android.view.View;import android.view.ViewAnimationUtils;import android.view.Window;import android.widget.Button;import android.widget.ImageView;public class AlertMotInternet {    Context context;    View v;    Button bt_logi;    public AlertMotInternet(Context context, View c){        this.context=context;        this.v=c;        showDiag();    }    private void showDiag() {        final View dialogView = View.inflate(context, R.layout.nointernet, null);        final Dialog dialog = new Dialog(context, R.style.MyAlertDialogStyle);        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);        dialog.setContentView(dialogView);        ImageView imageView = (ImageView) dialog.findViewById(R.id.closeDialogImg);        bt_logi = (Button) dialog.findViewById(R.id.bt_loin);        imageView.setOnClickListener(new View.OnClickListener() {            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)            @Override            public void onClick(View v) {                revealShow(dialogView, false, dialog);            }        });        bt_logi.setOnClickListener(new View.OnClickListener() {            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)            @Override            public void onClick(View v) {                revealShow(dialogView, false, dialog);            }        });        dialog.setOnShowListener(new DialogInterface.OnShowListener() {            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)            @Override            public void onShow(DialogInterface dialogInterface) {                revealShow(dialogView, true, null);            }        });        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)            @Override            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {                if (i == KeyEvent.KEYCODE_BACK) {                    revealShow(dialogView, false, dialog);                    return true;                }                return false;            }        });        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));        dialog.show();    }     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)     private void revealShow(View dialogView, boolean b, final Dialog dialog) {        final View view = dialogView.findViewById(R.id.root);        int w = view.getWidth();        int h = view.getHeight();        int endRadius = (int) Math.hypot(w, h);        int cx = (int) (v.getX() + (v.getWidth() / 2));        int cy = (int) (v.getY()) + v.getHeight() + 56;        if (b) {            Animator revealAnimator =                    ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);            view.setVisibility(View.VISIBLE);            revealAnimator.setDuration(700);            revealAnimator.start();        } else {            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);            anim.addListener(new AnimatorListenerAdapter() {                @Override                public void onAnimationEnd(Animator animation) {                    super.onAnimationEnd(animation);                    dialog.dismiss();                    view.setVisibility(View.INVISIBLE);                }            });            anim.setDuration(700);            anim.start();        }    }}