package ru.trumbull_pro.rebulicv_1_0;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Miner extends Activity{
	final int MENU_QUIT = 2;
	final int MENU_MINING = 3;
	final int MENU_STOP = 4;
	DatabaseHelper dbHelper;
	SQLiteDatabase db;
	String[] data = new String[] { "Добывать", "Стоп", "three" };
	final String LOG_TAG = "myLogs";
	TextView text_titan;
	TextView text_titan2;
	TextView text_titan3;
	TextView text_platinum;
	TextView text_platinum2;
	TextView text_platinum3;
	TextView text_rhodium;
	TextView text_rhodium2;
	TextView text_rhodium3;
	int online_titan = 0;
	int online_platinum = 0;
	int online_rhodium = 0;
	Timer timer;
	TimerTask timerTask;
	int mining_online_titan = 0;
	int mining_online_rhodium = 0;
	int mining_online_platinum = 0;

	// we are going to use a handler to be able to run in our TimerTask
	final Handler handler = new Handler();

	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.miner);

		Log.d(LOG_TAG, "--- Rows in minerals: ---");
		dbHelper = new DatabaseHelper(this);// открываем класс с бд
		db = dbHelper.getWritableDatabase();// позволяет читать бд
		// делаем запрос всех данных из таблицы mytable, получаем Cursor

		// делаем запрос для каждого минерала в отдельнном методе
		titan_online_mining();
		rhodium_online_mining();
		platinum_online_mining();

		titan();
		rhodium();
		platinum();

		startTimer();

	}

	public void titan() {
		Cursor c = db.query("minerals", null, "id = " + "1", null, null, null,
				null);
	
		if (c.moveToFirst()) {
	
			// определяем номера столбцов по имени в выборке
			int idColIndex = c.getColumnIndex("id");
			int nameColIndex = c.getColumnIndex("name");
			int countColIndex = c.getColumnIndex("count");
			int giantColIndex = c.getColumnIndex("giant");
			int bigColIndex = c.getColumnIndex("big");
			int averageColIndex = c.getColumnIndex("average");
			int smallColIndex = c.getColumnIndex("small");
	
			// получаем значения по номерам столбцов и пишем все в лог
			Log.d(LOG_TAG,
					"ID = " + c.getInt(idColIndex) + ", name = "
							+ c.getString(nameColIndex) + ", count = "
							+ c.getInt(countColIndex) + ", giant = "
							+ c.getInt(giantColIndex) + ", big = "
							+ c.getInt(bigColIndex) + ", average = "
							+ c.getInt(averageColIndex) + ", small = "
							+ c.getInt(smallColIndex));
			text_titan = (TextView) findViewById(R.id.titan_online_1);
			text_titan2 = (TextView) findViewById(R.id.titan_online_2);
			if (c.getInt(countColIndex)==0) {
				text_titan.setText("" + "Платина");
				text_titan2.setText("" + "-");
			} else {
			text_titan.setText("" + c.getString(nameColIndex));
			text_titan2.setText("" + c.getString(countColIndex));
			online_titan = (c.getInt(giantColIndex) * 10)
					+ (c.getInt(bigColIndex) * 20)
					+ (c.getInt(averageColIndex) * 30)
					+ (c.getInt(smallColIndex) * 40);
			}
		} else
			Log.d(LOG_TAG, "0 rows");
		c.close();
	}

	public void titan_online_mining() {
		Cursor c_online_titan = db.query("minerals", null, "id = " + "1", null,
				null, null, null);
	
		if (c_online_titan.moveToFirst()) {
	
			// определяем номера столбцов по имени в выборке
			int totalSumColIndex = c_online_titan.getColumnIndex("total_sum");
			mining_online_titan = c_online_titan.getInt(totalSumColIndex);
			
		} else
			Log.d(LOG_TAG, "0 rows");
	
		c_online_titan.close();
	}

	public void update_bd_titan() {
		ContentValues cv = new ContentValues();
		cv.put("total_sum", mining_online_titan);
		// Вставляем данные в базу
		int id = 1;
		String id_titan = Integer.toString(id);
		long rowID = db.update("minerals", cv, "id = ?",
				new String[] { id_titan });
		Log.d(LOG_TAG, "row titan, ID = " + rowID);
	}
	
	public void rhodium() {
		Cursor c1 = db.query("minerals", null, "id = " + "2", null, null, null,
				null);

		if (c1.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c1.getColumnIndex("id");
			int nameColIndex = c1.getColumnIndex("name");
			int countColIndex = c1.getColumnIndex("count");
			int giantColIndex = c1.getColumnIndex("giant");
			int bigColIndex = c1.getColumnIndex("big");
			int averageColIndex = c1.getColumnIndex("average");
			int smallColIndex = c1.getColumnIndex("small");

			// получаем значения по номерам столбцов и пишем все в лог
			Log.d(LOG_TAG,
					"ID = " + c1.getInt(idColIndex) + ", name = "
							+ c1.getString(nameColIndex) + ", count = "
							+ c1.getInt(countColIndex) + ", giant = "
							+ c1.getInt(giantColIndex) + ", big = "
							+ c1.getInt(bigColIndex) + ", average = "
							+ c1.getInt(averageColIndex) + ", small = "
							+ c1.getInt(smallColIndex));
			text_rhodium = (TextView) findViewById(R.id.rhodium_online_1);
			text_rhodium2 = (TextView) findViewById(R.id.rhodium_online_2);
			text_rhodium3 = (TextView) findViewById(R.id.rhodium_online_3);
			if (c1.getInt(countColIndex)==0) {
				text_rhodium.setText("" + "Родий");
				text_rhodium2.setText("" + "-");
			} else {
				text_rhodium.setText("" + c1.getString(nameColIndex));
				text_rhodium2.setText("" + c1.getString(countColIndex));
				online_rhodium = (c1.getInt(giantColIndex) * 10)
						+ (c1.getInt(bigColIndex) * 20)
						+ (c1.getInt(averageColIndex) * 30)
						+ (c1.getInt(smallColIndex) * 40);
			}
		} else
			Log.d(LOG_TAG, "0 rows");

		c1.close();
	}
	
	public void rhodium_online_mining() {
		Cursor c_online_rhodium = db.query("minerals", null, "id = " + "2",
				null, null, null, null);

		if (c_online_rhodium.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int totalSumColIndex = c_online_rhodium.getColumnIndex("total_sum");
			mining_online_rhodium = c_online_rhodium.getInt(totalSumColIndex);
			
		} else
			Log.d(LOG_TAG, "0 rows");

		c_online_rhodium.close();
	}
	public void update_bd_rhodium() {
		ContentValues cv = new ContentValues();
		cv.put("total_sum", mining_online_rhodium);
		// Вставляем данные в базу
		int id_pl = 2;
		String id_rhodium = Integer.toString(id_pl);
		long rowID = db.update("minerals", cv, "id = ?",
				new String[] { id_rhodium });
		Log.d(LOG_TAG, "row inserted rhodium, ID = " + rowID);
	}


	public void platinum() {
		Cursor c2 = db.query("minerals", null, "id = " + "3", null, null, null,
				null);

		if (c2.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = c2.getColumnIndex("id");
			int nameColIndex = c2.getColumnIndex("name");
			int countColIndex = c2.getColumnIndex("count");
			int giantColIndex = c2.getColumnIndex("giant");
			int bigColIndex = c2.getColumnIndex("big");
			int averageColIndex = c2.getColumnIndex("average");
			int smallColIndex = c2.getColumnIndex("small");

			// получаем значения по номерам столбцов и пишем все в лог
			Log.d(LOG_TAG,
					"ID = " + c2.getInt(idColIndex) + ", name = "
							+ c2.getString(nameColIndex) + ", count = "
							+ c2.getInt(countColIndex) + ", giant = "
							+ c2.getInt(giantColIndex) + ", big = "
							+ c2.getInt(bigColIndex) + ", average = "
							+ c2.getInt(averageColIndex) + ", small = "
							+ c2.getInt(smallColIndex));
			text_platinum = (TextView) findViewById(R.id.platinum_online_1);
			text_platinum2 = (TextView) findViewById(R.id.platinum_online_2);
			text_platinum3 = (TextView) findViewById(R.id.platinum_online_3);
			if (c2.getInt(countColIndex)==0) {
				text_platinum.setText("" + "Платина");
				text_platinum2.setText("" + "-");
			} else {
				text_platinum.setText("" + c2.getString(nameColIndex));
				text_platinum2.setText("" + c2.getString(countColIndex));
				online_platinum = (c2.getInt(giantColIndex) * 10)
						+ (c2.getInt(bigColIndex) * 20)
						+ (c2.getInt(averageColIndex) * 30)
						+ (c2.getInt(smallColIndex) * 40);
			}

		} else
			Log.d(LOG_TAG, "0 rows");

		c2.close();
	}
	
	public void platinum_online_mining() {
		Cursor c_online_platinum = db.query("minerals", null, "id = " + "3",
				null, null, null, null);

		if (c_online_platinum.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int totalSumColIndex = c_online_platinum
					.getColumnIndex("total_sum");
			mining_online_platinum = c_online_platinum.getInt(totalSumColIndex);

		} else
			Log.d(LOG_TAG, "0 rows");

		c_online_platinum.close();
	}
	
	public void update_bd_platinum() {
		ContentValues cv = new ContentValues();
		cv.put("total_sum", mining_online_platinum);
		// Вставляем данные в базу
		int id_pl = 3;
		String id_platinum = Integer.toString(id_pl);
		long rowID = db.update("minerals", cv, "id = ?",
				new String[] { id_platinum });
		Log.d(LOG_TAG, "row inserted platinum, ID = " + rowID);
	}
	
	
	public void service() {
		startService(new Intent(this, Mining_service.class));
	}

	

//	public void animation() {
	//	Animation anim = null;
		//anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
	//	text_titan3.startAnimation(anim);
		//text_platinum3.startAnimation(anim);
	//}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, MENU_MINING, 0, "Добывать");
		menu.add(0, MENU_STOP, 0, "СТОП");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case MENU_MINING:
			timer.cancel();
			startTimer();

			break;
		case MENU_STOP:
			stoptimertask();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void startTimer() {
		// set a new Timer
		timer = new Timer();

		// initialize the TimerTask's job
		initializeTimerTask();

		// schedule the timer, after the first 5000ms the TimerTask will run
		// every 10000ms
		timer.schedule(timerTask, 5000, 5000); //
	}

	public void stoptimertask() {
		// stop the timer, if it's not already null
		timer.cancel();

	}

	public void initializeTimerTask() {

		timerTask = new TimerTask() {
			public void run() {

				// use a handler to run a toast that shows the current timestamp
				handler.post(new Runnable() {
					public void run() {
						// get the current timeStamp
						mining_online_titan += online_titan;
						Log.d(LOG_TAG, "Титан онлайн= " + online_titan);
						Log.d(LOG_TAG, "Родий онлайн= " + online_rhodium);
						mining_online_platinum += online_platinum;
						Log.d(LOG_TAG, "Платина онлайн= " + online_platinum);

						service();
						text_titan3 = (TextView) findViewById(R.id.titan_online_3);
						text_titan3.setText("" + mining_online_titan);
						update_bd_titan();
						
						if (online_rhodium == 0) {
							text_rhodium3.setText("" + "-");
						} else {
							mining_online_rhodium += online_rhodium;
							text_rhodium3.setText("" + mining_online_rhodium);
							update_bd_rhodium();
						}
						
						
						if (online_platinum == 0) {
							text_platinum3.setText("" + "-");
						} else {
							mining_online_platinum += online_platinum;
							text_platinum3.setText("" + mining_online_platinum);
							update_bd_platinum();
						}
						
					}
				});
			}
		};
	}

}
