package com.micro.shop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 根据比例限定宽高的ImageView
 * 
 * @author E.M
 *
 */
public class AutoScaleImageView extends ImageView {
	public AutoScaleImageView(Context context) {
		super(context);
	}

	public AutoScaleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoScaleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// int imageWidth = getDrawable().getIntrinsicWidth();
		// int imageHeight = getDrawable().getIntrinsicHeight();
		int currentWidth = getMeasuredWidth();
		// System.out.println("<------------");
		int currentHeight = getMeasuredHeight();
		// System.out.println(currentWidth + ":" + currentHeight);
		float xx = (float) currentWidth / (float) 3f;
		currentHeight = (int) (xx * 2f);
		// System.out.println(currentWidth + ":" + currentHeight);
		// System.out.println("------------>");
		setMeasuredDimension(currentWidth, currentHeight);
	}
}
