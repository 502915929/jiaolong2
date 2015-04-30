package com.micro.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.activity.ProductDetailActivity;
import com.micro.shop.config.AppContext;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.LocalData;
import com.micro.shop.util.NumberFormatUtil;

import java.util.List;

/**
 * Created by 95 on 2015/4/25.
 */
public class LocalFragmentAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<LocalData> list;

    public LocalFragmentAdapter(Context context,List<LocalData> list) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.list=list;
    }

    public void addAll() {
        notifyDataSetChanged();
    }

    public void update() {
        this.list.clear();
        addAll();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list==null?null:list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder viewHolder;
        final LocalData local = list.get(position);
        if (view == null){
            view = inflater.inflate(R.layout.adapter_local_item, container,
                    false);
            viewHolder= new ViewHolder();
            viewHolder.local_item_address_title=(TextView)view.findViewById(R.id.local_item_address_title);
            viewHolder.local_item_icon=(ImageView)view.findViewById(R.id.local_item_icon);
            viewHolder.local_item_image=(ImageView)view.findViewById(R.id.local_item_image);
            viewHolder.local_item_intro_title=(TextView)view.findViewById(R.id.local_item_intro_title);
            viewHolder.local_item_name=(TextView)view.findViewById(R.id.local_item_name);
            viewHolder.local_item_time_title=(TextView)view.findViewById(R.id.local_item_time_title);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        double juli=local.getRange();
        if(juli>=1){
            viewHolder.local_item_address_title.setText(NumberFormatUtil.conventToString(juli)+"公里");
        }else{
            viewHolder.local_item_address_title.setText(NumberFormatUtil.conventToString(juli*1000)+"米");
        }

        if(local.getProductImage()!=null&&!"".equals(local.getProductImage())){
            AppContext.getImageLoader().displayImage(ConstantJiao.aliUrl + local.getProductImage()+"@!local-item-img", viewHolder.local_item_image);

        }
        if(local.getShopLogo()!=null&&!"".equals(local.getShopLogo())){
            AppContext.getImageLoader().displayImage(ConstantJiao.aliUrl+local.getShopLogo()+"@67-1ci.png",viewHolder.local_item_icon);
        }
        viewHolder.local_item_intro_title.setText(local.getProductName());
        viewHolder.local_item_name.setText(local.getShopName());

        Long upTime = local.getUpTime();
        if(upTime<=3){
            viewHolder.local_item_time_title.setText("刚刚");
        }else if(upTime>3&&upTime<60){
            viewHolder.local_item_time_title.setText(upTime+"分钟前");
        }else if(upTime>=60&&upTime<=1440){
            Long hour = upTime/60;
            viewHolder.local_item_time_title.setText(hour+"小时前");
        }else{
            Long day= upTime/1440;
            viewHolder.local_item_time_title.setText(day+"天前");
        }



        // 列表点击操作
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ProductDetailActivity.class);
                intent.putExtra("productCode",local.getProductCode());
                context.startActivity(intent);
            }
        });
        return view;
    }



    public class ViewHolder{
        ImageView local_item_image;
        TextView local_item_intro_title;
        ImageView local_item_icon;
        TextView local_item_name;
        TextView local_item_address_title;
        TextView local_item_time_title;
    }

}
