package com.micro.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.R;
import com.micro.shop.activity.ProductDetailActivity;
import com.micro.shop.activity.ShopMainActivity;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.Dynamic;
import com.micro.shop.net.HttpUtil;
import com.micro.shop.util.NumberFormatUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by 95 on 2015/4/16.
 */
public class DynamicAdapter extends BaseAdapter {

    private Context context;
    public static List<Dynamic> dynamicList;
    private LayoutInflater inflater;

    //图片控件
    DisplayImageOptions options;
    ImageLoader imageLoader;
    String priceEm;

    public DynamicAdapter(Context context){
        inflater=LayoutInflater.from(context);
        this.context=context;
        imageLoader= ImageLoader.getInstance();
        /*ShareSDK.initSDK(context);*/
    }

    public DynamicAdapter(Context context, List<Dynamic> list) {
        super();
        this.dynamicList = list;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    public void update(List<Dynamic> list) {
        this.dynamicList.clear();
        add(list);
    }

    public void add(List<Dynamic> list) {
        this.dynamicList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dynamicList==null?0:dynamicList.size();
    }

    @Override
    public Object getItem(int position) {
        return dynamicList==null?null:dynamicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Dynamic dy=dynamicList.get(position);
        if(dy.getType()==1){//商品
            view=inflater.inflate(R.layout.adapter_dynamic_item,parent,false);
            initA(view, dy, position);
        }else if(dy.getType()==2){
            view =inflater.inflate(R.layout.adapter_dynamic_activity,parent,false);
            initB(view,dy,position);
        }
        return view;

    }



    /**
     * 初始化控件A
     */
    private void initA(View view, final Dynamic dy, final int position){
        //店铺logo
        ImageView dy_shop_logo;
        //店铺名称
        TextView dy_shop_name;
        //商品名称
        TextView dy_pro_name;
        //商品图片
        ImageView dy_pro_image;
        //商品原价
        TextView dy_pro_order_price;
        //商品活动价格
        TextView dy_pro_sale_price;
        //店铺所在城市
        TextView dy_city_name;
        //商品点赞数
        TextView dy_total_good_num;
        final TextView dy_coll_pro;
        dy_shop_name=(TextView)view.findViewById(R.id.dynamic_item_icon_title);

        //分享
        LinearLayout fenxiang =(LinearLayout)view.findViewById(R.id.dynamic_item_share);

        priceEm=context.getResources().getText(R.string.price).toString();

        if(dy.getShopName()!=null){
            dy_shop_name.setText(dy.getShopName());
        }else{
            dy_shop_name.setText("脏数据，未填写");
        }
        dy_shop_logo=(ImageView)view.findViewById(R.id.dynamic_item_icon);
        if(dy.getShopLogo()!=null&&!"".equals(dy.getShopLogo())){
            imageLoader.displayImage(ConstantJiao.aliUrl + dy.getShopLogo() + "@61h_61w_0e", dy_shop_logo, options);
        }
        dy_coll_pro=(TextView) view.findViewById(R.id.dynamic_item_collect_btn);
        boolean isCollect =dy.isHasCollect();
        if(isCollect){
            dy_coll_pro.setText(R.string.dynamic_has_collect_btn);
        }else{
            dy_coll_pro.setText(R.string.dynamic_collect_btn);
        }
        dy_pro_name=(TextView)view.findViewById(R.id.dynamic_item_intro_title);
        if(dy.getProductName()!=null){
            dy_pro_name.setText(dy.getProductName());
        }else{
            dy_pro_name.setText("脏数据，未填写");
        }
        dy_pro_image=(ImageView)view.findViewById(R.id.dynamic_item_image);
        if(dy.getProductImage()!=null&&!"".equals(dy.getProductImage())){
            imageLoader.displayImage(ConstantJiao.aliUrl + dy.getProductImage() + "@!mobile-dynamic-bg", dy_pro_image, options);
        }
        dy_pro_order_price=(TextView)view.findViewById(R.id.dynamic_item_old_price_price);
        dy_pro_sale_price=(TextView)view.findViewById(R.id.dynamic_item_news_price);
        String orderPrice=NumberFormatUtil.conventToString1(dy.getOldPrice());
        if(dy.getOldPrice()!=0){
            dy_pro_order_price.setText(priceEm + orderPrice);
        }else{
            dy_pro_sale_price.setText(priceEm + orderPrice);
            dy_pro_order_price.setVisibility(View.GONE);
        }
        if(dy.getSalePrice()!=null&&dy.getSalePrice()!=0){
            String newPrice = NumberFormatUtil.conventToString1(dy.getSalePrice());
            dy_pro_order_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            dy_pro_sale_price.setText(priceEm + newPrice);
        }

        dy_city_name=(TextView)view.findViewById(R.id.dynamic_item_address_title);
        if(dy.getCityName()!=null&&!"".equals(dy.getCityName())){
            dy_city_name.setText(dy.getCityName());
        }
        dy_total_good_num=(TextView)view.findViewById(R.id.dynamic_item_good_title);
        if(dy.getTotalGoodNum()!=null){
            dy_total_good_num.setText(dy.getTotalGoodNum());
        }else {
            dy_total_good_num.setText("0");
        }

        //关注按键click事件
        dy_coll_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dynamic dy= dynamicList.get(position);
                if(dy.isHasCollect()){//已关注
                    dy.setHasCollect(false);
                    dy_coll_pro.setText(R.string.dynamic_has_collect_btn);
                    Toast.makeText(context, "取消关注商品成功", Toast.LENGTH_SHORT);
                    changeCollProStatus(1,dy);
                }else{//未关注
                    dy.setHasCollect(true);
                    dy_coll_pro.setText(R.string.dynamic_collect_btn);
                    Toast.makeText(context, "关注商品成功", Toast.LENGTH_SHORT);
                    changeCollProStatus(0, dy);
                }
                dynamicList.set(position,dy);
            }
        });

        //店铺logo点击进入店铺首页
        dy_shop_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Log.e("logo","logo"+position);
                Dynamic dy= dynamicList.get(position);
                Intent intent =new Intent(context,ShopIndexActivity.class);
                intent.putExtra("shopCode",dy.getShopCode());
                context.startActivity(intent);*/
            }
        });

        //店铺名点击进入店铺首页
        dy_shop_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Log.e("logo", "logo" + position);
                Dynamic dy= dynamicList.get(position);
                Intent intent =new Intent(context,ShopIndexActivity.class);
                intent.putExtra("shopCode", dy.getShopCode());
                context.startActivity(intent);*/
            }
        });

        //商品名进入商品详情页
        dy_pro_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Log.e("---->", "s商品详情" + position);
                Dynamic dy = dynamicList.get(position);
                Intent intent = new Intent(context, ProductMsgActivity.class);
                intent.putExtra("productCode", dy.getProductCode());
                context.startActivity(intent);*/
            }
        });

        //商品名进入商品详情页
        dy_pro_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Log.e("---->", "s商品详情" + position);
                Dynamic dy = dynamicList.get(position);
                Intent intent = new Intent(context, ProductMsgActivity.class);
                intent.putExtra("productCode", dy.getProductCode());
                context.startActivity(intent);*/
            }
        });

        //点击点赞数进入商品详情
        /*good_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("---->", "s商品详情" + position);
                Dynamic dy = dynamicList.get(position);
                Intent intent = new Intent(context, ProductMsgActivity.class);
                intent.putExtra("productCode", dy.getProductCode());
                context.startActivity(intent);
            }
        });*/

        //点击坐标跳转到店铺地图及周边详情
        /*dy_city_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("---->", "店铺地址地图展示" + position);
                Dynamic dy = dynamicList.get(position);
                MyApplication.mobileDao.findShopMapAdress(dy,context);
                Log.e("go shopMap","the success *******");
            }
        });*/

        //点击分享调用分享
        /*fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("---->", "店铺分享start" + position);
                Dynamic dynamic=dynamicList.get(position);
                OnekeyShare share = new OnekeyShare();
                share.setTitle("优商圈--你身边的平台");
                share.setText("还在苦苦的朋友圈发广告？还在苦恼您的店铺商品没有人知道？快来加入免费平台http://192.168.1.169:8080/qqt_up/detailPage/selectDetailProduct.htm?code=402881294c8ecc05014c8ed04e890000&shopCode=402881294c8e30b3014c8e374ea60000");
                share.setTitleUrl("http://192.168.1.169:8080/qqt_up/detailPage/selectDetailProduct.htm?code=402881294c8ecc05014c8ed04e890000&shopCode=402881294c8e30b3014c8e374ea60000");
                share.setImageUrl("http://125.oss-cn-beijing.aliyuncs.com/c5ae76c9d20c4459a1024242d4191e10.jpg");

                //设置精度
                share.setLongitude(113.372338f);*//*
                //设置是否是直接分享
                share.setSilent(false);
                share.show(context);
            }
        });*/


    }

    /**
     * 初始化控件b
     * @param view
     * @param dy
     * @param position
     */
    private void initB(View view,Dynamic dy,int position){
        ImageView activity_img=(ImageView)view.findViewById(R.id.dynamic_activity_image);
        imageLoader.displayImage(ConstantJiao.aliUrl+dy.getActivityImg()+"@303h_623w_2e",activity_img,options);
    }


    /**
     * 改变商品的收藏状态
     */
    public void changeCollProStatus(int status,Dynamic dy){
        String productCode = dy.getProductCode();

        //获取本地的baseId--这应该是唯一的，对于每台智能设备来说

        //根据baseId获得绑定的账号
        RequestParams params = new RequestParams();
        //params.put("userCode",userCode);
        params.put("productCode",productCode);
        params.put("status",status);
        HttpUtil.post(ConstantJiao.userCollProUrl, params, new JsonHttpResponseHandler() {
            //成功调用
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {

            }

            //失败调用
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable throwable) {
                Toast.makeText(context, "网络异常，请稍后重试", Toast.LENGTH_SHORT);
            }
        });
    }


}
