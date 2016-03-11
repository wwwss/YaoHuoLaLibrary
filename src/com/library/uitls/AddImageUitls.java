package com.library.uitls;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class AddImageUitls {
	private String imagePath;
	private Context context;
	private Activity activity;

	public AddImageUitls(Context context,Activity activity) {
		this.context = context;
		this.activity=activity;
	}

	public void addImage(final int c, final int p) {
		CharSequence[] charSequences = { "拍照", "图库" };
		new AlertDialog.Builder(context).setItems(charSequences,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							// 拍照
							try {
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(getFile()));
								activity.startActivityForResult(intent, c);
							} catch (Exception e) {
								Toast.makeText(context, "请插入sd卡后重试！",
										Toast.LENGTH_SHORT).show();
							}
							break;
						case 1:
							Intent intent;
							if (Build.VERSION.SDK_INT < 19) {
								intent = new Intent(Intent.ACTION_GET_CONTENT);
								intent.setType("image/*");
							} else {
								intent = new Intent(
										Intent.ACTION_PICK,
										android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							}
							activity.startActivityForResult(intent, p);
							break;

						default:
							break;
						}
					}
				}).show();
	}

	/**
	 * 获取拍照图片临时文件
	 * 
	 * @return 图片文件
	 * @throws IOException
	 */
	private File getFile() throws IOException {
		String parentPath = Environment.getExternalStorageDirectory()
				.getAbsoluteFile() + File.separator + context.getPackageName();
		File parent = new File(parentPath);
		if (!parent.exists()) {
			parent.mkdirs();
		}
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		imagePath = parentPath + File.separator + dateFormat.format(date)
				+ ".jpg";
		File mPhotoFile = new File(imagePath);
		if (!mPhotoFile.exists()) {
			mPhotoFile.createNewFile();
		}

		return mPhotoFile;
	}

	public String getImagePath() {
		return imagePath;
	}

}
