package com.micro.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.entity.ProductAttribute;

import java.util.List;

/**
 * Created by 95 on 2015/4/24.
 * 商品详情 -tab-商品参数集合
 */
public class ProductMsgAttrAdapter extends BaseAdapter {

    private List<ProductAttribute> attrList;
    Context context;
    LayoutInflater inflater;
    TextView product_attr;

    public ProductMsgAttrAdapter(Context context, List<ProductAttribute> attrList){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.attrList=attrList;
    }


    @Override
    public int getCount() {
        return attrList==null?0:attrList.size();
    }

    @Override
    public ProductAttribute getItem(int position) {
        return attrList==null?null:attrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ProductAttribute attr=attrList.get(position);
        if(view==null){
            view=inflater.inflate(R.layout.layout_pro_more_tab_one,parent,false);
        }
        product_attr=(TextView)view.findViewById(R.id.product_attr);
        product_attr.setText(attr.getAttributeName()+":"+attr.getAttributeContent());
        return view;
    }
}
