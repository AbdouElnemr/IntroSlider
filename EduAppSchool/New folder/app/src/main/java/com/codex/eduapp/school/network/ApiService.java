package com.codex.eduapp.school.network;

import com.codex.eduapp.school.network.models.AddTaskModel;
import com.codex.eduapp.school.network.models.AddendaceModel;
import com.codex.eduapp.school.network.models.AssigmnetsModel;
import com.codex.eduapp.school.network.models.ClassessModel;
import com.codex.eduapp.school.network.models.GeTShechules;
import com.codex.eduapp.school.network.models.GetStudents;
import com.codex.eduapp.school.network.models.LoginModel;
import com.codex.eduapp.school.network.models.RegisterModel;
import com.codex.eduapp.school.network.models.attendanceStudentModel;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiService {

    @POST("login")
    Single<LoginModel.LoginAPI> login(@Body HashMap<String,String> hashMap);
    @POST("register")
    Single<RegisterModel.Register_APi> register(@Body HashMap<String,String> hashMap);
    @POST("ClassList")
    Single<ClassessModel.Get_Classes> ClassList(@Body HashMap<String,String> hashMap);
    @POST("StudentList")
    Single<GetStudents.Get_Students> StudentList(@Body HashMap<String,String> hashMap);
    @POST("GetAttendence")
    Single<AddendaceModel.GetAddendanceModel> GetAttendence(@Body HashMap<String,String> hashMap);
    @FormUrlEncoded
    @POST("AddAttendence")
    Single<AddendaceModel.GetAddendanceModel> AddAttendence(
            @Field("token")String token ,
            @Field("date")String date ,
            @Field("students")JSONArray students ,
            @Field("attendences")JSONArray attendences
            );
    @POST("StudentAttendence")
    Single<attendanceStudentModel.GetAddendanceStudent> StudentAttendence(@Body HashMap<String,String> hashMap);


    @POST("Schedules")
    Single<GeTShechules.Get_schudel>Schedules(@Body HashMap<String,String> hashMap);
    @POST("Assignments")
    Single<AssigmnetsModel.GetAssigent>Assignments(@Body HashMap<String,String> hashMap);
    @Multipart
    @POST("AddAssignment")
    Single<AddTaskModel> AddAssignment(
            @PartMap Map<String, RequestBody> map
    );
}
