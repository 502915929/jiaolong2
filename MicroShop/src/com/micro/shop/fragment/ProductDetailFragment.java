package com.micro.shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.R;
import com.micro.shop.adapter.ProductMsgAttrAdapter;
import com.micro.shop.adapter.ProductMsgImgAdapter;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.ProductAttribute;
import com.micro.shop.entity.ProductImage;
import com.micro.shop.net.HttpUtil;
import com.micro.shop.view.InnerListView;

import org.apache.http.Header;

import java.util.List;

/**
 * 商品参数模板
 * 
 * @author B.B.D
 * 
 */
public class ProductDetailFragment extends Fragment {

	View view;
	InnerListView showView;
	Context context;

	private ProductMsgAttrAdapter attrAdapter;
	private ProductMsgImgAdapter tabTwoAdapter;


	Gson gson = new Gson();


	List<ProductAttribute> attrList;
	List<ProductImage> imageList;
	String productCode;
	int tabIndex;

	public  ProductDetailFragment(){}

	public  ProductDetailFragment(Context content,String productCode,int tabIndex){
		this.context=content;
		this.tabIndex = tabIndex;
		this.productCode=productCode;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		view =initView(inflater,container);
		return view;
	}

	public View initView(LayoutInflater inflater,ViewGroup container){
		view= inflater.inflate(R.layout.product_more_tab, container, false);
		showView=(InnerListView)view.findViewById(R.id.tab_one_view);
		if(tabIndex==0){
			Log.e("进入参数预览", "" );
			if(attrList==null||attrList.size()==0){
				ajaxAttr(productCode);
			}
		}else if(tabIndex==1){
			Log.e("进入图片预览", "");
			if(imageList==null||imageList.size()==0){
				ajaxImg(productCode);
			}
		}else if(tabIndex==2){
			Log.e("进入回复预览", "");
		}
		return view;
	}

	/**
	 * 远程加载图片10
	 * @param productCode
	 */
	public void ajaxImg(String productCode){
		RequestParams params = new RequestParams();
		params.put("productCode",productCode);
		params.put("start",0);
		params.put("number",10);
		HttpUtil.getClient().post(ConstantJiao.proDetailTab_two_url,params, new BaseJsonHttpResponseHandler<List<ProductImage>>() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<ProductImage> response) {
				imageList=response;
				tabTwoAdapter = new ProductMsgImgAdapter(context,imageList);
				showView.setAdapter(tabTwoAdapter);
				tabTwoAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<ProductImage> errorResponse) {
				Toast.makeText(context,"网络加载错误",Toast.LENGTH_SHORT);
			}

			@Override
			protected List<ProductImage> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				return gson.fromJson(rawJsonData,new TypeToken<List<ProductImage>>(){}.getType());
			}
		});
	}

	/**
	 * 加载扩展数据
	 * @param productCode
	 */
	public void ajaxAttr(String productCode){
		RequestParams params = new RequestParams();
		params.put("productCode",productCode);
		HttpUtil.getClient().post(ConstantJiao.proDetailTab_one_url, params, new BaseJsonHttpResponseHandler<List<ProductAttribute>>() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<ProductAttribute> response) {
				attrList=response;
				attrAdapter=new ProductMsgAttrAdapter(context,attrList);
				showView.setAdapter(attrAdapter);
				attrAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<ProductAttribute> errorResponse) {
				Toast.makeText(context,"网络加载错误",Toast.LENGTH_SHORT);
			}

			@Override
			protected List<ProductAttribute> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
				return gson.fromJson(rawJsonData,new TypeToken<List<ProductAttribute>>(){}.getType());
			}
		});
	}

}
