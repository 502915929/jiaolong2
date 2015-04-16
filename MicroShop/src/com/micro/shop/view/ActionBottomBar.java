package com.micro.shop.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.shop.R;

/**
 * 主页底部按钮自定义控件
 * 
 * @author B.B.D
 * 
 */
public class ActionBottomBar extends LinearLayout implements OnClickListener {
	private LinearLayout tabOne, tabTwo, tabThree, tabFour;
	private ImageView tabIvOne, tabIvTwo, tabIvThree, tabIvFour;
	private TextView tabTvOne, tabTvTwo, tabTvThree, tabTvFour;
	private OnActionBarClickListener onActionBarClickListener;

	public void setOnActionBarClickListener(
			OnActionBarClickListener onActionBarClickListener) {
		this.onActionBarClickListener = onActionBarClickListener;
	}

	public ActionBottomBar(Context context) {
		super(context);
		initView();
	}

	public ActionBottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View content = inflater.inflate(R.layout.view_action_bottom_bar, this,
				false);
		tabOne = (LinearLayout) content.findViewById(R.id.actionBarTabOne);
		tabTwo = (LinearLayout) content.findViewById(R.id.actionBarTabTwo);
		tabThree = (LinearLayout) content.findViewById(R.id.actionBarTabThree);
		tabFour = (LinearLayout) content.findViewById(R.id.actionBarTabFour);
		tabIvOne = (ImageView) content.findViewById(R.id.actionBarTabIconOne);
		tabIvTwo = (ImageView) content.findViewById(R.id.actionBarTabIconTwo);
		tabIvThree = (ImageView) content
				.findViewById(R.id.actionBarTabIconThree);
		tabIvFour = (ImageView) content.findViewById(R.id.actionBarTabIconFour);
		tabTvOne = (TextView) content.findViewById(R.id.actionBarTabOneTitle);
		tabTvTwo = (TextView) content.findViewById(R.id.actionBarTabTwoTitle);
		tabTvThree = (TextView) content
				.findViewById(R.id.actionBarTabThreeTitle);
		tabTvFour = (TextView) content.findViewById(R.id.actionBarTabFourTitle);
		addView(content);
		tabOne.setOnClickListener(this);
		tabTwo.setOnClickListener(this);
		tabThree.setOnClickListener(this);
		tabFour.setOnClickListener(this);
	}

	public void changeStatue(int position) {
		if (position == 0) {
			tabIvOne.setBackgroundResource(R.drawable.tab_dynamic_s);
			tabIvTwo.setBackgroundResource(R.drawable.tab_local);
			tabIvThree.setBackgroundResource(R.drawable.tab_search);
			tabIvFour.setBackgroundResource(R.drawable.tab_my);
			tabTvOne.setTextColor(Color.parseColor("#D13F40"));
			tabTvTwo.setTextColor(Color.parseColor("#828282"));
			tabTvThree.setTextColor(Color.parseColor("#828282"));
			tabTvFour.setTextColor(Color.parseColor("#828282"));
		} else if (position == 1) {
			tabIvOne.setBackgroundResource(R.drawable.tab_dynamic);
			tabIvTwo.setBackgroundResource(R.drawable.tab_local_s);
			tabIvThree.setBackgroundResource(R.drawable.tab_search);
			tabIvFour.setBackgroundResource(R.drawable.tab_my);
			tabTvOne.setTextColor(Color.parseColor("#828282"));
			tabTvTwo.setTextColor(Color.parseColor("#D13F40"));
			tabTvThree.setTextColor(Color.parseColor("#828282"));
			tabTvFour.setTextColor(Color.parseColor("#828282"));
		} else if (position == 2) {
			tabIvOne.setBackgroundResource(R.drawable.tab_dynamic);
			tabIvTwo.setBackgroundResource(R.drawable.tab_local);
			tabIvThree.setBackgroundResource(R.drawable.tab_search_s);
			tabIvFour.setBackgroundResource(R.drawable.tab_my);
			tabTvOne.setTextColor(Color.parseColor("#828282"));
			tabTvTwo.setTextColor(Color.parseColor("#828282"));
			tabTvThree.setTextColor(Color.parseColor("#D13F40"));
			tabTvFour.setTextColor(Color.parseColor("#828282"));

		} else if (position == 3) {
			tabIvOne.setBackgroundResource(R.drawable.tab_dynamic);
			tabIvTwo.setBackgroundResource(R.drawable.tab_local);
			tabIvThree.setBackgroundResource(R.drawable.tab_search);
			tabIvFour.setBackgroundResource(R.drawable.tab_my_s);
			tabTvOne.setTextColor(Color.parseColor("#828282"));
			tabTvTwo.setTextColor(Color.parseColor("#828282"));
			tabTvThree.setTextColor(Color.parseColor("#828282"));
			tabTvFour.setTextColor(Color.parseColor("#D13F40"));
		}
		if (onActionBarClickListener != null)
			onActionBarClickListener.onClick(position);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.actionBarTabOne) {
			changeStatue(0);
		} else if (id == R.id.actionBarTabTwo) {
			changeStatue(1);
		} else if (id == R.id.actionBarTabThree) {
			changeStatue(2);
		} else if (id == R.id.actionBarTabFour) {
			changeStatue(3);
		}
	}

	public interface OnActionBarClickListener {
		public void onClick(int position);
	}
}
