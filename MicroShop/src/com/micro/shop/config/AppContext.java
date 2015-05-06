package com.micro.shop.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.activeandroid.query.Select;
import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.entity.Device;
import com.micro.shop.entity.DeviceClientRel;
import com.micro.shop.entity.MsgVo.Msg;
import com.micro.shop.net.HttpUtil;
import com.micro.shop.util.UserUniqueCodeUtil;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


public class AppContext extends Application {

	private static String baseId ;
	private static String userCode;
	private static Gson gson ;
	private static ImageLoader imageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		//初始化DB
		ActiveAndroid.initialize(this);
		//初始化百度地图
		SDKInitializer.initialize(getApplicationContext());
		// ------------------- 初始化ImageLoader操作 ---------------------
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
		imageLoader=ImageLoader.getInstance();
		gson = new Gson();
		baseId=createUserCode(this);
	}

	public static String  getBaseId(){
		return baseId;
	}

	public static ImageLoader getImageLoader(){
		return imageLoader;
	}

	public static Gson getGson(){
		return  gson;
	}

	public static String getUserCode(){
		if(userCode==null){
			DeviceClientRel rel=new Select().from(DeviceClientRel.class).executeSingle();
			userCode=rel.userCode;
		}
		return  userCode;
	}


	public String createUserCode(final Context context){
		final String baseId;
		//读取本地数据库是否存在baseId，有则获取返回，无则生成

		List<Device> list = new Select().from(Device.class).execute();//本地取值
		if(list==null||list.size()==0) {
			baseId = UserUniqueCodeUtil.getDeviceInfo(context);
			//无则将baseId存储到本地并且传递到服务器端保存
			RequestParams params=new RequestParams();
			params.put("baseId", baseId);
			//发送到服务器
			HttpUtil.getClient().post(ConstantJiao.fristOpenAppUrl, params, new BaseJsonHttpResponseHandler<Msg>() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Msg msg) {
					if(msg.isSuccess()){
						Toast.makeText(context, "存储设备code到服务器成功", Toast.LENGTH_SHORT);
						//保存到本地
						Device device=new Device(baseId,new Timestamp(System.currentTimeMillis()));
						device.save();
						userCode=msg.getRel().userCode;
						int status=msg.getRel().status;
						DeviceClientRel rel = new DeviceClientRel(baseId,msg.getRel().userCode,null,null,status);
						rel.save();
					}else{
						Toast.makeText(context,"服务器500错误",Toast.LENGTH_SHORT);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Msg errorResponse) {
					Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT);
				}

				@Override
				protected Msg parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
					return getGson().fromJson(rawJsonData.toString(),Msg.class);
				}
			});
		}else{
			Log.e("start","you device not frist");
			baseId=list.get(0).baseId;
		}
		Log.e("app start","the baseId is----->"+baseId);
		return  baseId;
	}

}
