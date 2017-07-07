package com.deppon.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.deppon.bean.FieldToColumnRelationEntity;
import com.deppon.bean.Infor;
import com.deppon.bean.User;
import com.deppon.bean.UserClass;
import com.j256.ormlite.support.ConnectionSource;

public class MyDatabaseHelper<T> extends DatabaseHelper{
	private final static int DATABASE_VERSION=1;
	private final static String database_name="jj.db";
	
	public MyDatabaseHelper(Context context,String DATABASE_PATH) {
		super(context, null, null, DATABASE_PATH);
	
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		List<Class> classs = new ArrayList<Class>();
		classs.add(User.class);
		classs.add(Infor.class);
		classs.add(FieldToColumnRelationEntity.class);
		try {
			super.createTable(arg1, classs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource arg1,
			int arg2, int arg3) {
		// TODO Auto-generated method stub
        List<Class<T>> tableClass=new ArrayList<Class<T>>();
//        tableClass.add((Class<T>) Infor.class);
        tableClass.add((Class<T>) User.class);
	    try {
			super.updateTable(database,arg1, tableClass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
