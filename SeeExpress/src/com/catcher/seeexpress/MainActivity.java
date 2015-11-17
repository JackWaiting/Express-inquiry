package com.catcher.seeexpress;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity implements OnCheckedChangeListener {
	
	private TabHost tabHost;
	private Intent mKdcxIntent;
	private Intent mCxlsIntent;
	private Intent mKddhIntent;
	public static final String TAG = "express";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initIntents();
		initRadios();
		setupIntents();
	}
	
	
	/**
	 * ��ʼ��������ͼ
	 */
	private void initIntents(){
		mKdcxIntent = new Intent(this, KuaiDiChaXunActivity.class);
		mCxlsIntent = new Intent(this, ChaXunLiShiActivity.class);
		mKddhIntent = new Intent(this, KuaiDiDianHuaActivity.class);
	}
	
	/**
	 * ���ú�radiobutton
	 */
	private void initRadios(){
		((RadioButton) findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
	}

	/**
	 * ���ú���ͼ
	 */
	private void setupIntents(){
		tabHost = this.getTabHost();
		tabHost.addTab(tabHost.newTabSpec("intent1").setIndicator("��ݲ�ѯ").setContent(mKdcxIntent));
		tabHost.addTab(tabHost.newTabSpec("intent2").setIndicator("��ѯ��ʷ").setContent(mCxlsIntent));
		tabHost.addTab(tabHost.newTabSpec("intent3").setIndicator("��ݵ绰").setContent(mKddhIntent));
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if(arg1){
			switch (arg0.getId()) {
			case R.id.radio_button0:
				tabHost.setCurrentTabByTag("intent1");
				break;
			case R.id.radio_button1:
				tabHost.setCurrentTabByTag("intent2");
				break;
			case R.id.radio_button2:
				tabHost.setCurrentTabByTag("intent3");
				break;
			
			default:
				break;
			}
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
				exitApp();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private long exitTime;
	/**
	 * ��������˳�Ӧ��
	 */
	private void exitApp() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}

}
