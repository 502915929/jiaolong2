package com.micro.shop.config;

import android.app.Application;
import android.graphics.Bitmap;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class AppContext extends Application {

	public static final String baseId ="dsfsdfdsfs";
	public static final String userCode="liqihao";

	private static ImageLoader imageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		// ------------------- 初始化ImageLoader操作 ---------------------
		// ImageLoader URI:
		// String imageUri = "http://site.com/image.png"; // from Web
		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		// String imageUri = "content://media/external/audio/albumart/13"; //
		// from content provider
		// String imageUri = "assets://image.png"; // from assets
		// String imageUri = "drawable://" + R.drawable.image; // from drawables
		// (only images, non-9patch)
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc(true)
				// 图片存本地
				.cacheInMemory(true).displayer(new FadeInBitmapDisplayer(50))
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY) // default
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCache(new UsingFreqLimitedMemoryCache(16 * 1024 * 1024))
				.defaultDisplayImageOptions(defaultOptions).build();
		ImageLoader.getInstance().init(config);
		// -------------------------------------------------------------
	}

	public static ImageLoader getImageLoader(){
		imageLoader=ImageLoader.getInstance();
		return imageLoader;
	}

}
