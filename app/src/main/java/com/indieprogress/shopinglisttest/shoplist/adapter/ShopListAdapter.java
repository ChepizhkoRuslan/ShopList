package com.indieprogress.shopinglisttest.shoplist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.indieprogress.shopinglisttest.R;
import com.indieprogress.shopinglisttest.data.model.ShopModel;
import com.indieprogress.shopinglisttest.data.model.StateEnum;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder>{
    private Context context;
    private String page;
    private List<ShopModel> list;
    private ShopCallBack callBack;

    @Inject
    public ShopListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindView(list, position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<ShopModel> list, String page) {
        this.list = list;
        this.page = page;
        notifyDataSetChanged();

    }

    public void setShopCallBack(ShopCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ShopCallBack {
        void onClickItem(ShopModel list);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name)
        TextView tvName;
        @BindView(R.id.text_desc)
        TextView tvDesc;
        @BindView(R.id.text_price)
        TextView tvPrice;
        @BindView(R.id.image_state)
        ImageView imgState;
        private View itemView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        void bindView(List<ShopModel> list, int position) {

            if (list.get(position).getName() != null) {
                String name = list.get(position).getName();
                tvName.setText(name);
            }
            if (list.get(position).getDesc() != null) {
                String desc = list.get(position).getDesc();
                tvDesc.setText(desc);
            }
            if (list.get(position).getPrice() != null) {
                String price = list.get(position).getPrice();
                tvPrice.setText(price);
            }
            if (list.get(position).getState() != null) {
                String state = list.get(position).getState();
                if(state.equals(StateEnum.BOUGHT.toString())){
                    imgState.setImageResource(R.drawable.ic_check_box_black_24dp);
                }else {
                    imgState.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                }
            }
            if(page.equals(StateEnum.ALL.toString())) {
                itemView.setOnClickListener(v -> {
                    callBack.onClickItem(list.get(position));

                });
            }
        }
    }
}

