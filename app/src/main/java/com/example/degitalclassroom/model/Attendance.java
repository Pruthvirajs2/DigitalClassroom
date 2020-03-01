package com.example.degitalclassroom.model;

public class Attendance {


    String id;
    String studentId;
    String date;
    boolean checkAttendance;


    public Attendance() {
    }


    public Attendance(String id, String studentId, String date, boolean checkAttendance) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.checkAttendance = checkAttendance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCheckAttendance() {
        return checkAttendance;
    }

    public void setCheckAttendance(boolean checkAttendance) {
        this.checkAttendance = checkAttendance;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", date='" + date + '\'' +
                ", checkAttendance=" + checkAttendance +
                '}';
    }
}

