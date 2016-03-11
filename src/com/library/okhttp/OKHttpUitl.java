package com.library.okhttp;

import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;

public class OKHttpUitl {
	private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    static{
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
    }
}
