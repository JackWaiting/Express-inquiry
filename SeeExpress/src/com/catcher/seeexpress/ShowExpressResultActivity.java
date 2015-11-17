package com.catcher.seeexpress;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShowExpressResultActivity extends Activity {

	private static final String EXPRESS_API_URL = "http://m.kuaidi100.com/index_all.html?type=%s&postid=%s&callbackurl=%s";
	//private static final String EXPRESS_API_URL_2 = "http://wap.kuaidi100.com/wap_result.jsp?rand=20120517&id=%s&fromWeb=null&&postid=%s";
	private String type;
	private String number;
	private String url = null;

	private WebView wv;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_express_result);
		initData();
		setupViews();
	}

	private void initData() {
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		type = bundle.getString("type");
		number = bundle.getString("number");
		try {
			url = String.format(EXPRESS_API_URL, URLEncoder.encode(type,
					"utf-8"), URLEncoder.encode(number, "utf-8"), URLEncoder
					.encode("http://www.baidu.com", "utf-8"));
			
//			url = String.format(EXPRESS_API_URL_2, URLEncoder.encode(type,
//					"utf-8"), URLEncoder.encode(number, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setupViews() {
		wv = (WebView) findViewById(R.id.wv);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl("file:///android_asset/index.html");
				return true;
			}
		});

		wv.addJavascriptInterface(new Object() {

			public void clickOnAndroid() {

				mHandler.post(new Runnable() {

					public void run() {
						ShowExpressResultActivity.this.finish();
					}
				});
			}
		}, "demo");
		wv.loadUrl(url);

	}
}
