package com.micro.shop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.shop.R;
import com.micro.shop.activity.LoginActivity;
import com.micro.shop.entity.MyEntity;

/**
 * 我的
 * 
 * @author B.B.D
 * 
 */
public class MyFragment extends HeadBarSimpleListFragment {
	public static final int CODE = 1000;
	private Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			setProgressBarVisible(false);
			if (msg.what == CODE) {
				@SuppressWarnings("unchecked")
				List<MyEntity> list = (ArrayList<MyEntity>) msg.obj;
				MyFragmentAdapter adapter = new MyFragmentAdapter(
						getActivity(), list);
				ColorDrawable line = (ColorDrawable) getActivity()
						.getResources().getDrawable(R.drawable.myitemline);
				setHeadTitle("我的");
				setDividerDrawable(line);
				setDividerHeight(2);
				setAdapter(adapter);
			}
			return false;
		}
	});

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getData();
	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = Message.obtain();
				List<MyEntity> list = new ArrayList<MyEntity>();
				list.add(new MyEntity(R.drawable.shouchang, "收藏", 0));
				list.add(new MyEntity(R.drawable.dianpu, "关注的店铺", 1));
				list.add(new MyEntity(R.drawable.fankui, "反馈", 2));
				list.add(new MyEntity(R.drawable.guanyu, "关于", 3));
				list.add(new MyEntity(R.drawable.shezhi, "设置", 4));
				msg.what = CODE;
				msg.obj = list;
				handler.sendMessage(msg);
			}
		}).start();
	}

	private class MyFragmentAdapter extends BaseAdapter {
		private Context context;
		private List<MyEntity> list;
		private LayoutInflater inflater;

		public MyFragmentAdapter(Context context, List<MyEntity> list) {
			super();
			this.list = list;
			this.context = context;
			this.inflater = LayoutInflater.from(this.context);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if (view == null)
				view = inflater
						.inflate(R.layout.adapter_my_item, parent, false);
			ImageView image = (ImageView) view
					.findViewById(R.id.adapter_my_item_image);
			TextView title = (TextView) view
					.findViewById(R.id.adapter_my_item_title);
			final MyEntity entity = list.get(position);
			image.setBackgroundResource(entity.getImageUrl());
			title.setText(entity.getTitle());
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// 点击操作触发事件
					if (entity.getIndex() == 0) {
						context.startActivity(new Intent(context,
								LoginActivity.class));
					}
				}
			});
			return view;
		}

	}
}
