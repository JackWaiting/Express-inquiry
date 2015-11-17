package com.catcher.seeexpress.data;

import java.util.Comparator;

import com.catcher.seeexpress.entity.ExpressCom;

public class PinyinComparator implements Comparator<ExpressCom>{

	@Override
	public int compare(ExpressCom lhs, ExpressCom rhs) {
		
		return lhs.getType().compareTo(rhs.getType());
	}

}
