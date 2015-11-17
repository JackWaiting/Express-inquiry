package com.catcher.seeexpress.util;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.catcher.seeexpress.entity.ExpressCom;

public class XMLPullServer {
	
	/**
	 * 解析kuaidi.xml封装到List中返回
	 * 
	 * @param is 输入流
	 * @return 
	 * @throws Throwable
	 */
	public static ArrayList<ExpressCom> getExpressComList(InputStream is) throws Exception{
		ArrayList<ExpressCom> expressComList = new ArrayList<ExpressCom>();
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is,"utf-8");
		int type = parser.getEventType();
		ExpressCom ec = null;
		while(type != XmlPullParser.END_DOCUMENT){
			switch (type) {
			case XmlPullParser.START_TAG:
				if("com".equals(parser.getName())){
					ec = new ExpressCom();
				}else if("name".equals(parser.getName())){
					ec.setName(parser.nextText());
				}else if("type".equals(parser.getName())){
					ec.setType(parser.nextText());
				}else if("icon".equals(parser.getName())){
					ec.setIconPath(parser.nextText());
				}else if("phone".equals(parser.getName())){
					ec.setPhone(parser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if("com".equals(parser.getName())){
					expressComList.add(ec);
					ec = null;
				}
			default:
				break;
			}
			type = parser.next();
		}
		
		return expressComList;
	}
}
