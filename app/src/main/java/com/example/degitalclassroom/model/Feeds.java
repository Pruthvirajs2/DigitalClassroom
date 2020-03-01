package com.example.degitalclassroom.model;

public class Feeds {

    String id;
    String sectionId;
    String subName;
    String content;
    String thumbnail;
    String timestamp;
    String type;
    String status;

    public Feeds() {
    }

    public Feeds(String id, String sectionId, String subName,String content, String thumbnail, String timestamp, String type, String status) {
        this.id = id;
        this.sectionId = sectionId;
        this.subName = subName;
        this.content = content;
        this.thumbnail = thumbnail;
        this.timestamp = timestamp;
        this.type = type;
        this.status = status;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
