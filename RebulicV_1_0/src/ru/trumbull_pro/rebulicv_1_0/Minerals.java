package ru.trumbull_pro.rebulicv_1_0;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Minerals extends Activity  {
	final int MENU_QUIT = 2;
	final int MENU_BUILD = 3;
	final String LOG_TAG = "myLogs";
	private EditText a1;
	private EditText b1;
	String a2;
	String b2;
	TextView text;
	TextView text1;
	TextView text2;
	TextView text3;
	TextView text4;
	DatabaseHelper dbHelper;
	SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.minerals);
		dbHelper = new DatabaseHelper(this);
		db = dbHelper.getWritableDatabase();
		
		a1 = (EditText) findViewById(R.id.editText1);
		b1 = (EditText) findViewById(R.id.editText2);
		a2 = getIntent().getStringExtra("a1");
		b2 = getIntent().getStringExtra("b1");
		Log.d(LOG_TAG, "a2: " + a2);

		Log.d(LOG_TAG, "a1: " + a2);
		Toast toast = Toast.makeText(getApplicationContext(),
				"Сканирование закончено!", Toast.LENGTH_SHORT);
		toast.show();

		double a = Double.parseDouble(a2);
		double b = Double.parseDouble(b2);
		double p = a * b;
		int r = (int) (Math.floor(p / 32));
		Log.d(LOG_TAG, "Площадь: " + r);
		int titan_deposits = 0;
		int titan_deposits1 = 0;
		int titan_deposits2 = 0;
		int titan_deposits3 = 0;
		int rhodium_deposits = 0;
		int rhodium_deposits1 = 0;
		int rhodium_deposits2 = 0;
		int rhodium_deposits3 = 0;
		int platinum_deposits = 0;
		int platinum_deposits1 = 0;
		int platinum_deposits2 = 0;
		int platinum_deposits3 = 0;
		int gold_deposits = 0;
		int gold_deposits1 = 0;
		int gold_deposits2 = 0;
		int gold_deposits3 = 0;
		int iridium_deposits = 0;
		int iridium_deposits1 = 0;
		int iridium_deposits2 = 0;
		int iridium_deposits3 = 0;
		int diamond_deposits = 0;
		int diamond_deposits1 = 0;
		int diamond_deposits2 = 0;
		int diamond_deposits3 = 0;
		int silver_deposits = 0;
		int silver_deposits1 = 0;
		int silver_deposits2 = 0;
		int silver_deposits3 = 0;
		int iron_deposits = 0;
		int iron_deposits1 = 0;
		int iron_deposits2 = 0;
		int iron_deposits3 = 0;
		int cooper_deposits = 0;
		int cooper_deposits1 = 0;
		int cooper_deposits2 = 0;
		int cooper_deposits3 = 0;
		int oil_deposits = 0;
		int oil_deposits1 = 0;
		int oil_deposits2 = 0;
		int oil_deposits3 = 0;
		int gas_deposits = 0;
		int gas_deposits1 = 0;
		int gas_deposits2 = 0;
		int gas_deposits3 = 0;
		int nickel_deposits = 0;
		int nickel_deposits1 = 0;
		int nickel_deposits2 = 0;
		int nickel_deposits3 = 0;
		int aluminium_deposits = 0;
		int aluminium_deposits1 = 0;
		int aluminium_deposits2 = 0;
		int aluminium_deposits3 = 0;
		int manganese_deposits = 0;
		int manganese_deposits1 = 0;
		int manganese_deposits2 = 0;
		int manganese_deposits3 = 0;
		int tin_deposits = 0;
		int tin_deposits1 = 0;
		int tin_deposits2 = 0;
		int tin_deposits3 = 0;
		int coal_deposits = 0;
		int coal_deposits1 = 0;
		int coal_deposits2 = 0;
		int coal_deposits3 = 0;
		int uranium_deposits = 0;
		int uranium_deposits1 = 0;
		int uranium_deposits2 = 0;
		int uranium_deposits3 = 0;
		int rhodium = 0;
		int platinum = 0;
		int gold = 0;
		int iridium = 0;
		int diamond = 0;
		int silver = 0;
		int iron = 0;
		int cooper = 0;
		int oil = 0;
		int gas = 0;
		int nickel = 0;
		int aluminium = 0;
		int manganese = 0;
		int tin = 0;
		int uranium = 0;
		int coal = 0;
		int titan = 0;
		int[] array = new int[r];
		int[] array1 = new int[r];
		int[] array2 = new int[r];
		int[] array3 = new int[r];
		int[] array4 = new int[r];

		for (int i = 1; i < r; i++) {
			array[i] = (int) (Math.random() * 6 + 1);
			array1[i] = (int) (Math.random() * 6 + 1);
			array2[i] = (int) (Math.random() * 6 + 1);
			array3[i] = (int) (Math.random() * 6 + 1);
			array4[i] = (int) (Math.random() * 6 + 1);
			if (array[i] == 6 && array1[i] == 6 && array2[i] == 6
					&& array3[i] == 5) {
				rhodium++;
			}
			if (array[i] == 5 && array1[i] == 5 && array2[i] == 5
					&& array3[i] == 5) {
				platinum++;
			}
			if (array[i] == 4 && array1[i] == 4 && array2[i] == 4) {
				gold++;
			}
			if (array[i] == 3 && array1[i] == 3 && array2[i] == 3
					&& array3[i] == 3 && array4[i] == 3) {
				iridium++;
			}
			if (array[i] == 6 && array1[i] == 6 && array2[i] == 6
					&& array3[i] == 6) {
				diamond++;
			}
			if (array[i] == 1 && array1[i] == 1 && array2[i] == 1) {
				silver++;
			}
			if (array[i] == 1 && array1[i] == 2 && array2[i] == 2) {
				oil++;
			}
			if (array[i] == 1 && array1[i] == 3 && array2[i] == 3) {
				gas++;
			}
			if (array[i] == 2 && array1[i] == 1 && array2[i] == 1) {
				iron++;
			}
			if (array[i] == 2 && array1[i] == 2 && array2[i] == 1) {
				cooper++;
			}
			if (array[i] == 2 && array1[i] == 3 && array2[i] == 1) {
				nickel++;
			}
			if (array[i] == 2 && array1[i] == 4 && array2[i] == 1) {
				aluminium++;
			}
			if (array[i] == 2 && array1[i] == 5 && array2[i] == 1) {
				manganese++;
			}
			if (array[i] == 2 && array1[i] == 6 && array2[i] == 1) {
				tin++;
			}
			if (array2[i] == 3 && array3[i] == 2 && array4[i] == 2) {
				coal++;
			}
			if (array[i] == 4 && array1[i] == 1 && array2[i] == 1
					&& array3[i] == 1) {
				uranium++;
			}
			if (array[i] == 4 && array1[i] == 4 && array2[i] == 1) {
				titan++;
			}

		}
		Log.d(LOG_TAG, "titan: " + titan);
		if (titan != 0) {
			int[] result = new int[titan];//массив result состоящий из получившихся titan чисел

			for (int i = 0; i < titan; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					titan_deposits++;
				}
				if (result[i] == 2) {
					titan_deposits1++;
				}
				if (result[i] == 3) {
					titan_deposits2++;
				}
				if (result[i] == 4) {
					titan_deposits3++;
				}
			}
			
			Log.d(LOG_TAG, "Количеcтво: " + titan);
			text = (TextView) findViewById(R.id.titan1);
			text1 = (TextView) findViewById(R.id.titan_1_2);
			text2 = (TextView) findViewById(R.id.titan_1_3);
			text3 = (TextView) findViewById(R.id.titan_1_4);
			text4 = (TextView) findViewById(R.id.titan_1_5);

			text.setText("" + titan);
			text1.setText("" + titan_deposits3);
			text2.setText("" + titan_deposits2);
			text3.setText("" + titan_deposits1);
			text4.setText("" + titan_deposits); 
			// Задайте значения для каждой строки.
			ContentValues cv = new ContentValues();// не очень понял что она делает, но как я понял через ее можно вставлять в одну строчку все значения
			cv.put("name", "Титан");			
			cv.put("count", titan);
			cv.put("giant", titan_deposits3);
			cv.put("big", titan_deposits2);
			cv.put("average", titan_deposits1);
			cv.put("small", titan_deposits);
			cv.put("total_sum", "0");
			cv.put("price", "0");
			// Вставляем данные в базу
			long rowID = db.insert("minerals", null, cv);
		      Log.d(LOG_TAG, "row inserted, ID = " + rowID);


		} else {
			text = (TextView) findViewById(R.id.titan1);
			text1 = (TextView) findViewById(R.id.titan_1_2);
			text2 = (TextView) findViewById(R.id.titan_1_3);
			text3 = (TextView) findViewById(R.id.titan_1_4);
			text4 = (TextView) findViewById(R.id.titan_1_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
			ContentValues cv = new ContentValues();
			cv.put("name", "Титан");
			cv.put("total_sum", "0");
			// Вставляем данные в базу
			long rowID = db.insert("minerals", null, cv);
		      Log.d(LOG_TAG, "row inserted, ID = " + rowID);
			
		}

		if (rhodium != 0) {

			int[] result = new int[rhodium];

			for (int i = 0; i < rhodium; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					rhodium_deposits++;
				}
				if (result[i] == 2) {
					rhodium_deposits1++;
				}
				if (result[i] == 3) {
					rhodium_deposits2++;
				}
				if (result[i] == 4) {
					rhodium_deposits3++;
				}

			}
			text = (TextView) findViewById(R.id.Rhodium_1);
			text1 = (TextView) findViewById(R.id.Rhodium_2);
			text2 = (TextView) findViewById(R.id.Rhodium_3);
			text3 = (TextView) findViewById(R.id.Rhodium_4);
			text4 = (TextView) findViewById(R.id.Rhodium_5);

			text.setText("" + rhodium);
			text1.setText("" + rhodium_deposits3);
			text2.setText("" + rhodium_deposits2);
			text3.setText("" + rhodium_deposits1);
			text4.setText("" + rhodium_deposits);
			ContentValues cv1 = new ContentValues();
			cv1.put("name", "Родий");			
			cv1.put("count", rhodium);
			cv1.put("giant", rhodium_deposits3);
			cv1.put("big", rhodium_deposits2);
			cv1.put("average", rhodium_deposits1);
			cv1.put("small", rhodium_deposits);
			cv1.put("total_sum", "0");
			// Вставляем данные в базу
			long rowID = db.insert("minerals", null, cv1);
		      Log.d(LOG_TAG, "row inserted, ID = " + rowID);

		} else {
			text = (TextView) findViewById(R.id.Rhodium_1);
			text1 = (TextView) findViewById(R.id.Rhodium_2);
			text2 = (TextView) findViewById(R.id.Rhodium_3);
			text3 = (TextView) findViewById(R.id.Rhodium_4);
			text4 = (TextView) findViewById(R.id.Rhodium_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");	
			ContentValues cv1 = new ContentValues();
			cv1.put("name", "Родий");
			cv1.put("total_sum", "0");
			// Вставляем данные в базу
			long rowID = db.insert("minerals", null, cv1);
		      Log.d(LOG_TAG, "row inserted, ID = " + rowID);
		}

		if (platinum != 0) {

			int[] result = new int[platinum];

			for (int i = 0; i < platinum; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					platinum_deposits++;
				}
				if (result[i] == 2) {
					platinum_deposits1++;
				}
				if (result[i] == 3) {
					platinum_deposits2++;
				}
				if (result[i] == 4) {
					platinum_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.platinum_1);
			text1 = (TextView) findViewById(R.id.platinum_2);
			text2 = (TextView) findViewById(R.id.platinum_3);
			text3 = (TextView) findViewById(R.id.platinum_4);
			text4 = (TextView) findViewById(R.id.platinum_5);

			text.setText("" + platinum);
			text1.setText("" + platinum_deposits3);
			text2.setText("" + platinum_deposits2);
			text3.setText("" + platinum_deposits1);
			text4.setText("" + platinum_deposits);
			ContentValues cv2 = new ContentValues();
			cv2.put("name", "Платина");			
			cv2.put("count", platinum);
			cv2.put("giant", platinum_deposits3);
			cv2.put("big", platinum_deposits2);
			cv2.put("average", platinum_deposits1);
			cv2.put("small", platinum_deposits);
			cv2.put("total_sum", "0");
			// Вставляем данные в базу
			long rowID = db.insert("minerals", null, cv2);
		      Log.d(LOG_TAG, "row inserted, ID = " + rowID);

		} else {
			text = (TextView) findViewById(R.id.platinum_1);
			text1 = (TextView) findViewById(R.id.platinum_2);
			text2 = (TextView) findViewById(R.id.platinum_3);
			text3 = (TextView) findViewById(R.id.platinum_4);
			text4 = (TextView) findViewById(R.id.platinum_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
			ContentValues cv2 = new ContentValues();
			cv2.put("name", "Платина");	
			cv2.put("total_sum", "0");
			// Вставляем данные в базу
			long rowID = db.insert("minerals", null, cv2);
		      Log.d(LOG_TAG, "row inserted, ID = " + rowID);
		}

		if (gold != 0) {
			int[] result = new int[gold];

			for (int i = 0; i < gold; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					gold_deposits++;
				}
				if (result[i] == 2) {
					gold_deposits1++;
				}
				if (result[i] == 3) {
					gold_deposits2++;
				}
				if (result[i] == 4) {
					gold_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.gold_1);
			text1 = (TextView) findViewById(R.id.gold_2);
			text2 = (TextView) findViewById(R.id.gold_3);
			text3 = (TextView) findViewById(R.id.gold_4);
			text4 = (TextView) findViewById(R.id.gold_5);

			text.setText("" + gold);
			text1.setText("" + gold_deposits3);
			text2.setText("" + gold_deposits2);
			text3.setText("" + gold_deposits1);
			text4.setText("" + gold_deposits);

		} else {
			text = (TextView) findViewById(R.id.gold_1);
			text1 = (TextView) findViewById(R.id.gold_2);
			text2 = (TextView) findViewById(R.id.gold_3);
			text3 = (TextView) findViewById(R.id.gold_4);
			text4 = (TextView) findViewById(R.id.gold_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (iridium != 0) {
			int[] result = new int[iridium];

			for (int i = 0; i < iridium; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					iridium_deposits++;
				}
				if (result[i] == 2) {
					iridium_deposits1++;
				}
				if (result[i] == 3) {
					iridium_deposits2++;
				}
				if (result[i] == 4) {
					iridium_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.iridium_1);
			text1 = (TextView) findViewById(R.id.iridium_2);
			text2 = (TextView) findViewById(R.id.iridium_3);
			text3 = (TextView) findViewById(R.id.iridium_4);
			text4 = (TextView) findViewById(R.id.iridium_5);

			text.setText("" + iridium);
			text1.setText("" + iridium_deposits3);
			text2.setText("" + iridium_deposits2);
			text3.setText("" + iridium_deposits1);
			text4.setText("" + iridium_deposits);
		} else {
			text = (TextView) findViewById(R.id.iridium_1);
			text1 = (TextView) findViewById(R.id.iridium_2);
			text2 = (TextView) findViewById(R.id.iridium_3);
			text3 = (TextView) findViewById(R.id.iridium_4);
			text4 = (TextView) findViewById(R.id.iridium_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (diamond != 0) {
			int[] result = new int[diamond];

			for (int i = 0; i < diamond; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					diamond_deposits++;
				}
				if (result[i] == 2) {
					diamond_deposits1++;
				}
				if (result[i] == 3) {
					diamond_deposits2++;
				}
				if (result[i] == 4) {
					diamond_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.diamond_1);
			text1 = (TextView) findViewById(R.id.diamond_2);
			text2 = (TextView) findViewById(R.id.diamond_3);
			text3 = (TextView) findViewById(R.id.diamond_4);
			text4 = (TextView) findViewById(R.id.diamond_5);

			text.setText("" + diamond);
			text1.setText("" + diamond_deposits3);
			text2.setText("" + diamond_deposits2);
			text3.setText("" + diamond_deposits1);
			text4.setText("" + diamond_deposits);
		} else {
			text = (TextView) findViewById(R.id.diamond_1);
			text1 = (TextView) findViewById(R.id.diamond_2);
			text2 = (TextView) findViewById(R.id.diamond_3);
			text3 = (TextView) findViewById(R.id.diamond_4);
			text4 = (TextView) findViewById(R.id.diamond_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (silver != 0) {
			int[] result = new int[silver];

			for (int i = 0; i < silver; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					silver_deposits++;
				}
				if (result[i] == 2) {
					silver_deposits1++;
				}
				if (result[i] == 3) {
					silver_deposits2++;
				}
				if (result[i] == 4) {
					silver_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.silver_1);
			text1 = (TextView) findViewById(R.id.silver_2);
			text2 = (TextView) findViewById(R.id.silver_3);
			text3 = (TextView) findViewById(R.id.silver_4);
			text4 = (TextView) findViewById(R.id.silver_5);

			text.setText("" + silver);
			text1.setText("" + silver_deposits3);
			text2.setText("" + silver_deposits2);
			text3.setText("" + silver_deposits1);
			text4.setText("" + silver_deposits);
		} else {
			text = (TextView) findViewById(R.id.silver_1);
			text1 = (TextView) findViewById(R.id.silver_2);
			text2 = (TextView) findViewById(R.id.silver_3);
			text3 = (TextView) findViewById(R.id.silver_4);
			text4 = (TextView) findViewById(R.id.silver_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (iron != 0) {
			int[] result = new int[iron];

			for (int i = 0; i < iron; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					iron_deposits++;
				}
				if (result[i] == 2) {
					iron_deposits1++;
				}
				if (result[i] == 3) {
					iron_deposits2++;
				}
				if (result[i] == 4) {
					iron_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.iron_1);
			text1 = (TextView) findViewById(R.id.iron_2);
			text2 = (TextView) findViewById(R.id.iron_3);
			text3 = (TextView) findViewById(R.id.iron_4);
			text4 = (TextView) findViewById(R.id.iron_5);

			text.setText("" + iron);
			text1.setText("" + iron_deposits3);
			text2.setText("" + iron_deposits2);
			text3.setText("" + iron_deposits1);
			text4.setText("" + iron_deposits);
		} else {
			text = (TextView) findViewById(R.id.iron_1);
			text1 = (TextView) findViewById(R.id.iron_2);
			text2 = (TextView) findViewById(R.id.iron_3);
			text3 = (TextView) findViewById(R.id.iron_4);
			text4 = (TextView) findViewById(R.id.iron_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (cooper != 0) {
			int[] result = new int[cooper];

			for (int i = 0; i < cooper; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					cooper_deposits++;
				}
				if (result[i] == 2) {
					cooper_deposits1++;
				}
				if (result[i] == 3) {
					cooper_deposits2++;
				}
				if (result[i] == 4) {
					cooper_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.cooper_1);
			text1 = (TextView) findViewById(R.id.cooper_2);
			text2 = (TextView) findViewById(R.id.cooper_3);
			text3 = (TextView) findViewById(R.id.cooper_4);
			text4 = (TextView) findViewById(R.id.cooper_5);

			text.setText("" + cooper);
			text1.setText("" + cooper_deposits3);
			text2.setText("" + cooper_deposits2);
			text3.setText("" + cooper_deposits1);
			text4.setText("" + cooper_deposits);
		} else {
			text = (TextView) findViewById(R.id.cooper_1);
			text1 = (TextView) findViewById(R.id.cooper_2);
			text2 = (TextView) findViewById(R.id.cooper_3);
			text3 = (TextView) findViewById(R.id.cooper_4);
			text4 = (TextView) findViewById(R.id.cooper_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (oil != 0) {
			int[] result = new int[oil];

			for (int i = 0; i < oil; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					oil_deposits++;
				}
				if (result[i] == 2) {
					oil_deposits1++;
				}
				if (result[i] == 3) {
					oil_deposits2++;
				}
				if (result[i] == 4) {
					oil_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.oil_1);
			text1 = (TextView) findViewById(R.id.oil_2);
			text2 = (TextView) findViewById(R.id.oil_3);
			text3 = (TextView) findViewById(R.id.oil_4);
			text4 = (TextView) findViewById(R.id.oil_5);

			text.setText("" + oil);
			text1.setText("" + oil_deposits3);
			text2.setText("" + oil_deposits2);
			text3.setText("" + oil_deposits1);
			text4.setText("" + oil_deposits);
		} else {
			text = (TextView) findViewById(R.id.oil_1);
			text1 = (TextView) findViewById(R.id.oil_2);
			text2 = (TextView) findViewById(R.id.oil_3);
			text3 = (TextView) findViewById(R.id.oil_4);
			text4 = (TextView) findViewById(R.id.oil_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (gas != 0) {
			int[] result = new int[gas];

			for (int i = 0; i < gas; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					gas_deposits++;
				}
				if (result[i] == 2) {
					gas_deposits1++;
				}
				if (result[i] == 3) {
					gas_deposits2++;
				}
				if (result[i] == 4) {
					gas_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.gas_1);
			text1 = (TextView) findViewById(R.id.gas_2);
			text2 = (TextView) findViewById(R.id.gas_3);
			text3 = (TextView) findViewById(R.id.gas_4);
			text4 = (TextView) findViewById(R.id.gas_5);

			text.setText("" + gas);
			text1.setText("" + gas_deposits3);
			text2.setText("" + gas_deposits2);
			text3.setText("" + gas_deposits1);
			text4.setText("" + gas_deposits);
		} else {
			text = (TextView) findViewById(R.id.gas_1);
			text1 = (TextView) findViewById(R.id.gas_2);
			text2 = (TextView) findViewById(R.id.gas_3);
			text3 = (TextView) findViewById(R.id.gas_4);
			text4 = (TextView) findViewById(R.id.gas_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (nickel != 0) {
			int[] result = new int[nickel];

			for (int i = 0; i < nickel; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					nickel_deposits++;
				}
				if (result[i] == 2) {
					nickel_deposits1++;
				}
				if (result[i] == 3) {
					nickel_deposits2++;
				}
				if (result[i] == 4) {
					nickel_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.nickel_1);
			text1 = (TextView) findViewById(R.id.nickel_2);
			text2 = (TextView) findViewById(R.id.nickel_3);
			text3 = (TextView) findViewById(R.id.nickel_4);
			text4 = (TextView) findViewById(R.id.nickel_5);

			text.setText("" + nickel);
			text1.setText("" + nickel_deposits3);
			text2.setText("" + nickel_deposits2);
			text3.setText("" + nickel_deposits1);
			text4.setText("" + nickel_deposits);
		} else {
			text = (TextView) findViewById(R.id.nickel_1);
			text1 = (TextView) findViewById(R.id.nickel_2);
			text2 = (TextView) findViewById(R.id.nickel_3);
			text3 = (TextView) findViewById(R.id.nickel_4);
			text4 = (TextView) findViewById(R.id.nickel_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (aluminium != 0) {
			int[] result = new int[aluminium];

			for (int i = 0; i < aluminium; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					aluminium_deposits++;
				}
				if (result[i] == 2) {
					aluminium_deposits1++;
				}
				if (result[i] == 3) {
					aluminium_deposits2++;
				}
				if (result[i] == 4) {
					aluminium_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.aluminium_1);
			text1 = (TextView) findViewById(R.id.aluminium_2);
			text2 = (TextView) findViewById(R.id.aluminium_3);
			text3 = (TextView) findViewById(R.id.aluminium_4);
			text4 = (TextView) findViewById(R.id.aluminium_5);

			text.setText("" + aluminium);
			text1.setText("" + aluminium_deposits3);
			text2.setText("" + aluminium_deposits2);
			text3.setText("" + aluminium_deposits1);
			text4.setText("" + aluminium_deposits);
		} else {
			text = (TextView) findViewById(R.id.aluminium_1);
			text1 = (TextView) findViewById(R.id.aluminium_2);
			text2 = (TextView) findViewById(R.id.aluminium_3);
			text3 = (TextView) findViewById(R.id.aluminium_4);
			text4 = (TextView) findViewById(R.id.aluminium_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (manganese != 0) {
			int[] result = new int[manganese];

			for (int i = 0; i < manganese; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					manganese_deposits++;
				}
				if (result[i] == 2) {
					manganese_deposits1++;
				}
				if (result[i] == 3) {
					manganese_deposits2++;
				}
				if (result[i] == 4) {
					manganese_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.manganese_1);
			text1 = (TextView) findViewById(R.id.manganese_2);
			text2 = (TextView) findViewById(R.id.manganese_3);
			text3 = (TextView) findViewById(R.id.manganese_4);
			text4 = (TextView) findViewById(R.id.manganese_5);

			text.setText("" + manganese);
			text1.setText("" + manganese_deposits3);
			text2.setText("" + manganese_deposits2);
			text3.setText("" + manganese_deposits1);
			text4.setText("" + manganese_deposits);
		} else {
			text = (TextView) findViewById(R.id.manganese_1);
			text1 = (TextView) findViewById(R.id.manganese_2);
			text2 = (TextView) findViewById(R.id.manganese_3);
			text3 = (TextView) findViewById(R.id.manganese_4);
			text4 = (TextView) findViewById(R.id.manganese_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (tin != 0) {
			int[] result = new int[tin];

			for (int i = 0; i < tin; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					tin_deposits++;
				}
				if (result[i] == 2) {
					tin_deposits1++;
				}
				if (result[i] == 3) {
					tin_deposits2++;
				}
				if (result[i] == 4) {
					tin_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.tin_1);
			text1 = (TextView) findViewById(R.id.tin_2);
			text2 = (TextView) findViewById(R.id.tin_3);
			text3 = (TextView) findViewById(R.id.tin_4);
			text4 = (TextView) findViewById(R.id.tin_5);

			text.setText("" + tin);
			text1.setText("" + tin_deposits3);
			text2.setText("" + tin_deposits2);
			text3.setText("" + tin_deposits1);
			text4.setText("" + tin_deposits);
		} else {
			text = (TextView) findViewById(R.id.tin_1);
			text1 = (TextView) findViewById(R.id.tin_2);
			text2 = (TextView) findViewById(R.id.tin_3);
			text3 = (TextView) findViewById(R.id.tin_4);
			text4 = (TextView) findViewById(R.id.tin_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (coal != 0) {
			int[] result = new int[coal];

			for (int i = 0; i < coal; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					coal_deposits++;
				}
				if (result[i] == 2) {
					coal_deposits1++;
				}
				if (result[i] == 3) {
					coal_deposits2++;
				}
				if (result[i] == 4) {
					coal_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.coal_1);
			text1 = (TextView) findViewById(R.id.coal_2);
			text2 = (TextView) findViewById(R.id.coal_3);
			text3 = (TextView) findViewById(R.id.coal_4);
			text4 = (TextView) findViewById(R.id.coal_5);

			text.setText("" + coal);
			text1.setText("" + coal_deposits3);
			text2.setText("" + coal_deposits2);
			text3.setText("" + coal_deposits1);
			text4.setText("" + coal_deposits);
		} else {
			text = (TextView) findViewById(R.id.coal_1);
			text1 = (TextView) findViewById(R.id.coal_2);
			text2 = (TextView) findViewById(R.id.coal_3);
			text3 = (TextView) findViewById(R.id.coal_4);
			text4 = (TextView) findViewById(R.id.coal_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		if (uranium != 0) {
			int[] result = new int[uranium];

			for (int i = 0; i < uranium; i++) {
				result[i] = (int) (Math.random() * 4 + 1);
				if (result[i] == 1) {
					uranium_deposits++;
				}
				if (result[i] == 2) {
					uranium_deposits1++;
				}
				if (result[i] == 3) {
					uranium_deposits2++;
				}
				if (result[i] == 4) {
					uranium_deposits3++;
				}
			}
			text = (TextView) findViewById(R.id.uranium_1);
			text1 = (TextView) findViewById(R.id.uranium_2);
			text2 = (TextView) findViewById(R.id.uranium_3);
			text3 = (TextView) findViewById(R.id.uranium_4);
			text4 = (TextView) findViewById(R.id.uranium_5);

			text.setText("" + uranium);
			text1.setText("" + uranium_deposits3);
			text2.setText("" + uranium_deposits2);
			text3.setText("" + uranium_deposits1);
			text4.setText("" + uranium_deposits);
		} else {
			text = (TextView) findViewById(R.id.uranium_1);
			text1 = (TextView) findViewById(R.id.uranium_2);
			text2 = (TextView) findViewById(R.id.uranium_3);
			text3 = (TextView) findViewById(R.id.uranium_4);
			text4 = (TextView) findViewById(R.id.uranium_5);

			text.setText("-");
			text1.setText("-");
			text2.setText("-");
			text3.setText("-");
			text4.setText("-");
		}
		Log.d(LOG_TAG, "titan: " + titan_deposits);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, MENU_QUIT, 0, "Выход");
		menu.add(0, MENU_BUILD, 0, "Строить");
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case MENU_QUIT:
			// выход из приложения
			finish();
			break;
		case MENU_BUILD:		
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);			
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}

