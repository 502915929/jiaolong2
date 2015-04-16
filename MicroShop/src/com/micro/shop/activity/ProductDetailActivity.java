package com.micro.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.entity.AdEntity;
import com.micro.shop.view.AdvertisementView;
import com.micro.shop.view.AdvertisementView.OnImageClickListener;

/**
 * 商品详细页面
 * 
 * @author B.B.D
 * 
 */
public class ProductDetailActivity extends FragmentActivity {
	private TextView mTvOldPrice, mTvComments;
	private RelativeLayout mRlBack, mRlHome;
	private LinearLayout mLlCollect, mLlShop, mLlType;
	private AdvertisementView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_detail);
		initView();
		initData();
	}

	private void initView() {
		mTvOldPrice = (TextView) findViewById(R.id.product_old_price);
		mRlBack = (RelativeLayout) findViewById(R.id.product_head_back);
		mRlHome = (RelativeLayout) findViewById(R.id.product_head_home);
		mLlShop = (LinearLayout) findViewById(R.id.product_detail_shop);
		mLlCollect = (LinearLayout) findViewById(R.id.product_collect);
		mLlType = (LinearLayout) findViewById(R.id.product_detail_type);
		mTvComments = (TextView) findViewById(R.id.product_comments);
		adView = (AdvertisementView) findViewById(R.id.business_list_adv_atlas);
	}

	private void initData() {
		// ---------------------图集-------------------------
		List<AdEntity> dataList = new ArrayList<AdEntity>();
		dataList.add(new AdEntity("drawable://" + R.drawable.product_img, ""));
		dataList.add(new AdEntity("drawable://" + R.drawable.product_img, ""));
		dataList.add(new AdEntity("drawable://" + R.drawable.product_img, ""));
		dataList.add(new AdEntity("drawable://" + R.drawable.product_img, ""));
		dataList.add(new AdEntity("drawable://" + R.drawable.product_img, ""));
		if (adView.getDataCount() == 0) {
			adView.setData(this, dataList,
					AdvertisementView.STYLE_POINER_INDICATOR);
		}
		adView.setOnImageClickListener(new OnImageClickListener() {

			@Override
			public void onDoubleClickItem(int position) {

			}

			@Override
			public void onClickItem(int position) {
				// 点击图集的图片进入下一个模块
				startActivity(new Intent(ProductDetailActivity.this,
						ProductImageDetailActivity.class));
			}
		});
		// ---------------------图集-------------------------
		// 设置中划线
		mTvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		mRlBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mRlHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 分享按钮点击事件
			}
		});
		mLlCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 收藏按钮点击事件
			}
		});
		mTvComments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 查看所有评论点击事件
			}
		});
		mLlType.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 查看更多分类点击事件
			}
		});
		mLlShop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 商铺按钮点击事件
			}
		});
	}
}
