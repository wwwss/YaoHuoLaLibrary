package com.library.activity;

import com.library.uitls.NetUtils;
import com.library.uitls.SmartLog;
import com.umeng.analytics.MobclickAgent;
import com.yaohuola.library.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 功能：界面基类，抽象类，activity界面需要继承此类，实现一些共有方法<br>
 * 作者：wss<br>
 * 时间：2014年11月19日<br>
 * 版本：<br>
 * 
 * @param <NoNetPopupWindow>
 */
public abstract class BaseActivity extends FragmentActivity implements IActivity {
	private LinearLayout parentLinearLayout;//
	// 把父类activity和子类activity的view都add到这里
	// log标签
	protected final String TAG = getClass().getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initContentView(R.layout.activity_base);
		// 设置布局文件
		setContentView();
		// 初始化
		initView();
		// 注册监听网络变化的广播
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
	}

	/**
	 * 初始化contentview
	 */
	private void initContentView(int layoutResID) {
		ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
		viewGroup.removeAllViews();
		parentLinearLayout = new LinearLayout(this);
		parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
		viewGroup.addView(parentLinearLayout);
		LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
	}

	@Override
	public void setContentView(int layoutResID) {
		LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
	}

	@Override
	public void setContentView(View view) {
		parentLinearLayout.addView(view);
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {
		parentLinearLayout.addView(view, params);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 开启页面统计
		MobclickAgent.onPageStart(TAG);
		// 开启时长统计
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 关闭页面统计
		MobclickAgent.onPageEnd(TAG);
		// 关闭时长统计
		MobclickAgent.onPause(this);
	}

	public void ToastShow(String content) {
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	private ConnectivityManager connectivityManager;
	private NetworkInfo info;
	private boolean isRefresh;
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				SmartLog.i(TAG, "网络状态已经改变");
				connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					SmartLog.i(TAG, "可用网络" + info.getTypeName());
					parentLinearLayout.findViewById(R.id.hint).setVisibility(View.GONE);
					if (isRefresh) {
						refreshData();
						isRefresh = false;
					}
				} else {
					SmartLog.i(TAG, "没有可用网络");
					isRefresh = true;
					parentLinearLayout.findViewById(R.id.hint).setVisibility(View.VISIBLE);
					// startActivity(new Intent(BaseActivity.this,
					// NoNetActivity.class));
				}
			}
		}

	};

	public void openSetting(View v) {
		NetUtils.openSetting(this);

	}

//	@Override
//	public void refreshData() {
//
//	}

}
