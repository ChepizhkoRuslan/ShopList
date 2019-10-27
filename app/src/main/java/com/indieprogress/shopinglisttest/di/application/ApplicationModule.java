package com.indieprogress.shopinglisttest.di.application;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public interface ApplicationModule {
    @Binds
    Context bindContext(Application application);
}
