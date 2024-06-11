package com.example.electronicsstore.model;

import java.io.Serializable;
import java.util.ArrayList;


public class OrderModel implements Serializable {
    String orderId, userId, userName, status, dateTime;
    ArrayList<ProductsModel> productList;

    public OrderModel() {
    }

    public OrderModel(String userId, String userName, String status, String dateTime, ArrayList<ProductsModel> productList) {
        this.userId = userId;
        this.userName = userName;
        this.status = status;
        this.dateTime = dateTime;
        this.productList = productList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public ArrayList<ProductsModel> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductsModel> productList) {
        this.productList = productList;
    }
}
