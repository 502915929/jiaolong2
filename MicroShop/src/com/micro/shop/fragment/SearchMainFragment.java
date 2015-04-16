package com.micro.shop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.shop.R;

/**
 * 搜索主模块
 * 
 * @author B.B.D
 * 
 */
public class SearchMainFragment extends Fragment {
	@SuppressWarnings("unused")
	private EditText mEtSearch;
	private LinearLayout mLlMap;
	private TextView mMapText;
	private ImageView mMapIcon;
	private FragmentManager manager;
	private List<Fragment> fragments;
	private int index = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_main, container,
				false);
		initView(view);
		initData();
		return view;
	}

	private void initView(View view) {
		mEtSearch = (EditText) view.findViewById(R.id.search_input);
		mLlMap = (LinearLayout) view.findViewById(R.id.search_head_home);
		mMapIcon = (ImageView) view.findViewById(R.id.search_head_home_icon);
		mMapText = (TextView) view.findViewById(R.id.search_head_home_title);
	}

	private void initData() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new SearchFragment());
		fragments.add(new AdressFragment());
		manager = getChildFragmentManager();
		changeChildFragment(index);
		mLlMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (index == 0) {
					mMapIcon.setBackgroundResource(R.drawable.serach_list);
					mMapText.setText(R.string.search_list);
					index = 1;
				} else {
					mMapIcon.setBackgroundResource(R.drawable.serach_map);
					mMapText.setText(R.string.search_item_map);
					index = 0;
				}
				changeChildFragment(index);
			}
		});
	}

	public void changeChildFragment(int index) {
		FragmentTransaction ft = manager.beginTransaction();
		Fragment fragment = fragments.get(index);
		if (fragment.isAdded())
			fragment.onResume();
		else
			ft.add(R.id.search_main_content, fragment);
		for (int i = 0; i < fragments.size(); i++) {
			FragmentTransaction childFt = manager.beginTransaction();
			Fragment child = fragments.get(i);
			if (index == i) {
				childFt.show(child);
			} else {
				childFt.hide(child);
			}
			childFt.commit();
		}
		ft.commit();
	}
}
