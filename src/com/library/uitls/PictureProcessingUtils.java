package com.library.uitls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.text.TextUtils;

import com.library.interfaces.UploadPicturesCallBack;

/**
 * 
 * @author wss 图片处理工具类
 *
 */

public class PictureProcessingUtils {
	// log标签
	private static final String TAG = "PictureProcessingUtils";

	/**
	 * 上传图片文件
	 * 
	 * @param url
	 *            服务器链接
	 * @param imageFile
	 *            图片路径
	 * @return
	 * @throws Exception
	 */
	public static String uploadImageFile(String url, String imageFile,Map<String, String> params,UploadPicturesCallBack callback)
			throws Exception {
		if (TextUtils.isEmpty(url) || TextUtils.isEmpty(imageFile)) {
			SmartLog.i(TAG, "uploadImageFile参数为null，取消上传");
			return null;
		}
		// 获取图片旋转角度
		Matrix matrix = new Matrix();
		matrix.postRotate(getBitmapDegree(imageFile));
		// 获取图片数据
		Bitmap bitmap = decodeSampledBitmapFromResource(imageFile, 800, 480);
		if (bitmap == null) {
			SmartLog.i(TAG, "uploadImageFile读取图片为null，取消上传");
			return null;
		}
		String result = null;
		String newimageFile = "/storage/sdcard0/washbrush.jpg";
		File file = new File(newimageFile);
		FileOutputStream fOut = new FileOutputStream(file);
		// 处理图片旋转
		Bitmap returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		// 图片转换为jpeg
		returnBm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		// ios不支持webp,修改压缩格式
		if (VERSION.SDK_INT >= 14) {
			bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fOut);
		} else {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		}
		if (file == null || !file.exists()) {
			// 文件不存在
			SmartLog.i(TAG, "uploadFile文件为null，取消上传");
			return null;
		}
		result = FileUploadUtils.uploadFile(url, file.getAbsolutePath(), params,callback);
		SmartLog.i(TAG, file.getAbsolutePath());
		file.delete();
		// 清空bitmap
		bitmap.recycle();
		bitmap = null;
		if (file != null && file.exists()) {
			file.delete();
		}
		return result;
	}

	public static String imageDispose(String imagePath) {
		if (TextUtils.isEmpty(imagePath)) {
			// 文件不存在
			SmartLog.i(TAG, "处理文件为null，取消处理");
			return null;
		}
		// 获取图片旋转角度
		Matrix matrix = new Matrix();
		matrix.postRotate(getBitmapDegree(imagePath));
		// 获取图片数据
		Bitmap bitmap = decodeSampledBitmapFromResource(imagePath, 200, 200);
		if (bitmap == null) {
			SmartLog.i(TAG, "uploadImageFile读取图片为null，取消上传");
			return null;
		}
		String result = null;
		String newimageFile = "/storage/sdcard0/yaohuola.jpg";
		File file = new File(newimageFile);
		try {
			FileOutputStream fOut;
			fOut = new FileOutputStream(file);
			// 处理图片旋转
			Bitmap returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			// 图片转换为jpeg
			returnBm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			// ios不支持webp,修改压缩格式
			if (VERSION.SDK_INT >= 14) {
				bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fOut);
			} else {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			}
			if (file == null || !file.exists()) {
				// 文件不存在
				SmartLog.i(TAG, "处理文件为null，取消处理");
				return null;
			}
			result=file.getAbsolutePath();
			SmartLog.i(TAG, file.getAbsolutePath());
		//	file.delete();
			// 清空bitmap
			bitmap.recycle();
			bitmap = null;
//			if (file != null && file.exists()) {
//				file.delete();
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 计算压缩比例
	 * 
	 * @param options
	 * @param reqWidth
	 *            宽
	 * @param reqHeight
	 *            高
	 * @return 压缩比例
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int inSampleSize = 1;
		while ((options.outHeight / inSampleSize) > reqHeight || (options.outWidth / inSampleSize) > reqWidth) {
			inSampleSize++;
		}
		return inSampleSize;
	}

	/**
	 * 按要求宽高压缩图片
	 * 
	 * @param imageFile
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(String imageFile, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imageFile, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imageFile, options);
	}

	/**
	 * 读取图片的旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return 图片的旋转角度
	 */
	private static int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
}
