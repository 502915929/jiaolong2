package com.micro.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.activity.ProductDetailActivity;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.Product;
import com.micro.shop.util.NumberFormatUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95 on 2015/4/18.
 * 店铺首页adapter
 */
public class ShopMainGridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Product> list;

    //图片控件
    DisplayImageOptions options;
    ImageLoader imageLoader;

    String priceEm;

    public ShopMainGridViewAdapter(Context context,List<Product> likeList) {
        super();
        this.context = context;
        this.list = likeList;
        this.inflater = LayoutInflater.from(this.context);
        imageLoader=ImageLoader.getInstance();
        priceEm=context.getResources().getText(R.string.price).toString();
    }

    public void addAll(List<Product> likeList) {
        this.list.addAll(likeList);
        notifyDataSetChanged();
    }

    public void update() {
        this.list.clear();
        //addAll(list);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Product getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        final Product pro = list.get(position);
        ViewHolder holder;
        if (view == null){
            view = inflater.inflate(R.layout.adapter_shop_main_item,
                    container, false);
            holder=new ViewHolder();
            init(view,holder);
            view.setTag(holder);
        }else {
            holder=(ViewHolder)view.getTag();
        }
        imageLoader.displayImage(ConstantJiao.aliUrl + pro.getCoverBigImage()+"@w291_h323", holder.like_pro_img, options);
        holder.like_pro_name.setText(pro.getProductName());
        if(pro.getSalePrice()==null||pro.getSalePrice()==0){
            holder.like_pro_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getProductPrice()));
        }else{
            holder.like_pro_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getSalePrice()));
        }
        // 列表点击操作
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,ProductDetailActivity.class);
                intent.putExtra("productCode",pro.getProductCode());
                context.startActivity(intent);
            }
        });
        return view;
    }

    void init(View view,ViewHolder holder){
        holder. like_pro_img =(ImageView)view.findViewById(R.id.shop_main_item_image);
        holder.like_pro_name=(TextView)view.findViewById(R.id.shop_main_item_intro_title);
        holder.like_pro_price=(TextView)view.findViewById(R.id.three_content_one_price);
    }

    public static class ViewHolder{
        ImageView like_pro_img;
        TextView like_pro_name;
        TextView like_pro_price;
    }


}
