package com.example.electronicsstore.model;

import java.io.Serializable;

public class OrderDetailsModel implements Serializable {
    String name;
    String price;
    String description;
    String quantity;
    int pic;


    public OrderDetailsModel(String name, String price, String description, String quantity, int pic) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.pic = pic;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public OrderDetailsModel() {
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

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
