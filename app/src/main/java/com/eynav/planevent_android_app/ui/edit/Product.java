package com.eynav.planevent_android_app.ui.edit;

public class Product {
    String name;
    int price;
    String image;
    boolean chooseThis;
    public Product() {
    }

    public Product(String name, int price, String image, boolean chooseThis) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.chooseThis = chooseThis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isChooseThis() {
        return chooseThis;
    }

    public void setChooseThis(boolean chooseThis) {
        this.chooseThis = chooseThis;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", chooseThis=" + chooseThis +
                '}';
    }
}
