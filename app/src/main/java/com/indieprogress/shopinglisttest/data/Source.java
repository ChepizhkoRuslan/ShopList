package com.indieprogress.shopinglisttest.data;

import androidx.annotation.NonNull;

import com.indieprogress.shopinglisttest.data.model.ShopResponse;

import java.util.List;
import rx.Observable;

public interface Source {
    @NonNull
    Observable<List<ShopResponse>> getListShop();
}
