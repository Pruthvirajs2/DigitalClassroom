package com.example.degitalclassroom.model;

public class Dashboard {

    String id;
    String name;
    Integer icon;

    public Dashboard() {
    }

    public Dashboard(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Dashboard(String id, String name, Integer icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
