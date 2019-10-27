package com.indieprogress.shopinglisttest.di;

import com.indieprogress.shopinglisttest.di.scoped.FragmentScoped;
import com.indieprogress.shopinglisttest.shoplist.view.ShopBoughtFragment;
import com.indieprogress.shopinglisttest.shoplist.view.ShopListFragment;
import com.indieprogress.shopinglisttest.shoplist.view.ShopNotBoughtFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainActivityModule {
    @FragmentScoped
    @ContributesAndroidInjector()
    abstract ShopListFragment shopListFragment();

    @FragmentScoped
    @ContributesAndroidInjector()
    abstract ShopNotBoughtFragment shopNotBoughtFragment();

    @FragmentScoped
    @ContributesAndroidInjector()
    abstract ShopBoughtFragment shopBoughtFragment();

}
