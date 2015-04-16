package com.micro.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.micro.shop.R;
import com.micro.shop.fragment.DynamicFragment;
import com.micro.shop.fragment.LocalFragment;
import com.micro.shop.fragment.MyFragment;
import com.micro.shop.fragment.SearchMainFragment;
import com.micro.shop.view.ActionBottomBar;
import com.micro.shop.view.ActionBottomBar.OnActionBarClickListener;

/**
 * 主页
 * 
 * @author B.B.D
 * 
 */
public class MainActivity extends FragmentActivity {
	private ActionBottomBar bottomBar;
	private FragmentManager manager;
	private FragmentTabAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		bottomBar = (ActionBottomBar) findViewById(R.id.mainActionBottomBar);
	}

	private void initData() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new DynamicFragment());
		fragments.add(new LocalFragment());
		fragments.add(new SearchMainFragment());
		fragments.add(new MyFragment());
		manager = getSupportFragmentManager();
		adapter = new FragmentTabAdapter(manager, R.id.mainActionContent,
				fragments);
		bottomBar.changeStatue(0);
		bottomBar.setOnActionBarClickListener(new OnActionBarClickListener() {

			@Override
			public void onClick(int position) {
				adapter.onChange(position);
			}
		});
	}

	public class FragmentTabAdapter {
		private FragmentManager manager;
		private int resId;
		private int curreIndex;
		private List<Fragment> fragments;

		public FragmentTabAdapter(FragmentManager manager, int resId,
				List<Fragment> fragments) {
			super();
			this.manager = manager;
			this.resId = resId;
			this.fragments = fragments;
			// 默认显示第一页
			FragmentTransaction ft = manager.beginTransaction();
			ft.add(resId, fragments.get(0));
			ft.commit();
		}

		public int getCurreIndex() {
			return curreIndex;
		}

		public Fragment getCurrFragment() {
			return fragments.get(curreIndex);
		}

		public void onChange(int i) {
			FragmentTransaction ft = manager.beginTransaction();
			Fragment fragment = fragments.get(i);
			getCurrFragment().onPause(); // 暂停当前tab

			if (fragment.isAdded()) {
				fragment.onResume(); // 启动目标tab的onResume()
			} else {
				ft.add(resId, fragment);
			}
			showTab(i);
			ft.commit();
		}

		/**
		 * 切换tab
		 * 
		 * @param idx
		 */
		private void showTab(int idx) {
			for (int i = 0; i < fragments.size(); i++) {
				Fragment fragment = fragments.get(i);
				FragmentTransaction ft = manager.beginTransaction();
				if (idx == i) {
					ft.show(fragment);
				} else {
					ft.hide(fragment);
				}
				ft.commit();
			}
			curreIndex = idx; // 更新目标tab为当前tab
		}
	}
}
