package com.micro.shop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.micro.shop.R;
import com.micro.shop.view.InnerGridView;

/**
 * 用于嵌套可滚动控件内部的GridViewFragment,根据子视图高度自动修正自身高度
 * 
 * @author B.B.D
 * 
 */
public class InnerGridViewFragment extends Fragment {
	private ProgressBar mBar;
	private InnerGridView mGridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_innergridview,
				container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		mBar = (ProgressBar) view.findViewById(R.id.inner_gridview_bar);
		mGridView = (InnerGridView) view
				.findViewById(R.id.inner_gridview_content);
	}

	public void setAdapter(BaseAdapter adapter) {
		mGridView.setAdapter(adapter);
	}

	public void setSize(int coulumn, int space) {
		mGridView.setNumColumns(coulumn);
		mGridView.setHorizontalSpacing(space);
		mGridView.setVerticalSpacing(space);
	}

	public void setProgressBarVisible(int v) {
		mBar.setVisibility(v);
	}
}
