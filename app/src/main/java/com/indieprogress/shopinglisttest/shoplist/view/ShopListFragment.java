package com.indieprogress.shopinglisttest.shoplist.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.indieprogress.shopinglisttest.R;
import com.indieprogress.shopinglisttest.base.BaseFragment;
import com.indieprogress.shopinglisttest.data.model.ShopModel;
import com.indieprogress.shopinglisttest.data.model.StateEnum;
import com.indieprogress.shopinglisttest.shoplist.ShopListContract;
import com.indieprogress.shopinglisttest.shoplist.adapter.ShopListAdapter;
import com.indieprogress.shopinglisttest.shoplist.presenter.ShopListPresenter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShopListFragment extends BaseFragment implements ShopListContract.View {

    @Inject
    ShopListPresenter presenter;

    @Inject
    ShopListAdapter listAdapter;

    @BindView(R.id.rv_list)
    RecyclerView rvListShop;

    @BindView(R.id.image_all)
    ImageView imgAllItems;

    @BindView(R.id.constraintLayout)
    ConstraintLayout constraint;

    private int count = 0;
    private List<ShopModel> shopModels;

    @Inject
    public ShopListFragment(){
    }

    @OnClick(R.id.image_all)
    void setAllShopItems(){
        count++;
        if(count%2 == 1) {
            imgAllItems.setImageResource(R.drawable.ic_check_box_black_24dp);
            presenter.updateShopList(shopModels, StateEnum.BOUGHT.toString());
        }else {
            imgAllItems.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
            presenter.updateShopList(shopModels, StateEnum.NOT_BOUGHT.toString());
        }
    }

    @Nullable
    @Override
    @SuppressLint({"InflateParams", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_rv_list, null, false);
        ButterKnife.bind(this, view);
        constraint.setVisibility(View.VISIBLE);
        showProgressDialog();
        shopModels = new ArrayList<>();
        initReviewsAdapter();
        presenter.getAllListShopFromDb();
        return view;
    }

    private void initReviewsAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        listAdapter = new ShopListAdapter(getContext());
        rvListShop.addItemDecoration(new DividerItemDecoration(rvListShop.getContext(), manager.getOrientation()));
        rvListShop.setLayoutManager(manager);
        rvListShop.setAdapter(listAdapter);
        listAdapter.setShopCallBack(shopModel -> presenter.updateShopItem(shopModel));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
        hideProgressDialog();
    }

    @Override
    public void setListShopToAdapter(List<ShopModel> listShop) {
        if(listShop == null)return;
        if(listShop.size() == 0){
            presenter.loadListShop();
        }
        shopModels.addAll(listShop);
        listAdapter.setList(listShop, StateEnum.ALL.toString());
        hideProgressDialog();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

}
