package com.catcher.seeexpress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.catcher.seeexpress.dao.ExpressHistoryDao;
import com.catcher.seeexpress.entity.ExpressQureyEntity;
import com.google.zxing.client.android.CaptureActivity;

public class KuaiDiChaXunActivity extends Activity implements OnClickListener {

	private EditText expressNumberEdit;
	private Button selectExpressComBtn;
	private String expressType = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kuai_di_cha_xun);
		setupViews();
	}

	/**
	 * ������ͼ�����Ӧ
	 */
	private void setupViews() {
		((Button) findViewById(R.id.ckd_bt_select)).setOnClickListener(this);
		expressNumberEdit = (EditText) findViewById(R.id.ckd_et_waybill);
		((ImageButton) findViewById(R.id.ckd_ibt_scanning))
				.setOnClickListener(this);
		((Button) findViewById(R.id.ckd_bt_query)).setOnClickListener(this);

		selectExpressComBtn = (Button) findViewById(R.id.ckd_bt_select);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.ckd_bt_select:
			gotoSelectExpressCom();
			break;
		case R.id.ckd_ibt_scanning:
			gotoZxing();
			break;
		case R.id.ckd_bt_query:
			handleExpressQuery();
			break;

		default:
			break;
		}
	}

	/**
	 * ��ת��ɨ�����������
	 */
	private void gotoZxing() {
		Intent intent = new Intent(KuaiDiChaXunActivity.this,
				CaptureActivity.class);
		startActivityForResult(intent, 0);
	}

	/**
	 * ��ת��ѡ���ݹ�˾�б�
	 */
	private void gotoSelectExpressCom() {
		Intent intent = new Intent(KuaiDiChaXunActivity.this,
				SelectExpressComActivity.class);
		startActivityForResult(intent, 0);
	}
	
	/**
	 * �����ѯ����
	 */
	private void handleExpressQuery(){
		if(expressType == null){
			Toast.makeText(this, "��ѡ���ݹ�˾", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String expressNumber = expressNumberEdit.getText().toString().trim();
		if(null == expressNumber || "".equals(expressNumber)){
			Toast.makeText(this, "�������ݵ���", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (!isNetworkConnected()) {
			Toast.makeText(this, "���粻���ã���������", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String expressName = selectExpressComBtn.getText().toString().trim();
		
		saveToDB(expressNumber,expressName);
		
		Intent intent = new Intent(KuaiDiChaXunActivity.this, ShowExpressResultActivity.class);
		intent.putExtra("type", expressType);
		intent.putExtra("number", expressNumber);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 30) {
			String result = data.getExtras().getString("result");
			expressNumberEdit.setText(result);
		} else if (resultCode == 20) {
			String name = data.getExtras().getString("name");
			String type = data.getExtras().getString("type");

			selectExpressComBtn.setText(name);
			expressType = type;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * ����ѯ���ݱ��浽���ݿ�
	 */
	private void saveToDB(String exNumber, String expressName){
		ExpressHistoryDao ehd = new ExpressHistoryDao(this);
		if(!(ehd.exist(exNumber))){
			ExpressQureyEntity eqe = new ExpressQureyEntity(exNumber, expressType, expressName);
			ehd.insert(eqe);
		}
	}

	/**
	 * ��������Ƿ����
	 * @return true ���� false ������
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}
}
