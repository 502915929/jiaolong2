package com.micro.shop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.entity.SearchResult;

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
	List<Fragment> fragments;
	private int index = 0;

	public SearchFragment searchFragment;
	public AdressFragment adressFragment;

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
		searchFragment=new SearchFragment();
		adressFragment=new AdressFragment();
		fragments.add(searchFragment);
		fragments.add(adressFragment);
		manager = getChildFragmentManager();
		changeChildFragment(index,searchFragment.searchList);
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
				changeChildFragment(index, searchFragment.searchList);
			}
		});
	}

	public void changeMapData(List<SearchResult> searchResults){
		adressFragment.changeMapData(searchResults);
	}


	public void changeChildFragment(int index,List<SearchResult> searchResults) {
		Log.e("change------>",searchResults==null?"the list is null":searchResults.size()+"");
		int i=0;
		FragmentTransaction ft = manager.beginTransaction();
		Fragment fragment = fragments.get(index);
		FragmentTransaction childFt;
		Fragment child;
		if (fragment.isAdded()) {
			if(fragment instanceof  AdressFragment){
				adressFragment=(AdressFragment)fragment;
				adressFragment.onResume();
			}else{
				fragment.onResume();
			}
		}else {
			ft.add(R.id.search_main_content, fragment);
		}

		for (; i < fragments.size(); i++) {
			childFt = manager.beginTransaction();
			child = fragments.get(i);
			if (index == i) {
				if(index==1){
					Log.e("开始标记坐标", "" + searchResults.size());
					adressFragment.list=searchResults;
					adressFragment.clearMarker();
					adressFragment.clearRouteOverlay();
					getChildFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
						@Override
						public void onBackStackChanged() {
							Log.e("where","where");
						}
					});
				}
				childFt.show(child);

			} else {
				childFt.hide(child);
			}
			childFt.commit();
		}

		ft.commit();
	}

	public void changeWayFragment(int index) {
		mMapIcon.setBackgroundResource(R.drawable.serach_list);
		mMapText.setText(R.string.search_list);
		FragmentTransaction ft = manager.beginTransaction();
		Fragment fragment = fragments.get(index);
		this.index=1;
		int i=0;
		FragmentTransaction childFt;
		Fragment child;
		for (; i < fragments.size(); i++) {
			childFt = manager.beginTransaction();
			child = fragments.get(i);
			if (index == i) {
				childFt.show(child);
			} else {
				childFt.hide(child);
			}
			childFt.commit();
		}
		if (fragment.isAdded()) {
			if(fragment instanceof AdressFragment){
				AdressFragment adressFragment=(AdressFragment)fragment;
				adressFragment.onResume();
			}else{
				fragment.onResume();
			}
		}else {
			ft.add(R.id.search_main_content, fragment);
		}
		ft.commit();
	}


}
