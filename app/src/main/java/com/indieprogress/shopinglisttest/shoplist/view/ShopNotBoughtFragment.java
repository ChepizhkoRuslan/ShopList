package com.indieprogress.shopinglisttest.shoplist.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ShopNotBoughtFragment extends BaseFragment implements ShopListContract.View {

    @Inject
    ShopListPresenter presenter;

    @Inject
    ShopListAdapter listAdapter;

    @BindView(R.id.rv_list)
    RecyclerView rvListShop;

    @BindView(R.id.constraintLayout)
    ConstraintLayout constraint;

    @Inject
    public ShopNotBoughtFragment(){
    }

    @Nullable
    @Override
    @SuppressLint({"InflateParams", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_rv_list, null, false);
        ButterKnife.bind(this, view);
        constraint.setVisibility(View.GONE);
        showProgressDialog();
        initReviewsAdapter();
        return view;
    }

    private void initReviewsAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        listAdapter = new ShopListAdapter(getContext());
        rvListShop.addItemDecoration(new DividerItemDecoration(rvListShop.getContext(), manager.getOrientation()));
        rvListShop.setLayoutManager(manager);
        rvListShop.setAdapter(listAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.getAllListShopFromDb();
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
        List<ShopModel> shopNotBought = new ArrayList<>();
        for(int i=0; i<listShop.size(); i++){
            if(listShop.get(i).getState().equals(StateEnum.NOT_BOUGHT.toString())){
                shopNotBought.add(listShop.get(i));
            }
        }
        listAdapter.setList(shopNotBought, StateEnum.NOT_BOUGHT.toString());
        hideProgressDialog();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
