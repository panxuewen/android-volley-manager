package com.android.http;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Map;
import com.android.http.RequestManager.RequestListener;

/**
 * RequestListener Holder to avoid memory leak!
 * 
 * @author panxw
 */
public class RequestListenerHolder implements LoadListener {

	private static final String CHARSET_UTF_8 = "UTF-8";

	private WeakReference<RequestListener> mRequestListenerRef;

	public RequestListenerHolder(RequestListener requestListener) {
		this.mRequestListenerRef = new WeakReference<RequestListener>(
				requestListener);
	}

	@Override
	public void onStart() {
		RequestListener requestListener = mRequestListenerRef.get();
		if (requestListener != null) {
			requestListener.onRequest();
		}
	}

	@Override
	public void onSuccess(byte[] data, Map<String, String> headers, String url,
			int actionId) {
		String parsed = null;
		try {
			parsed = new String(data, CHARSET_UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		RequestListener requestListener = mRequestListenerRef.get();
		if (requestListener != null) {
			requestListener.onSuccess(parsed, headers, url, actionId);
		}
	}

	@Override
	public void onError(String errorMsg, String url, int actionId) {
		RequestListener requestListener = mRequestListenerRef.get();
		if (requestListener != null) {
			requestListener.onError(errorMsg, url, actionId);
		}
	}
}