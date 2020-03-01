package com.example.degitalclassroom.model;

public class User {

    private String id;
    private String userid;
    private String first_name;
    private String last_name;
    private String address;
    private String designation;
    private String email;
    private String dob;
    private String contact;
    private String avatar;
    private String className;
    private boolean checkAttendance;

    public User() {
    }

    public User(String id, String userid, String first_name, String last_name,
                String address, String designation,
                String email, String dob, String contact, String avatar, String className) {
        this.id = id;
        this.userid = userid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.designation = designation;
        this.email = email;
        this.dob = dob;
        this.contact = contact;
        this.avatar = avatar;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isCheckAttendance() {
        return checkAttendance;
    }

    public void setCheckAttendance(boolean checkAttendance) {
        this.checkAttendance = checkAttendance;
    }
}
