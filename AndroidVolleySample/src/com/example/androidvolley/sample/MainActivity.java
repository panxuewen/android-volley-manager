package com.example.androidvolley.sample;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;

import com.android.http.LoadControler;
import com.android.http.RequestManager;
import com.android.http.RequestManager.RequestListener;
import com.android.http.RequestMap;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.utils.R;

public class MainActivity extends Activity {
	private LoadControler loadControler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		testPost();
		testGet();
		testFileUpload();
		testImageLoader();
	}

	/**
	 * test POST method
	 */
	private void testPost() {
		final String json = "{\"action\":\"test\", \"info\":\"hello world\"}";
		loadControler = RequestManager.getInstance().post("http://allthelucky.ap01.aws.af.cm/memoServer", json,
				requestListener, 0);
	}

	/**
	 * test GET method
	 */
	private void testGet() {
		loadControler = RequestManager.getInstance().get("http://allthelucky.ap01.aws.af.cm/memoServer", requestListener, 1);
	}

	/**
	 * test FileUpload
	 */
	private void testFileUpload() {
		RequestMap params = new RequestMap();
		params.put("file1", new File("/mnt/sdcard/out.txt"));
		params.put("share", "1");

		loadControler = RequestManager.getInstance().post("http://upload.vdisk.cn/webupload", params, requestListener, 2);
	}

	/**
	 * RequestListener for receiving result
	 */
	private RequestListener requestListener = new RequestListener() {

		@Override
		public void onSuccess(String response, String url, int actionId) {
			System.out.println("actionId:" + actionId + ", OnSucess!\n" + response);
		}

		@Override
		public void onError(String errorMsg, String url, int actionId) {
			System.out.println("actionId:" + actionId + ", onError!\n" + errorMsg);
		}

		@Override
		public void onRequest() {

		}
	};
	
	/**
	 * test ImageLoader
	 */
	private void testImageLoader() {
		NetworkApplication.getImageLoader().get("", new ImageListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
			
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		loadControler.cancel();
	}

}
