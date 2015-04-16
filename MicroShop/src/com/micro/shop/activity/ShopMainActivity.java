package com.micro.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.view.InnerGridView;

/**
 * 店铺首页
 * 
 * @author B.B.D
 * 
 */
public class ShopMainActivity extends Activity {
	private InnerGridView mInnerGridView;
	private ScrollView mScrollView;
	private LinearLayout mLlCollectContent, mLlSellContent;
	private RelativeLayout mRlBack, mRlHome, mRlTabCollect, mRlTabSell,
			mRlTabCollectLine, mRlTabSellLine, mRlPhoneBtn;
	private TextView mTvCollect, mTvTabCollect, mTvTabSell;
	private PopupWindow pop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_main);
		initView();
		initData();
	}

	private void initView() {
		mInnerGridView = (InnerGridView) findViewById(R.id.shop_main_innergridview);
		mScrollView = (ScrollView) findViewById(R.id.shop_main_scrollview);
		mRlBack = (RelativeLayout) findViewById(R.id.shop_main_head_back);
		mRlHome = (RelativeLayout) findViewById(R.id.shop_main_head_home);
		mTvCollect = (TextView) findViewById(R.id.shop_main_collect_btn);
		mRlTabCollect = (RelativeLayout) findViewById(R.id.shop_main_ban_collect_content);
		mRlTabSell = (RelativeLayout) findViewById(R.id.shop_main_ban_sell_content);
		mTvTabCollect = (TextView) findViewById(R.id.shop_main_ban_collect);
		mRlTabCollectLine = (RelativeLayout) findViewById(R.id.shop_main_ban_collect_line);
		mTvTabSell = (TextView) findViewById(R.id.shop_main_ban_sell);
		mRlTabSellLine = (RelativeLayout) findViewById(R.id.shop_main_ban_sell_line);
		mLlCollectContent = (LinearLayout) findViewById(R.id.shop_main_three_collect_content);
		mLlSellContent = (LinearLayout) findViewById(R.id.shop_main_three_sell_content);
		mRlPhoneBtn = (RelativeLayout) findViewById(R.id.shop_main_tel_btn);
	}

	private void initData() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.pop_shop_content, null);
		// 创建PopupWindow对象
		pop = new PopupWindow(view, dip2px(this, 180), dip2px(this, 100), false);
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mInnerGridView.setAdapter(new ShopMainGridViewAdapter(this));
		mInnerGridView.setNumColumns(2);
		mInnerGridView.setVerticalSpacing(20);
		mInnerGridView.setHorizontalSpacing(20);
		mInnerGridView.setGravity(Gravity.CENTER);
		mScrollView.smoothScrollTo(0, 0);
		// 默认收藏排行选中
		selectCollectContent();
		mRlBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mRlHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 分享点击事件
			}
		});
		mTvCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 收藏按钮点击事件
			}
		});
		mRlTabCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 收藏排行点击事件
				selectCollectContent();
			}
		});
		mRlTabSell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 销量排行点击事件
				selectSellContent();
			}
		});
		mRlPhoneBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 联系方式
				if (pop.isShowing()) {
					// 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
					pop.dismiss();
				} else {
					// 显示窗口
					int[] location = new int[2];
					mRlPhoneBtn.getLocationOnScreen(location);
					pop.showAtLocation(mRlPhoneBtn, Gravity.NO_GRAVITY,
							location[0], location[1] - pop.getHeight());
				}
			}
		});
	}

	/**
	 * 收藏排行
	 */
	private void selectCollectContent() {
		mTvTabCollect.setTextColor(Color.parseColor("#D0393B"));
		mTvTabSell.setTextColor(Color.parseColor("#000000"));
		mRlTabCollectLine.setVisibility(View.VISIBLE);
		mRlTabSellLine.setVisibility(View.GONE);
		mLlCollectContent.setVisibility(View.VISIBLE);
		mLlSellContent.setVisibility(View.GONE);
	}

	/**
	 * 销售排行
	 */
	private void selectSellContent() {
		mTvTabCollect.setTextColor(Color.parseColor("#000000"));
		mTvTabSell.setTextColor(Color.parseColor("#D0393B"));
		mRlTabCollectLine.setVisibility(View.GONE);
		mRlTabSellLine.setVisibility(View.VISIBLE);
		mLlCollectContent.setVisibility(View.GONE);
		mLlSellContent.setVisibility(View.VISIBLE);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public class ShopMainGridViewAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<String> list;

		public ShopMainGridViewAdapter(Context context) {
			super();
			this.context = context;
			this.list = new ArrayList<String>();
			this.list.add("1");
			this.list.add("1");
			this.list.add("1");
			this.list.add("1");
			this.list.add("1");
			this.list.add("1");
			this.inflater = LayoutInflater.from(this.context);

		}

		public void addAll() {
			this.list.add("1");
			this.list.add("1");
			this.list.add("1");
			this.list.add("1");
			this.list.add("1");
			this.list.add("1");
			notifyDataSetChanged();
		}

		public void update() {
			this.list.clear();
			addAll();
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup container) {
			if (view == null)
				view = inflater.inflate(R.layout.adapter_shop_main_item,
						container, false);
			// 列表点击操作
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(context,
							ProductDetailActivity.class));
				}
			});
			return view;
		}

	}
}
