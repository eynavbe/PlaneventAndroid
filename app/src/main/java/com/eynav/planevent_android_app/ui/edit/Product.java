package com.eynav.planevent_android_app.ui.edit;

public class Product {
    String name;
    Long price;
    Boolean inPrice;
    Long priceClient;
    String image;
    Boolean chooseThis;
    public Product() {
    }

    public Product(String name, Long price, Boolean inPrice, Long priceClient, String image, Boolean chooseThis) {
        this.name = name;
        this.price = price;
        this.inPrice = inPrice;
        this.priceClient = priceClient;
        this.image = image;
        this.chooseThis = chooseThis;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean isInPrice() {
        return inPrice;
    }

    public void setInPrice(Boolean inPrice) {
        this.inPrice = inPrice;
    }

    public Long getPriceClient() {
        return priceClient;
    }

    public void setPriceClient(Long priceClient) {
        this.priceClient = priceClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean isChooseThis() {
        return chooseThis;
    }

    public void setChooseThis(Boolean chooseThis) {
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
