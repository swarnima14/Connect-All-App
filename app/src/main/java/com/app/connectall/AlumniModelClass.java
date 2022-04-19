package com.app.connectall;

public class AlumniModelClass {
    private String branch;
    private String company;
    private String expNum;
    private String gradYear;
    private String linkedIn;
    private String mail;
    private String name;
    private String exp;
    private String imgUrl;
    private boolean expanded=false;

    /*public AlumniModelClass(String branch, String company, String expNum, String gradYear, String linkedIn, String mail, String name, String exp) {
        this.branch = branch;
        this.company = company;
        this.expNum = expNum;
        this.gradYear = gradYear;
        this.linkedIn = linkedIn;
        this.mail = mail;
        this.name = name;
        this.exp = exp;
    }*/

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBranch() {
        return branch;
    }

    public String getCompany() {
        return company;
    }

    public String getExpNum() {
        return expNum;
    }

    public String getGradYear() {
        return gradYear;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getExp() {
        return exp;
    }

    private boolean getExpanded() {return expanded;}

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setExpNum(String expNum) {
        this.expNum = expNum;
    }

    public void setGradYear(String gradYear) {
        this.gradYear = gradYear;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setExpanded(boolean expanded){this.expanded = expanded;}

    public boolean isExpanded() {
        return expanded;
    }
}
