package com.example.autoparts_ver30;

public class Part {
    private String name;
    private String carModel;
    private double price;  // изменяем на double
    private String photo;

    public Part(String name, String carModel, double price, String photo) {
        this.name = name;
        this.carModel = carModel;
        this.price = price;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getCarModel() {
        return carModel;
    }

    public double getPrice() {  // возвращаем double
        return price;
    }

    public String getPhoto() {
        return photo;
    }

    // Если всё-таки необходимо получать цену как строку:
    public String getPriceString() {
        return String.format("%s руб.", price);
    }
}
