package com.micro.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.activity.ShopMainActivity_;
import com.micro.shop.entity.SearchResult;
import com.micro.shop.fragment.SearchFragment;
import com.micro.shop.util.NumberFormatUtil;

import java.util.List;

/**
 * Created by 95 on 2015/5/2.
 */
public class AddressAdapter extends BaseAdapter {

    Context context;
    public List<SearchResult> list;
    private LayoutInflater inflater;

    public AddressAdapter(Context context,List<SearchResult> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public SearchResult getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        final SearchResult re=list.get(position);
        if(view==null){
            holder=new ViewHolder();
            view=inflater.inflate(R.layout.address_item,parent);
            holder.address_shop_name=(TextView)view.findViewById(R.id.address_shop_name);
            holder.address_range=(TextView)view.findViewById(R.id.address_range);
            holder.address_shop_info=(TextView)view.findViewById(R.id.address_shop_info);
            holder.address_slogan=(TextView)view.findViewById(R.id.address_slogan);
            holder.address_detail=(LinearLayout)view.findViewById(R.id.address_detail);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        holder.address_shop_name.setText(re.getShopName());
        Double range = NumberFormatUtil.m1(re.getRange());
        if(range>=1){
            holder.address_range.setText("距离"+range+"公里");
        }else{
            holder.address_range.setText("距离"+(range*1000)+"米");
        }
        holder.address_shop_info.setText(re.getHotNum()+"访问");
        if(re.getShopSlogan()!=null&&!"".equals(re.getShopSlogan())){
            holder.address_slogan.setText("该店铺还没有发布店铺简介...");
        }else{
            holder.address_slogan.setText(re.getShopSlogan());
        }
        holder.address_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ShopMainActivity_.intent(context).get();
                intent.putExtra("shopCode", re.getShopCode());
                context.startActivity(intent);
            }
        });
        return view;
    }

    public class ViewHolder{
        TextView address_shop_name;
        TextView address_range;
        TextView address_shop_info;
        TextView address_slogan;
        LinearLayout address_detail;
    }
}
