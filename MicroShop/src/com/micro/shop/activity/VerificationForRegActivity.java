package com.micro.shop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.view.ActionHeadBar;

/**
 * 注册-验证码页面
 * 
 * @author B.B.D
 * 
 */
public class VerificationForRegActivity extends Activity {
	private ActionHeadBar mHeadBar;
	@SuppressWarnings("unused")
	private TextView mTvSend, mTvCommit, mTvInput, mTvHint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reg_verification);
		initView();
		initData();
	}

	private void initView() {
		mHeadBar = (ActionHeadBar) findViewById(R.id.verification_headbar);
		mTvSend = (TextView) findViewById(R.id.verification_btn);
		mTvCommit = (TextView) findViewById(R.id.verification_ok);
		// 获取到验证码后,显示验证码
		mTvInput = (TextView) findViewById(R.id.verification_input);
		// 提示用户的控件(已经验证码发送到你的手机号：xxxx)
		mTvHint = (TextView) findViewById(R.id.verification_hint);
	}

	private void initData() {
		mHeadBar.setTitle("注册");
		mHeadBar.setOnLeftListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTvSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 发送验证码按钮点击事件
			}
		});
		mTvCommit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 确定按钮点击事件
			}
		});
	}
}
