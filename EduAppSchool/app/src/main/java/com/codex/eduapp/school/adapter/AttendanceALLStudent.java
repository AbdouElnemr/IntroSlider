package com.codex.eduapp.school.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codex.eduapp.school.R;
import com.codex.eduapp.school.activities.AttendanceStudent;
import com.codex.eduapp.school.network.CheckBoxInterface;
import com.codex.eduapp.school.network.models.AddendaceModel;
import com.codex.eduapp.school.network.models.attendanceStudentModel;

import java.util.ArrayList;
import java.util.List;

import static com.codex.eduapp.school.adapter.AttendanceALLStudent.MyViewHolder.checkOnly;

public class AttendanceALLStudent extends
        RecyclerView.Adapter<AttendanceALLStudent.MyViewHolder> {

    private List<AddendaceModel.Datum> moviesList;
    Context context;
    public static List<String> list_Stundtent;
    public static List<String> list_Absents;



    private int selected_position = -1;
    static int count = 0;
    static int flag = 0;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView st_Date, st_attend_data;
        public static CheckBox checkOnly;
        RelativeLayout t_presnet, t_late, t_latewith, t_early, t_absent;
        public ImageView header_img;


        public MyViewHolder(final View view) {
            super(view);
            st_Date = (TextView) view.findViewById(R.id.header_title);
            st_attend_data = (TextView) view.findViewById(R.id.attend_data);
            t_presnet = view.findViewById(R.id.status_0);
            t_late = view.findViewById(R.id.status_2);
            t_latewith = view.findViewById(R.id.status_3);
            t_early = view.findViewById(R.id.status_4);
            t_absent = view.findViewById(R.id.status_1);

            checkOnly = view.findViewById(R.id.attendanceItemCheckBox);
            checkOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        count++;
                        flag = 0;

                        // AttendanceStudent.checkAll.setChecked(true);
                    } else {

                        flag = 1;

                    }
                }

            });

            if (count == list_Stundtent.size()) {
                AttendanceStudent.checkAll.setChecked(true);
            } else if ((count < list_Stundtent.size())) {
                //AttendanceStudent.checkAll.setChecked(false);
            }
//            for (int i = 0; i < checkOnly.length; i++) {
//                // checkOnly[i] = (CheckBox) findViewById(chkBoxIds[i]);
//                checkOnly[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            st_attend_data.setText(R.string.Present);
//                            flag = 0;
//                        } else {
//                            st_attend_data.setText(R.string.Absent);
//                            flag = 1;
//                        }
//                    }
//
//                });
//                if (checkOnly[list_Stundtent.size()].isChecked()){
//                    AttendanceStudent.checkAll.setChecked(true);
//                }
//            }


//            checkOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        flag = 0;
//                    } else {
//                        flag = 1;
//                    }
//                }
//            });

        }
    }


    public AttendanceALLStudent(List<AddendaceModel.Datum> moviesList, Context ctx) {
        this.moviesList = moviesList;
        this.context = ctx;

        list_Stundtent = new ArrayList<>();
        list_Absents = new ArrayList<>();
    }

    @Override
    public AttendanceALLStudent.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendceitem_list, parent, false);

        return new AttendanceALLStudent.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttendanceALLStudent.MyViewHolder holder, final int position) {
        final AddendaceModel.Datum movie = moviesList.get(position);
        holder.st_Date.setText(movie.getStudentName());

        //holder.checkOnly.setChecked(position == selected_position);
        // holder.checkOnly.setVisibility(View.VISIBLE);


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
        } catch (Exception x) {

        }
        if (flag == 0) {

            if (list_Stundtent.contains(movie.getStudentId() + "")) {
                Log.d("der", list_Stundtent.size() + "");
                int indx = list_Stundtent.indexOf(movie.getStudentId() + "");

                list_Stundtent.remove(indx);
                list_Absents.remove(indx);
                list_Stundtent.add(movie.getStudentId() + "");
                list_Absents.add("1");
            } else {
                list_Stundtent.add(movie.getStudentId() + "");
                list_Absents.add("1");
            }
        } else if (flag == 1) {
            if (list_Stundtent.contains(movie.getStudentId() + "")) {
                int indx = list_Stundtent.indexOf(movie.getStudentId() + "");
                list_Stundtent.remove(indx);
                list_Absents.remove(indx);
                list_Stundtent.add(movie.getStudentId() + "");
                list_Absents.add("0");
            } else {
                list_Stundtent.add(movie.getStudentId() + "");
                list_Absents.add("0");
            }
        }

//        holder.t_absent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (list_Stundtent.contains(movie.getStudentId() + "")) {
//                    int indx = list_Stundtent.indexOf(movie.getStudentId() + "");
//                    list_Stundtent.remove(indx);
//                    list_Absents.remove(indx);
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("0");
//                } else {
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("0");
//                }
//                holder.st_attend_data.setText(context.getString(R.string.Absent));
//                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
//                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//
//            }
//        });
//        holder.t_presnet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.st_attend_data.setText(context.getString(R.string.Present));
//                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
//                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//
//                if (list_Stundtent.contains(movie.getStudentId() + "")) {
//                    Log.d("der", list_Stundtent.size() + "");
//                    int indx = list_Stundtent.indexOf(movie.getStudentId() + "");
//
//                    list_Stundtent.remove(indx);
//                    list_Absents.remove(indx);
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("1");
//                } else {
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("1");
//                }
//            }
//        });
//        holder.t_early.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.st_attend_data.setText(context.getString(R.string.Early_Dismissal));
//                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
//                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//
//                if (list_Stundtent.contains(movie.getStudentId() + "")) {
//                    int indx = list_Stundtent.indexOf(movie.getStudentId() + "");
//                    list_Stundtent.remove(indx);
//                    list_Absents.remove(indx);
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("4");
//                } else {
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("4");
//                }
//            }
//        });
//        holder.t_late.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.st_attend_data.setText(context.getString(R.string.Late));
//                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
//                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//
//                if (list_Stundtent.contains(movie.getStudentId() + "")) {
//                    int indx = list_Stundtent.indexOf(movie.getStudentId() + "");
//                    Log.e("ert", indx + "");
//                    list_Stundtent.remove(indx);
//                    list_Absents.remove(indx);
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("2");
//                } else {
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("2");
//                }
//            }
//        });
//        holder.t_latewith.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.st_attend_data.setText(context.getString(R.string.Late_with_excuse));
//                holder.t_latewith.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red1));
//                holder.t_absent.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_late.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_early.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//                holder.t_presnet.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_blue1));
//
//                if (list_Stundtent.contains(movie.getStudentId() + "")) {
//                    int indx = list_Stundtent.indexOf(movie.getStudentId() + "");
//                    list_Stundtent.remove(indx);
//                    list_Absents.remove(indx);
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("3");
//                } else {
//                    list_Stundtent.add(movie.getStudentId() + "");
//                    list_Absents.add("3");
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
