package com.micro.shop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.view.ActionHeadBar;

/**
 * 注册页面
 * 
 * @author B.B.D
 * 
 */
public class RegActivity extends Activity {
	private ActionHeadBar headBar;
	private LinearLayout mLlVerification;
	@SuppressWarnings("unused")
	private TextView mTvCommit, mTvVerification;
	@SuppressWarnings("unused")
	private EditText mEtUserName, mEtPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reg);
		initView();
		initData();
	}

	private void initView() {
		headBar = (ActionHeadBar) findViewById(R.id.reg_headbar);
		mLlVerification = (LinearLayout) findViewById(R.id.reg_verification);
		mTvCommit = (TextView) findViewById(R.id.reg_ok);
		mTvVerification = (TextView) findViewById(R.id.reg_verification_input);
		mEtUserName = (EditText) findViewById(R.id.reg_username_input);
		mEtPwd = (EditText) findViewById(R.id.reg_pwd_input);
	}

	private void initData() {
		headBar.setTitle("注册");
		headBar.setOnLeftListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mLlVerification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击验证码模块后，进入获取验证码页面
			}
		});
		mTvCommit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 注册按钮点击事件
			}
		});
	}
}
