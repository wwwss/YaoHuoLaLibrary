package com.library;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.update.UmengUpdateAgent;

import android.app.Application;

public class LibraryApplaction extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// 初始化异步加载图片工具类
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		// 程序启动时像友盟发送统计数据
		OnlineConfigAgent.getInstance().updateOnlineConfig(this);
		// 程序启动时检查是否有更新
		UmengUpdateAgent.setUpdateAutoPopup(true);
		// 任意网络下都提示更新
		UmengUpdateAgent.setUpdateOnlyWifi(true);
		// 关闭自动检查提示
		UmengUpdateAgent.setUpdateCheckConfig(false);
	}

}
