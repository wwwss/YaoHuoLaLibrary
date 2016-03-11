package com.library.view;

import com.yaohuola.library.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

public class NoNetPopupWindow extends PopupWindow {

		public boolean isDismiss;

		public NoNetPopupWindow(Context context) {
			// 设置contentView
			View contentView = LayoutInflater.from(context).inflate(R.layout.activity_base, null);
			// popupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
			// LayoutParams.WRAP_CONTENT, true);
			// 设置SelectPicPopupWindow的View
			this.setContentView(contentView);
			// 设置SelectPicPopupWindow弹出窗体的宽
			this.setWidth(LayoutParams.MATCH_PARENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			this.setHeight(LayoutParams.WRAP_CONTENT);
			// 设置SelectPicPopupWindow弹出窗体可点击
			this.setFocusable(true);
			this.setOutsideTouchable(true);
			// 刷新状态
			this.update();
			// 实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(0000000000);
			// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
			this.setBackgroundDrawable(dw);
			// LinearLayout linearLayout = new LinearLayout(context);
			// // 显示PopupWindow
			// this.showAsDropDown(linearLayout, 0, 100, Gravity.CENTER);
		}

		/**
		 * 显示popupWindow
		 * 
		 * @param parent
		 */
		public void showPopupWindow(View parent) {
			if (!this.isShowing()) {
				// 以下拉方式显示popupwindow
				this.showAsDropDown(parent, 0, 100, Gravity.CENTER);
			} else {
				this.dismiss();
			}
		}

//		@Override
//		public void dismiss() {
//			if (isDismiss) {
//				super.dismiss();
//				isDismiss=false;
//			}else{
//				this.setFocusable(false);
//				SmartLog.i("测试", "--------------------");
//			}
//
//		}
}
