package com.catcher.seeexpress.util;

import com.catcher.seeexpress.R;

public class Utils {

	public static int getIdByImageName(String picName) throws Exception{
		Class<com.catcher.seeexpress.R.drawable> cls = R.drawable.class;
		return cls.getField(picName).getInt(null);
	}
	
}
