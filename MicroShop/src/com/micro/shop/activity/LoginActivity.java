package com.micro.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.micro.shop.R;
import com.micro.shop.config.AppContext;
import com.micro.shop.constant.ConstantJiao;
import com.micro.shop.constant.ShareConstant;
import com.micro.shop.entity.ClientUserBase;
import com.micro.shop.entity.Device;
import com.micro.shop.entity.MsgVo.Msg;
import com.micro.shop.net.HttpUtil;
import com.micro.shop.share.ShareJson.JsonPage;
import com.micro.shop.view.ActionHeadBar;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.FakeActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.ShareCore;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * 登录页面
 *
 * @author B.B.D
 *
 */
public class LoginActivity extends Activity implements Handler.Callback,
		OnClickListener, PlatformActionListener {
	private ActionHeadBar headBar;
	@SuppressWarnings("unused")
	private EditText mEtUserName, mEtPwd;
	private TextView mTvReg, mTvCommit;
	private ImageView mIvWeiBo, mIvWeiXin, mIvQq;
	private Integer type;

	//************share sdk
	String nickName;
	String imageUrl;
	String idStr;
	//**********************

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		ShareSDK.initSDK(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_logo);
		initView();
		initData();
	}

	private void initView() {
		headBar = (ActionHeadBar) findViewById(R.id.logo_headbar);
		mTvReg = (TextView) findViewById(R.id.logo_forget_reg);
		mEtUserName = (EditText) findViewById(R.id.logo_username_input);
		mEtPwd = (EditText) findViewById(R.id.logo_pwd_input);
		mIvWeiBo = (ImageView) findViewById(R.id.logo_weibo);
		mIvWeiXin = (ImageView) findViewById(R.id.logo_weixin);
		mIvQq = (ImageView) findViewById(R.id.logo_qq);
		mTvCommit = (TextView) findViewById(R.id.logo_ok);

		mTvReg.setOnClickListener(this);
		mIvWeiBo.setOnClickListener(this);
		mIvWeiXin.setOnClickListener(this);
		mIvQq.setOnClickListener(this);
		mTvCommit.setOnClickListener(this);
	}

	private void initData() {
		headBar.setTitle("登录");
		headBar.setOnLeftListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.logo_forget_reg:// 跳转到注册页面
				startActivity(new Intent(LoginActivity.this, RegActivity.class));
				break;
			case R.id.logo_ok://登录按键
				break;
			case R.id.logo_weibo://新浪微博登录
				Log.e("weibo login","start");
				type=0;
				Platform weibo = ShareSDK.getPlatform(LoginActivity.this, SinaWeibo.NAME);
				weibo.setPlatformActionListener(this);
				weibo.showUser(null);//执行登录，登录后在回调里面获取用户资料
				break;
			case R.id.logo_weixin://微信登录
				type=1;
				break;
			case R.id.logo_qq://qq登录
				type=2;
				break;

		}
	}


	@Override
	public void onComplete(Platform plat, int action,
						   HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);

		Message msg2 = new Message();
		msg2.what = 1;
		msg2.obj = res;
		UIHandler.sendMessage(msg2, this);
	}

	@Override
	public void onError(Platform palt, int action, Throwable t) {
		t.printStackTrace();
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = palt;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void onCancel(Platform plat, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	/** 处理操作结果 */
	@SuppressWarnings("unchecked")
	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case 1: {//授权成功
				/*// 成功
				JsonPage page = new JsonPage();
				String title = llTitle.getTvTitle().getText().toString();
				page.setData(title, (HashMap<String, Object>) msg.obj);
				page.show(getContext(), null);*/
				Map<String,String> map = new HashMap<>();
				map =(HashMap<String,String>)msg.obj;
				saveOtherUserMsg(map,type);
			}
			break;
			default: {
				Platform plat = (Platform) msg.obj;
				String text="";
				switch (msg.arg1) {
					case 1: {
						// 成功
						text = plat.getName()  + ShareConstant.successText;
					}
					break;
					case 2: {
						// 失败
						text = plat.getName() + ShareConstant.errorText;
					}
					break;
					case 3: {
						// 取消
						text = plat.getName() + ShareConstant.cancalText;
					}
					break;
				}

				Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
			}
			break;
		}
		return false;
	}


	public void saveOtherUserMsg(Map<String,String> map,int type){
		Log.e("用户资料是", AppContext.getGson().toJson(map));
		switch (type){
			case 0://新浪微博
				nickName=map.get("name");
				imageUrl=map.get("profile_image_url");
				idStr=map.get("idstr");
				break;
			case 1://微信
				break;
			case 2://qq
				break;
		}
		String userCode=AppContext.getUserCode();
		List<ClientUserBase> list = new Select().from(ClientUserBase.class).where("userCode=?", userCode).execute();//本地取值
		if(list!=null&&list.size()>0){
			Log.e("系统提示","该第三方资料已存在");
		}else{
			RequestParams params=new RequestParams();
			params.add("baseId",AppContext.getBaseId());
			params.add("nickName",nickName);
			params.add("userHeadImg", imageUrl);
			params.put("comingType", type);
			params.add("idstr", idStr);
			params.put("userCode", userCode);

			HttpUtil.getClient().post(ConstantJiao.otherLoginUrl, params, new BaseJsonHttpResponseHandler<Msg>() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Msg msg) {
					ClientUserBase user = msg.getClientUserBase();
					Log.e(".......", user.getUserCode());
					user.save();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Msg errorResponse) {
					Toast.makeText(LoginActivity.this, ConstantJiao.interError, Toast.LENGTH_SHORT);
				}

				@Override
				protected Msg parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
					return AppContext.getGson().fromJson(rawJsonData, Msg.class);
				}
			});
		}
	}

}
