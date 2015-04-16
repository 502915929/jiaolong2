package com.micro.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.view.ActionHeadBar;

/**
 * 登录页面
 * 
 * @author B.B.D
 * 
 */
public class LoginActivity extends Activity {
	private ActionHeadBar headBar;
	@SuppressWarnings("unused")
	private EditText mEtUserName, mEtPwd;
	private TextView mTvReg, mTvCommit;
	private ImageView mIvWeiBo, mIvWeiXin, mIvQq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	}

	private void initData() {
		headBar.setTitle("登录");
		headBar.setOnLeftListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTvReg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到注册页面
				startActivity(new Intent(LoginActivity.this, RegActivity.class));
			}
		});
		mTvCommit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 登录按钮点击事件
			}
		});
		mIvWeiBo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 微博登录点击事件
			}
		});
		mIvWeiXin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 微信登录点击事件
			}
		});
		mIvQq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// QQ登录点击事件
			}
		});
	}
}
