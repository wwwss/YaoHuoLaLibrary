package com.library.view;

import java.util.ArrayList;
import java.util.List;

import com.library.entity.Tab;
import com.library.interfaces.OnNavigationBarClickListenter;
import com.library.interfaces.OnTabSelectedListenter;
import com.yaohuola.library.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class NavigationBar extends LinearLayout implements
		OnTabSelectedListenter {
	private Context context;
	private LinearLayout linearLayout;
	private OnNavigationBarClickListenter listenter;
	private List<TabView> tabViews;

	public NavigationBar(Context context) {
		super(context);
		this.context = context;

		initView();
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(context)
				.inflate(R.layout.view_navigation_bar, this);
		linearLayout = (LinearLayout) findViewById(R.id.layout);
		tabViews = new ArrayList<TabView>();
	}

	public void initData(List<Tab> list, int widthPixels,
			OnNavigationBarClickListenter listenter) {
		for (int i = 0; i < list.size(); i++) {
			Tab tab = list.get(i);
			TabView tabView = new TabView(context, tab.getIndex(),
					tab.getDrawableId(), tab.getTextId(), this);
			linearLayout.addView(tabView, widthPixels / list.size(),
					LayoutParams.MATCH_PARENT);
			tabViews.add(tabView);
		}
		this.listenter=listenter;
	}

	@Override
	public void onTabSelected(int index) {
		for (int i = 0; i < tabViews.size(); i++) {
			if (i == index) {
				tabViews.get(i).setSelected(true);
			} else {
				tabViews.get(i).setSelected(false);
			}
		}
		listenter.OnNavBarClick(index);
	}

}
