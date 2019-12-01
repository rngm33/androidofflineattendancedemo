package com.ml.offlineattendancedemo.modal;

public class Attendance {

    public Attendance() {
        //empty construsctor
    }

    private int atd_id,tid;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    private String status,name,date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAtd_id() {
        return atd_id;
    }

    public void setAtd_id(int atd_id) {
        this.atd_id = atd_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
