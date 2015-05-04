package com.micro.shop.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.R;
import com.micro.shop.adapter.SearchAdapter;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.constant.Order;
import com.micro.shop.entity.SearchResult;
import com.micro.shop.net.HttpUtil;
import com.micro.shop.util.BaiduUtil;
import com.micro.shop.view.OrderTextView;

import org.apache.http.Header;

/**
 * 搜索模块
 * 
 * @author B.B.D
 * 
 */
public class SearchFragment extends Fragment {
	private OrderTextView mOtvTime, mOtvFocus, mOtvPrice;
	private PullToRefreshListView mPsvPage;
	private ProgressBar mPrBar;
	private int page = 0;
	private SearchAdapter adapter;
	SearchMainFragment mainFragment;
	boolean isFirst=true;

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

	private double latitude;
	private double longitude;
	private String orderType="-1";
	private String orderStr="desc";
	List<SearchResult> searchList;
	//***********end*******************************************************************z


	Gson gson = new Gson();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		initView(view);

		mPsvPage.setMode(Mode.BOTH);
		//下拉执行
		mPsvPage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				if(page<=0){
				}else{
					page--;
					//下拉
					if(page==0){
						mPsvPage.setRefreshing();
						mPrBar.setVisibility(View.GONE);
						mPsvPage.setMode(Mode.PULL_FROM_END);
					}else{
						mPsvPage.setRefreshing();
						mPrBar.setVisibility(View.VISIBLE);
						mPsvPage.setMode(Mode.BOTH);
						mPsvPage.getLoadingLayoutProxy(true,false).setPullLabel("下拉加载上一页");
						mPsvPage.getLoadingLayoutProxy(true,false).setReleaseLabel("松开加载上一页");
						mPsvPage.getLoadingLayoutProxy(true,false).setLoadingDrawable(null);
					}
					ajaxData(latitude,longitude,(page*6)+"","6");
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				page++;
				//上拉
				//mPsvPage.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
				mPsvPage.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载下一页");
				mPsvPage.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载下一页");
				mPsvPage.getLoadingLayoutProxy(false,true).setLoadingDrawable(null);
				mPsvPage.setRefreshing();
				mPrBar.setVisibility(View.VISIBLE);
				mPsvPage.setMode(Mode.BOTH);
				ajaxData(latitude,longitude,(page*6)+"","6");
			}

		});

		initData();
		findLocation();//开始定位
		return view;
	}

	private void initView(View view) {
		mPsvPage = (PullToRefreshListView) view
				.findViewById(R.id.search_psv_page);
		mPrBar = (ProgressBar) view.findViewById(R.id.search_bar);
		mOtvTime = (OrderTextView) view.findViewById(R.id.search_otv_time);
		mOtvFocus = (OrderTextView) view.findViewById(R.id.search_otv_focus);
		mOtvPrice = (OrderTextView) view.findViewById(R.id.search_otv_price);
		adapter = new SearchAdapter(getActivity(),searchList,mainFragment);
		mPsvPage.setAdapter(adapter);
	}

	private void initData() {
		mOtvTime.setText("距离");
		mOtvFocus.setText("热度");
		mOtvPrice.setText("活动");
		// 1排序点击事件
		mOtvTime.setOnTabClickListener(mTimeOrderClickListener);
		// 2排序点击事件
		mOtvFocus.setOnTabClickListener(mFocusOrderClickListener);
		//3点击事件
		mOtvPrice.setOnTabClickListener(mPriceOrderClickListener);
	}

	public void ajaxData(final Double latitude,final Double longitude,String start,String number){
		RequestParams params = new RequestParams();
		params.put("latitude",latitude);
		params.put("longitude",longitude);
		params.put("start",start);
		params.put("number",number);
		params.put("orderType",orderType);
		params.put("orderStr",orderStr);
		HttpUtil.getClient().post(ConstantJiao.searchShopListUrl, params, new BaseJsonHttpResponseHandler<List<SearchResult>>() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<SearchResult> res) {
				mPrBar.setVisibility(View.GONE);
				Log.e("page is-->",page+"");
				searchList=res;
				adapter.list=searchList;
				adapter.longitude=longitude;
				adapter.latitude=latitude;
				//mPsvPage.getRefreshableView().setSelection(page*6);
				adapter.notifyDataSetChanged();
				mPsvPage.onRefreshComplete();
				if(res==null||res.size()<6){
					Toast.makeText(getActivity(), "没有更多内容了!", Toast.LENGTH_SHORT).show();
					mPsvPage.setMode(Mode.PULL_FROM_START);
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<SearchResult> errorResponse) {
				Toast.makeText(getActivity(),"网络出现问题，请稍后再试",Toast.LENGTH_SHORT);
			}

			@Override
			protected List<SearchResult> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				return gson.fromJson(rawJsonData, new TypeToken<List<SearchResult>>(){}.getType());
			}
		});
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
		BaiduUtil.baiduMapStatus(mLocClient);
	}

	private PullToRefreshBase.OnRefreshListener2<ListView> mPageRefreshListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			page--;
			if(page==-1){
				page=0;
			}
			mPsvPage.setRefreshing();
			mPrBar.setVisibility(View.VISIBLE);
			mPsvPage.setMode(Mode.BOTH);
			ajaxData(latitude, longitude, (page*6)+"", "6");
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			page++;
			refreshView.getLoadingLayoutProxy(false, true).setPullLabel(
					"上拉查看第" + (page+1) + "页");
			refreshView.getLoadingLayoutProxy(false, true).setReleaseLabel(
					"放开以加载");
			// TODO 加载更多
			mPsvPage.setRefreshing();
			mPrBar.setVisibility(View.VISIBLE);
			isFirst=false;
			ajaxData(latitude, longitude, (page*6)+"", "6");

		}
	};
	private OrderTextView.OnTabClickListener mTimeOrderClickListener = new OrderTextView.OnTabClickListener() {
		@Override
		public void onClick(Order order) {
			if (mOtvFocus.isTabActivated()) {
				mOtvFocus.deactivate();
			}
			if (mOtvPrice.isTabActivated()) {
				mOtvPrice.deactivate();
			}
			orderType="0";
			orderStr=order.getValue();
			ajaxData(latitude,longitude,"0","6");
		}
	};
	private OrderTextView.OnTabClickListener mFocusOrderClickListener = new OrderTextView.OnTabClickListener() {
		@Override
		public void onClick(Order order) {
			if (mOtvTime.isTabActivated()) {
				mOtvTime.deactivate();
			}
			if (mOtvPrice.isTabActivated()) {
				mOtvPrice.deactivate();
			}
			orderType="1";
			orderStr=order.getValue();
			ajaxData(latitude,longitude,"0","6");
		}
	};
	private OrderTextView.OnTabClickListener mPriceOrderClickListener = new OrderTextView.OnTabClickListener() {
		@Override
		public void onClick(Order order) {
			if (mOtvTime.isTabActivated()) {
				mOtvTime.deactivate();
			}
			if (mOtvFocus.isTabActivated()) {
				mOtvFocus.deactivate();
			}
			orderType="2";
			orderStr=order.getValue();
			ajaxData(latitude,longitude,"0","6");
		}
	};


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
			longitude = location.getLongitude();
			Log.e("定位坐标", "纬度=" + latitude + " 经度=" + longitude);
			mPsvPage.setMode(Mode.PULL_FROM_END);
			mPsvPage.setRefreshing();
			mPrBar.setVisibility(View.VISIBLE);
			ajaxData(latitude, longitude, page+"", "6");
		}

		public void onReceivePoi(BDLocation poiLocation) {
			Log.e("the location is------>", poiLocation.getProvince() + poiLocation.getCity() + poiLocation.getDistrict() + poiLocation.getStreet());
		}

	}

}
