package com.indieprogress.shopinglisttest.shoplist.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.annotation.NonNull;
import com.indieprogress.shopinglisttest.data.Repository;
import com.indieprogress.shopinglisttest.data.model.ShopModel;
import com.indieprogress.shopinglisttest.data.model.ShopResponse;
import com.indieprogress.shopinglisttest.data.room.AppDatabase;
import com.indieprogress.shopinglisttest.data.room.Shop;
import com.indieprogress.shopinglisttest.data.room.ShopDao;
import com.indieprogress.shopinglisttest.shoplist.ShopListContract;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.Subscriber;


public class ShopListPresenter implements ShopListContract.Presenter {

    private ShopListContract.View view;
    private Repository repository;
    private ShopDao shopDao;

    @Inject
    Application context;

    @Inject
    ShopListPresenter(Repository repository) {
        AppDatabase db = repository.provideDatabase();
        shopDao = db.shopDao();
        this.repository = repository;
    }

    @Override
    public void takeView(@NonNull ShopListContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void loadListShop() {
        repository.getListShop().subscribe(new Subscriber<List<ShopResponse>>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                view.showError(e.toString());
            }

            @Override
            public void onNext(List<ShopResponse> shopResponse) {
                if (view != null) {
                    setListShopToDB(shopResponse);
                }
            }
        });
    }

    @Override
    public void setListShopToDB(List<ShopResponse> shopResponse) {

        Completable.fromAction(() -> {
            for (ShopResponse sh : shopResponse) {
                Shop shop = new Shop(sh.getId(), sh.getName(), sh.getDesc(), sh.getPrice(), "Not bought");
                shopDao.insert(shop);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onComplete() {
                getAllListShopFromDb();
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.toString());
            }
        });
    }

    @Override
    @SuppressLint("CheckResult")
    public void getAllListShopFromDb() {
        shopDao.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(shopList -> {
                    List<ShopModel> shopModels = new ArrayList<>();
                    for (int i = 0; i < shopList.size(); i++) {
                        shopModels.add(new ShopModel(shopList.get(i).getId(), shopList.get(i).getName(),
                                shopList.get(i).getDesc(), shopList.get(i).getPrice(), shopList.get(i).getState()));
                    }
                    view.setListShopToAdapter(shopModels);
                });
    }

    @Override
    public void updateShopItem(ShopModel shopModel) {
        final String state;
        if(shopModel.getState().equals("Bought")){
            state = "Not bought";
        }else{
            state = "Bought";
        }
        Completable.fromAction(() -> {

            Shop shop = new Shop(shopModel.getId(), shopModel.getName(), shopModel.getDesc(),
                    shopModel.getPrice(), state);
            shopDao.updateShop(shop);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onComplete() {
                getAllListShopFromDb();
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.toString());
            }
        });
    }

    @Override
    public void updateShopList(List<ShopModel> listShop, String state) {
        Completable.fromAction(() -> {
            for (ShopModel sh : listShop) {
                Shop shop = new Shop(sh.getId(), sh.getName(), sh.getDesc(), sh.getPrice(), state);
                shopDao.updateShop(shop);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onComplete() {
                getAllListShopFromDb();
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.toString());
            }
        });
    }
}


