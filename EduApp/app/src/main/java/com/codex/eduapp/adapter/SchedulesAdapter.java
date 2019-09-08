package com.codex.eduapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codex.eduapp.school.R;
import com.codex.eduapp.school.network.models.GeTShechules;

import java.util.List;

public class SchedulesAdapter   extends RecyclerView.Adapter<SchedulesAdapter.MyViewHolder> {

    private List<GeTShechules.Datum> moviesList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView st_name, st_start, st_end;
        public ImageView header_img;


        public MyViewHolder(View view) {
            super(view);
            st_name = (TextView) view.findViewById(R.id.header_title);
            st_start = (TextView) view.findViewById(R.id.start_time_data);
            st_end = (TextView) view.findViewById(R.id.end_time_data);


        }
    }


    public SchedulesAdapter(List<GeTShechules.Datum> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.context = ctx;
    }

    @Override
    public SchedulesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_classes_sch_adap_item, parent, false);

        return new SchedulesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SchedulesAdapter.MyViewHolder holder, int position) {
        GeTShechules.Datum movie = moviesList.get(position);
        holder.st_name.setText(movie.getSubject() + "\n" + movie.getSection() + "\n" + movie.getClass_() + "\n" + movie.getLevel());
        holder.st_start.setText(movie.getFrom());
        holder.st_end.setText(movie.getTo());


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
