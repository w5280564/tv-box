package com.bosan.audiorecordbybluetooth.base;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.bosan.audiorecordbybluetooth.MD5;
import com.bosan.audiorecordbybluetooth.utils.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by ouyang
 */
public class HttpSender {
	public static String TAG_API = "HttpSenderApi";
	private OnHttpResListener mListener;
	private String requestUrl = "";
	private Map<String, Object> paramsMap;
	private Map<String, String > headerMap = new HashMap<>() ;//添加header头


	public HttpSender(String requestUrl,  Object mRequestObj,
                      OnHttpResListener mListener) {
		super();
		this.requestUrl = requestUrl;
		this.mListener = mListener;
		if (mRequestObj != null) {
			this.paramsMap = Obj2Map(mRequestObj);
		}
		headerMap.put("key", Constants.PUBLIC_KEY);
		long time = System.currentTimeMillis()/1000;
		headerMap.put("sign", getSign(time));
		Log.d("Sign","sign>>>"+getSign(time));
		headerMap.put("time", time+"");

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(headerMap);
		Log.d(TAG_API,"---Header Data---" + jsonObject.toJSONString());
	}



	private String getSign(long time){
		String originalSine = String.format("key=%s&route=%s&time=%s%s",Constants.PUBLIC_KEY,Constants.POST_VOICE_FILE_ROUTE,time+"",Constants.PRIVATE_SECRET);
		Log.d("Sign","originalSine>>>"+originalSine);
		return MD5.getStringMD5(originalSine);
	}


	public void sendGet() {
		requestGet();
	}

	public void sendPost() {
		requestPost();
	}

	/**
	 * 上传单个文件
	 */
	public void sendPostFile(File file) {
		requestPostFile(file);
	}

	/**
	 * get请求
	 */
	private void requestGet() {
		HashMap<String, String> upLoadMap = getRequestData();
		OkHttpUtils.get().url(requestUrl)
				.params(upLoadMap)
				.headers(headerMap)
				.build().execute(new StringDialogCallback());
	}

	/**
	 * POST请求
	 */
	private void requestPost() {
		setRequestData_POST();
		String request_data = GsonUtil.getInstance().toJson(paramsMap);
		if (!TextUtils.isEmpty(request_data)) {
			OkHttpUtils.postString()
					.url(requestUrl)
					.content(request_data)
					.mediaType(MediaType.parse("application/json; charset=utf-8"))
					.headers(headerMap)
					.build().execute(new StringDialogCallback());
		}
	}

	/**
	 * 获取请求的数据
	 * @return
	 */
	private HashMap<String, String> getRequestData() {
		HashMap<String, String> upLoadMap = new HashMap<>();
		if (paramsMap != null) {
			for (String key : paramsMap.keySet()) {
				Object requestParams = paramsMap.get(key);
				if(requestParams!=null){
					upLoadMap.put(key,requestParams.toString());
				}
			}
		}
		return upLoadMap;
	}


	/**
	 * 设置请求的数据(POST)
	 * @return
	 */
	private void setRequestData_POST() {
			if (paramsMap != null) {

				for (String key : paramsMap.keySet()) {
					Object requestParams = paramsMap.get(key);
					if(requestParams!=null){
					}

				}
			}
	}

	/**
	 * POST 带文件上传时 调用此方法
	 *
	 * @param file
	 */
	private void requestPostFile(File file) {
		HashMap<String, String> upLoadMap = new HashMap<String, String>();

		if (paramsMap != null) {
			for (String key : paramsMap.keySet()) {
				Object requestParams = paramsMap.get(key);
				if(requestParams!=null){
					upLoadMap.put(key,requestParams.toString());
				}
			}
		}
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(paramsMap);
		Log.d(TAG_API,"---请求data---" + jsonObject.toJSONString());
		OkHttpUtils.post().url(requestUrl)
				.params(upLoadMap)
				.headers(headerMap)
				.addFile("sound_file",file.getName(),file).build().execute(new StringDialogCallback());
	}


	/**
	 * obj 转为 map
	 *
	 * @param obj
	 *            需要转的对象
	 * @return
	 */
	public Map<String, Object> Obj2Map(Object obj) {
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(obj);
		Map<String,Object> objMap =JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
		return objMap;
	}



	public  class StringDialogCallback extends StringCallback {

		@Override
		public void onError(Call call, Exception e, int id) {
			String errorInfo =e.getMessage();
			Log.d(TAG_API,"接口出现异常，异常信息：" +errorInfo);

		}

		@Override
		public void onResponse(String json, int id) {
			Log.d(TAG_API,"接口返回结果：" + json);
			JSONObject mJSONObject = JSONObject.parseObject(json);
			int code = mJSONObject.getInteger("code");//获取状态码
			String msg = mJSONObject.getString("msg");
			if (code == Constants.REQUEST_SUCCESS_CODE) {
				if (mListener != null) {
					mListener.onComplete(mJSONObject, code, msg);
				}
			}
		}


		@Override
		public void onBefore(Request request, int id) {
			super.onBefore(request, id);
		}

		@Override
		public void onAfter(int id) {

		}
	}


	/**
	 *
	 * @author ouyang 服务器返回数据监听
	 */
	public interface OnHttpResListener {

		/**
		 *
		 * @param json_root 返回数据的根json
		 * @param code 服务器返回码
		 * @param msg  返回信息（提示语）
		 */
		void onComplete(JSONObject json_root, int code, String msg);

	}

}
