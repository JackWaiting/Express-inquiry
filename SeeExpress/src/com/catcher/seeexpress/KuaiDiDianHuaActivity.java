package com.catcher.seeexpress;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.catcher.seeexpress.data.ExpressComListAdapter2;
import com.catcher.seeexpress.data.PinyinComparator;
import com.catcher.seeexpress.entity.ExpressCom;
import com.catcher.seeexpress.util.XMLPullServer;
import com.catcher.seeexpress.view.BladeView;
import com.catcher.seeexpress.view.BladeView.OnItemClickListener;
import com.catcher.seeexpress.view.PinnedHeaderListView;

public class KuaiDiDianHuaActivity extends Activity {
	
	private ArrayList<ExpressCom> expressComList;
	private PinnedHeaderListView mListView;
	private ExpressComListAdapter2 adapter;
	
	private String currentName = null;
	private String currentPhone = null;
	private AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kuai_di_dian_hua);
		parseXMLFromFile();
		initData();
		setupViews();
		initDialog();
	}
	
	private void parseXMLFromFile() {
		try {
			InputStream is = this.getAssets().open("kuaidi.xml");
			expressComList = XMLPullServer.getExpressComList(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupViews() {
		mListView = (PinnedHeaderListView) findViewById(R.id.citys_list);
		mListView.setAdapter(adapter);

		BladeView bladeView = (BladeView) findViewById(R.id.citys_bladeview);
		bladeView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(String s) {
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					mListView.setSelection(position);
				}
			}
		});

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				ExpressCom ec = expressComList.get(position);
				currentName = ec.getName();
				currentPhone = ec.getPhone();
				
				updateDialog();
				builder.show();
			}

		});
	}

	private void initData() {
		PinyinComparator pinyinComparator = new PinyinComparator();
		Collections.sort(expressComList, pinyinComparator);
		adapter = new ExpressComListAdapter2(this, expressComList);
	}
	
	private void initDialog() {  
        builder = new AlertDialog.Builder(this);  
         
        builder.setPositiveButton("拨打",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	//拨打电话
                    	Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+currentPhone));
                    	startActivity(intent);
                    }  
                });  
        builder.setNegativeButton("取消",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                        dialog.dismiss();  
                    }  
                });  
    }  
	
	private void updateDialog(){
		builder.setTitle("拨打"+currentName+"电话");  
        builder.setMessage(currentPhone); 
	}
}
