package com.example.androidvolley.sample;

import com.android.http.RequestManager;
import com.android.volley.toolbox.ImageLoader;

import android.app.Application;

public class NetworkApplication extends Application {

	private static ImageLoader sImageLoader = null;

	private final NetworkImageCache imageCacheMap = new NetworkImageCache();

	public static ImageLoader getImageLoader() {
		return sImageLoader;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		RequestManager.getInstance().init(NetworkApplication.this);
		sImageLoader = new ImageLoader(RequestManager.getInstance()
				.getRequestQueue(), imageCacheMap);
	}

}
