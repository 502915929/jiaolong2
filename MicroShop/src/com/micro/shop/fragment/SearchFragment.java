package com.micro.shop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.micro.shop.R;
import com.micro.shop.activity.ProductDetailActivity;
import com.micro.shop.constant.Order;
import com.micro.shop.view.OrderTextView;

/**
 * 搜索模块
 * 
 * @author B.B.D
 * 
 */
public class SearchFragment extends Fragment {
	private OrderTextView mOtvTime, mOtvFocus, mOtvPrice;
	private PullToRefreshListView mPsvPage;
	private ProgressBar mPrBar;
	private int CODE = 1000;
	private int MORE = 1001;
	private int page = 1;
	private SearchAdapter adapter;
	private Handler mHandler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			mPrBar.setVisibility(View.GONE);
			if (mPsvPage.isRefreshing())
				mPsvPage.onRefreshComplete();
			if (msg.what == CODE) {
				if (adapter == null)
					adapter = new SearchAdapter(getActivity());
				mPsvPage.setAdapter(adapter);
			}
			if (msg.what == MORE) {
				if (page == 1) {
					adapter.update();
				} else {
					adapter.addAll();
				}
			}
			return false;
		}
	});

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		initView(view);
		initData();
		sendData(CODE);
		return view;
	}

	private void initView(View view) {
		mPsvPage = (PullToRefreshListView) view
				.findViewById(R.id.search_psv_page);
		mPrBar = (ProgressBar) view.findViewById(R.id.search_bar);
		mOtvTime = (OrderTextView) view.findViewById(R.id.search_otv_time);
		mOtvFocus = (OrderTextView) view.findViewById(R.id.search_otv_focus);
		mOtvPrice = (OrderTextView) view.findViewById(R.id.search_otv_price);
	}

	private void initData() {
		mOtvTime.setText("5000米");
		mOtvFocus.setText("咖啡厅");
		mOtvPrice.setText("默认排序");
		// 距离排序点击事件
		mOtvTime.setOnTabClickListener(mTimeOrderClickListener);
		// 咖啡厅排序点击事件
		mOtvFocus.setOnTabClickListener(mFocusOrderClickListener);
		// 默认排序点击事件
		mOtvPrice.setOnTabClickListener(mPriceOrderClickListener);
		mPsvPage.setOnRefreshListener(mPageRefreshListener);
		mPsvPage.setMode(Mode.BOTH);
	}

	public void sendData(final int code) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Message message = Message.obtain();
						message.what = code;
						mHandler.sendMessage(message);
					}
				}, 2 * 1000);
			}
		}).start();
	}

	private PullToRefreshBase.OnRefreshListener2<ListView> mPageRefreshListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			refreshView.getLoadingLayoutProxy(true, false).setPullLabel("上拉刷新");
			// TODO 刷新列表
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					page = 1;
					sendData(MORE);
				}
			}, 1000);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			refreshView.getLoadingLayoutProxy(false, true).setPullLabel(
					"下拉查看更多");
			refreshView.getLoadingLayoutProxy(false, true).setReleaseLabel(
					"放开以加载");
			// TODO 加载更多
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (page + 1 <= 3) {
						sendData(MORE);
					} else {
						Toast.makeText(getActivity(), "没有更多内容了!",
								Toast.LENGTH_SHORT).show();
						mPsvPage.onRefreshComplete();
					}
				}
			}, 1000);
		}
	};
	private OrderTextView.OnTabClickListener mTimeOrderClickListener = new OrderTextView.OnTabClickListener() {
		@Override
		public void onClick(Order order) {
			if (mOtvFocus.isTabActivated()) {
				mOtvFocus.deactivate();
			}
			if (mOtvPrice.isTabActivated()) {
				mOtvPrice.deactivate();
			}
		}
	};
	private OrderTextView.OnTabClickListener mFocusOrderClickListener = new OrderTextView.OnTabClickListener() {
		@Override
		public void onClick(Order order) {
			if (mOtvTime.isTabActivated()) {
				mOtvTime.deactivate();
			}
			if (mOtvPrice.isTabActivated()) {
				mOtvPrice.deactivate();
			}
		}
	};
	private OrderTextView.OnTabClickListener mPriceOrderClickListener = new OrderTextView.OnTabClickListener() {
		@Override
		public void onClick(Order order) {
			if (mOtvTime.isTabActivated()) {
				mOtvTime.deactivate();
			}
			if (mOtvFocus.isTabActivated()) {
				mOtvFocus.deactivate();
			}
		}
	};

	public class SearchAdapter extends BaseAdapter {
		private Context context;
		private List<String> list;
		private LayoutInflater inflater;

		public SearchAdapter(Context context) {
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
		public View getView(int position, View view, ViewGroup parent) {
			if (view == null)
				view = inflater.inflate(R.layout.adapter_search_item, parent,
						false);
			LinearLayout mLlRoute = (LinearLayout) view
					.findViewById(R.id.search_item_route);
			LinearLayout mLlTel = (LinearLayout) view
					.findViewById(R.id.search_item_tel);
			LinearLayout mLlShop = (LinearLayout) view
					.findViewById(R.id.search_item_shop);
			mLlRoute.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 线路点击事件
				}
			});
			mLlTel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 电话点击事件
				}
			});
			mLlShop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 进店逛逛点击事件
				}
			});
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 列表点击事件
					startActivity(new Intent(context,
							ProductDetailActivity.class));
				}
			});
			return view;
		}

	}
}
