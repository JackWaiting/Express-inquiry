package com.catcher.seeexpress.data;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.catcher.seeexpress.R;
import com.catcher.seeexpress.entity.ExpressCom;
import com.catcher.seeexpress.view.PinnedHeaderListView.PinnedHeaderAdapter;

public class ExpressComListAdapter extends BaseAdapter implements
		PinnedHeaderAdapter, SectionIndexer {

	private Context mContext;
	private List<ExpressCom> mList;

	public ExpressComListAdapter(Context context, List<ExpressCom> list) {
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.select_city_item, null);
			holder = new ItemHolder();
			holder.groupTextView = (TextView) convertView
					.findViewById(R.id.group_title);
			holder.cityTextView = (TextView) convertView
					.findViewById(R.id.column_title);
			convertView.setTag(holder);
		} else {
			holder = (ItemHolder) convertView.getTag();
		}
		
		ExpressCom ec = mList.get(position);

		// 获取首字母的ASCII码值
		int section = getSectionForPosition(position);
		
		if(position == getPositionForSection(section)){
			holder.groupTextView.setVisibility(View.VISIBLE);
			holder.groupTextView.setText(String.valueOf(ec.getType().toUpperCase().charAt(0)));
		}else{
			holder.groupTextView.setVisibility(View.GONE);
		}
		holder.cityTextView.setText(ec.getName());
		return convertView;
	}

	private class ItemHolder {
		TextView groupTextView;
		TextView cityTextView;
	}

	@Override
	public int getPinnedHeaderState(int position) {
		int realPosition = position;
		if (realPosition < 0 || position >= getCount()) {
			return PINNED_HEADER_GONE;
		}
		int section = getSectionForPosition(realPosition);
		int nextSectionPosition = getPositionForSection(section + 1);
		if (nextSectionPosition != -1
				&& realPosition == nextSectionPosition - 1) {
			return PINNED_HEADER_PUSHED_UP;
		}
		return PINNED_HEADER_VISIBLE;
	}

	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getSections() {
		return null;
	}

	/**
	 * 根据首字母的ascii值来获取在该ListView中第一次出现该首字母的位置，例如：从上面的效果图1中，如果section是66
	 * ，也就是‘B’的ascii值，那么该方法返回的position就是2
	 */
	@Override
	public int getPositionForSection(int section) {
		
		for(int i=0; i<getCount(); i++){
			String py = mList.get(i).getType();
			char firstChar = py.toUpperCase().charAt(0);
			if(firstChar == section){
				return i;
			}
		}
		return -1;
	}

	/**
	 * 根据ListView的position来获取该位置上面的name的首字母char的ascii值，例如：
	 * 如果该position上面的name是阿妹，首字母就是A，那么此方法返回的就是'A'字母的ascii值，也就是65， 'B'是66，依次类推
	 */
	@Override
	public int getSectionForPosition(int position) {
		return mList.get(position).getType().toUpperCase().charAt(0);
	}

}
