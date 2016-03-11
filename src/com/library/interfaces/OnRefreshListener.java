package com.library.interfaces;

public interface OnRefreshListener {
	/**
	 * 下拉刷新
	 */
	void onDownPullRefresh();

	/**
	 * 上拉加载
	 */
	void onLoadingMore();
}
