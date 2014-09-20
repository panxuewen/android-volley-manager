package com.android.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.android.http.multipart.MultipartRequestParams;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class MultipartRequest extends Request<byte[]> {
	private final Listener<byte[]> mListener;
	private MultipartRequestParams mParams = null;

	public MultipartRequest(String url, MultipartRequestParams params, Listener<byte[]> listener, ErrorListener errorListener) {
		super(Method.POST, url, errorListener);
		this.mParams = params;
		this.mListener = listener;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (mParams != null) {
			HttpEntity httpEntity = mParams.getEntity();
			try {
				httpEntity.writeTo(baos);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();
		if (null == headers || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}
		return headers;
	}

	@Override
	public String getBodyContentType() {
		if (mParams != null) {
			HttpEntity httpEntity = mParams.getEntity();
			return httpEntity.getContentType().getValue();
		}
		return null;
	}

	@Override
	protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
		return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected void deliverResponse(byte[] response) {
		this.mListener.onResponse(response);
	}

}
