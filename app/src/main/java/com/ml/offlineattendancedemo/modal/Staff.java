package com.ml.offlineattendancedemo.modal;

import android.content.Context;

import com.ml.offlineattendancedemo.db.SqLiteDbHelper;

public class Staff {
    Context ctx;

    public Staff(Context ctx) {
        this.ctx = ctx;
    }
    public Staff(){

    }

    private int staff_id;
    private String name, address;

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStaffData(String name, String add) {
        this.name = name;
        this.address = add;
    }

    public void addStaff() {
        SqLiteDbHelper sqLiteDbHelper = new SqLiteDbHelper(ctx);

        for (int i = 0; i < 25; i++) {
            Staff staff = new Staff();
            staff.setStaffData("Ram" + i, "KTM " + i);

            sqLiteDbHelper.saveStaff(staff);

        }

    }
}
