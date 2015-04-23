package com.micro.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.R;
import com.micro.shop.config.AppContext;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.AdEntity;
import com.micro.shop.entity.Product;
import com.micro.shop.entity.ProductDetail;
import com.micro.shop.entity.ProductImage;
import com.micro.shop.entity.ProductLabel;
import com.micro.shop.entity.ShopBase;
import com.micro.shop.entity.UserCommentPrudoct;
import com.micro.shop.fragment.ProductDetailFragment;
import com.micro.shop.net.HttpUtil;
import com.micro.shop.util.NumberFormatUtil;
import com.micro.shop.view.AdvertisementView;
import com.micro.shop.view.AdvertisementView.OnImageClickListener;

import org.androidannotations.annotations.EActivity;
import org.apache.http.Header;

/**
 * 商品详细页面
 *
 * @author B.B.D
 *
 */
public class ProductDetailActivity extends FragmentActivity
		implements OnClickListener {
	private TextView product_old_price, mTvComments;
	private RelativeLayout mRlBack, mRlHome;
	private LinearLayout mLlCollect, mLlShop, mLlType;
	private AdvertisementView adView;
	private RelativeLayout product_head;

	private TextView product_comments_one;
	private TextView product_comments_two;
	private TextView product_comments_three;

	private TextView product_name;
	private TextView product_news_price;
	private TextView label_one;
	private TextView label_two;
	private TextView label_three;
	private ImageView label_one_img;
	private ImageView label_two_img;
	private ImageView label_three_img;
	private LinearLayout label_view;

	private ImageView search_item_img;
	private TextView product_item_name;
	private TextView product_item_mark;
	Handler handler = new Handler();

	private TextView info_num;
	private TextView collect_num;
	private TextView good_num;
	private TextView comment_num;
	private ScrollView parent_scroll_line;

	Gson gson = new Gson();

	String productCode;
	List<ProductImage> imgList;
	List<String> labelList;
	List<UserCommentPrudoct> commentList;
	Product pro ;
	ShopBase shopBase;

	ProductDetail detail;
	String priceEm;
	Double salePrice;
	Double oldPrice;
	Runnable runnable;
	boolean handler_post;


	//*****************************************
	private List<Fragment> fragments;
	private TextView mTabOneTitle, mTabTwoTitle, mTabThreeTitle;
	private RelativeLayout mBackBtn, mHomeBtn, mTabOne, mTabTwo, mTabThree,
			mTabOneLine, mTabTwoLine, mTabThreeLine;
	private ViewPager mViewPager;
	private PopupWindow pop;

	private  ViewPagerAdapter pagerAdapter;
	//******************************************


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_detail);
		initView();
		initData();
		ajaxData();
	}


	private void initView() {
		product_old_price = (TextView) findViewById(R.id.product_old_price);
		mRlHome = (RelativeLayout) findViewById(R.id.product_head_home);
		mLlShop = (LinearLayout) findViewById(R.id.product_detail_shop);
		mLlCollect = (LinearLayout) findViewById(R.id.product_collect);
		mLlType = (LinearLayout) findViewById(R.id.product_detail_type);
		mTvComments = (TextView) findViewById(R.id.product_comments);
		adView = (AdvertisementView) findViewById(R.id.business_list_adv_atlas);
		mRlBack = (RelativeLayout) findViewById(R.id.product_head_back);

		product_news_price=(TextView)findViewById(R.id.product_news_price);
		product_name=(TextView)findViewById(R.id.product_name);
		label_one=(TextView)findViewById(R.id.label_one);
		label_two=(TextView)findViewById(R.id.label_two);
		label_three=(TextView)findViewById(R.id.label_three);
		label_view=(LinearLayout)findViewById(R.id.label_view);
		label_one_img=(ImageView)findViewById(R.id.label_one_img);
		label_two_img=(ImageView)findViewById(R.id.label_two_img);
		label_three_img=(ImageView)findViewById(R.id.label_three_img);

		product_comments_one=(TextView)findViewById(R.id.product_comments_one);
		product_comments_two=(TextView)findViewById(R.id.product_comments_two);
		product_comments_three=(TextView)findViewById(R.id.product_comments_three);

		search_item_img=(ImageView)findViewById(R.id.search_item_img);
		product_item_name=(TextView)findViewById(R.id.product_item_name);
		product_item_mark=(TextView)findViewById(R.id.product_item_mark);

		info_num=(TextView)findViewById(R.id.info_num);
		collect_num=(TextView)findViewById(R.id.collect_num);
		good_num=(TextView)findViewById(R.id.good_num);
		comment_num=(TextView)findViewById(R.id.comment_num);

		//*****************
		/*mBackBtn = (RelativeLayout) findViewById(R.id.shop_main_head_back);
		mHomeBtn = (RelativeLayout) findViewById(R.id.shop_main_head_home);*/
		mTabOne = (RelativeLayout) findViewById(R.id.product_img_tab_one);
		mTabTwo = (RelativeLayout) findViewById(R.id.product_img_tab_two);
		mTabThree = (RelativeLayout) findViewById(R.id.product_img_tab_three);
		mTabOneTitle = (TextView) findViewById(R.id.product_img_tab_one_title);
		mTabTwoTitle = (TextView) findViewById(R.id.product_img_tab_two_title);
		mTabThreeTitle = (TextView) findViewById(R.id.product_img_tab_three_title);
		mTabOneLine = (RelativeLayout) findViewById(R.id.product_img_tab_one_line);
		mTabTwoLine = (RelativeLayout) findViewById(R.id.product_img_tab_two_line);
		mTabThreeLine = (RelativeLayout) findViewById(R.id.product_img_tab_three_line);
		mViewPager = (ViewPager) findViewById(R.id.layout_adv_style_pointer_vp_page2);
		//*****************

		parent_scroll_line=(ScrollView)findViewById(R.id.parent_scroll_line);
		product_head=(RelativeLayout)findViewById(R.id.product_head);

	}

	private void initData() {
		priceEm=getResources().getText(R.string.price).toString();
		// 设置中划线
		product_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		mRlBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mRlHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 分享按钮点击事件
			}
		});
		mLlCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 收藏按钮点击事件
			}
		});
		mTvComments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 查看所有评论点击事件
			}
		});
		mLlType.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 查看更多分类点击事件
			}
		});
		mLlShop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 商铺按钮点击事件
			}
		});

		parent_scroll_line.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(product_head.getVisibility()==View.GONE){
					product_head.setVisibility(View.VISIBLE);
					runnable = new Runnable() {
						@Override
						public void run() {
							product_head.setVisibility(View.GONE);
						}
					};
					handler.postDelayed(runnable, 5000);
				}
				return false;
			}

		});
		//************************
		View view = LayoutInflater.from(this).inflate(
				R.layout.pop_product_content, null);
		// 创建PopupWindow对象
		pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, false);
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		// pop.setFocusable(true);
		fragments = new ArrayList<Fragment>();
		//ProductDetailFragment fragment =new ProductDetailFragment();
		fragments.add(new ProductDetailFragment());
		fragments.add(new ProductDetailFragment());
		fragments.add(new ProductDetailFragment());
		pagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(pagerAdapter);
		select(0);
		pagerAdapter.notifyDataSetChanged();


		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				//select(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

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

	public void ajaxData(){
		Intent intent = getIntent();
		if(intent.hasExtra("productCode")){
			productCode =intent.getExtras().getString("productCode");
			Log.e("coming in ajaxData","115-->"+productCode);
			RequestParams params = new RequestParams();
			params.put("productCode",productCode);
			params.put("userCode", AppContext.userCode);
			HttpUtil.getClient().post(ConstantJiao.showProDetailUrl, params, new BaseJsonHttpResponseHandler<ProductDetail>() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ProductDetail response) {
					detail=response;
					if (adView.getDataCount() == 0) {
						adView.setData(ProductDetailActivity.this, response.getImageList(),
								AdvertisementView.STYLE_POINER_INDICATOR);
					}
					adView.setOnImageClickListener(new OnImageClickListener() {

						@Override
						public void onDoubleClickItem(int position) {

						}

						@Override
						public void onClickItem(int position) {


						}
					});
					pro=response.getProduct();
					product_name.setText(pro.getProductName());
					salePrice = response.getProduct().getSalePrice();
					oldPrice =pro.getProductPrice();
					if(salePrice!=null&&salePrice>0){
						product_news_price.setText(priceEm+ NumberFormatUtil.conventToString(salePrice));
						product_old_price.setText(priceEm+NumberFormatUtil.conventToString(oldPrice));
					}else{
						product_news_price.setText(priceEm+NumberFormatUtil.conventToString(oldPrice));
						product_old_price.setVisibility(View.GONE);
					}
					labelList = response.getLabelList();
					if(labelList!=null&&labelList.size()>0){
						String str;
						int i;
						for(i=0;i<labelList.size();i++){
							str=labelList.get(i);
							if(i==0){
								label_one.setText(str);
								label_one_img.setVisibility(View.VISIBLE);
							}else if(i==1){
								label_two.setText(str);
								label_two_img.setVisibility(View.VISIBLE);
							}else if(i==2){
								label_three.setText(str);
								label_three_img.setVisibility(View.VISIBLE);
							}
						}

					}else{
						label_view.setVisibility(View.GONE);
					}
					commentList=response.getCommentProList();
					if(commentList!=null&&commentList.size()>0){
						mTvComments.setText("查看所有"+detail.getProCommentNum()+"条评论");
						product_comments_one.setText(commentList.get(0).getCommentContent());
						product_comments_two.setText(commentList.get(1).getCommentContent());
						product_comments_three.setText(commentList.get(2).getCommentContent());
					}else{
						mTvComments.setText("还没有任何评论...");
						product_comments_one.setVisibility(View.GONE);
						product_comments_two.setVisibility(View.GONE);
						product_comments_three.setVisibility(View.GONE);
					}
					shopBase=response.getShopBase();
					if(shopBase.getShopLogo()!=null&&!"".equals(shopBase.getShopLogo())){
						AppContext.getImageLoader().displayImage(ConstantJiao.aliUrl+shopBase.getShopLogo(),search_item_img);
					}
					product_item_name.setText(shopBase.getShopName());
					if(shopBase.getSlogan()!=null&&!"".equals(shopBase.getSlogan())){
						product_item_mark.setText("店铺心情："+shopBase.getSlogan());
					}
					info_num.setText(detail.getProInfoNum().toString());
					collect_num.setText(detail.getProCollectNum().toString());
					good_num.setText(detail.getProGoodNum().toString());
					comment_num.setText(detail.getProCommentNum().toString());

				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ProductDetail errorResponse) {
					Toast.makeText(ProductDetailActivity.this,"网络超时，请稍后再试",Toast.LENGTH_SHORT);
				}

				@Override
				protected ProductDetail parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
					Log.e("the result is --->",""+isFailure);
					return gson.fromJson(rawJsonData,ProductDetail.class);
				}
			});
		}
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
			return fragments==null?null: fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments==null?0:fragments.size();
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()){

		}
	}



}
