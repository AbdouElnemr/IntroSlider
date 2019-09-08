package com.codex.eduapp.school.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

 import com.codex.eduapp.school.network.models.AddendaceModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceALLStudent extends RecyclerView.Adapter<AttendanceALLStudent.MyViewHolder> {

    private List<AddendaceModel.Datum> moviesList;
    Context context;
    public static List<String>list_Stundtent;
    public static  List<String>list_Absents;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView st_Date, st_attend_data;
        RelativeLayout t_presnet,t_late,t_latewith,t_early,t_absent;
        public ImageView header_img;


        public MyViewHolder(View view) {
            super(view);
            st_Date = (TextView) view.findViewById(R.id.header_title);
            st_attend_data = (TextView) view.findViewById(R.id.attend_data);
            t_presnet =  view.findViewById(R.id.status_0);
            t_late = view.findViewById(R.id.status_2);
            t_latewith = view.findViewById(R.id.status_3);
            t_early = view.findViewById(R.id.status_4);
            t_absent = view.findViewById(R.id.status_1);



        }
    }


    public AttendanceALLStudent(List<AddendaceModel.Datum> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.context = ctx;
        list_Stundtent=new ArrayList<>();
        list_Absents=new ArrayList<>();
    }

    @Override
    public AttendanceALLStudent.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendceitem_list, parent, false);

        return new AttendanceALLStudent.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttendanceALLStudent.MyViewHolder holder, int position) {
        final AddendaceModel.Datum movie = moviesList.get(position);
        holder.st_Date.setText(movie.getStudentName());
        try {


            switch (movie.getAttendences()) {
                case "1":
                    holder.st_attend_data.setText(context.getString(R.string.Present));
                    holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
                    break;
                case "0":
                    holder.st_attend_data.setText(context.getString(R.string.Absent));
                    holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));

                    break;
                case "2":
                    holder.st_attend_data.setText(context.getString(R.string.Late));
                    holder.t_late.setBackgroundColor(context.getResources().getColor(R.color.red));

                    break;
                case "3":
                    holder.st_attend_data.setText(context.getString(R.string.Late_with_excuse));
                    holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));

                    break;
                case "4":
                    holder.st_attend_data.setText(context.getString(R.string.Early_Dismissal));
                    holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));

                    break;

            }
        }
        catch (Exception x){

        }
        holder.t_absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_Stundtent.contains(movie.getStudentId()+"")){
                    int indx=list_Stundtent.indexOf(movie.getStudentId()+"");
                    list_Stundtent.remove(indx);
                    list_Absents.remove(indx);
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("0");
                }
                else {
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("0");
                }
                holder.st_attend_data.setText(context.getString(R.string.Absent));
                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));

            }
        });
        holder.t_presnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.st_attend_data.setText(context.getString(R.string.Present));
                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));

                if (list_Stundtent.contains(movie.getStudentId()+"")){
                    Log.d("der",list_Stundtent.size()+"");
                    int indx=list_Stundtent.indexOf(movie.getStudentId()+"");

                    list_Stundtent.remove(indx);
                    list_Absents.remove(indx);
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("1");
                }
                else {
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("1");
                }
            }
        });
        holder.t_early.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.st_attend_data.setText(context.getString(R.string.Early_Dismissal));
                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));

                if (list_Stundtent.contains(movie.getStudentId()+"")){
                    int indx=list_Stundtent.indexOf(movie.getStudentId()+"");
                    list_Stundtent.remove(indx);
                    list_Absents.remove(indx);
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("4");
                }
                else {
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("4");
                }
            }
        });
        holder.t_late.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.st_attend_data.setText(context.getString(R.string.Late));
                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));

                if (list_Stundtent.contains(movie.getStudentId()+"")){
                    int indx=list_Stundtent.indexOf(movie.getStudentId()+"");
                    Log.e("ert",indx+"");
                    list_Stundtent.remove(indx);
                    list_Absents.remove(indx);
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("2");
                }
                else {
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("2");
                }
            }
        });
        holder.t_latewith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.st_attend_data.setText(context.getString(R.string.Late_with_excuse));
                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));

                if (list_Stundtent.contains(movie.getStudentId()+"")){
                    int indx=list_Stundtent.indexOf(movie.getStudentId()+"");
                    list_Stundtent.remove(indx);
                    list_Absents.remove(indx);
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("3");
                }
                else {
                    list_Stundtent.add(movie.getStudentId()+"");
                    list_Absents.add("3");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
