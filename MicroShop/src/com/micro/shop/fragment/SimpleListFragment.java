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

import com.micro.shop.R;

public class SimpleListFragment extends Fragment {
	private BaseAdapter mAdapter;
	private ProgressBar mPbLoadingBar;
	private ListView mLvList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_simple_list,
				container, false);
		initView(rootView);
		return rootView;
	}

	private void initView(View rootView) {
		mPbLoadingBar = (ProgressBar) rootView
				.findViewById(R.id.fragment_simple_list_pb_loadingbar);

		mLvList = (ListView) rootView
				.findViewById(R.id.fragment_simple_list_lv);
	}

	public void setDividerDrawable(Drawable divider) {
		mLvList.setDivider(divider);
	}

	public void setDividerHeight(int height) {
		mLvList.setDividerHeight(height);
	}

	public void setAdapter(BaseAdapter adapter) {
		mAdapter = adapter;
		mLvList.setAdapter(mAdapter);
	}

	public BaseAdapter getAdapter() {
		return mAdapter;
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		mLvList.setOnItemClickListener(listener);
	}

	public void setProgressBarVisible(boolean visible) {
		mPbLoadingBar.setVisibility(visible == true ? View.VISIBLE : View.GONE);
	}
}
