package com.library.uitls;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by Taurus on 2015/12/22.
 */
public class OkHttpManager {
	private String TAG = "OkHttpManager";
	private static OkHttpManager instance = new OkHttpManager();
	private OkHttpClient okHttpClient = new OkHttpClient();
	private final int TIME_OUT_CONNECTION = 30 * 1000;
	private final int TIME_OUT_READ = 30 * 1000;
	private final long DEFAULT_KEEP_ALIVE_DURATION_MS = 5 * 60 * 1000; // 5 min

	private OkHttpManager() {
	}

	public static OkHttpManager getInstance() {
		return instance;
	}

	public void init() {
		ConnectionPool connectionPool = new ConnectionPool(15, DEFAULT_KEEP_ALIVE_DURATION_MS);
		okHttpClient.setConnectionPool(connectionPool);
		okHttpClient.setConnectTimeout(TIME_OUT_CONNECTION, TimeUnit.MILLISECONDS);
		okHttpClient.setReadTimeout(TIME_OUT_READ, TimeUnit.MILLISECONDS);
	}

	public OkHttpClient getOkHttpClient() {
		if (okHttpClient == null) {
			SmartLog.i(TAG, "----------------------实例化OkHttpManager");
			okHttpClient = new OkHttpClient();
			init();
		}
		return okHttpClient;
	}
	
	public void cancel(){
	}
}
