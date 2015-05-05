package com.micro.shop.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.R;
import com.micro.shop.adapter.LocalFragmentAdapter;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.LocalData;
import com.micro.shop.net.HttpUtil;
import com.micro.shop.util.BaiduUtil;
import com.micro.shop.view.ActionHeadBar;
import com.micro.shop.view.AdvertisementView;
import com.micro.shop.view.InnerGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.apache.http.Header;

/**
 * 本地
 *
 * @author B.B.D
 *
 */
public class LocalFragment extends Fragment {
	private ProgressBar mBar;
	private PullToRefreshScrollView mPrScrollView;
	private LinearLayout mContainer;
	private ActionHeadBar headBar;
	private int page = 0;
	private AdvertisementView adView;
	private LocalFragmentAdapter adapter;


	Gson gson = new Gson();
	//图片控件
	DisplayImageOptions options;
	InnerGridView gridview;
	//************定位******************************************************************
	/**
	 * 定位的客户端
	 */
	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();

	/**
	 * 定位选项
	 */
	LocationClientOption option;
	boolean isFirstLoc = true;// 是否首次定位

	private double latitude;
	private double longitude;
	private List<LocalData> myList;
	//***********end*******************************************************************


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}



	public void init(){
		headBar.setTitle("本地");
		// ---------------------九宫格-------------------------
		gridview = new InnerGridView(getActivity());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		lp.setMargins(10, 10, 10, 10);
		gridview.setLayoutParams(lp);
		gridview.setNumColumns(2);
		gridview.setVerticalSpacing(10);
		gridview.setHorizontalSpacing(10);
		gridview.setGravity(Gravity.CENTER);
		getContentView().addView(gridview);
		mPrScrollView.setMode(Mode.BOTH);
		mPrScrollView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						refreshView.getLoadingLayoutProxy(true, false)
								.setPullLabel("下拉刷新");
						page = 0;
						mPrScrollView.setRefreshing();
						setProgressBarVisible(true);
						mPrScrollView.setMode(Mode.BOTH);
						ajaxData(latitude, longitude, page + "", "6");
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						refreshView.getLoadingLayoutProxy(false, true)
								.setPullLabel("上拉查看更多");
						refreshView.getLoadingLayoutProxy(false, true)
								.setReleaseLabel("放开以加载");
						// TODO 加载更多
						++page;
						setProgressBarVisible(true);
						mPrScrollView.setRefreshing();
						ajaxData(latitude, longitude, (page * 6) + "", "6");
					}

				});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_local, container, false);
		initView(view);
		init();
		findLocation();
		return view;
	}

	private void initView(View view) {
		mBar = (ProgressBar) view.findViewById(R.id.refresh_bar);
		mPrScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.refresh_scrollview);
		mContainer = (LinearLayout) view.findViewById(R.id.refresh_container);
		headBar = (ActionHeadBar) view.findViewById(R.id.logo_headbar);
		adView = (AdvertisementView) view
				.findViewById(R.id.business_list_adv_atlas);
	}

	public void setProgressBarVisible(boolean visible) {
		mBar.setVisibility(visible == true ? View.VISIBLE : View.GONE);
	}

	/**
	 * 下拉刷新监听
	 *
	 * @param listener
	 */
	public void setOnRefreshListener(
			PullToRefreshBase.OnRefreshListener<ScrollView> listener) {
		mPrScrollView.setOnRefreshListener(listener);
	}

	/**
	 * 主体视图
	 *
	 * @return
	 */
	public LinearLayout getContentView() {
		return mContainer;
	}

	/**
	 * 下拉刷新、上拉刷新监听(可以把上拉刷新监听实现方法中来处理成加载更多)
	 *
	 * @param listener
	 */
	public void setOnRefreshListener(
			PullToRefreshBase.OnRefreshListener2<ScrollView> listener) {
		mPrScrollView.setOnRefreshListener(listener);
	}

	/**
	 * 刷新完成
	 */
	public void onRefreshComplete() {
		mPrScrollView.onRefreshComplete();
	}


	/**
	 * 开始定位
	 */
	public void findLocation(){
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(myListener);
		option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09l1"); // 设置坐标类型
		option.setScanSpan(500);//当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。
		mLocClient.setLocOption(option);
		Log.e("the local is---->", "" + mLocClient.isStarted());
		BaiduUtil.baiduMapStatus(mLocClient);
	}

	public void ajaxData(double latitude,double longitude, final String start,String number){
		RequestParams params= new RequestParams();
		params.put("latitude",latitude);
		params.put("longitude",longitude);
		params.add("start", start);
		params.add("number", number);
		HttpUtil.getClient().post(ConstantJiao.localProListUrl, params, new BaseJsonHttpResponseHandler<List<LocalData>>() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<LocalData> localList) {
				setProgressBarVisible(false);
				mPrScrollView.onRefreshComplete();
				if(start.equals("0")){
					myList=localList;
				}else{
					myList.addAll(localList);
				}
				adapter = new LocalFragmentAdapter(getActivity(),myList);
				gridview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				if(localList==null||localList.size()==0){
					Toast.makeText(getActivity(), "没有更多内容了!", Toast.LENGTH_SHORT).show();
					mPrScrollView.setMode(Mode.PULL_FROM_START);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<LocalData> errorResponse) {
				Toast.makeText(getActivity(), "网络异常，请稍后重试", Toast.LENGTH_SHORT);
			}

			@Override
			protected List<LocalData> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				return gson.fromJson(rawJsonData,new TypeToken<List<LocalData>>(){}.getType());
			}
		});

	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null ) {
				return;
			}
			latitude=location.getLatitude();
			longitude=location.getLongitude();
			Log.e("定位坐标","纬度="+latitude+" 经度="+longitude);
			ajaxData(latitude, longitude, "0", "6");
		}

	}


}
