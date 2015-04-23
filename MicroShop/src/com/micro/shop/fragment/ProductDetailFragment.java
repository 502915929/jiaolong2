package com.micro.shop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.micro.shop.R;

/**
 * 商品参数模板
 * 
 * @author B.B.D
 * 
 */
public class ProductDetailFragment extends Fragment {
	private ScrollView mScroll;
	private ImageView mImage;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_product_detail,
				container, false);
		mScroll = (ScrollView) view.findViewById(R.id.product_detail_scroll);
		mImage = (ImageView) view.findViewById(R.id.product_detail_scroll_top);
		mImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						mScroll.fullScroll(ScrollView.FOCUS_UP);
					}
				});

			}
		});
		return view;
	}
}
