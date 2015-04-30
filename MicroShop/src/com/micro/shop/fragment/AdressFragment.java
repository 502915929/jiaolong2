package com.micro.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.poi.PoiResult;
import com.micro.shop.R;
import com.micro.shop.activity.ShopMainActivity_;
import com.micro.shop.entity.SearchResult;

import java.util.ArrayList;
import java.util.List;

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

	List<SearchResult> list;
	private double latitude;
	private double longitude;

	LatLng point;
	BitmapDescriptor bitmap;
	OverlayOptions option;
	MapStatusUpdate u;
	Marker marker;
	InfoWindow mInfoWindow;
	BitmapDescriptor bdDefault = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
	BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.dynamic_address);

	List<Marker> markerList=new ArrayList<Marker>();

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_address, container,
				false);
		initView(view);
		initData();
		drawMap(list);
		return view;
	}

	private void initView(View view) {
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		mRouteButton = (LinearLayout) view.findViewById(R.id.search_item_route);
		mPhoneButton = (LinearLayout) view.findViewById(R.id.search_item_tel);
		mDianButton = (LinearLayout) view.findViewById(R.id.search_item_shop);
		mProductDetail = (TextView) view.findViewById(R.id.address_detail);
	}


	/**
	 * 画图层
	 * @param list
	 */
	public void drawMap(List<SearchResult> list){
		Log.e("aaa",mMapView==null?"true":"false");
		Log.e("aaa", mMapView.getMap() == null ? "true" : "false");
		//mMapView.getMap().clear();
		if(list!=null){
			int i=0;
			SearchResult result;
			for (;i<list.size();i++){
				result=list.get(i);
				latitude=result.getLatitude();
				longitude=result.getLongitude();
				//根据坐标画图层
				drawShop(result);
			}
			mMapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(16).build()));
			//覆盖物点击方法
			mMapView.getMap().setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(final Marker marker) {
					Log.e(marker.getTitle(), marker.getPosition().latitude + "," + marker.getPosition().latitude);
					Button button = new Button(getActivity());
					button.setBackgroundResource(R.drawable.location_shop_green);
					button.setText(marker.getTitle());
					marker.setIcon(bd);
					button.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							Intent intent = ShopMainActivity_.intent(getActivity()).get();
							intent.putExtra("shopCode", marker.getExtraInfo().getString("shopCode"));
							Fragment fragment=AdressFragment.this;
							fragment.onPause();
							getActivity().startActivity(intent);
						}
					});
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(button, ll, -47);
					mMapView.getMap().showInfoWindow(mInfoWindow);
					return true;
				}
			});
			u = MapStatusUpdateFactory.newLatLng(point);
			mMapView.getMap().animateMapStatus(u);
		}
	}


	/**
	 * 画图1
	 */
	public void drawShop(SearchResult result){
		//定义Maker坐标点
		point = new LatLng(result.getLatitude(), result.getLatitude());
		//构建Marker图标
		//构建MarkerOption，用于在地图上添加Marker
		option = new MarkerOptions().position(point).icon(bdDefault);
		//在地图上添加Marker，并显示
		marker=(Marker)mMapView.getMap().addOverlay(option);
		marker.setTitle(result.getShopName());
		Bundle bundle=new Bundle();
		bundle.putString("shopCode",result.getShopCode());
		marker.setExtraInfo(bundle);
		markerList.add(marker);
	}


	public void clearMarker(){
		this.markerList.clear();
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
