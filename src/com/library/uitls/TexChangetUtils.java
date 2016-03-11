package com.library.uitls;

import android.content.res.ColorStateList;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

/**
 * 
 * @author wss 动态改变一行文字的大小或颜色
 */
public class TexChangetUtils {
	/**
	 * 
	 * @param str
	 *            文字内容
	 * @param size
	 *            大小         
	 * @param color
	 *            颜色
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return style 文字样式
	 */

	public static SpannableStringBuilder updateText(String str, int size,
			int color, int start, int end) {
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		ColorStateList redColors = ColorStateList.valueOf(color);
		style.setSpan(new TextAppearanceSpan(null, 0, size, redColors, null),
				start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		return style;

	}

}
