package com.micro.shop.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.ProductImage;
import com.micro.shop.fragment.FixRatioImageFragment;
import com.micro.shop.view.AdvertisementView;

import java.util.List;

/**
 * Created by 95 on 2015/4/21.
 */
public class PageIndicatorAdapter  extends FragmentPagerAdapter {

    private AdvertisementView.OnImageClickListener mListener;
    List<ProductImage> mData;

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
        bundle.putString(FixRatioImageFragment.ARGUMENT_IMAGE_URL, ConstantJiao.aliUrl+mData
                .get(position).getImageUrl());
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
        return mData==null?0:mData.size();

    }

    public void setOnImageClickListener(AdvertisementView.OnImageClickListener listener) {
        this.mListener = listener;
    }
}
