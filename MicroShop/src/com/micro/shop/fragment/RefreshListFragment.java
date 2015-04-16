package com.micro.shop.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.micro.shop.R;

/**
 * 下拉刷新上拉加载Fragment
 * 
 * @author B.B.D
 * 
 */
public class RefreshListFragment extends Fragment {

	public static final int PULL_UP_ONLY = 0;
	public static final int PULL_DOWN_ONLY = 1;
	public static final int PULL_BOTH = 2;

	private BaseAdapter mAdapter;
	private ProgressBar mPbLoadingBar;
	private PullToRefreshListView mPlvList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_refresh_list,
				container, false);
		initView(rootView);
		return rootView;
	}

	private void initView(View rootView) {
		mPbLoadingBar = (ProgressBar) rootView
				.findViewById(R.id.fragment_refresh_list_pb_loadingbar);

		mPlvList = (PullToRefreshListView) rootView
				.findViewById(R.id.fragment_refresh_list_plv);
		mPlvList.setMode(Mode.DISABLED);
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
		mPlvList.onRefreshComplete();
	}

	/**
	 * 设置进度条
	 * 
	 * @param visible
	 */
	public void setProgressBarVisible(boolean visible) {
		mPbLoadingBar.setVisibility(visible ? View.VISIBLE : View.GONE);
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
