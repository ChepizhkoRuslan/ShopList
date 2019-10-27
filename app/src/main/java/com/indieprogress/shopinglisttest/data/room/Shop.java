package com.indieprogress.shopinglisttest.data.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Shop {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "price")
    private String price;
    @ColumnInfo(name = "state")
    private String state;

    public Shop(@NonNull String id,String name,String desc,String price,String state) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.state = state;
    }
    @NonNull
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
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
    public void setState(String state) {
        this.state = state;
    }
}
