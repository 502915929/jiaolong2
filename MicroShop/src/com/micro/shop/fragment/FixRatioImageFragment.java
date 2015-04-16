package com.micro.shop.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.micro.shop.R;
import com.micro.shop.uitls.DateUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * <b>单一图片碎片，{@link ImageRunnerView}组件</b>
 * 
 * @author EM
 * 
 */
public class FixRatioImageFragment extends Fragment {
	public static final String ARGUMENT_IMAGE_URL = "ARGUMENT_IMAGE_URL";
	public static final String ARGUMENT_LOADING_TIP_RESOURCE_ID = "ARGUMENT_LOADING_TIP_RESOURCE_ID";
	public static final String ARGUMENT_IMAGE_SCALE_TYPE = "ARGUMENT_IMAGE_SCALE_TYPE";

	public static final int CENTER = 1;
	public static final int CENTER_CROP = 2;
	public static final int CENTER_INSIDE = 3;
	public static final int FIT_END = 4;
	public static final int FIT_START = 5;
	public static final int FIT_XY = 6;
	public static final int FIT_CENTER = 7;
	public static final int MATRIX = 8;

	protected static final String TAG = "SimpleImageFragment";

	private ScaleType imgScaleType;
	private String imgUrl;
	private int loadingTipResourceId;
	private ImageView mImageView, mIvLoadingTip;
	private ImageClickCallback mCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_fix_ratio_image,
				container, false);
		initData();
		initViews(rootView);
		resetDisplayingImage(imgUrl);

		return rootView;
	}

	private void initData() {
		Bundle bundle = getArguments();
		imgUrl = bundle.getString(ARGUMENT_IMAGE_URL);
		loadingTipResourceId = bundle.getInt(ARGUMENT_LOADING_TIP_RESOURCE_ID,
				-1);
		switch (bundle.getInt(ARGUMENT_IMAGE_SCALE_TYPE)) {
		case CENTER:
			imgScaleType = ScaleType.CENTER;
			break;
		case CENTER_CROP:
			imgScaleType = ScaleType.CENTER_CROP;
			break;
		case CENTER_INSIDE:
			imgScaleType = ScaleType.CENTER_INSIDE;
			break;
		case FIT_CENTER:
			imgScaleType = ScaleType.FIT_CENTER;
			break;
		case FIT_END:
			imgScaleType = ScaleType.FIT_END;
			break;
		case FIT_START:
			imgScaleType = ScaleType.FIT_START;
			break;
		case MATRIX:
			imgScaleType = ScaleType.MATRIX;
			break;
		case FIT_XY:
		default:
			imgScaleType = ScaleType.FIT_XY;
			break;
		}
	}

	private void initViews(View rootView) {
		mImageView = (ImageView) rootView
				.findViewById(R.id.fragment_billboard_iv);
		mImageView.setScaleType(imgScaleType);
		mImageView.setImageBitmap(null);
		mIvLoadingTip = (ImageView) rootView
				.findViewById(R.id.fragment_billboard_iv_loading_tip);
		if (loadingTipResourceId != -1) {
			mIvLoadingTip.setImageResource(loadingTipResourceId);
		}

		mImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCallback != null) {
					mCallback.onSingleClick();
				}
				long currentTimeMillis = DateUtil.getCurrentTimeInMillis();
				if (currentTimeMillis - timeInMillis < DOUBLE_CLICK_DURATION) {
					if (mCallback != null) {
						mCallback.onDoubleClick();
					}
				}
				timeInMillis = currentTimeMillis;
			}
		});
	}

	private static final int DOUBLE_CLICK_DURATION = 350;
	private long timeInMillis = 0;

	/**
	 * 重新加载显示的图片
	 * 
	 * @param imgUrl
	 */
	public void resetDisplayingImage(String imgUrl) {
		if (imgUrl != null) {
			if (imgUrl.startsWith("http://")) {
				ImageLoader.getInstance().displayImage(imgUrl, mImageView,
						mImageLoadingListener);
			} else {
				ImageLoader.getInstance().displayImage(imgUrl, mImageView,
						mImageLoadingListener);
			}
		}
	}

	protected ImageLoadingListener mImageLoadingListener = new ImageLoadingListener() {
		@Override
		public void onLoadingStarted(String arg0, View arg1) {

		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			mIvLoadingTip.setVisibility(View.GONE);
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {

		}
	};

	public void setImageClickCallback(ImageClickCallback callback) {
		this.mCallback = callback;
	}

	public interface ImageClickCallback {
		public void onSingleClick();

		public void onDoubleClick();
	}
}
