package com.micro.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.activity.ShopMainActivity_;
import com.micro.shop.util.NumberFormatUtil;


/**
 * Created by 95 on 2015/5/3.
 */
public class AddressBottomFragment extends Fragment {
    View view;
    ViewHolder holder;

    public AddressBottomFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.address_item, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        if(view.getTag()==null){
            holder=new ViewHolder();
            holder.address_shop_name=(TextView)view.findViewById(R.id.address_shop_name);
            holder.address_range=(TextView)view.findViewById(R.id.address_range);
            holder.address_shop_info=(TextView)view.findViewById(R.id.address_shop_info);
            holder.address_slogan=(TextView)view.findViewById(R.id.address_slogan);
            holder.address_detail=(LinearLayout)view.findViewById(R.id.address_detail);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        initData();

    }

    public void initData(){
        Bundle bundle=getArguments();
        String shopName=bundle.getString("shopName");
        String slogan = bundle.getString("slogan");
        String address =bundle.getString("address");
        final String shopCode=bundle.getString("shopCode");
        Log.e("the shopName is-->",shopName);
        holder.address_shop_name.setText(shopName+"("+address+")");
        Double range = NumberFormatUtil.m1(bundle.getDouble("range"));
        if(range>=1){
            holder.address_range.setText("距离"+range+"公里");
        }else{
            holder.address_range.setText("距离"+(range*1000)+"米");
        }
        holder.address_shop_info.setText(bundle.getInt("hotNum")+"访问");
        if(slogan==null||"".equals(slogan)){
            holder.address_slogan.setText("该店铺还没有发布店铺简介...");
        }else{
            holder.address_slogan.setText(slogan);
        }
        holder.address_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ShopMainActivity_.intent(getActivity()).get();
                intent.putExtra("shopCode", shopCode);
                getActivity().startActivity(intent);
            }
        });
    }

    public class ViewHolder{
        TextView address_shop_name;
        TextView address_range;
        TextView address_shop_info;
        TextView address_slogan;
        LinearLayout address_detail;
    }


}
