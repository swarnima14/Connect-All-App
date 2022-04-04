package com.app.connectall;

public class UploadModel {
    String name;
    String desc;
    String uploadedBy;
    String link;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
