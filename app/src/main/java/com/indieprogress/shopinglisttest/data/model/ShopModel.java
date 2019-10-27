package com.indieprogress.shopinglisttest.data.model;

public class ShopModel {
    private String id;
    private String name;
    private String desc;
    private String price;
    private String state;

    public ShopModel(String id, String name, String desc, String price, String state) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }
}
