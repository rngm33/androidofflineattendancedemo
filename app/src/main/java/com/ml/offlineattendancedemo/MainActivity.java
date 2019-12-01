package com.ml.offlineattendancedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ml.offlineattendancedemo.db.SqLiteDbHelper;
import com.ml.offlineattendancedemo.modal.Staff;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    List<Staff> list;
    private SqLiteDbHelper sqLiteDbHelper;
    Staff staff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        staff = new Staff(this);
        sqLiteDbHelper = new SqLiteDbHelper(this);
        staff.addStaff();
        list = new ArrayList<>();

    }

    public void TakeAttendance(View view) {
//        for(int i=0;i<list.size();i++) {
//            Toast.makeText(this, list.get(i).getName() + "", Toast.LENGTH_SHORT).show();
//        }
        Intent intent= new Intent(this,ViewTeachersActivity.class);
//        startActivity(new Intent(this,ViewTeachersActivity.class));
//        intent.putExtra("take","take_atd");
        intent.putExtra("take",true);
        startActivity(intent);
    }

    public void ViewAttendance(View view) {

        Intent intent= new Intent(this,ViewTeachersActivity.class);
//        startActivity(new Intent(this,ViewTeachersActivity.class));
        intent.putExtra("view",true);
        startActivity(intent);
    }


}
