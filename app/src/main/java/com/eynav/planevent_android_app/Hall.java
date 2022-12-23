package com.eynav.planevent_android_app;

public class Hall {
    String nameHall, hallImage,hallArea,email,phoneNum;
    String hallCountPeople;

    public Hall(String nameHall, String hallImage, String hallArea, String hallCountPeople, String email, String phoneNum) {
        this.nameHall = nameHall;
        this.hallImage = hallImage;
        this.hallArea = hallArea;
        this.hallCountPeople = hallCountPeople;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public String getNameHall() {
        return nameHall;
    }

    public void setNameHall(String nameHall) {
        this.nameHall = nameHall;
    }

    public String getHallImage() {
        return hallImage;
    }

    public void setHallImage(String hallImage) {
        this.hallImage = hallImage;
    }

    public String getHallArea() {
        return hallArea;
    }

    public void setHallArea(String hallArea) {
        this.hallArea = hallArea;
    }

    public String getHallCountPeople() {
        return hallCountPeople;
    }

    public void setHallCountPeople(String hallCountPeople) {
        this.hallCountPeople = hallCountPeople;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "nameHall='" + nameHall + '\'' +
                ", hallImage='" + hallImage + '\'' +
                ", hallArea='" + hallArea + '\'' +
                ", hallCountPeople=" + hallCountPeople +
                '}';
    }
}
