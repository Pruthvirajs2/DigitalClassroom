package com.example.degitalclassroom.model;

public class Attendance {


    String uniqueId;
    String studentId;
    String date;
    String sectionId;
    String status;


    public Attendance() {
    }

    public Attendance(String uniqueId, String studentId, String date, String sectionId, String status) {
        this.uniqueId = uniqueId;
        this.studentId = studentId;
        this.date = date;
        this.sectionId = sectionId;
        this.status = status;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
