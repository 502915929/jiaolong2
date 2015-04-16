package com.micro.shop.view;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.entity.AdEntity;
import com.micro.shop.fragment.FixRatioImageFragment;
import com.viewpagerindicator.CirclePageIndicator;

public class AdvertisementView extends RelativeLayout {
	/**
	 * 圆点指示器风格
	 */
	public static final int STYLE_POINER_INDICATOR = 0;
	/**
	 * 箭头控制器风格
	 */
	public static final int STYLE_ARROW_CONTROLLER = 1;
	/**
	 * 商情详细独特风格
	 */
	public static final int STYLE_SMALL_INDEX_POINER = 2;

	private int style;
	private int selectedImage;
	private List<AdEntity> mData;

	private FragmentActivity mContext;
	private TextView mTvAtlasNumber;
	private AutoHeightViewPager mViewPager;
	private LayoutInflater mInflater;
	// private LinearLayout mLlMainLayout;
	private RelativeLayout mRlVpiContainer;
	private CirclePageIndicator mIndicator;
	// private ImageButton mIbtnLeft, mIbtnRight;
	private PageIndicatorAdapter mAdapter;
	private OnImageClickListener mListener;

	public AdvertisementView(Context context) {
		super(context);
	}

	public AdvertisementView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AdvertisementView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private void initViews() {
		mInflater = LayoutInflater.from(mContext);
		View mainLayout = null;
		switch (style) {
		case STYLE_POINER_INDICATOR:
			mainLayout = mInflater.inflate(
					R.layout.layout_adv_style_pointer_indicator, this, false);
			break;
		case STYLE_ARROW_CONTROLLER:
			break;
		case STYLE_SMALL_INDEX_POINER:
			mainLayout = mInflater.inflate(
					R.layout.layout_adv_style_small_index, this, false);
			break;
		}
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mainLayout.setLayoutParams(lp);
		this.addView(mainLayout);

		switch (style) {
		case STYLE_POINER_INDICATOR:
			mViewPager = (AutoHeightViewPager) mainLayout
					.findViewById(R.id.layout_adv_style_pointer_vp_page);
			mRlVpiContainer = (RelativeLayout) mainLayout
					.findViewById(R.id.layout_adv_style_pointer_rl_vpi_container);
			mIndicator = new CirclePageIndicator(mContext);
			mIndicator.setFillColor(Color.parseColor("#FF12A2EB"));
			mIndicator.setPageColor(Color.parseColor("#FFFFFF"));
			mIndicator.setStrokeWidth(0f);
			mIndicator.setRadius(6f);
			mIndicator.setCentered(false);
			LayoutParams indicatorLp = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mIndicator.setLayoutParams(indicatorLp);
			mRlVpiContainer.addView(mIndicator);
			break;
		case STYLE_ARROW_CONTROLLER:
			break;
		case STYLE_SMALL_INDEX_POINER:
			mTvAtlasNumber = (TextView) mainLayout
					.findViewById(R.id.layout_adv_style_small_index_tv_atlas_number);
			mViewPager = (AutoHeightViewPager) mainLayout
					.findViewById(R.id.layout_adv_style_small_index_vp_page);
			break;
		}
	}

	public void setData(FragmentActivity context, List<AdEntity> data, int style) {
		this.mContext = context;
		this.selectedImage = 0;
		this.mData = data;
		if (style == 0 || style == 1 || style == 2) {
			this.style = style;
		} else {
			this.style = STYLE_POINER_INDICATOR;
		}
		initViews();

		mAdapter = new PageIndicatorAdapter(context.getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);

		if (style == STYLE_POINER_INDICATOR) {
			mIndicator.setViewPager(mViewPager, selectedImage);
			mIndicator.setOnPageChangeListener(mPageChangeListener);
		} else if (style == STYLE_ARROW_CONTROLLER) {

		} else if (style == STYLE_SMALL_INDEX_POINER) {
			mViewPager.setOnPageChangeListener(mPageChangeListener);
			setAtlasNumber(0);
		}
	}

	private void setAtlasNumber(int selected) {
		if (mTvAtlasNumber != null) {
			mTvAtlasNumber.setText((selected + 1) + "/" + mAdapter.getCount());
		}
	}

	private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			mViewPager.setCurrentItem(position);
			selectedImage = position;
			if (style == STYLE_ARROW_CONTROLLER
					|| style == STYLE_SMALL_INDEX_POINER) {
				setAtlasNumber(selectedImage);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	};

	private void addImageItem(AdEntity entity) {
		if (mData != null) {
			if (!mData.contains(entity)) {
				mData.add(entity);
			}
		}
	}

	public void addImageItems(List<AdEntity> list) {
		if (mData != null) {
			for (AdEntity e : list) {
				addImageItem(e);
			}
			mIndicator.notifyDataSetChanged();
		}
	}

	public void removeImageItem(AdEntity entity) {
		if (mData != null) {
			mData.remove(entity);
		}
	}

	public int getDataCount() {
		return (mData == null) ? 0 : mData.size();
	}

	public AdEntity getData(int position) {
		return (mData == null) ? null : mData.get(position);
	}

	public void setOnImageClickListener(OnImageClickListener listener) {
		this.mListener = listener;
	}

	/**
	 * 图片点击回掉接口
	 * 
	 */
	public interface OnImageClickListener {
		public void onClickItem(int position);

		public void onDoubleClickItem(int position);
	}

	protected class PageIndicatorAdapter extends FragmentPagerAdapter {
		public PageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return super.instantiateItem(container, position);
		}

		@Override
		public Fragment getItem(int position) {
			FixRatioImageFragment fragment = new FixRatioImageFragment();
			Bundle bundle = new Bundle();
			bundle.putString(FixRatioImageFragment.ARGUMENT_IMAGE_URL, mData
					.get(position).getUrl());
			final int fragmentIndex = position;

			fragment.setArguments(bundle);
			fragment.setImageClickCallback(new FixRatioImageFragment.ImageClickCallback() {
				@Override
				public void onSingleClick() {
					if (mListener != null) {
						mListener.onClickItem(fragmentIndex);
					}
				}

				@Override
				public void onDoubleClick() {
					if (mListener != null) {
						mListener.onDoubleClickItem(fragmentIndex);
					}
				}
			});
			return fragment;
		}

		@Override
		public int getCount() {
			return mData.size();
		}
	}
}
