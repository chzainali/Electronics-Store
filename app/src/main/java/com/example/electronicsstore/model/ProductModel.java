package com.example.electronicsstore.model;

import java.io.Serializable;

public class ProductModel implements Serializable {
    String name, price, description, pic;

    public ProductModel() {
    }

    public ProductModel(String name, String price, String description, String pic) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
