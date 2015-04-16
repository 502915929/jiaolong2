package com.micro.shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.micro.shop.R;

/**
 * 地图模块
 * 
 * @author B.B.D
 * 
 */
public class AdressFragment extends Fragment {
	private MapView mMapView = null;
	private TextView mProductDetail;
	private LinearLayout mRouteButton, mPhoneButton, mDianButton;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_address, container,
				false);
		initView(view);
		initData();
		return view;
	}

	private void initView(View view) {
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		mRouteButton = (LinearLayout) view.findViewById(R.id.search_item_route);
		mPhoneButton = (LinearLayout) view.findViewById(R.id.search_item_tel);
		mDianButton = (LinearLayout) view.findViewById(R.id.search_item_shop);
		mProductDetail = (TextView) view.findViewById(R.id.address_detail);
	}

	private void initData() {
		mProductDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 详细按钮事件
			}
		});
		mRouteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 线路点击事件
			}
		});
		mPhoneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 电话点击事件
			}
		});
		mDianButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 进店逛逛事件
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

}
