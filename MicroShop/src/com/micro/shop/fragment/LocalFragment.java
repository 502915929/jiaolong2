package com.micro.shop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.micro.shop.R;
import com.micro.shop.activity.ProductDetailActivity;
import com.micro.shop.entity.AdEntity;
import com.micro.shop.entity.ProductImage;
import com.micro.shop.view.ActionHeadBar;
import com.micro.shop.view.AdvertisementView;
import com.micro.shop.view.AdvertisementView.OnImageClickListener;
import com.micro.shop.view.InnerGridView;

/**
 * 本地
 * 
 * @author B.B.D
 * 
 */
public class LocalFragment extends Fragment {
	private static final int CODE = 1000;
	private static final int MORE = 1001;
	private ProgressBar mBar;
	private PullToRefreshScrollView mPrScrollView;
	private LinearLayout mContainer;
	private ActionHeadBar headBar;
	private int page = 1;
	private AdvertisementView adView;
	private LocalFragmentAdapter adapter;
	private Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			setProgressBarVisible(false);
			if (msg.what == CODE) {

				headBar.setTitle("本地");
				// ---------------------九宫格-------------------------
				InnerGridView gridview = new InnerGridView(getActivity());
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
				lp.setMargins(10, 10, 10, 10);
				gridview.setLayoutParams(lp);
				getContentView().addView(gridview);
				adapter = new LocalFragmentAdapter(getActivity());
				gridview.setAdapter(adapter);
				gridview.setNumColumns(2);
				gridview.setVerticalSpacing(10);
				gridview.setHorizontalSpacing(10);
				gridview.setGravity(Gravity.CENTER);
				// ---------------------九宫格-------------------------
				// ---------------------图集-------------------------
				List<ProductImage> dataList = new ArrayList<ProductImage>();
				dataList.add(new ProductImage("drawable://" + R.drawable.banner1,
						""));
				dataList.add(new ProductImage("drawable://" + R.drawable.banner2,
						""));
				dataList.add(new ProductImage("drawable://" + R.drawable.banner3,
						""));
				dataList.add(new ProductImage("drawable://" + R.drawable.banner4,
						""));
				dataList.add(new ProductImage("drawable://" + R.drawable.banner5,
						""));
				if (adView.getDataCount() == 0) {
					adView.setData(getActivity(), dataList,
							AdvertisementView.STYLE_POINER_INDICATOR);
				}
				adView.setOnImageClickListener(new OnImageClickListener() {

					@Override
					public void onDoubleClickItem(int position) {

					}

					@Override
					public void onClickItem(int position) {
						// 点击图集的图片进入下一个模块
					}
				});
				// ---------------------图集-------------------------
				// ---------------------上拉刷新,下拉加载设置-------------------------
				mPrScrollView.setMode(Mode.BOTH);
				mPrScrollView
						.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

							@Override
							public void onPullDownToRefresh(
									PullToRefreshBase<ScrollView> refreshView) {
								refreshView.getLoadingLayoutProxy(true, false)
										.setPullLabel("上拉刷新");
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										page = 1;
										sendData(MORE);
									}
								}, 1000);
							}

							@Override
							public void onPullUpToRefresh(
									PullToRefreshBase<ScrollView> refreshView) {
								refreshView.getLoadingLayoutProxy(false, true)
										.setPullLabel("下拉查看更多");
								refreshView.getLoadingLayoutProxy(false, true)
										.setReleaseLabel("放开以加载");
								// TODO 加载更多
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										if (page + 1 <= 3) {
											sendData(MORE);
											++page;
										} else {
											Toast.makeText(getActivity(),
													"没有更多内容了!",
													Toast.LENGTH_SHORT).show();
											mPrScrollView.onRefreshComplete();
										}
									}
								}, 1000);
							}

						});
				// ---------------------上拉刷新,下拉加载设置-------------------------
			}
			if (msg.what == MORE) {
				if (mPrScrollView.isRefreshing())
					onRefreshComplete();
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
		sendData(CODE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_local, container, false);
		initView(view);
		return view;
	}

	public void sendData(final int code) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Message message = Message.obtain();
						message.what = code;
						handler.sendMessage(message);
					}
				}, 2 * 1000);
			}
		}).start();
	}

	private void initView(View view) {
		mBar = (ProgressBar) view.findViewById(R.id.refresh_bar);
		mPrScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.refresh_scrollview);
		mContainer = (LinearLayout) view.findViewById(R.id.refresh_container);
		headBar = (ActionHeadBar) view.findViewById(R.id.logo_headbar);
		adView = (AdvertisementView) view
				.findViewById(R.id.business_list_adv_atlas);
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

	public class LocalFragmentAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<String> list;

		public LocalFragmentAdapter(Context context) {
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
				view = inflater.inflate(R.layout.adapter_local_item, container,
						false);
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
