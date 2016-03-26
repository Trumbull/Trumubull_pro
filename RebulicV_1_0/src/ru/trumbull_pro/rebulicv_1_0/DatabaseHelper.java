package ru.trumbull_pro.rebulicv_1_0;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	final String LOG_TAG = "myLogs";

	    public DatabaseHelper(Context context) {
	      // конструктор суперкласса
	      super(context, "myDB", null, 1);
	    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(LOG_TAG, "--- onCreate database ---");
		// создаем таблицу с полями
		db.execSQL("create table minerals ("
				+ "id integer primary key autoincrement," + "name text,"
				+ "count integer, " + "giant integer, " + "big integer, "
				+ "average integer, " + "small integer, " + "total_sum integer, " + "price text" 
				+ ");");
	}

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	    }
	  }