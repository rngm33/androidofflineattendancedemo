package com.ml.offlineattendancedemo.adaptor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ml.offlineattendancedemo.R;
import com.ml.offlineattendancedemo.db.SqLiteDbHelper;
import com.ml.offlineattendancedemo.modal.Attendance;
import com.ml.offlineattendancedemo.modal.Staff;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherAdaptor extends RecyclerView.Adapter<TeacherAdaptor.MyHolder> {
    private Context ctx;
    private SqLiteDbHelper sqLiteDbHelper;
    private List<Staff> staffList = new ArrayList<>();
    private String status = "", date, type;

    public TeacherAdaptor(Context ctx, List<Staff> stfList, String type) {
        this.ctx = ctx;
        this.staffList = stfList;
        sqLiteDbHelper = new SqLiteDbHelper(ctx);
        this.type = type;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.teacherlist, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Staff staff = staffList.get(position);
        holder.setData(staff);
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvid, tvname;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvid = itemView.findViewById(R.id.tvid);
            tvname = itemView.findViewById(R.id.tvname);

            if (type.equals("take")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAttendanceDialog(tvname.getText().toString(), tvid.getText().toString());
                    }
                });
            } else if (type.equals("view")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                        viewAttendanceDialog(tvid.getText().toString());

                    }
                });
            }
        }

        public void setData(Staff staff) {
            tvid.setText(String.valueOf(staff.getStaff_id()));
            tvname.setText(staff.getName());
        }
    }

    private void viewAttendanceDialog(String tid) {
        List<Attendance> atlist = sqLiteDbHelper.getAttendanceById(tid);
        final Dialog dialog = new Dialog(ctx, R.style.AppTheme);
        dialog.setContentView(R.layout.dialog_viewattendance);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Button btnback= dialog.findViewById(R.id.btnback);
        RecyclerView rview = dialog.findViewById(R.id.recyclerview);
        rview.setLayoutManager(new LinearLayoutManager(ctx));
        rview.setHasFixedSize(true);
        if(atlist.size()>0) {
            rview.setAdapter(new ViewAttendanceAdaptor(ctx, atlist));
        }
        else{
            Toast.makeText(ctx, "NO RECORD FOUND :(", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void showAttendanceDialog(final String tname, final String tid) {
        final Dialog dialog = new Dialog(ctx, R.style.Theme_AppCompat_DayNight_DarkActionBar);
        dialog.setContentView(R.layout.dialog_attendance);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Button btnSave = dialog.findViewById(R.id.btnsave);
        Button btnCancel = dialog.findViewById(R.id.btncancel);
        final RadioButton rbtnP = dialog.findViewById(R.id.rbtnP);
        final RadioButton rbtnA = dialog.findViewById(R.id.rbtnA);
        RadioGroup radioGroup = dialog.findViewById(R.id.rgroup);
        TextView tvdate = dialog.findViewById(R.id.tvdate);
        TextView tvname = dialog.findViewById(R.id.tname);

        tvname.setText("You Are Taking Attendance Of : " + tname);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbtnP.isChecked()) {
                    status = "Present";
                }
                if (rbtnA.isChecked()) {
                    status = "Absent";
                }
            }
        });

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        tvdate.setText("Date : " + date);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Attendance attendance = new Attendance();
                attendance.setStatus(status);
                attendance.setName(tname);
                attendance.setTid(Integer.parseInt(tid));
                attendance.setDate(date);
                if ((rbtnA.isChecked()) || (rbtnP.isChecked())) {
                    sendAttendanceToDb(attendance);
                } else {
                    Toast.makeText(ctx, "Please choose Present or Absent :P", Toast.LENGTH_SHORT).show();

                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void sendAttendanceToDb(Attendance attendance) {
        sqLiteDbHelper.saveAttendance(attendance);
        ((Activity) ctx).finish();

    }

}
