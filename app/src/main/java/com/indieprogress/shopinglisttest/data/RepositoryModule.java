package com.indieprogress.shopinglisttest.data;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indieprogress.shopinglisttest.data.annotations.Remote;
import com.indieprogress.shopinglisttest.data.remote.RemoteApi;
import com.indieprogress.shopinglisttest.data.remote.RemoteSource;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@SuppressWarnings("unused")
public abstract class RepositoryModule {
    private static final String BASE_URL = "https://api.myjson.com/";
    @Inject
    Application context;

    @Singleton
    @Provides
    @Named("provideRetrofit")
    static Retrofit provideRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    static RemoteApi provideRemoteApi(@Named("provideRetrofit") Retrofit retrofit) {
        return retrofit.create(RemoteApi.class);
    }

    @Singleton
    @Binds
    @Remote
    abstract Source getListUsers(RemoteSource source);
}
