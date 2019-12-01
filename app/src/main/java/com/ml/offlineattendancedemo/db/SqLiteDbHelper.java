package com.ml.offlineattendancedemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ml.offlineattendancedemo.modal.Attendance;
import com.ml.offlineattendancedemo.modal.Staff;

import java.util.ArrayList;
import java.util.List;

public class SqLiteDbHelper extends SQLiteOpenHelper {
    private Context ctx;
    private static String DATABASE_NAME = "attendance";
    private static int DATABASE_VERSION = 1;

    public static final String STAFF_TABLE_NAME = "staff";
    public static final String STAFF_NAME = "name";
    public static final String STAFF_ADDRESS = "address";
    private static final String STAFF_ID = "TID";

    public static final String CREATE_TABLE_TEACHER = "CREATE TABLE IF NOT EXISTS " +
            STAFF_TABLE_NAME + " (" +
            STAFF_NAME + " TEXT, " +
            STAFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STAFF_ADDRESS + " TEXT " + ")";

    private static final String STAFF_ATTENDANCE_TABLE = "attendance";
    private static final String STAFF_ATTENDANCE_ID = "AID";
    private static final String STAFF_ATTENDANCE_DATE = "date";
    private static final String STAFF_ATTENDANCE_STATUS = "status";


    private static final String CREATE_ATTENDANCE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            STAFF_ATTENDANCE_TABLE + " (" +
            STAFF_ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STAFF_ID + " INTEGER, " +
            STAFF_NAME + " TEXT, " +
            STAFF_ADDRESS + " TEXT, " +
            STAFF_ATTENDANCE_DATE + " TEXT, " +
            STAFF_ATTENDANCE_STATUS + " TEXT " + ")";

    public SqLiteDbHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TEACHER);
        sqLiteDatabase.execSQL(CREATE_ATTENDANCE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STAFF_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STAFF_ATTENDANCE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void saveStaff(Staff staff) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT name,address FROM staff where name" + " =?" + " AND address" +
                " =?";

        Cursor c = db.rawQuery(query1, new String[]{(staff.getName()), staff.getAddress()});
        if (c.getCount() > 0) {
//            return true;

        } else {
            String query = "INSERT INTO staff (name,address) values ('" +
                    staff.getName() + "', '" +
                    staff.getAddress() + "') ";
            Log.d("query", query);
            db.execSQL(query);
            db.close();
        }

    }

    public boolean saveAttendance(Attendance attendance) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT date,name FROM attendance where date" + " =?" + " AND TID" +
                " =?";

        Cursor c = db.rawQuery(query1, new String[]{(attendance.getDate()), String.valueOf(attendance.getTid())});
        if (c.getCount() > 0) {
            Toast.makeText(ctx, "Sorry! Attendance Already Done For Today", Toast.LENGTH_SHORT).show();
            return true;

        } else {
            String query = "INSERT INTO attendance (TID,name,status,date) values ('" +
                    attendance.getTid() + "', '" +
                    attendance.getName() + "', '" +
                    attendance.getStatus() + "', '" +
                    attendance.getDate() + "') ";
            Log.d("query", query);
            Toast.makeText(ctx, "Attendance done For Today :)", Toast.LENGTH_SHORT).show();
            db.execSQL(query);
            db.close();
            return false;
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> stfList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM staff";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Staff staff = new Staff();
                staff.setStaff_id(cursor.getInt(1));
                staff.setName(cursor.getString(0));
                staff.setAddress(cursor.getString(2));
                stfList.add(staff);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return stfList;
    }

    public List<Attendance> getAttendanceById(String id) {
        List<Attendance> atlist = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM attendance where TID='" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Attendance attendance = new Attendance();
                attendance.setName(cursor.getString(2));
                attendance.setDate(cursor.getString(4));
                attendance.setStatus(cursor.getString(5));
                atlist.add(attendance);
            }
            while (cursor.moveToNext());
        }
        return atlist;
    }
}
