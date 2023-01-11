package com.eynav.planevent_android_app;

public class CountHallEdit {
    String chooseCountAll;
    String chooseFromAll;
    public CountHallEdit(){}
    public CountHallEdit(String chooseCountAll, String chooseFromAll){
        this.chooseCountAll = chooseCountAll;
        this.chooseFromAll = chooseFromAll;
    }

    public String getChooseCountAll() {
        return chooseCountAll;
    }

    public String getChooseFromAll() {
        return chooseFromAll;
    }

    public void setChooseCountAll(String chooseCountAll) {
        this.chooseCountAll = chooseCountAll;
    }

    public void setChooseFromAll(String chooseFromAll) {
        this.chooseFromAll = chooseFromAll;
    }

    @Override
    public String toString() {
        return "CountHallEdit{" +
                "chooseCountAll='" + chooseCountAll + '\'' +
                ", chooseFromAll='" + chooseFromAll + '\'' +
                '}';
    }
}
