package com.codex.eduapp.school.network.Requests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codex.eduapp.school.activities.Choose_Type;
import com.codex.eduapp.school.activities.HomePage;
import com.codex.eduapp.school.activities.Signup;
import com.codex.eduapp.school.helper.PrefManager;
import com.codex.eduapp.school.network.ApiClient;
import com.codex.eduapp.school.network.ApiService;
import com.codex.eduapp.school.network.models.AddTaskModel;
import com.codex.eduapp.school.network.models.AddendaceModel;
import com.codex.eduapp.school.network.models.AssigmnetsModel;
import com.codex.eduapp.school.network.models.ClassessModel;
import com.codex.eduapp.school.network.models.GeTShechules;
import com.codex.eduapp.school.network.models.GetStudents;
import com.codex.eduapp.school.network.models.LoginModel;
import com.codex.eduapp.school.network.models.RegisterModel;
import com.codex.eduapp.school.network.models.attendanceStudentModel;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class AllRequestsAPI {
    Context mContext;
    ApiService apiService;
    PrefManager prefManager;
    String  Result;
    KProgressHUD hud;
    OnComplete onComplete;

    public AllRequestsAPI(Context context){
        this.mContext=context;
        onComplete= (OnComplete) context;
         apiService = ApiClient.getClient(mContext).create(ApiService.class);
         prefManager=new PrefManager(mContext);
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
    public void Login_Method(HashMap<String , String> hashMap){


        apiService.login(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoginModel.LoginAPI>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(LoginModel.LoginAPI reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                            prefManager.settokenreply(reponsed.getData().getRememberToken());
                            prefManager.setlogin(true);
                            Gson gson = new Gson();
                            String json = gson.toJson(reponsed.getData());
                            prefManager.setprofile(json);
                            mContext.startActivity(new Intent(mContext,HomePage.class));
                            ((Activity)mContext).finish();
                        }
                        else if (reponsed.getSuccess().equals("failed")){
                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        hud.dismiss();
                    }
                });
    }
    public String GetClasses_Method(){
        Result="0";
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("token",prefManager.getTokenreply());

        apiService.ClassList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ClassessModel.Get_Classes>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(ClassessModel.Get_Classes reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            Gson gson = new Gson();
                            String json = gson.toJson(reponsed);
                            Result=json;
                            onComplete.onSuccess(reponsed);



                        }
                        else if (reponsed.getSuccess().equals("failed")){

                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else if (reponsed.getSuccess().equals("logged")){
                            prefManager.setlogin(false);
                            mContext.startActivity(new Intent(mContext,Choose_Type.class));
                            ((Activity)mContext).finish();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        Log.e("df",e.getMessage());
                        hud.dismiss();
                    }
                });
        return Result;
    }

    public String Get_Students_methods(String section_Id){
        String Sec_Id=section_Id;
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("token",prefManager.getTokenreply());
        hashMap.put("section_id",Sec_Id);

        apiService.StudentList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GetStudents.Get_Students>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(GetStudents.Get_Students reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            onComplete.onSuccess(reponsed);



                        }
                        else if (reponsed.getSuccess().equals("failed")){

                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else if (reponsed.getSuccess().equals("logged")){
                            prefManager.setlogin(false);
                            mContext.startActivity(new Intent(mContext,Choose_Type.class));
                            ((Activity)mContext).finish();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        Log.e("df",e.getMessage());
                        hud.dismiss();
                    }
                });
        return Result;
    }
    public String Get_AttendancOFstudent_methods(String studentid){
        String student_id=studentid;

        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("token",prefManager.getTokenreply());
        hashMap.put("student_id",student_id);

        apiService.StudentAttendence(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<attendanceStudentModel.GetAddendanceStudent>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(attendanceStudentModel.GetAddendanceStudent reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            onComplete.onSuccess(reponsed);



                        }
                        else if (reponsed.getSuccess().equals("failed")){

                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else if (reponsed.getSuccess().equals("logged")){
                            prefManager.setlogin(false);
                            mContext.startActivity(new Intent(mContext,Choose_Type.class));
                            ((Activity)mContext).finish();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        Log.e("df",e.getMessage());
                        hud.dismiss();
                    }
                });
        return Result;
    }
    public String Get_Attendanc_methods(String classid,String date){
        String class_id=classid;
        String Datee=date;
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("token",prefManager.getTokenreply());
        hashMap.put("class_id",class_id);
        hashMap.put("date",Datee);

        apiService.GetAttendence(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddendaceModel.GetAddendanceModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(AddendaceModel.GetAddendanceModel reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            onComplete.onSuccess(reponsed);



                        }
                        else if (reponsed.getSuccess().equals("failed")){

                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else if (reponsed.getSuccess().equals("logged")){
                            prefManager.setlogin(false);
                            mContext.startActivity(new Intent(mContext,Choose_Type.class));
                            ((Activity)mContext).finish();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error

                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        Log.e("df",e.getMessage());
                        hud.dismiss();
                    }
                });
        return Result;
    }

    public String AddAttendence_methods(JSONArray Stentends, JSONArray attendences, String Dates){



        apiService.AddAttendence(prefManager.getTokenreply(),
                Dates,
                Stentends,
                attendences)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddendaceModel.GetAddendanceModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(AddendaceModel.GetAddendanceModel reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            onComplete.onSuccess(reponsed);



                        }
                        else if (reponsed.getSuccess().equals("failed")){

                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else if (reponsed.getSuccess().equals("logged")){
                            prefManager.setlogin(false);
                            mContext.startActivity(new Intent(mContext,Choose_Type.class));
                            ((Activity)mContext).finish();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error

                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        Log.e("df",e.getMessage());
                        hud.dismiss();
                    }
                });
        return Result;
    }
    public String Schedules_methods(String dy){

        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("token",prefManager.getTokenreply());
        hashMap.put("day",dy);

        apiService.Schedules(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GeTShechules.Get_schudel>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(GeTShechules.Get_schudel reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            onComplete.onSuccess(reponsed);



                        }
                        else if (reponsed.getSuccess().equals("failed")){
                            onComplete.onSuccess(reponsed);

                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else if (reponsed.getSuccess().equals("logged")){
                            prefManager.setlogin(false);
                            mContext.startActivity(new Intent(mContext,Choose_Type.class));
                            ((Activity)mContext).finish();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error

                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        Log.e("df",e.getMessage());
                        hud.dismiss();
                    }
                });
        return Result;
    }

    public String Assignments_methods(String dy) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", prefManager.getTokenreply());
        if (!dy.equals("no")){
            hashMap.put("section_id", dy);
    }

        apiService.Assignments(hashMap
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AssigmnetsModel.GetAssigent>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(AssigmnetsModel.GetAssigent reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            onComplete.onSuccess(reponsed);



                        }
                        else if (reponsed.getSuccess().equals("failed")){
                            onComplete.onSuccess(reponsed);

                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else if (reponsed.getSuccess().equals("logged")){
                            prefManager.setlogin(false);
                            mContext.startActivity(new Intent(mContext,Choose_Type.class));
                            ((Activity)mContext).finish();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error

                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        Log.e("df",e.getMessage());
                        hud.dismiss();
                    }
                });
        return Result;
    }

    public String ADDAssignments_methods(Map<String, RequestBody> map) {



        apiService.AddAssignment(map
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddTaskModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(AddTaskModel reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();

                            onComplete.onSuccess(reponsed);



                        }
                        else if (reponsed.getSuccess().equals("failed")){
                            onComplete.onSuccess(reponsed);

                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else if (reponsed.getSuccess().equals("logged")){
                            prefManager.setlogin(false);
                            mContext.startActivity(new Intent(mContext,Choose_Type.class));
                            ((Activity)mContext).finish();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error

                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        Log.e("df",e.getMessage());
                        hud.dismiss();
                    }
                });
        return Result;
    }
    public void Signup_Method(HashMap<String , String> hashMap){


        apiService.register(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RegisterModel.Register_APi>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(RegisterModel.Register_APi reponsed) {
                        // Received all notes
                        hud.dismiss();
                        if (reponsed.getSuccess().equals("success")){
                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                            prefManager.settokenreply(reponsed.getUser().getRememberToken());
                            Gson gson = new Gson();
                            String json = gson.toJson(reponsed.getUser());
                            prefManager.setprofile(json);
                            mContext.startActivity(new Intent(mContext,HomePage.class));
                            ((Activity)mContext).finish();
                        }
                        else if (reponsed.getSuccess().equals("failed")){
                            View focusView = null;
                            try {


                                if (!reponsed.getErrors().getEmail().equals(null)) {
                                    focusView = Signup.et_Email;
                                    Signup.et_Email.setError(reponsed.getErrors().getEmail().get(0));
                                }
                            }
                            catch (Exception x) {
                                try {


                                    if (reponsed.getErrors().getMobile().size() > 0) {
                                        focusView = Signup.et_Phone;
                                        Signup.et_Phone.setError(reponsed.getErrors().getMobile().get(0));
                                    }
                                }
                                catch (Exception d) {
                                    try {


                                        if (reponsed.getErrors().getName().size() > 0) {
                                            focusView = Signup.et_username;
                                            Signup.et_username.setError(reponsed.getErrors().getName().get(0));
                                        }
                                    }
                                    catch (Exception xc){
                                        try {
                                            if (reponsed.getErrors().getPassword().size()>0){
                                                focusView=Signup.et_password;
                                                Signup.et_password.setError(reponsed.getErrors().getPassword().get(0));
                                            }
                                        }
                                        catch (Exception xx){

                                        }
                                    }
                                }
                            }


                            focusView.requestFocus();


                            Toast.makeText(mContext,reponsed.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext,"Connection Timed Out",Toast.LENGTH_LONG).show();
                        hud.dismiss();
                    }
                });
    }
    public interface OnComplete {
        void onSuccess(Object object);

    }
}
