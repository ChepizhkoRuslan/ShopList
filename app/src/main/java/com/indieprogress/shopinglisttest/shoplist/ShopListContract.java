package com.indieprogress.shopinglisttest.shoplist;

import com.indieprogress.shopinglisttest.base.baseMVP.BasePresenter;
import com.indieprogress.shopinglisttest.base.baseMVP.BaseView;
import com.indieprogress.shopinglisttest.data.model.ShopModel;
import com.indieprogress.shopinglisttest.data.model.ShopResponse;
import java.util.List;


public interface ShopListContract {

    interface View extends BaseView {
        void setListShopToAdapter(List<ShopModel> listShop);
        void showError(String error);
    }

    interface Presenter extends BasePresenter<View> {
        void loadListShop();
        void setListShopToDB(List<ShopResponse> shopResponse);
        void updateShopItem(ShopModel shop);
        void updateShopList(List<ShopModel> listShop, String state);
        void getAllListShopFromDb();
    }
}
