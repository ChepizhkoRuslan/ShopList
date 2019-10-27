package com.indieprogress.shopinglisttest.data.room;


import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Shop.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShopDao shopDao();
}