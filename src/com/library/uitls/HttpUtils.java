package com.library.uitls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import android.text.TextUtils;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * 
 * @author wss
 *
 */
public class HttpUtils {
	// log标签
	private static final String TAG = "HttpUtils";

	/**
	 * 请求编码
	 */
	private static String requestEncoding = "UTF-8";

	/**
	 * 向指定 URL 发送Get方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, Map<String, String> params) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = null;
			StringBuffer buffer = new StringBuffer();
			if (params != null && !params.isEmpty()) {
				for (Entry<String, String> entry : params.entrySet()) {
					// 完成转码操作
					try {
						buffer.append(entry.getKey()).append("=")
								.append(URLEncoder.encode((String) entry.getValue(), "UTF-8")).append("&");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				buffer.deleteCharAt(buffer.length() - 1);
				urlName = "?" + buffer;
			}
			SmartLog.i(TAG, url + urlName);
			URL realUrl = new URL(url + urlName);
			// 打开和URL之间的连接
			URLConnection url_con = realUrl.openConnection();
			// 设置通用的请求属性
			url_con.setRequestProperty("accept", "*/*");
			url_con.setRequestProperty("connection", "Keep-Alive");
			url_con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			url_con.setRequestProperty("Content-Type", "application/json");
			SmartLog.i("发送GET的HTTP请求", url);
			// 建立实际的连接
			url_con.connect();
			// // 获取所有响应头字段
			// Map<String, List<String>> map = url_con.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// SmartLog.i(key + "--->", map.get(key) + "");
			// }
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(url_con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			SmartLog.w(TAG, "发送GET请求出现异常！", e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		SmartLog.i(TAG, "请求返回的结果是" + result);
		return result;

	}

	/**
	 * postjson请求
	 */
	public static String sendPost(String reqUrl, StringBuffer params) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setConnectTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,连接超时
			url_con.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			url_con.setDoOutput(true);
			// url_con.setRequestProperty("accept", "*/*");
			// url_con.setRequestProperty("connection", "Keep-Alive");
			// url_con.setRequestProperty("user-agent", "Mozilla/4.0
			// (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			url_con.setRequestProperty("Content-Type", "application/json");
			SmartLog.i("发送带参数的POST的HTTP请求", reqUrl + params.toString());
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, HttpUtils.requestEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			// logger.error("网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		SmartLog.i(TAG, "请求返回的结果是" + responseContent);
		return responseContent;
	}

	public static String sendPut(String reqUrl, StringBuffer params) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("PUT");
			url_con.setConnectTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,连接超时
			url_con.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			url_con.setDoOutput(true);
			url_con.setRequestProperty("accept", "*/*");
			url_con.setRequestProperty("connection", "Keep-Alive");
			url_con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			url_con.setRequestProperty("Content-Type", "application/json");
			SmartLog.i("发送带参数的PUT的HTTP请求", reqUrl + params.toString());
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, HttpUtils.requestEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			SmartLog.i("发送带参数的PUT的HTTP请求", "-----------------------------0");
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			SmartLog.i("发送带参数的PUT的HTTP请求", "-----------------------------1");
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			SmartLog.w(TAG, "网络故障", e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		SmartLog.i(TAG, "请求返回的结果是" + responseContent);
		return responseContent;
	}

	/**
	 * post请求表单提交
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String sendPost2(final String url, final Map<String, String> map) {
		String result = null;
		try {
			FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
			for (Entry<String, String> entry : map.entrySet()) {
				SmartLog.i(TAG, "请求的参数是" + entry.getKey().toString() + entry.getValue().toString());
				formEncodingBuilder.add(entry.getKey().toString(), entry.getValue().toString());
			}
			RequestBody formBody = formEncodingBuilder.build();
			SmartLog.i("发送带参数的POST的HTTP请求", url);
			Request request = new Request.Builder().url(url).post(formBody).build();
			Response response = OkHttpManager.getInstance().getOkHttpClient().newCall(request).execute();
			SmartLog.i(TAG, "post_result : code = " + response.code());
			if (response.isSuccessful()) {
				result = new String(response.body().bytes());
				SmartLog.i(TAG, "请求返回的结果是" + result);
				return result;
			} else {
				result = new String(response.body().bytes());
				if (!TextUtils.isEmpty(result)) {
					SmartLog.i(TAG, "请求返回的结果是" + result);
					return result;
				}
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * put请求表单提交
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String sendPut2(final String url, Map<String, String> map) {
		String result = null;
		try {
			FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
			for (Entry<String, String> entry : map.entrySet()) {
				SmartLog.i(TAG, "请求的参数是" + entry.getKey().toString() + entry.getValue().toString());
				formEncodingBuilder.add(entry.getKey().toString(), entry.getValue().toString());
			}
			RequestBody formBody = formEncodingBuilder.build();
			SmartLog.i("发送带参数的PUT的HTTP请求", url);
			Request request = new Request.Builder().url(url).put(formBody).build();
			Response response = OkHttpManager.getInstance().getOkHttpClient().newCall(request).execute();
			SmartLog.i(TAG, "Put_result : code = " + response.code());
			if (response.isSuccessful()) {
				result = new String(response.body().bytes());
				SmartLog.i(TAG, "请求返回的结果是" + result);
				return result;
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * put请求表单提交
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String sendPutFile(final String url, String filePath, Map<String, String> map) {
		String result = null;
		try {
			FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
			for (Entry<String, String> entry : map.entrySet()) {
				formEncodingBuilder.add(entry.getKey().toString(), entry.getValue().toString());
			}
			if (!TextUtils.isEmpty(filePath)) {
				formEncodingBuilder.add("head_portrait", Base64Uitls.encodeBase64File(filePath));
			}
			RequestBody formBody = formEncodingBuilder.build();
			Request request = new Request.Builder().url(url).put(formBody).build();
			SmartLog.i("发送带参数的POST的HTTP请求", url);
			Response response = OkHttpManager.getInstance().getOkHttpClient().newCall(request).execute();
			SmartLog.i(TAG, "PutFile_result : code = " + response.code());
			// callBack.onUploadStart();
			if (response.isSuccessful()) {
				result = new String(response.body().bytes());
				SmartLog.i(TAG, "请求返回的结果是" + result);
				// callBack.onUploadSuccess(result);
				return result;
			} else {
				// callBack.onUploadFailure();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String sendGet2(final String url, final Map<String, String> map) {
		String result = null;
		try {
			String urlName = null;
			StringBuffer buffer = new StringBuffer();
			if (map != null && !map.isEmpty()) {
				for (Entry<String, String> entry : map.entrySet()) {
					// 完成转码操作
					try {
						buffer.append(entry.getKey()).append("=")
								.append(URLEncoder.encode((String) entry.getValue(), "UTF-8")).append("&");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				buffer.deleteCharAt(buffer.length() - 1);
				urlName = "?" + buffer;
			}
			String reUrl = url;
			if (!TextUtils.isEmpty(urlName)) {
				reUrl = url + urlName;
			}
			SmartLog.i("发送GET的HTTP请求", reUrl);
			Request request = new Request.Builder().url(reUrl).get().build();
			Response response = OkHttpManager.getInstance().getOkHttpClient().newCall(request).execute();
			SmartLog.i(TAG, "Get_result : code = " + response.code());
			if (response.isSuccessful()) {
				result = new String(response.body().bytes());
				SmartLog.i(TAG, "请求返回的结果是" + result);
				return result;
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DELETE请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String sendDelete2(final String url, final Map<String, String> map) {
		String result = null;
		try {
			FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
			SmartLog.i("发送带参数的DELETE的HTTP请求", url);
			for (Entry<String, String> entry : map.entrySet()) {
				SmartLog.i(TAG, "请求的参数是" + entry.getKey().toString() + entry.getValue().toString());
				formEncodingBuilder.add(entry.getKey().toString(), entry.getValue().toString());
			}
			RequestBody formBody = formEncodingBuilder.build();
			Request request = new Request.Builder().url(url).delete(formBody).build();
			Response response = OkHttpManager.getInstance().getOkHttpClient().newCall(request).execute();
			SmartLog.i(TAG, "Delete_result : code = " + response.code());
			if (response.isSuccessful()) {
				result = new String(response.body().bytes());
				SmartLog.i(TAG, "请求返回的结果是" + result);
				return result;
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
//
// /* 上传文件至Server的方法 */
// public static void uploadFile(String actionUrl,String uploadFile,String
// newName) {
// String end = "\r\n";
// String twoHyphens = "--";
// String boundary = "*****";
//// String newName = "image.jpg";
//// String uploadFile = "storage/sdcard1/bagPictures/102.jpg";
// //String actionUrl =
// "http://192.168.1.123:8080/upload/servlet/UploadServlet";
// try {
// URL url = new URL(actionUrl);
// HttpURLConnection con = (HttpURLConnection) url.openConnection();
// /* 允许Input、Output，不使用Cache */
// con.setDoInput(true);
// con.setDoOutput(true);
// con.setUseCaches(false);
// /* 设置传送的method=POST */
// con.setRequestMethod("PUT");
// /* setRequestProperty */
// con.setRequestProperty("Connection", "Keep-Alive");
// con.setRequestProperty("Charset", "UTF-8");
// con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" +
// boundary);
// /* 设置DataOutputStream */
// DataOutputStream ds = new DataOutputStream(con.getOutputStream());
// ds.writeBytes(twoHyphens + boundary + end);
// ds.writeBytes("Content-Disposition: form-data; " +
// "name=\"file1\";filename=\"" + newName + "\"" + end);
// ds.writeBytes(end);
// /* 取得文件的FileInputStream */
// FileInputStream fStream = new FileInputStream(uploadFile);
// /* 设置每次写入1024bytes */
// int bufferSize = 1024;
// byte[] buffer = new byte[bufferSize];
// int length = -1;
// /* 从文件读取数据至缓冲区 */
// while ((length = fStream.read(buffer)) != -1) {
// /* 将资料写入DataOutputStream中 */
// ds.write(buffer, 0, length);
// }
// ds.writeBytes(end);
// ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
// /* close streams */
// fStream.close();
// ds.flush();
// /* 取得Response内容 */
// InputStream is = con.getInputStream();
// int ch;
// StringBuffer b = new StringBuffer();
// while ((ch = is.read()) != -1) {
// b.append((char) ch);
// }
// /* 将Response显示于Dialog */
// // showDialog("上传成功" + b.toString().trim());
// /* 关闭DataOutputStream */
// ds.close();
// } catch (Exception e) {
// // showDialog("上传失败" + e);
// }
// }

// public static String encodeBase64File(String path) throws Exception {
// File file = new File(path);
// FileInputStream inputFile = new FileInputStream(file);
// byte[] buffer = new byte[(int)file.length()];
// inputFile.read(buffer);
// inputFile.close();
// return new BASE64Encoder().encode(buffer);
// }
// StringBuffer params = new StringBuffer();
// for (Iterator iter = parameters.entrySet().iterator(); iter
// .hasNext();)
// {
// Entry element = (Entry) iter.next();
// params.append(element.getKey().toString());
// params.append("=");
// params.append(URLEncoder.encode(element.getValue().toString(),
// HttpUtils.requestEncoding));
// params.append("&");
// }
//
// if (params.length() > 0)
// {
// params = params.deleteCharAt(params.length() - 1);
// }
