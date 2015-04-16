package com.micro.shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.micro.shop.R;

/**
 * 正在建设中碎片
 * 
 * @author B.B.D
 * 
 */
public class UnderConstructionFragment extends Fragment {
	private TextView mTvTitle;
	private String mStr;
	private static final String EXTRA_TITLE = "UnderConstructionFragment.title";

	public static UnderConstructionFragment instance(String title) {
		UnderConstructionFragment fragment = new UnderConstructionFragment();
		Bundle argumens = new Bundle();
		argumens.putString(EXTRA_TITLE, title);
		fragment.setArguments(argumens);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mStr = getArguments().getString(EXTRA_TITLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_underconstrucation,
				container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		mTvTitle = (TextView) view.findViewById(R.id.underConstruacteTitle);
		mTvTitle.setText(mStr);
	}
}
