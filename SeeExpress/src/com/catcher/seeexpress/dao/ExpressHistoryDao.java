package com.catcher.seeexpress.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.catcher.seeexpress.db.DBHelper;
import com.catcher.seeexpress.entity.ExpressQureyEntity;

public class ExpressHistoryDao {
	
	private DBHelper helper;
	private SQLiteDatabase db;
	
	public ExpressHistoryDao(Context context){
		helper = new DBHelper(context);
	} 
	
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<ExpressQureyEntity> selectAll(){
		List<ExpressQureyEntity> list = new ArrayList<ExpressQureyEntity>();
		ExpressQureyEntity eqe;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("exhistory", new String[]{"exnumber","extype","exname"}, null, null, null, null, null);
		while(cursor.moveToNext()){
			eqe = new ExpressQureyEntity();
			eqe.setExNumber(cursor.getString(0));
			eqe.setExType(cursor.getString(1));
			eqe.setExName(cursor.getString(2));
			list.add(eqe);
		}
		cursor.close();
		closeDB();
		return list;
	}
	
	/**
	 * 插入数据到表
	 * @param place
	 */
	public void insert(ExpressQureyEntity eqe){
		getWriteDB();
		
		ContentValues values = new ContentValues();
		values.put("exnumber", eqe.getExNumber());
		values.put("extype", eqe.getExType());
		values.put("exname", eqe.getExName());
		db.insert("exhistory", null, values);
		closeDB();
	}
	
	/**
	 * 判断是否存在数据
	 */
	public boolean exist(String exNumber){
		
		Log.v("test exist", exNumber);
		db = helper.getReadableDatabase();
		String selection = "exnumber=?";
		String[] selectionArgs = new String[]{exNumber};
		Cursor c = db.query("exhistory", new String[]{"exnumber"}, selection, selectionArgs, null, null, null);
		if(c.moveToNext()){
			Log.v("test exist", "return true");
			return true;
		}else{
			Log.v("test exist", "return false");
			return false;
		}
	}
	
	public int delete(ExpressQureyEntity eqe){
		getWriteDB();
		String exNumber = eqe.getExNumber();
		String whereClause = "exnumber=?";
		String[] whereArgs = new String[]{exNumber};
		return db.delete("exhistory", whereClause, whereArgs);
	}
	
	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		try {
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 打开数据库
	 */
	public void getWriteDB() {
		db = helper.getWritableDatabase();
	}
}
