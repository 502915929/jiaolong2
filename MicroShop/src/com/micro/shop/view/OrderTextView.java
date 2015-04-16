package com.micro.shop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.constant.Order;

/**
 * 带上下箭头的TextView
 * 
 * @author E.M
 */
public class OrderTextView extends RelativeLayout {
	private boolean isActivated;
	private boolean isUpActivated;
	private Context mContext;
	private TextView mTextView;
	private LayoutInflater mInflater;
	private LinearLayout mLlMainLayout;
	private ImageView mIvArrowUp, mIvArrowDown;
	private OnTabClickListener mTabClickListener;

	public OrderTextView(Context context) {
		super(context);
		this.mContext = context;
	}

	public OrderTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public OrderTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

	public void setText(CharSequence text) {
		initViews();
		mTextView.setText(text);
		this.setOnClickListener(mSelfClickListener);
	}

	private void initViews() {
		mInflater = LayoutInflater.from(mContext);
		mLlMainLayout = (LinearLayout) mInflater.inflate(
				R.layout.layout_order_textview, this, false);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp.addRule(CENTER_IN_PARENT);
		mLlMainLayout.setLayoutParams(lp);
		this.addView(mLlMainLayout);
		mTextView = (TextView) mLlMainLayout
				.findViewById(R.id.layout_order_textview_tv);
		mIvArrowUp = (ImageView) mLlMainLayout
				.findViewById(R.id.layout_order_textview_iv_arrow_up);
		mIvArrowDown = (ImageView) mLlMainLayout
				.findViewById(R.id.layout_order_textview_iv_arrow_down);
	}

	public boolean isTabActivated() {
		return isActivated;
	}

	public void deactivate() {
		isActivated = false;
		mTextView.setTextColor(mContext.getResources().getColor(
				R.color.color_hex_333333));
		mIvArrowUp.setImageResource(R.drawable.img_triangle_up);
		mIvArrowDown.setImageResource(R.drawable.img_triangle_down);
	}

	public void toggleArrowState() {
		// System.out.println("toggleArrowState : " + isUpActivated);
		if (isUpActivated) {
			mIvArrowUp.setImageResource(R.drawable.img_triangle_up);
			mIvArrowDown.setImageResource(R.drawable.img_triangle_down_red);
			isUpActivated = false;
		} else {
			mIvArrowUp.setImageResource(R.drawable.img_triangle_up_red);
			mIvArrowDown.setImageResource(R.drawable.img_triangle_down);
			isUpActivated = true;
		}
	}

	private OnClickListener mSelfClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!isActivated) {
				isActivated = true;
				isUpActivated = true;
				mTextView.setTextColor(mContext.getResources().getColor(
						R.color.apptheme));
			}
			toggleArrowState();
			if (mTabClickListener != null) {
				mTabClickListener.onClick(isUpActivated ? Order.ASC
						: Order.DESC);
			}
		}
	};

	public void setOnTabClickListener(OnTabClickListener listener) {
		this.mTabClickListener = listener;
	}

	public interface OnTabClickListener {
		public void onClick(Order order);
	}
}
