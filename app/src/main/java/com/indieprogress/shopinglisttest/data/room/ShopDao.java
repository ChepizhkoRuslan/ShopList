package com.indieprogress.shopinglisttest.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import io.reactivex.Maybe;


@Dao
public interface ShopDao {

    @Query("SELECT * FROM Shop")
    Maybe<List<Shop>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Shop shop);

    @Update
    void updateShop(Shop shop);


}
