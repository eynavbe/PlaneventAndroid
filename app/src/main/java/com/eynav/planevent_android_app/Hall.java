package com.eynav.planevent_android_app;

public class Hall {
    String nameHall, hallImage,hallArea;
    Integer hallCountPeople;

    public Hall(String nameHall, String hallImage, String hallArea, Integer hallCountPeople) {
        this.nameHall = nameHall;
        this.hallImage = hallImage;
        this.hallArea = hallArea;
        this.hallCountPeople = hallCountPeople;
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

    public int getHallCountPeople() {
        return hallCountPeople;
    }

    public void setHallCountPeople(Integer hallCountPeople) {
        this.hallCountPeople = hallCountPeople;
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
