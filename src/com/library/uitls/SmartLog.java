package com.library.uitls;

import android.text.TextUtils;
import android.util.Log;

/**
 * 功能：聪明的log信息打印类<br>
 * 作者：wss<br>
 * 时间：2014年11月19日<br>
 * 版本：<br>
 */
public class SmartLog {

	// 是否处于调试模式
	private static boolean isDebug = false;

	/**
	 * 打印提示信息
	 * 
	 * @param tag
	 *            信息标签
	 * @param msg
	 *            信息内容
	 */
	public static void i(String tag, String msg) {
		if (TextUtils.isEmpty(msg)) {
			Log.i(tag, "打印信息为Null");
			return;
		}
		// 开启调试模式时打印提示信息
		if (isDebug) {
			Log.i(tag, msg);
		}
	}

	/**
	 * 打印警告信息
	 * 
	 * @param tag
	 *            警告标签
	 * @param msg
	 *            警告信息
	 * @param tr
	 *            抛出异常
	 */
	public static void w(String tag, String msg, Throwable tr) {
		// 开启调试模式时打印警告信息
		if (isDebug) {
			Log.w(tag, msg, tr);
		}
	}
}
