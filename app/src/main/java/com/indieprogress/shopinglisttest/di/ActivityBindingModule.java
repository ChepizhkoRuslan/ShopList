package com.indieprogress.shopinglisttest.di;

import com.indieprogress.shopinglisttest.MainActivity;
import com.indieprogress.shopinglisttest.di.scoped.ActivityScoped;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();
}
