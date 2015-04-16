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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.micro.shop.R;
import com.micro.shop.activity.ProductDetailActivity;
import com.micro.shop.activity.ShopMainActivity;
import com.micro.shop.adapter.DynamicAdapter;
import com.micro.shop.entity.Dynamic;
import com.micro.shop.entity.DynamicEntity;

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
	private int page = 1;


	private Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what == 1000) {
				onRefreshComplete();
				setProgressBarVisible(false);
				@SuppressWarnings("unchecked")
				List<Dynamic> list = (List<Dynamic>) msg.obj;
				if (adapter == null) {
					adapter = new DynamicAdapter(getActivity(), list);
					setAdapter(adapter);
				} else {
					if (page == 1) {
						adapter.update(list);
					} else {
						adapter.add(list);
					}
				}
			}
			return false;
		}
	});
	private PullToRefreshBase.OnRefreshListener2<ListView> mPlvRefleshListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			refreshView.getLoadingLayoutProxy(true, false).setPullLabel("上拉刷新");
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					page = 1;
					loaddata();
				}
			}, 1000);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			refreshView.getLoadingLayoutProxy(false, true).setPullLabel(
					"下拉查看更多");
			refreshView.getLoadingLayoutProxy(false, true).setReleaseLabel(
					"放开以加载");
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if (page + 1 <= 3) {
						++page;
						loaddata();
					} else {
						Toast.makeText(getActivity(), "没有更多内容了!",
								Toast.LENGTH_SHORT).show();
						mPlvList.onRefreshComplete();
					}
				}
			}, 1000);
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
		loaddata();
	}

	private void loaddata() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				final Message msg = Message.obtain();
				List<DynamicEntity> list = getData();
				msg.what = CODE;
				msg.obj = list;
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						handler.sendMessage(msg);
					}
				}, 2 * 2000);
			}
		}).start();
	}

	public List<DynamicEntity> getData() {
		List<DynamicEntity> list = new ArrayList<DynamicEntity>();
		DynamicEntity one = new DynamicEntity();
		one.setOnlyImage(false);
		list.add(one);
		DynamicEntity two = new DynamicEntity();
		two.setOnlyImage(true);
		list.add(two);
		return list;
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
