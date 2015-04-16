package com.micro.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.fragment.ProductDetailFragment;

/**
 * 图文详细页面
 * 
 * @author B.B.D
 * 
 */
public class ProductImageDetailActivity extends FragmentActivity {
	private List<Fragment> fragments;
	private TextView mTabOneTitle, mTabTwoTitle, mTabThreeTitle;
	private RelativeLayout mBackBtn, mHomeBtn, mTabOne, mTabTwo, mTabThree,
			mTabOneLine, mTabTwoLine, mTabThreeLine;
	private ViewPager mViewPager;
	private PopupWindow pop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_image_detail);
		initView();
		initData();
	}

	private void initView() {
		mBackBtn = (RelativeLayout) findViewById(R.id.shop_main_head_back);
		mHomeBtn = (RelativeLayout) findViewById(R.id.shop_main_head_home);
		mTabOne = (RelativeLayout) findViewById(R.id.product_img_tab_one);
		mTabTwo = (RelativeLayout) findViewById(R.id.product_img_tab_two);
		mTabThree = (RelativeLayout) findViewById(R.id.product_img_tab_three);
		mTabOneTitle = (TextView) findViewById(R.id.product_img_tab_one_title);
		mTabTwoTitle = (TextView) findViewById(R.id.product_img_tab_two_title);
		mTabThreeTitle = (TextView) findViewById(R.id.product_img_tab_three_title);
		mTabOneLine = (RelativeLayout) findViewById(R.id.product_img_tab_one_line);
		mTabTwoLine = (RelativeLayout) findViewById(R.id.product_img_tab_two_line);
		mTabThreeLine = (RelativeLayout) findViewById(R.id.product_img_tab_three_line);
		mViewPager = (ViewPager) findViewById(R.id.layout_adv_style_pointer_vp_page);
	}

	private void initData() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.pop_product_content, null);
		// 创建PopupWindow对象
		pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		// pop.setFocusable(true);
		fragments = new ArrayList<Fragment>();
		fragments.add(new ProductDetailFragment());
		fragments.add(new ProductDetailFragment());
		fragments.add(new ProductDetailFragment());
		mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),
				fragments));
		select(0);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				select(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mHomeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 更多操作按钮
				if (pop.isShowing()) {
					// 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
					pop.dismiss();
				} else {
					// 显示窗口
					pop.showAsDropDown(v);
				}
			}
		});
		mTabOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				select(0);
			}
		});
		mTabTwo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				select(1);
			}
		});
		mTabThree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				select(2);
			}
		});
	}

	private void select(int index) {
		mViewPager.setCurrentItem(index);
		if (index == 0) {
			mTabOneTitle.setTextColor(Color.parseColor("#D0393B"));
			mTabTwoTitle.setTextColor(Color.parseColor("#000000"));
			mTabThreeTitle.setTextColor(Color.parseColor("#000000"));
			mTabOneLine.setVisibility(View.VISIBLE);
			mTabTwoLine.setVisibility(View.GONE);
			mTabThreeLine.setVisibility(View.GONE);
		} else if (index == 1) {
			mTabTwoTitle.setTextColor(Color.parseColor("#D0393B"));
			mTabOneTitle.setTextColor(Color.parseColor("#000000"));
			mTabThreeTitle.setTextColor(Color.parseColor("#000000"));
			mTabOneLine.setVisibility(View.GONE);
			mTabTwoLine.setVisibility(View.VISIBLE);
			mTabThreeLine.setVisibility(View.GONE);
		} else if (index == 2) {
			mTabOneTitle.setTextColor(Color.parseColor("#000000"));
			mTabTwoTitle.setTextColor(Color.parseColor("#000000"));
			mTabThreeTitle.setTextColor(Color.parseColor("#D0393B"));
			mTabOneLine.setVisibility(View.GONE);
			mTabTwoLine.setVisibility(View.GONE);
			mTabThreeLine.setVisibility(View.VISIBLE);
		}
	}

	public class ViewPagerAdapter extends FragmentStatePagerAdapter {
		private List<Fragment> fragments;

		public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}
}
