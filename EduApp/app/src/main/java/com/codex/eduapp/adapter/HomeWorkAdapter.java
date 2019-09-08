package com.codex.eduapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codex.eduapp.school.R;
import com.codex.eduapp.school.network.models.AssigmnetsModel;

import java.util.List;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.MyViewHolder> {

    private List<AssigmnetsModel.Datum> moviesList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, header_content, subject;
        public ImageView header_img;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.header_title);
            header_content = (TextView) view.findViewById(R.id.header_content);
            subject = (TextView) view.findViewById(R.id.AssignmentDeadline);


        }
    }


    public HomeWorkAdapter(List<AssigmnetsModel.Datum> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.context = ctx;
    }

    @Override
    public HomeWorkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_assign_list_item, parent, false);

        return new HomeWorkAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeWorkAdapter.MyViewHolder holder, int position) {
        AssigmnetsModel.Datum movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.header_content.setText(movie.getDisc());
        holder.subject.setText(movie.getSubject());


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
