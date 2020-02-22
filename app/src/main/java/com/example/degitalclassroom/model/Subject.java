package com.example.degitalclassroom.model;

public class Subject {
    private String id;
    private String subName;
    private String className;
    private String batchYear;
    private String semester;

    public Subject() {
    }

    public Subject(String id, String subName, String className, String batchYear, String semester) {
        this.id = id;
        this.subName = subName;
        this.className = className;
        this.batchYear = batchYear;
        this.semester = semester;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(String batchYear) {
        this.batchYear = batchYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
