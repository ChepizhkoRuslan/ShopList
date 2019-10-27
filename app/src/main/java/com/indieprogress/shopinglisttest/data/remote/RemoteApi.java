package com.indieprogress.shopinglisttest.data.remote;

import com.indieprogress.shopinglisttest.data.model.ShopResponse;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

public interface RemoteApi {

    @Headers("Content-Type:application/json")
    @GET("bins/7ul60")
    Observable<List<ShopResponse>> getListShop();

}

