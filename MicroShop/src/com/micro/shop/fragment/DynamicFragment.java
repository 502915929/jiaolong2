package com.micro.shop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.R;
import com.micro.shop.activity.MainActivity;
import com.micro.shop.activity.ProductDetailActivity;
import com.micro.shop.activity.ShopMainActivity;
import com.micro.shop.adapter.DynamicAdapter;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.Dynamic;
import com.micro.shop.entity.DynamicEntity;
import com.micro.shop.net.HttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * 动态碎片
 *
 * @author B.B.D
 *
 */
public class DynamicFragment extends Fragment {
	public static final int PULL_UP_ONLY = 0;
	public static final int PULL_DOWN_ONLY = 1;
	public static final int PULL_BOTH = 2;
	public static final int CODE = 1000;
	public static final int MORE = 1001;

	private BaseAdapter mAdapter;
	private ProgressBar mPbLoadingBar;
	private PullToRefreshListView mPlvList;
	private DynamicAdapter adapter = null;
	private int page = 0;

	private Gson gson=new Gson();

	public DynamicFragment(){
	}


	private PullToRefreshBase.OnRefreshListener2<ListView> mPlvRefleshListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
		/**
		 * 下拉--顶部刷新数据
		 * @param refreshView
		 */
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			refreshView.getLoadingLayoutProxy(true, false).setPullLabel("上拉刷新");
			page=0;
			setProgressBarVisible(true);
			mPlvList.setRefreshing();
			getData(page + "", "5");
		}

		/**
		 * 上拉--底部加载更多
		 * @param refreshView
		 */
		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			refreshView.getLoadingLayoutProxy(false, true).setPullLabel(
					"下拉查看更多");
			refreshView.getLoadingLayoutProxy(false, true).setReleaseLabel(
					"放开以加载");
			++page;
			setProgressBarVisible(true);
			mPlvList.setRefreshing();
			getData((page * 5) + "", "5");

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dynamic, container,
				false);
		initView(rootView);
		return rootView;
	}

	private void initView(View rootView) {
		mPbLoadingBar = (ProgressBar) rootView
				.findViewById(R.id.dynamic_pb_loadingbar);

		mPlvList = (PullToRefreshListView) rootView
				.findViewById(R.id.dynamic_refresh_list_plv);
		setPullMode(PULL_BOTH);
		setOnRefreshListener(mPlvRefleshListener);
		adapter = new DynamicAdapter(getActivity());
		setAdapter(adapter);
		setProgressBarVisible(true);
		getData("0", "5");
	}



	public List<Dynamic> getData(final String start,String number) {
		RequestParams params = new RequestParams();
		params.add("start",start);
		params.add("number", number);
		HttpUtil.getClient().post(ConstantJiao.dynamicUrl, params, new JsonHttpResponseHandler() {
			//成功调用
			public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
				onRefreshComplete();
				setProgressBarVisible(false);
				List<Dynamic> list = gson.fromJson(array.toString(), new TypeToken<List<Dynamic>>() {
				}.getType());
				if(start.equals("0")){
					adapter.dynamicList = list;
					setPullMode(PULL_BOTH);
				}else{
					adapter.dynamicList.addAll(list);
					setPullMode(PULL_BOTH);
				}
				adapter.notifyDataSetChanged();

				if(list==null||list.size()==0){
					Toast.makeText(getActivity(), "没有更多内容了!",
							Toast.LENGTH_SHORT).show();
					setPullMode(PULL_UP_ONLY);
				}

			}

			//失败调用
			public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable throwable) {
				Toast.makeText(getActivity(), "网络异常，请稍后重试", Toast.LENGTH_SHORT);
			}
		});
		return DynamicAdapter.dynamicList;
	}

	public void setDividerDrawable(Drawable drawable) {
		mPlvList.setDividerDrawable(drawable);
	}

	public void setAdapter(BaseAdapter adapter) {
		mAdapter = adapter;
		mPlvList.setAdapter(mAdapter);
	}

	public BaseAdapter getAdapter() {
		return mAdapter;
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		mPlvList.setOnItemClickListener(listener);
	}

	/**
	 * 下拉刷新监听
	 *
	 * @param listener
	 */
	public void setOnRefreshListener(
			PullToRefreshBase.OnRefreshListener<ListView> listener) {
		mPlvList.setOnRefreshListener(listener);
	}

	/**
	 * 下拉刷新、上拉刷新监听(可以把上拉刷新监听实现方法中来处理成加载更多)
	 *
	 * @param listener
	 */
	public void setOnRefreshListener(
			PullToRefreshBase.OnRefreshListener2<ListView> listener) {
		mPlvList.setOnRefreshListener(listener);
	}

	/**
	 * 刷新完成
	 */
	public void onRefreshComplete() {
		if (mPlvList.isRefreshing()) {
			mPlvList.onRefreshComplete();
		}
	}

	/**
	 * 设置进度条
	 *
	 * @param visible
	 */
	public void setProgressBarVisible(boolean visible) {
		mPbLoadingBar.setVisibility(visible == true ? View.VISIBLE : View.GONE);
	}

	/**
	 * 设置刷新模式
	 *
	 * @param mode
	 */
	public void setPullMode(int mode) {

		switch (mode) {
			case PULL_UP_ONLY:
				mPlvList.setMode(Mode.PULL_FROM_START);
				break;
			case PULL_DOWN_ONLY:
				mPlvList.setMode(Mode.PULL_FROM_END);
				break;
			case PULL_BOTH:
				mPlvList.setMode(Mode.BOTH);
				break;
		}
	}


}
