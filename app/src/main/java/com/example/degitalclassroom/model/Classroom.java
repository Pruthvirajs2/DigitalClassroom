package com.example.degitalclassroom.model;

public class Classroom {
    private String id;
    private String classroom;

    public Classroom() {

    }

    public Classroom(String id, String classroom) {
        this.id = id;
        this.classroom = classroom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
