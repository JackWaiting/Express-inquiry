package com.catcher.seeexpress.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "expressrecord.db";
	private static final int DB_SERVION = 1;
	private static final String CREATE_TABLE_EXPRESSHISTORY = "create table exhistory(exnumber primary key, extype,exname)";
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_SERVION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_EXPRESSHISTORY); //创建快递查询历史记录表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
