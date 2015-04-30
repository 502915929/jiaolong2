package com.micro.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.R;
import com.micro.shop.adapter.ShopMainGridViewAdapter;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.Product;
import com.micro.shop.entity.ShopBase;
import com.micro.shop.entity.ShopIndex;
import com.micro.shop.net.HttpUtil;
import com.micro.shop.util.NumberFormatUtil;
import com.micro.shop.view.InnerGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 店铺首页
 *
 * @author B.B.D
 *
 */
@EActivity
public class ShopMainActivity extends Activity implements OnClickListener{

	@ViewById(R.id.shop_main_innergridview)
	InnerGridView mInnerGridView;

	@ViewById(R.id.shop_main_scrollview)
	ScrollView mScrollView;

	@ViewById(R.id.shop_main_three_collect_content)
	LinearLayout mLlCollectContent;

	@ViewById(R.id.shop_main_three_sell_content)
	LinearLayout mLlSellContent;

	@ViewById(R.id.shop_main_head_back)
	RelativeLayout mRlBack;

	@ViewById(R.id.shop_main_head_home)
	RelativeLayout mRlHome;

	@ViewById(R.id.shop_main_ban_collect_content)
	RelativeLayout mRlTabCollect;

	@ViewById(R.id.shop_main_ban_sell_content)
	RelativeLayout mRlTabSell;

	@ViewById(R.id.shop_main_ban_collect_line)
	RelativeLayout mRlTabCollectLine;

	@ViewById(R.id.shop_main_ban_sell_line)
	RelativeLayout mRlTabSellLine;

	@ViewById(R.id.shop_main_tel_btn)
	RelativeLayout mRlPhoneBtn;

	@ViewById(R.id.shop_main_ban_collect)
	TextView mTvTabCollect;

	@ViewById(R.id.shop_main_ban_sell)
	TextView mTvTabSell;

	PopupWindow pop;

	//jiaolong add
	@ViewById(R.id.shop_main_img)
	ImageView shop_logo;

	@ViewById(R.id.shop_main_name)
	TextView shop_name;

	@ViewById(R.id.shop_slogan)
	TextView shop_slogan;
	//用户收藏与取消收藏按键
	@ViewById(R.id.shop_main_collect_btn)
	TextView mTvCollect;

	@ViewById(R.id.shop_main_background)
	ImageView shop_background;

	@ViewById(R.id.shop_pro_num)
	TextView shop_pro_num;

	@ViewById(R.id.shop_hot_num)
	TextView shop_hot_num;

	@ViewById(R.id.shop_activity_num)
	TextView shop_activity_num;

	@ViewById(R.id.shop_collect_num)
	TextView shop_collect_num;

	@ViewById(R.id.shop_top_one_image)
	ImageView shop_top_one;

	@ViewById(R.id.shop_top_one_sale_price)
	TextView shop_top_one_sale_price;

	@ViewById(R.id.shop_top_two_image)
	ImageView shop_top_two;

	@ViewById(R.id.shop_top_two_sale_price)
	TextView shop_top_two_sale_price;

	@ViewById(R.id.shop_top_three_image)
	ImageView shop_top_three;

	@ViewById(R.id.shop_top_three_sale_price)
	TextView shop_top_three_sale_price;

	@ViewById
	ImageView info_one_image;
	@ViewById
	TextView info_one_price;
	@ViewById
	ImageView info_two_image;
	@ViewById
	TextView info_two_price;
	@ViewById
	ImageView info_three_image;
	@ViewById
	TextView info_three_price;

	String shopCode;
	List<Product> topInfoList;
	List<Product> topCollectList;
	List<Product> likeList;
	ShopBase shopBase;
	Long totalProNum;
	Long totalActivityNum;
	Long totalCollectNum;
	Long hotNum;
	String priceEm;

	Gson gson=new Gson();
	//图片控件
	DisplayImageOptions options;
	ImageLoader imageLoader;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_main);
	}

	@AfterViews
	public void initData() {
		imageLoader= ImageLoader.getInstance();
		View view = LayoutInflater.from(this).inflate(
				R.layout.pop_shop_content, null);
		priceEm=ShopMainActivity.this.getResources().getText(R.string.price).toString();
		// 创建PopupWindow对象
		pop = new PopupWindow(view, dip2px(this, 180), dip2px(this, 100), false);
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		initAjax();

		// 默认收藏排行选中
		selectCollectContent();
	}


	/**
	 * 点击返回上一页
	 */
	@Click(R.id.shop_main_head_back)
	void mRlBackClick(){
		finish();
	}

	/**
	 * 点击分享按键
	 */
	@Click((R.id.shop_main_head_home))
	void mRlHomeClick(){

	}

	/**
	 * 点击收藏按键
	 */
	@Click(R.id.shop_main_collect_btn)
	void mTvCollectClick(){

	}


	/**
	 * 点击收藏排行
	 */
	@Click(R.id.shop_main_ban_collect_content)
	void mRlTabCollectClick(){
		selectCollectContent();
	}

	/**
	 * 点击访问排行
	 */
	@Click(R.id.shop_main_ban_sell_content)
	void mRlTabSellClick(){
		selectSellContent();
		infoTopList();
	}

	/**
	 * 点击联系方式
	 */
	@Click(R.id.shop_main_tel_btn)
	void mRlPhoneBtnClick(){
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
	 * 访问排行
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


	/**
	 * 加载网络数据
	 */
	public void initAjax(){
		Intent intent= getIntent();
		if(intent.hasExtra("shopCode")){
			RequestParams params=new RequestParams();
			shopCode=intent.getExtras().getString("shopCode");
			//userCode暂时用随机
			String userCode="123456789";
			params.put("shopCode",shopCode);
			params.put("userCode", userCode);
			HttpUtil.getClient().post(ConstantJiao.showShopIndexUrl, params, new BaseJsonHttpResponseHandler<ShopIndex>() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, ShopIndex index) {
					topCollectList = index.getCollectProList();
					collectPro();
					topInfoList = index.getInfoList();
					likeList = index.getYouLoveList();

					shopBase = index.getShopBase();
					totalProNum = index.getTotalProNum();
					totalActivityNum = index.getTotalActivityNum();
					totalCollectNum = index.getTotalCollectNum();
					hotNum = index.getHotNum();
					shopContext();
					mInnerGridView.setAdapter(new ShopMainGridViewAdapter(ShopMainActivity.this,likeList));
					mInnerGridView.setNumColumns(2);
					mInnerGridView.setVerticalSpacing(20);
					mInnerGridView.setHorizontalSpacing(20);
					mInnerGridView.setGravity(Gravity.CENTER);
					mScrollView.smoothScrollTo(0, 0);

				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, ShopIndex errorResponse) {
					Toast.makeText(ShopMainActivity.this, "网络异常，请稍后重试", Toast.LENGTH_SHORT);
				}

				@Override
				protected ShopIndex parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
					return gson.fromJson(rawJsonData.toString(), ShopIndex.class);
				}


			});
		}
	}

	/**
	 * 设置商铺情况
	 */
	public void  shopContext(){
		if (shopBase.getShopLogo() != null) {
			imageLoader.displayImage(ConstantJiao.aliUrl + shopBase.getShopLogo() + "@61-1ci.png", shop_logo, options);
		}
		if (shopBase.getShopName() != null) {
			shop_name.setText(shopBase.getShopName());
		}
		if (shopBase.getSlogan() != null&&!"".equals(shopBase.getSlogan())) {
			shop_slogan.setText(shopBase.getSlogan());
		}
		if (shopBase.getShopBackground() != null) {
			imageLoader.displayImage(ConstantJiao.aliUrl + shopBase.getShopBackground() + "@69h_69w_0e", shop_background, options);
		}
		shop_pro_num.setText(totalProNum+"");
		shop_hot_num.setText(hotNum+"");
		shop_activity_num.setText(totalActivityNum+"");
		shop_collect_num.setText(totalCollectNum+"");
	}

	/**
	 * 设置收藏排行
	 */
	public void  collectPro(){
		int i;
		if(topCollectList!=null&&topCollectList.size()>0){
			Product pro;
			for(i=0;i<topCollectList.size();i++){
				pro =topCollectList.get(i);
				switch (i){
					case 0:
						imageLoader.displayImage(ConstantJiao.aliUrl + pro.getCoverBigImage(), shop_top_one, options);
						if(pro.getSalePrice()==null||pro.getSalePrice()==0){
							shop_top_one_sale_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getProductPrice()));
						}else{
							shop_top_one_sale_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getSalePrice()));
						}
						final Product finalPro = pro;
						shop_top_one.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent =new Intent(ShopMainActivity.this,ProductDetailActivity.class);
								intent.putExtra("productCode", finalPro.getProductCode());
								startActivity(intent);
							}
						});
						break;
					case 1:
						imageLoader.displayImage(ConstantJiao.aliUrl + pro.getCoverBigImage(), shop_top_two, options);
						if(pro.getSalePrice()==null||pro.getSalePrice()==0){
							shop_top_two_sale_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getProductPrice()));
						}else{
							shop_top_two_sale_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getSalePrice()));
						}
						final Product product = pro;
						shop_top_two.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent =new Intent(ShopMainActivity.this,ProductDetailActivity.class);
								intent.putExtra("productCode", product.getProductCode());
								startActivity(intent);
							}
						});
						break;
					case 2:
						imageLoader.displayImage(ConstantJiao.aliUrl + pro.getCoverBigImage(), shop_top_three, options);
						if(pro.getSalePrice()==null||pro.getSalePrice()==0){
							shop_top_three_sale_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getProductPrice()));
						}else{
							shop_top_three_sale_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getSalePrice()));
						}
						final Product p = pro;
						shop_top_three.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent =new Intent(ShopMainActivity.this,ProductDetailActivity.class);
								intent.putExtra("productCode", p.getProductCode());
								startActivity(intent);
							}
						});
						break;
					default:
						Log.e("没有商品","收藏排行");
						break;
				}
			}

		}else{//使用默认图片--无商品

		}
	}


	/**
	 * 设置访问排行
	 */
	public void infoTopList(){
		int i;
		if(topInfoList!=null&&topInfoList.size()>0){
			Product pro;
			for(i=0;i<topInfoList.size();i++){
				pro =topInfoList.get(i);
				switch (i){
					case 0:
						imageLoader.displayImage(ConstantJiao.aliUrl + pro.getCoverBigImage(), info_one_image, options);
						if(pro.getSalePrice()!=null&&pro.getSalePrice()!=0){
							info_one_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getSalePrice()));
						}else {
							info_one_price.setText(priceEm+NumberFormatUtil.conventToString(pro.getProductPrice()));
						}
						final Product p1=pro;
						info_one_image.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent =new Intent(ShopMainActivity.this,ProductDetailActivity.class);
								intent.putExtra("productCode", p1.getProductCode());
								startActivity(intent);
							}
						});
						break;
					case 1:
						imageLoader.displayImage(ConstantJiao.aliUrl + pro.getCoverBigImage(), info_two_image, options);
						if(pro.getSalePrice()!=null&&pro.getSalePrice()!=0) {
							info_two_price.setText(priceEm + NumberFormatUtil.conventToString(pro.getSalePrice()));
						}else{
							info_two_price.setText(priceEm + NumberFormatUtil.conventToString(pro.getProductPrice()));
						}
						final Product p2=pro;
						info_two_image.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(ShopMainActivity.this, ProductDetailActivity.class);
								intent.putExtra("productCode", p2.getProductCode());
								startActivity(intent);
							}
						});
						break;
					case 2:
						imageLoader.displayImage(ConstantJiao.aliUrl + pro.getCoverBigImage(), info_three_image, options);
						if(pro.getSalePrice()!=null&&pro.getSalePrice()!=0) {
							info_three_price.setText(priceEm + NumberFormatUtil.conventToString(pro.getSalePrice()));
						}else{
							info_three_price.setText(priceEm + NumberFormatUtil.conventToString(pro.getProductPrice()));
						}
						final Product p3=pro;
						info_three_image.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(ShopMainActivity.this, ProductDetailActivity.class);
								intent.putExtra("productCode", p3.getProductCode());
								startActivity(intent);
							}
						});
						break;
					default:
						Log.e("没有商品","访问排行");
						break;
				}
			}

		}else{//使用默认图片--无商品

		}
	}


	@Override
	public void onClick(View v) {

	}
}
