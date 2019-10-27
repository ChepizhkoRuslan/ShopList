package com.indieprogress.shopinglisttest.data.remote;

import android.app.Application;

import androidx.annotation.NonNull;

import com.indieprogress.shopinglisttest.data.Source;
import com.indieprogress.shopinglisttest.data.model.ShopResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@Singleton
public class RemoteSource implements Source {

    private final RemoteApi mApi;

    @Inject
    RemoteSource(RemoteApi api) {
        this.mApi = api;
    }

    @NonNull
    @Override
    public Observable<List<ShopResponse>> getListShop() {
        return mApi.getListShop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
