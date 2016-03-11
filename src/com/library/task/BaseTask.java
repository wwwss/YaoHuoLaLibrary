package com.library.task;

import android.os.AsyncTask;
import android.os.Build.VERSION;

/**
 * 功能：异步任务基类，实现Runnable接口，调用run()方法后方法可以并发执行<br>
 * 作者：wss<br>
 * 时间：2014年11月19日<br>
 * 版本：<br>
 */
public abstract  class BaseTask extends AsyncTask<Void, Void, String> implements
		Runnable {
	@Override
	public void run() {
		// api11以上调用并发执行的方法，11以下，默认
		if (VERSION.SDK_INT >= 11) {
			executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			execute();
		}
	}
}
