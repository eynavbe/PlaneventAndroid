package com.eynav.planevent_android_app;

public class Hall {
    String nameHall, imHallImage,tvHallArea;
    int tvHallCountPeople;

    public Hall(String nameHall, String imHallImage, String tvHallArea, int tvHallCountPeople) {
        this.nameHall = nameHall;
        this.imHallImage = imHallImage;
        this.tvHallArea = tvHallArea;
        this.tvHallCountPeople = tvHallCountPeople;
    }

    public String getNameHall() {
        return nameHall;
    }

    public void setNameHall(String nameHall) {
        this.nameHall = nameHall;
    }

    public String getImHallImage() {
        return imHallImage;
    }

    public void setImHallImage(String imHallImage) {
        this.imHallImage = imHallImage;
    }

    public String getTvHallArea() {
        return tvHallArea;
    }

    public void setTvHallArea(String tvHallArea) {
        this.tvHallArea = tvHallArea;
    }

    public int getTvHallCountPeople() {
        return tvHallCountPeople;
    }

    public void setTvHallCountPeople(int tvHallCountPeople) {
        this.tvHallCountPeople = tvHallCountPeople;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "nameHall='" + nameHall + '\'' +
                ", imHallImage='" + imHallImage + '\'' +
                ", tvHallArea='" + tvHallArea + '\'' +
                ", tvHallCountPeople=" + tvHallCountPeople +
                '}';
    }
}
