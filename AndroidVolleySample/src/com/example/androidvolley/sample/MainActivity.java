package com.example.androidvolley.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.http.LoadControler;
import com.android.http.RequestManager;
import com.android.http.RequestManager.RequestListener;
import com.android.http.RequestMap;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

/**
 * 测试程序
 * 
 * @author panxw
 *
 */
public class MainActivity extends Activity {

	private static final String OUT_FILE = "upload.txt";

	private static final String OUT_DATA = "sadf464764sdf3ds1f3adsf789213557r12-34912-482130487321gjsaldfalfu2390q3rtheslafkhsdafhreasof";

	private static final String POST_URL = "http://allthelucky.ap01.aws.af.cm/memoServer";

	private static final String POST_JSON = "{\"action\":\"test\", \"info\":\"hello world\"}";

	private static final String GET_URL = "https://raw.githubusercontent.com/panxw/android-volley-manager/master/test.txt";

	private static final String UPLOAD_URL = "http://www.splashpadmobile.com/upload.php";

	private LoadControler mLoadControler = null;

	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.mImageView = (ImageView) findViewById(R.id.imageView1);

		this.testPost();
		this.testGet();
		this.testFileUpload();
		this.testImageLoader();
	}

	/**
	 * 测试POST
	 */
	private void testPost() {
		mLoadControler = RequestManager.getInstance().post(POST_URL, POST_JSON,
				requestListener, 0);
	}

	/**
	 * 测试GET
	 */
	private void testGet() {
		mLoadControler = RequestManager.getInstance().get(GET_URL,
				requestListener, 1);
	}

	/**
	 * 测试POST（文件上传）
	 */
	private void testFileUpload() {
		MainActivity.prepareFile(this);

		RequestMap params = new RequestMap();
		File uploadFile = new File(this.getFilesDir(), OUT_FILE);
		params.put("uploadedfile", uploadFile);
		params.put("share", "1");

		mLoadControler = RequestManager.getInstance().post(UPLOAD_URL, params,
				requestListener, 2);
	}

	/**
	 * 测试图片加载
	 */
	private void testImageLoader() {
		NetworkApplication.getImageLoader().get(
				"http://www.baidu.com/img/bdlogo.png", mImageListener);
	}

	/**
	 * 因ImageLoader将回调对象存放在了WeakReference中，所以这里要将ImageListener回调设置成类对象
	 */
	private ImageListener mImageListener = new ImageListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			System.out.println("Image onErrorResponse");
		}

		@Override
		public void onResponse(ImageContainer response, boolean isImmediate) {
			System.out.println("Image onResponse");
			if (response != null && response.getBitmap() != null) {
				System.out.println("Image onResponse daata");
				mImageView.setImageBitmap(response.getBitmap());
			}
		}
	};

	/**
	 * 因ImageLoader将回调对象存放在了WeakReference中，所以这里要将ImageListener回调设置成类对象
	 */
	private RequestListener requestListener = new RequestListener() {
		@Override
		public void onSuccess(String response, Map<String, String> headers,
				String url, int actionId) {
			System.out.println("actionId:" + actionId + ", OnSucess!\n"
					+ response);
		}

		@Override
		public void onError(String errorMsg, String url, int actionId) {
			System.out.println("actionId:" + actionId + ", onError!\n"
					+ errorMsg);
		}

		@Override
		public void onRequest() {
			System.out.println("request send...");
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mLoadControler != null) {
			mLoadControler.cancel();
		}
	}

	private static void prepareFile(Context context) {
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(OUT_FILE, Context.MODE_PRIVATE);
			try {
				fos.write(OUT_DATA.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
