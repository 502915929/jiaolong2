package com.micro.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.micro.shop.R;
import com.micro.shop.config.AppContext;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.ProductImage;

import java.util.List;

/**
 * Created by 95 on 2015/4/23.
 */
public class ProductMsgImgAdapter extends BaseAdapter {

    List<ProductImage> list;
    Context context;
    LayoutInflater inflater;

    ImageView proImage;

    public ProductMsgImgAdapter(Context context, List<ProductImage> list){
        inflater=LayoutInflater.from(context);
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductImage image =list.get(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.layout_pro_more_tab_two,parent,false);
        }else{

        }
        proImage =(ImageView)convertView.findViewById(R.id.product_detail_fragment_img);
        AppContext.getImageLoader().displayImage(ConstantJiao.aliUrl+image.getImageUrl()+"@!mobile-product-header",proImage);
        return convertView;
    }
}
