package com.micro.shop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micro.shop.R;

/**
 * 头部导航条
 * 
 * @author B.B.D
 * 
 */
public class ActionHeadBar extends LinearLayout {
	private RelativeLayout mRlLeft;
	private RelativeLayout mRlRight;
	private TextView mTvTitle;

	public ActionHeadBar(Context context) {
		super(context);
		initView();
	}

	public ActionHeadBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.actionbar_simple_title_with_btn, null);
		mRlLeft = (RelativeLayout) view.findViewById(R.id.action_head_back);
		mRlRight = (RelativeLayout) view.findViewById(R.id.action_head_home);
		mTvTitle = (TextView) view.findViewById(R.id.action_head_title);
		addView(view);

	}

	public void setOnLeftListener(OnClickListener listener) {
		mRlLeft.setVisibility(View.VISIBLE);
		mRlLeft.setOnClickListener(listener);
	}

	public void setOnRightListener(OnClickListener listener) {
		mRlRight.setVisibility(View.VISIBLE);
		mRlRight.setOnClickListener(listener);
	}

	public void setTitle(int res) {
		mTvTitle.setVisibility(View.VISIBLE);
		mTvTitle.setText(res);
	}

	public void setTitle(String res) {
		mTvTitle.setVisibility(View.VISIBLE);
		mTvTitle.setText(res);
	}
}
