package com.catcher.seeexpress;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.catcher.seeexpress.dao.ExpressHistoryDao;
import com.catcher.seeexpress.entity.ExpressQureyEntity;

public class ChaXunLiShiActivity extends Activity {
	
	private ListView mListView;
	private List<ExpressQureyEntity> eqeList;
	private ExpressHistoryListAdapter adapter;
	private HashMap<String, Boolean> isSelectedMap;
	private ExpressHistoryDao ehd;
	
	private PopupWindow mPopupWindow;
	private View contentView;
	private LinearLayout parentLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cha_xun_li_shi);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
		setupViews();
	}
	
	private void initData(){
		ehd = new ExpressHistoryDao(this);
		eqeList = ehd.selectAll();
		isSelectedMap = new HashMap<String, Boolean>();
		for(ExpressQureyEntity eqe : eqeList){
			isSelectedMap.put(eqe.getExNumber(), false);
		}
	}
	
	private void setupViews(){
		contentView = LayoutInflater.from(this).inflate(
                R.layout.alert_dialog, null);
		
		mPopupWindow = new PopupWindow(contentView,
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		
		mPopupWindow.setTouchable(true);
//		mPopupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.touming));
//		mPopupWindow.setOutsideTouchable(true);
		
		Button allBtn = (Button)contentView.findViewById(R.id.all);
		allBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				setAllItemChecked(true);
			}
		});
		Button cancleBtn = (Button) contentView.findViewById(R.id.cancle);
		cancleBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				setAllItemChecked(false);
				mPopupWindow.dismiss();
			}
		});
		Button deleteBtn = (Button) contentView.findViewById(R.id.delete);
		deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Iterator<Map.Entry<String, Boolean>> iterator = isSelectedMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) iterator.next();
					if(entry.getValue()){
						//删除历史记录
						String number = entry.getKey();
						isSelectedMap.remove(number);
						for(ExpressQureyEntity eqe : eqeList){
							if(number.equals(eqe.getExNumber())){
								eqeList.remove(eqe);
								ehd.delete(eqe);
								break;
							}
						}
					}
				}
				
				adapter.notifyDataSetChanged();
				mPopupWindow.dismiss();
			}
		});
		
		parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
		mListView = (ListView) findViewById(R.id.listView);
		adapter = new ExpressHistoryListAdapter();
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				if (!isNetworkConnected()) {
					Toast.makeText(ChaXunLiShiActivity.this, "网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
					return;
				}
				
				ExpressQureyEntity eqe = eqeList.get(position);
				Intent intent = new Intent(ChaXunLiShiActivity.this, ShowExpressResultActivity.class);
				intent.putExtra("type", eqe.getExType());
				intent.putExtra("number", eqe.getExNumber());
				startActivity(intent);
				
			}
			
		});
	}

	/**
	 * 检查网络是否可用
	 * @return true 可用 false 不可用
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}
	
	
	private void setAllItemChecked(boolean b){
		Iterator<Map.Entry<String, Boolean>> iterator = isSelectedMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Boolean> entry = (Map.Entry<String, Boolean>) iterator.next();
			entry.setValue(b);
		}
		
		adapter.notifyDataSetChanged();
	}
	
	private void showPopupView(){
		if(mPopupWindow.isShowing()){
			return;
		}
		mPopupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);
	}
	
	private class ExpressHistoryListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return eqeList.size();
		}

		@Override
		public Object getItem(int position) {
			return eqeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ItemViewHolder holder = null;
			if(convertView == null){
				convertView = LayoutInflater.from(ChaXunLiShiActivity.this).inflate(R.layout.ckd_history_item, null);
				holder = new ItemViewHolder();
				holder.nameTextView = (TextView) convertView.findViewById(R.id.name);
				holder.codeTextView = (TextView) convertView.findViewById(R.id.code);
				holder.checkBox = (CheckBox) convertView.findViewById(R.id.item_cb);
				convertView.setTag(holder);
			}else{
				holder = (ItemViewHolder) convertView.getTag();
			}
			
			ExpressQureyEntity eqe = eqeList.get(position);
			String name = eqe.getExName();
			final String code = eqe.getExNumber();
			holder.nameTextView.setText(name);
			holder.codeTextView.setText(code);
			
			holder.checkBox.setChecked(isSelectedMap.get(code));
			
			holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						showPopupView();
						isSelectedMap.put(code, true);
					}else{
						isSelectedMap.put(code, false);
					}
					
				}
			});
			return convertView;
		}
		
	}
	
	private class ItemViewHolder{
		TextView nameTextView;
		TextView codeTextView;
		CheckBox checkBox;
	}
	
}
