package com.micro.shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.micro.shop.R;
import com.micro.shop.activity.ShopMainActivity_;
import com.micro.shop.entity.SearchResult;
import com.micro.shop.view.AutoHeightViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图模块
 *
 * @author B.B.D
 *
 */
public class AdressFragment extends Fragment implements OnGetRoutePlanResultListener,BaiduMap.OnMapClickListener {
	public MapView mMapView = null;
	//private TextView mProductDetail;
	//private LinearLayout mRouteButton, mPhoneButton, mDianButton;


	//----------------百度地图相关-----------//
	public List<SearchResult> list;
	private double latitude;
	private double longitude;

	MapStatusUpdate u;
	Marker marker;
	InfoWindow mInfoWindow;
	OverlayOptions option;

	BaiduMap baiduMap;
	BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
	BitmapDescriptor bdDefault = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
	Button button;
	//---------------------------------//



	//*******************************************搜索相关***********************************************
	RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
	//浏览路线节点相关
	int nodeIndex = -1;//节点索引,供浏览节点时使用
	RouteLine route = null;
	OverlayManager routeOverlay = null;
	boolean useDefaultIcon = false;
	//**************************************************************************************************

	private String[] areas = new String[]{"驾车","公交", "步行"};
	List<Marker> markerList=new ArrayList<>();
	List<Button> buttonList=new ArrayList<>();

	//++++++++++++++viewAdapter+++++++++++++++++++++
	public AutoHeightViewPager viewPager;
	private List<Fragment> fragments;
	AddressBottomFragment bottomFragment;
	public PagerAdapter pagerAdapter;
	//++++++++++++++viewAdapter+++++++++++++++++++++

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_address, container,false);
		initView(view);
		drawMap();
		return view;
	}

	private void initView(View view) {
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		baiduMap=mMapView.getMap();
		/*mRouteButton = (LinearLayout) view.findViewById(R.id.search_item_route);
		mPhoneButton = (LinearLayout) view.findViewById(R.id.search_item_tel);
		mDianButton = (LinearLayout) view.findViewById(R.id.search_item_shop);*/
		//mProductDetail = (TextView) view.findViewById(R.id.address_detail);
		mMapView.showZoomControls(false);//是否展示地图缩放按键
		//mMapView.showScaleControl(false);//是否展示地图缩放级别

		//初始化viewPager
		viewPager=(AutoHeightViewPager)view.findViewById(R.id.address_bottom);
		fragments = new ArrayList<>();
		bottomFragment=new AddressBottomFragment();
		fragments.add(bottomFragment);

	}

	public void initViewPager() {
		pagerAdapter=new ViewPagerAdapter(getFragmentManager(),list);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {
			}

			@Override
			public void onPageSelected(int i) {
				Marker marker=markerList.get(i);
				Log.e("the viewPager value",marker.getTitle());
				LatLng latLng= new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
				clickMarker(marker);
				u = MapStatusUpdateFactory.newLatLng(latLng);
				baiduMap.animateMapStatus(u);
			}

			@Override
			public void onPageScrollStateChanged(int i) {
			}
		});
		pagerAdapter.notifyDataSetChanged();
	}


	/**
	 * 画图层
	 *
	 */
	public void drawMap(){
		/*baiduMap.clear();
		mMapView.invalidate();*/
		if(list!=null){
			initViewPager();
			int i=0;
			SearchResult result;
			for (;i<list.size();i++){
				result=list.get(i);
				latitude=result.getLatitude();
				longitude=result.getLongitude();
				//根据坐标画图层
				drawShop(result,i);
				Log.e("shopName","after change "+result.getShopName());

			}
			baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(14).build()));
			//覆盖物点击方法
			baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(final Marker marker) {
					clickMarker(marker);
					return true;
				}
			});
		}
	}

	/**
	 * 标注展示店铺地址方法
	 * @param marker
	 */
	public void clickMarker(final Marker marker){
		Log.e(marker.getTitle(), marker.getPosition().latitude + "," + marker.getPosition().longitude);
		button = new Button(getActivity());
		button.setBackgroundResource(R.drawable.location_shop_green);
		button.setText(marker.getTitle());
		for (Marker m : markerList) {
			if (m.getIcon()==bitmap) {
				m.setIcon(bdDefault);
			}
		}
		marker.setIcon(bitmap);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = ShopMainActivity_.intent(getActivity()).get();
				intent.putExtra("shopCode", marker.getExtraInfo().getString("shopCode"));
				AdressFragment.this.onPause();
				getActivity().startActivity(intent);
			}
		});
		buttonList.add(button);
		mInfoWindow = new InfoWindow(button, marker.getPosition(), -47);
		baiduMap.showInfoWindow(mInfoWindow);
	}


	/**
	 * 画图1
	 */
	public void drawShop(SearchResult result,int i){
		//定义Maker坐标点
		LatLng point = new LatLng(result.getLatitude(), result.getLongitude());
		//构建Marker图标
		//构建MarkerOption，用于在地图上添加Marker
		//BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
		if(i==0){
			option = new MarkerOptions().position(point).icon(bitmap);
			u = MapStatusUpdateFactory.newLatLng(point);
			baiduMap.animateMapStatus(u);
		}else{
			option = new MarkerOptions().position(point).icon(bdDefault);
		}
		//在地图上添加Marker，并显示
		marker=(Marker)baiduMap.addOverlay(option);
		marker.setTitle(result.getShopName());
		Bundle bundle=new Bundle();
		bundle.putString("shopCode", result.getShopCode());
		marker.setExtraInfo(bundle);
		markerList.add(marker);
	}

	/**
	 * 路线
	 */
	public void showWay(int goType,SearchResult result,Double latitude,Double longitude){
		this.latitude=latitude;
		this.longitude=longitude;
		Log.e("查询路线", latitude + "," + longitude + ">>>>" + result.getLatitude() + "," + result.getLongitude());

		//如果已经规划过路线，则清楚原路线
		clearRouteOverlay();
		//如果地图上有覆盖物对象，清除marker对象
		clearMarker();
		//baiduMap.clear();
		routeOverlay=null;
		//初始化搜索
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);

		//设置起终点信息，对于tranist search 来说，城市名无意义
		LatLng ll = new LatLng(latitude,longitude);
		PlanNode stNode = PlanNode.withLocation(ll);
		PlanNode enNode = PlanNode.withLocation(new LatLng(result.getLatitude(), result.getLongitude()));
		if(goType==0){
			mSearch.drivingSearch((new DrivingRoutePlanOption())
					.from(stNode)
					.to(enNode));
		} else if (goType == 1) {
			mSearch.transitSearch((new TransitRoutePlanOption())
					.from(stNode)
					.city("北京")
					.to(enNode));
		}else if(goType==2){
			mSearch.walkingSearch((new WalkingRoutePlanOption())
					.from(stNode)
					.to(enNode));
		}


	}


	/**
	 * 清除标注覆盖物
	 */
	public void clearMarker(){
		if(markerList!=null&&markerList.size()>0){
			for(Marker m:markerList){
				m.remove();
			}
			for(Button b:buttonList){
				b.setVisibility(View.GONE);
				b.setOnClickListener(null);
			}
			markerList.clear();
			buttonList.clear();
			mMapView.removeView(button);
			//mMapView.removeAllViews();
		}
	}

	/**
	 * 清除路线规划
	 */
	public void clearRouteOverlay(){
		if(routeOverlay!=null){
			routeOverlay.removeFromMap();
			mMapView.removeView(button);
		}
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

	/**
	 * 步行规划路线
	 * @param result
	 */
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result == null || result.error != com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(getActivity(), "抱歉，未找到结果,您可以尝试选择其他路线方式", Toast.LENGTH_SHORT).show();
		}
		if (result.error == com.baidu.mapapi.search.core.SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			//result.getSuggestAddrInfo()
			return;
		}
		if (result.error == com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			route = result.getRouteLines().get(0);
			Log.e("baiduMap", baiduMap + "****");
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);
			baiduMap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	/**
	 * 公交规划路线
	 * @param result
	 */
	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(latitude,longitude));
		baiduMap.animateMapStatus(u);
		if (result == null || result.error != com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(getActivity(), "抱歉，未找到结果，您可以尝试选择其他路线方式", Toast.LENGTH_SHORT).show();
		}
		if (result.error == com.baidu.mapapi.search.core.SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			//result.getSuggestAddrInfo()
			return;
		}
		if (result.error == com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			route = result.getRouteLines().get(0);
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(baiduMap);
			baiduMap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	/**
	 * 驾车规划路线
	 * @param result
	 */
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null || result.error != com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(getActivity(), "抱歉，未找到结果，您可以尝试选择其他路线方式", Toast.LENGTH_SHORT).show();
		}
		if (result.error == com.baidu.mapapi.search.core.SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			//result.getSuggestAddrInfo()
			return;
		}
		if (result.error == com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			route = result.getRouteLines().get(0);
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
			routeOverlay = overlay;
			baiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}


	@Override
	public void onMapClick(LatLng latLng) {
		baiduMap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi mapPoi) {
		return false;
	}


	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}


	private class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	//定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}


	public class ViewPagerAdapter extends FragmentStatePagerAdapter {
		private List<SearchResult> list;

		public ViewPagerAdapter(FragmentManager fm, List<SearchResult> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = new AddressBottomFragment();
			if(list!=null) {
				Bundle bundle = new Bundle();
				SearchResult re = list.get(arg0);
				bundle.putString("shopName", re.getShopName());
				bundle.putString("address", re.getAddress());
				bundle.putDouble("range", re.getRange());
				bundle.putInt("hotNum", re.getHotNum());
				bundle.putString("slogan", re.getShopSlogan());
				bundle.putDouble("latitude", re.getLatitude());
				bundle.putDouble("longitude", re.getLongitude());
				bundle.putString("shopLogo", re.getShopLogo());
				bundle.putString("shopCode", re.getShopCode());
				fragment.setArguments(bundle);
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return list==null?0:list.size();
		}

	}


	public void changeMapData(List<SearchResult> searchResults){
		list=searchResults;
		drawMap();
	}

}
