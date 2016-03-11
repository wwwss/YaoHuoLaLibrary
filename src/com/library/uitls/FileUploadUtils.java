package com.library.uitls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;

import com.library.interfaces.UploadPicturesCallBack;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import Decoder.BASE64Encoder;

public class FileUploadUtils {

	private static String TAG = "FileUploadUtils";
	// @SuppressWarnings("deprecation")
	// public static String uploadFile(String host, String filePath,
	// Map<String, String> params) {
	// try {
	// HttpClient httpclient = new DefaultHttpClient();
	// httpclient.getParams().setParameter(
	// CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	// HttpPost httppost = new HttpPost(host);
	// MultipartEntity entity = new MultipartEntity();
	// File file = new File(filePath);
	// FileBody fileBody = new FileBody(file);
	// entity.addPart("photo", fileBody);
	// // for (String key : params.keySet()) {
	// // entity.addPart(key, new StringBody(params.get(key)));
	// // }
	// httppost.setEntity(entity);
	// HttpResponse response = httpclient.execute(httppost);
	// HttpEntity resEntity = response.getEntity();
	// httpclient.getConnectionManager().shutdown();
	// return EntityUtils.toString(resEntity);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	private static String result;

	public static String uploadFile(String url, String filePath, Map<String, String> params,final UploadPicturesCallBack callBack) {
		result = null;
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams param = new RequestParams();
		try {
			if (params != null && !params.isEmpty()) {
				for (Entry<String, String> entry : params.entrySet()) {
					param.put(entry.getKey(), entry.getValue());
				}
			}
			param.put("head_portrait", encodeBase64File(filePath));
			SmartLog.i(TAG, "上传图片请求" + url + param.toString());
			httpClient.put(url, param, new AsyncHttpResponseHandler() {
				@Override
				public void onStart() {
					super.onStart();
					SmartLog.i(TAG, "开始上传");
					callBack.onUploadStart();
				}

				@Override
				public void onSuccess(String arg0) {
					super.onSuccess(arg0);
					result = arg0;
					SmartLog.i(TAG, "上传成功,返回结果是" + result);
					callBack.onUploadSuccess(result);

				}

				@Override
				public void onFailure(Throwable e, String arg1) {
					super.onFailure(e, arg1);
					SmartLog.w(TAG, "上传失败", e);
					callBack.onUploadFailure();
				}
			});

		} catch (FileNotFoundException e) {
			SmartLog.w(TAG, "上传失败", e);
		} catch (Exception e1) {
			SmartLog.w(TAG, "转码失败", e1);
			e1.printStackTrace();
		}
		return result;
	}
	  public static String encodeBase64File(String path) throws Exception {
	        File  file = new File(path);
	        FileInputStream inputFile = new FileInputStream(file);
	        byte[] buffer = new byte[(int)file.length()];
	        inputFile.read(buffer);
	        inputFile.close();
	        return new BASE64Encoder().encode(buffer);
	    }
	  
	  
}
