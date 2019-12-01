package com.ml.offlineattendancedemo.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ml.offlineattendancedemo.R;
import com.ml.offlineattendancedemo.modal.Attendance;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAttendanceAdaptor extends RecyclerView.Adapter<ViewAttendanceAdaptor.VHolder> {
    private Context ctx;
    private List<Attendance> atlist = new ArrayList<>();

    public ViewAttendanceAdaptor(Context ctx, List<Attendance> atlist) {
        this.atlist = atlist;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.adaptor_view_attendance, parent, false);
        return new VHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        Attendance attendance = atlist.get(position);

        holder.tname.setText("Name : " +attendance.getName());
        holder.tstatus.setText("Status : " +attendance.getStatus());
        holder.tdate.setText("Date : " +attendance.getDate());

        if (attendance.getStatus().equals("Present")) {
            holder.cview.setCardBackgroundColor(Color.parseColor("#27AE60"));
        } else if (attendance.getStatus().equals("Absent")) {
            holder.cview.setCardBackgroundColor(Color.parseColor("#C0392B"));
        }

    }

    @Override
    public int getItemCount() {
        return atlist != null ? atlist.size() : 0;
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView tname, tstatus, tdate;
        CardView cview;

        public VHolder(@NonNull View itemView) {
            super(itemView);

            tname = itemView.findViewById(R.id.tname);
            tstatus = itemView.findViewById(R.id.tstatus);
            tdate = itemView.findViewById(R.id.tdate);
            cview = itemView.findViewById(R.id.cview);
        }
    }
}
