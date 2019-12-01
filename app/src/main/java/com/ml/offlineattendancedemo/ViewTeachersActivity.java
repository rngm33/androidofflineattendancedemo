package com.ml.offlineattendancedemo;

import android.os.Bundle;
import android.widget.TextView;

import com.ml.offlineattendancedemo.adaptor.TeacherAdaptor;
import com.ml.offlineattendancedemo.db.SqLiteDbHelper;
import com.ml.offlineattendancedemo.modal.Staff;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewTeachersActivity extends AppCompatActivity {
    private RecyclerView rview;
    private SqLiteDbHelper sqLiteDbHelper;
    List<Staff> stfList;
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teachers);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getBoolean("take")) {
                type = "take";

            }
            if (bundle.getBoolean("view")) {
                type = "view";
                TextView tv= findViewById(R.id.tv);
                tv.setText("Select particular teacher to view his/her attendance");
            }

        }

        rview = findViewById(R.id.rview);
        stfList = new ArrayList<>();
        sqLiteDbHelper = new SqLiteDbHelper(this);

        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setHasFixedSize(true);

        stfList = sqLiteDbHelper.getAllStaff();

        rview.setAdapter(new TeacherAdaptor(this, stfList,type));
    }
}
