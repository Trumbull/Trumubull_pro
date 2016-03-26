package ru.trumbull_pro.rebulicv_1_0;

import ru.trumbull_pro.rebublicv_1_0.price_trade.Trade_copper;
import ru.trumbull_pro.rebulicv_1_0.fragment.JSONParser;
import ru.trumbull_pro.rebulicv_1_0.fragment.Open_order;
import ru.trumbull_pro.rebulicv_1_0.fragment.Price;
import ru.trumbull_pro.rebulicv_1_0.fragment.Price_titan;
import ru.trumbull_pro.rebulicv_1_0.fragment.Transaction_order;
import ru.trumbull_pro.rebulicv_1_0.graph_cooper.DynamicGraph_Cooper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Bourse_main extends Activity implements OnClickListener {
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// укажите свой адрес
	private static final String url_order_id = "http://192.168.1.35/order_id.php";

	// Имена узлов JSON
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TRANSACTION = "transaction";
	private static final String TAG_ID = "id";

	private android.app.FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransaction;
	Fragment frag2 = new Price();
	Fragment frag3 = new Price_titan();
	Fragment frag_order = new Transaction_order();
	Fragment open_order= new Open_order();
	FragmentTransaction ft;
	final int MENU_NAME = 0;
	final int MENU_NEW_TRADE = 1;
	final int MENU_OPEN_CHART = 2;
	ImageButton menu_button;
	TableRow p1, p2, p3, p4;
	TextView quotes_cooper, quotes_cooper_1_1, quotes_cooper_1, quotes_cooper_2;// котировки
	final String LOG_TAG = "myLogs";
	LinearLayout content, menu;
	LinearLayout.LayoutParams contentParams;
	TranslateAnimation slide;
	int flag;
	int marginX, animateFromX, animateToX = 0;
	boolean menuOpen = false;
	String[] paragraphs;
	String id_order;
	int[] id_order_massiv = new int[15]; // массив id из таблицы транзакций

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bourse_main);

		paragraphs = getResources().getStringArray(R.array.lorem);

		menu = (LinearLayout) findViewById(R.id.menu);

		content = (LinearLayout) findViewById(R.id.content);
		contentParams = (LinearLayout.LayoutParams) content.getLayoutParams();
		contentParams.width = getWindowManager().getDefaultDisplay().getWidth(); // Обеспечивает
																					// постоянную
																					// ширину
																					// в
																					// контекстном
																					// меню
		contentParams.leftMargin = -(menu.getLayoutParams().width); // Позиция
																	// содержания
																	// в
																	// начальном
																	// экране
		content.setLayoutParams(contentParams);

		menu_button = (ImageButton) findViewById(R.id.menu_button);
		menu_button.setOnClickListener(this);

		p1 = (TableRow) findViewById(R.id.p1);
		p1.setOnClickListener(this);

		p2 = (TableRow) findViewById(R.id.p2);
		p2.setOnClickListener(this);

		p3 = (TableRow) findViewById(R.id.p3);
		p3.setOnClickListener(this);

		p4 = (TableRow) findViewById(R.id.p4);
		p4.setOnClickListener(this);
		mFragmentManager = getFragmentManager();
		new Order_massiv().execute(); //собираем в массив информацию о сделках,с привязкой к IMEI 
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (menuOpen) {
				slideMenuIn(0, -(menu.getLayoutParams().width), -(menu.getLayoutParams().width)); // Pass
																									// slide
																									// in
																									// paramters
				menuOpen = false;
				return true;
			}
		}
		return super.onKeyDown(keyCode, keyEvent);
	}

	public void onClick(View v) {
		ft = getFragmentManager().beginTransaction();
		switch (v.getId()) {

		case R.id.p1:
			if (flag == 0) {
				ft.add(R.id.fragment1, frag2);
				ft.add(R.id.fragment2, frag3);
				flag++;
			} else {
				ft.show(frag2);
				ft.show(frag3);
			}

			Log.d(LOG_TAG, "flag= " + flag);
			break;
		case R.id.p2:
			if (flag == 1) {
				ft.hide(frag2);
				ft.hide(frag3);
			}
			Log.d(LOG_TAG, "flag= " + flag);
			Intent intent1 = new Intent(this, DynamicGraph_Cooper.class);
			startActivity(intent1);
			break;
		case R.id.p3:
			if (flag == 1) {
				ft.hide(frag2);
				ft.hide(frag3);
			}

			Log.d(LOG_TAG, "flag= " + flag);
			Intent intent2 = new Intent(this, Trade_copper.class);
			startActivity(intent2);
			break;
		case R.id.p4: 
			if (flag == 1) {
				ft.hide(frag2);
				ft.hide(frag3);
			}
			//new Order_massiv().execute();
			new ButtonListener_Open_Order_1(String.valueOf(id_order_massiv[0]));
			
			break;

		}

		if (contentParams.leftMargin == -(menu.getLayoutParams().width)) { // Menu
																			// is
																			// hidden
																			// (slide
																			// out
																			// parameters)
			animateFromX = 0;
			animateToX = (menu.getLayoutParams().width);
			marginX = 0;
			menuOpen = true;
		} else { // Menu is visible (slide in parameter)
			animateFromX = 0;
			animateToX = -(menu.getLayoutParams().width);
			marginX = -(menu.getLayoutParams().width);
			menuOpen = false;
		}
		slideMenuIn(animateFromX, animateToX, marginX);

		// fTrans.commit();
		ft.commit();
	}

	public void slideMenuIn(int animateFromX, int animateToX, final int marginX) {
		slide = new TranslateAnimation(animateFromX, animateToX, 0, 0);
		slide.setDuration(300);
		slide.setFillEnabled(true);
		slide.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) { // Make movement
																// of content
																// permanent
																// after
																// animation has
																// completed
				contentParams.setMargins(marginX, 0, 0, 0); // by positioning
															// its left margin
				content.setLayoutParams(contentParams);
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationStart(Animation animation) {
			}
		});
		content.startAnimation(slide); // Slide menu in or out
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		// пункты меню для cooper
		case MENU_NEW_TRADE:

			break;
		case MENU_OPEN_CHART:

			break;
		// пункты меню для cooper
		}
		return super.onContextItemSelected(item);
	}

	class Order_massiv extends AsyncTask<String, String, String> {
		// Запускаем Progress Dialog
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Bourse_main.this);
			pDialog.setMessage("Загружается информация о сделках. Подождите...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();			
		}

		protected String doInBackground(String... args) {
			// проверяем тег success
			int success;
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String devicIMEI = telephonyManager.getDeviceId();
			// Log.d(LOG_TAG, "IMEI: " + devicIMEI);

			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("identification_user", devicIMEI));

				// получаем информацию через запрос HTTP GET
				JSONObject json = jsonParser.makeHttpRequest(url_order_id, "GET", params);

				// ответ от json о товаре
				// Log.d("Запрос: ", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// если получили информацию о товаре
					JSONArray orderObj = json.getJSONArray(TAG_TRANSACTION);
					// получим первый объект из массива JSON Array
					Log.d(LOG_TAG, "длина: " + orderObj.length());
					for (int i = 0; i < orderObj.length(); i++) {
						JSONObject order = orderObj.getJSONObject(i);
						id_order = order.getString(TAG_ID);
						//Log.d(LOG_TAG, "ID: " + Integer.valueOf(id_order));
						id_order_massiv[i] = Integer.valueOf(id_order);

					}
				} else {
					// не нашли товар по pid
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// закрываем диалоговое окно с индикатором
			pDialog.dismiss();
			for (int i = 0; i < 15; i++) {
				Log.d(LOG_TAG, "ID записей: " + id_order_massiv[i]);
			}
		}
	}
	
class ButtonListener_Open_Order_1{
		
		private String testText;
		
		public ButtonListener_Open_Order_1(String testText) {
			super();
			this.testText = testText;
			android.app.FragmentManager manager = getFragmentManager();
			FragmentTransaction transact = manager.beginTransaction();
			Bundle args = new Bundle();
			args.putString("test_text", testText);
			open_order.setArguments(args);
			transact.add(R.id.fragment1, open_order);
			transact.addToBackStack(null);
			transact.commit();
			manager.executePendingTransactions();
		}

			
		
		
	}
	
}