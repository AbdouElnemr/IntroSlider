package com.codex.eduapp.school.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codex.eduapp.school.R;
import com.codex.eduapp.school.network.models.GetStudents;
import com.codex.eduapp.school.network.models.attendanceStudentModel;

import java.util.List;

public class StudentAttendanceAdapter  extends RecyclerView.Adapter<StudentAttendanceAdapter.MyViewHolder> {

    private List<attendanceStudentModel.Datum> moviesList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView st_Date, st_attend_data;
        public ImageView header_img;



        public MyViewHolder(View view) {
            super(view);
            st_Date = (TextView) view.findViewById(R.id.header_title);
            st_attend_data = (TextView) view.findViewById(R.id.attend_data);




        }
    }


    public StudentAttendanceAdapter(List<attendanceStudentModel.Datum> moviesList,Context ctx) {
        this.moviesList = moviesList;
        this.context=ctx;
    }

    @Override
    public StudentAttendanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendceitem, parent, false);

        return new StudentAttendanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentAttendanceAdapter.MyViewHolder holder, int position) {
        attendanceStudentModel.Datum movie = moviesList.get(position);
        holder.st_Date.setText(movie.getDate());
        switch (movie.getAttendences()){
            case "1":
                holder.st_attend_data.setText(context.getString(R.string.Present));
                break;
            case "0":
                holder.st_attend_data.setText(context.getString(R.string.Absent));
                break;
            case "2":
                holder.st_attend_data.setText(context.getString(R.string.Late));
                break;
            case "3":
                holder.st_attend_data.setText(context.getString(R.string.Late_with_excuse));
                break;
            case "4":
                holder.st_attend_data.setText(context.getString(R.string.Early_Dismissal));
                break;

        }



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}