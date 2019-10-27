package com.indieprogress.shopinglisttest.base.baseMVP;


import androidx.annotation.NonNull;


public interface BasePresenter<T> {
    void takeView(@NonNull T view);
    void dropView();
}
