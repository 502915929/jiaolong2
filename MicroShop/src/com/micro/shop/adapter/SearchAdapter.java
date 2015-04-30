package com.micro.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.activity.ProductDetailActivity;
import com.micro.shop.activity.ShopMainActivity_;
import com.micro.shop.config.AppContext;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.SearchResult;
import com.micro.shop.util.NumberFormatUtil;

import java.util.List;

/**
 * Created by 95 on 2015/4/27.
 */
public class SearchAdapter extends BaseAdapter {
    private Context context;
    public List<SearchResult> list;
    private LayoutInflater inflater;

    public SearchAdapter(Context context,List<SearchResult> list) {
        super();
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(this.context);

    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public SearchResult getItem(int arg0) {
        return list==null?null:list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final SearchResult rs = list.get(position);
        ViewHolder holder;
        if (view == null){
            view = inflater.inflate(R.layout.adapter_search_item, parent,
                    false);
            holder=new ViewHolder();
            holder.search_item_img=(ImageView)view.findViewById(R.id.search_item_img);
            holder.search_item_name=(TextView)view.findViewById(R.id.search_item_name);
            holder.search_item_mark=(TextView)view.findViewById(R.id.search_item_mark);
            holder.search_table_address=(TextView)view.findViewById(R.id.search_table_address);
            holder.search_shop_range=(TextView)view.findViewById(R.id.search_shop_range);
            holder.search_shop_slogan=(TextView)view.findViewById(R.id.search_shop_slogan);

            holder.search_show_way=(RelativeLayout)view.findViewById(R.id.search_show_way);
            holder.search_shop_mobile=(RelativeLayout)view.findViewById(R.id.search_shop_mobile);
            holder.search_goto_shop=(RelativeLayout)view.findViewById(R.id.search_goto_shop);

            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        String shopLogo=rs.getShopLogo();
        if(shopLogo!=null&&!"".equals(shopLogo)){
            AppContext.getImageLoader().displayImage(ConstantJiao.aliUrl+shopLogo,holder.search_item_img);
        }
        holder.search_item_name.setText(rs.getShopName());
        holder.search_item_mark.setText(rs.getHotNum()+"收藏");
        String address=rs.getAddress();
        if(address==null||"".equals(address)){
            holder.search_table_address.setText("店铺地址未填写");
        }else{
            holder.search_table_address.setText(rs.getAddress());
        }
        double juli=rs.getRange();
        if(juli>=1){
            holder.search_shop_range.setText(NumberFormatUtil.conventToString(juli)+"公里");
        }else{
            holder.search_shop_range.setText(NumberFormatUtil.conventToString(juli*1000)+"米");
        }
        String slogan = rs.getShopSlogan();
        if(slogan==null||"".equals(slogan)){
            holder.search_shop_slogan.setText("还没有填写店铺简介...");
        }else {
            holder.search_shop_slogan.setText(rs.getShopSlogan());
        }

        holder.search_show_way.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 线路点击事件
            }
        });
        holder.search_shop_mobile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 电话点击事件
            }
        });
        holder.search_goto_shop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("click start","this");
                Intent intent= ShopMainActivity_.intent(context).get();
                intent.putExtra("shopCode",rs.getShopCode());
                context.startActivity(intent);
            }
        });

        return view;
    }


    public class ViewHolder{
        ImageView search_item_img;
        TextView search_item_name;
        TextView search_item_mark;
        TextView search_table_address;
        TextView search_shop_range;
        TextView search_shop_slogan;
        RelativeLayout search_show_way;
        RelativeLayout search_shop_mobile;
        RelativeLayout search_goto_shop;

    }

}