package com.android.db;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteOpenHelper extends SQLiteOpenHelper{

	private SQLiteDatabase mDatabase;

	public MySQLiteOpenHelper(Context context) {
		super(context, constant.DATABASE_NAME, null,
				constant.DATABASE_VERSION);
		mDatabase = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table " + constant.TABLE_NAME + "("
				+ constant.ID + " integer primary key,"
				+ constant.XBBH + " integer not null,"
				+ constant.YDBH + " integer not null,"
				+ constant.Treenumber + " integer,"
				+ constant.Treehigh + " double,"
				+ constant.BZ + " varchar(50))";
		db.execSQL(sql);
	}
	public boolean insertData(ContentValues cv){
		return mDatabase.insert(constant.TABLE_NAME, null, cv)>0;
	}
	public void del(){
		String sql = "delete from " + constant.TABLE_NAME;
		mDatabase.execSQL(sql);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);
	}
	public List<TreeOne> queryData(){
		List<TreeOne> trees=new ArrayList<TreeOne>();
		Cursor cursor=mDatabase.query(constant.TABLE_NAME, null, null, null, null, null, null);
		
		if(cursor!=null){
			
			while (cursor.moveToNext()) {
				TreeOne tree=new TreeOne();
				tree.setId(cursor.getInt(0));
				tree.setxbbh(cursor.getInt(1));
				tree.setydbh(cursor.getInt(2));
				tree.settreenumber(cursor.getInt(3));
				tree.sethigh(cursor.getDouble(4));
				tree.setbz(cursor.getString(5));
				trees.add(tree);
			}
		}
		return trees;
	}
	public void edit(String id,double hi){
		int id_num=Integer.valueOf(id).intValue();
		String sql = "update " + constant.TABLE_NAME + " set "
				+ constant.Treehigh + "="+ hi+ " where "+constant.ID+"="+id_num;
		mDatabase.execSQL(sql);
		
	}
	public void dele(int id){
		String sql = "delete from " + constant.TABLE_NAME + " where "+constant.ID+"="+id;
		mDatabase.execSQL(sql);
	}
}
