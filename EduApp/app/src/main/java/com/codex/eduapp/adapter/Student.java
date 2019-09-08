package com.codex.eduapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codex.eduapp.school.R;
import com.codex.eduapp.school.activities.GetAttendaceStudent;
import com.codex.eduapp.school.network.models.GetStudents;

import java.util.List;

public class  Student extends RecyclerView.Adapter<Student.MyViewHolder> {

    private List<GetStudents.Datum> moviesList;
    Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView st_name, st_class, st_username,st_email;
        public ImageView header_img;
        public RelativeLayout menu_attend,menu_marksheet;


        public MyViewHolder(View view) {
            super(view);
            st_name = (TextView) view.findViewById(R.id.header_title);
            st_class = (TextView) view.findViewById(R.id.header_class);
            st_username = (TextView) view.findViewById(R.id.footer_username_data);
            st_email = (TextView) view.findViewById(R.id.footer_email_data);
            header_img=(ImageView)view.findViewById(R.id.header_img);
            menu_attend=(RelativeLayout)view.findViewById(R.id.menu_attend);
            menu_marksheet=(RelativeLayout)view.findViewById(R.id.menu_marksheet);



        }
    }


    public Student(List<GetStudents.Datum> moviesList, Context context) {
        this.moviesList = moviesList;
        this.mcontext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_students_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        GetStudents.Datum movie = moviesList.get(position);
        holder.st_name.setText(movie.getStudentName());
        holder.st_class.setText(movie.getSectionName());
        holder.st_email.setText(movie.getEmail());
        holder.st_username.setText(movie.getMobile());
        holder.menu_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetStudents.Datum movie = moviesList.get(position);
                Intent intent=new Intent(mcontext,GetAttendaceStudent.class);
                intent.putExtra("student_id",movie.getId()+"");
                intent.putExtra("name",movie.getStudentName()+"");
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}