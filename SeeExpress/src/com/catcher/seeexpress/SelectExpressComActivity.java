package com.catcher.seeexpress;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import com.catcher.seeexpress.data.ExpressComListAdapter;
import com.catcher.seeexpress.data.PinyinComparator;
import com.catcher.seeexpress.entity.ExpressCom;
import com.catcher.seeexpress.util.XMLPullServer;
import com.catcher.seeexpress.view.BladeView;
import com.catcher.seeexpress.view.BladeView.OnItemClickListener;
import com.catcher.seeexpress.view.PinnedHeaderListView;

/**
 * 选择快递公司列表界面
 * 
 * @author Administrator
 *
 */
public class SelectExpressComActivity extends Activity implements
		OnClickListener {

	private ArrayList<ExpressCom> expressComList;
	private PinnedHeaderListView mListView;
	private ExpressComListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_express_com);
		parseXMLFromFile();
		initData();
		setupViews();
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
		findViewById(R.id.title_back).setOnClickListener(this);
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
				String name = ec.getName();
				String type = ec.getType();
				
				Intent data = new Intent();
				data.putExtra("name", name);
				data.putExtra("type", type);
				setResult(20, data);
				SelectExpressComActivity.this.finish();
			}

		});
	}

	private void initData() {
		PinyinComparator pinyinComparator = new PinyinComparator();
		Collections.sort(expressComList, pinyinComparator);
		adapter = new ExpressComListAdapter(this, expressComList);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.title_back:
			this.finish();
			break;

		default:
			break;
		}
	}

}
