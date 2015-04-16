package com.micro.shop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.micro.shop.R;

/**
 * 上拉刷新、下拉加载ScrollView
 * 
 * @author B.B.D
 * 
 */
public class RefreshScrollViewFragment extends Fragment {
	private ProgressBar mBar;
	private PullToRefreshScrollView mPrScrollView;
	private LinearLayout mContainer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_refresh_scrollview,
				container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		mBar = (ProgressBar) view.findViewById(R.id.refresh_bar);
		mPrScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.refresh_scrollview);
		mContainer = (LinearLayout) view.findViewById(R.id.refresh_container);
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

}
