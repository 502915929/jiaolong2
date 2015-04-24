package com.micro.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.config.AppContext;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.ClientUserBase;
import com.micro.shop.entity.ProductAttribute;

import java.util.List;

/**
 * Created by 95 on 2015/4/24.
 */
public class ProductMsgGoodAdapter extends BaseAdapter {

    List<ClientUserBase> goodList;
    Context context;
    LayoutInflater inflater;


    public ProductMsgGoodAdapter(Context context,List<ClientUserBase> goodList){
        inflater= LayoutInflater.from(context);
        this.context=context;
        this.goodList=goodList;
    }

    @Override
    public int getCount() {
        return goodList==null?0:goodList.size();
    }

    @Override
    public ClientUserBase getItem(int position) {
        return goodList==null?null:goodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ClientUserBase client=goodList.get(position);
        if(view==null){
            view=inflater.inflate(R.layout.layout_pro_good_well,parent,false);
        }
        ImageView  user_head_img =(ImageView)view.findViewById(R.id.user_head_img);
        AppContext.getImageLoader().displayImage(ConstantJiao.aliUrl+client.getUserHeadImg()+"@112w_114h",user_head_img);
        return view;
    }
}
