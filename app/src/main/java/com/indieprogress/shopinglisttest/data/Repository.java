package com.indieprogress.shopinglisttest.data;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import androidx.room.Room;
import com.indieprogress.shopinglisttest.data.annotations.Remote;
import com.indieprogress.shopinglisttest.data.model.ShopResponse;
import com.indieprogress.shopinglisttest.data.room.AppDatabase;
import java.net.ConnectException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;


@Singleton
public class Repository implements Source {

    private final Source mSource;

    @Inject
    Application context;

    @Inject
    Repository(@Remote Source source) {
        this.mSource = source;
    }

    private boolean isInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null;
    }

    @NonNull
    @Override
    public Observable<List<ShopResponse>> getListShop() {
        if (isInternetConnection())
            return mSource.getListShop();
        else
            return Observable.error(new ConnectException("No internet connection"));
    }

    public AppDatabase provideDatabase() {
        return Room.databaseBuilder(context, AppDatabase.class, "database").build();
    }
}
