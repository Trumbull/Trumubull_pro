package ru.trumbull_pro.rebulicv_1_0.fragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ru.trumbull_pro.rebulicv_1_0.R;

public class Price extends Fragment {
	JSONParser jParser = new JSONParser();

	// укажите свой адрес
	private static String url_all_products = "http://192.168.1.34/get_all_products.php";

	// Имена узлов JSON
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "price";
	private static final String TAG_NAME = "name";
	private static final String TAG_PRICE = "price_m";
	private static final String TAG_CHANGE_PRICE = "change_price";
	private static final String TAG_TIME = "time";

	// массив товаров JSONArray
	JSONArray price = null;
	int flag = 0;

	boolean status;
	TaskPrice tp, tp1;
	Timer timer;
	TimerTask timerTask;
	Animation anim, anim_ghost;
	final String LOG_TAG = "myLogs";
	final Handler handler = new Handler();
	private static final int MENU_NEW_TRADE = 1;
	private static final int MENU_OPEN_CHART = 0;
	TextView quotes_cooper, quotes_cooper_1, quotes_cooper_2, price_change,
			quotes_titan_1, quotes_titan_2;
	RelativeLayout copper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.price, container, false);
		quotes_cooper = (TextView) view.findViewById(R.id.id_order);
		quotes_cooper_1 = (TextView) view.findViewById(R.id.view_order);
		quotes_cooper_2 = (TextView) view.findViewById(R.id.end_price);

		quotes_titan_1 = (TextView) view.findViewById(R.id.quotes_titan_1);
		quotes_titan_2 = (TextView) view.findViewById(R.id.quotes_titan_2);

		price_change = (TextView) view.findViewById(R.id.price_change);
		price_change.setVisibility(View.INVISIBLE);
		copper = (RelativeLayout) view.findViewById(R.id.copper);
		anim = AnimationUtils.loadAnimation(getActivity(), R.anim.myalpha);
		anim_ghost = AnimationUtils.loadAnimation(getActivity(), R.anim.ghost);

		registerForContextMenu(copper);
		quotes_cooper.setText(Html.fromHtml("Медь" + "<br />"));

		// регулятор рыночной цены металлов в программе БП "Бизнес" Ver.1.2
		// Долгушин В.А. 27.06.15
		// БД биржы хранится на сервере

		tp = new TaskPrice();
		tp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		return view;
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.copper:
			menu.setHeaderTitle(Html.fromHtml("<font color=#2c2c2c>" + "Медь"
					+ "</font>"));

			menu.add(0, MENU_NEW_TRADE, 0, "Новая сделка");
			menu.add(0, MENU_OPEN_CHART, 0, "Открыть график");
			break;
		}
	}

	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case MENU_NEW_TRADE:
			Toast.makeText(getActivity(), "Выбран пункт А", Toast.LENGTH_LONG)
					.show();
			return true;
		case MENU_OPEN_CHART:

			return true;
		}
		return super.onContextItemSelected(item);
	}

	private class TaskPrice extends AsyncTask<String, String, String> {
		// Generates dummy data in a non-ui thread
		@Override
		protected String doInBackground(String... args) {
			DecimalFormatSymbols format = new DecimalFormatSymbols();
			format.setDecimalSeparator('.');
			DecimalFormat format_number = new DecimalFormat("#,##0.00", format);
			String[] values = new String[3];
			int time = 0;
			int flag_update = 0;// перменная отвечающая за первую цену
								// отображаемую на экране
			double min_price = 0;
			double max_price = 0;
			double base_price;// начальная цена
			double price_comparison = 0; // старая цена, используется для
											// сравнения
			int flag_price_comparison = 0;
			// Строим параметры
			for(;;){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// получим строку JSON из URL
				JSONObject json = jParser.makeHttpRequest(url_all_products,
						"GET", params);

				// Check your log cat for JSON reponse
				//Log.d("All Products: ", json.toString());

				try {

					// Проверяем переменную SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// товар найден
						// получаем массив товаров
						price = json.getJSONArray(TAG_PRODUCTS);
						// проходим в цикле через все товары
						for (int i = 0; i < price.length(); i++) {
							JSONObject c = price.getJSONObject(i);

							// сохраняем каждый json-элемент в переменной;
							String name = c.getString(TAG_NAME);
							String price_copper = c.getString(TAG_PRICE);
							String change_price = c.getString(TAG_CHANGE_PRICE);
							time = c.getInt(TAG_TIME);

							min_price = new Double(format_number.format(Double
									.valueOf(price_copper) - 0.1));
							max_price = new Double(format_number.format(Double
									.valueOf(price_copper) + 0.1)); // 0.1
																	// разница
																	// цену
																	// продажи и
																	// покупки,
																	// по
																	// умолчанию.
																	// Надо
																	// редактировать.
							base_price = Double.valueOf(price_copper);
							if (price_comparison == base_price) {// сравниваем
																	// цену
																	// старую и
																	// новую,
																	// еслиони
																	// одинаковые
																	// запускаем
																	// счетчик
								time = 2;
								flag_price_comparison++;
								change_price = "0";
								Log.d(LOG_TAG, "Биржа не работает: "
										+ flag_price_comparison);
							}
							price_comparison = base_price;

							values[0] = Double.toString(min_price);
							values[1] = Double.toString(max_price);
							values[2] = change_price;

							Log.d(LOG_TAG, "Имя: " + name);
							Log.d(LOG_TAG, "Цена: " + price_copper);
							Log.d(LOG_TAG, "Время: " + time);
							Log.d(LOG_TAG, "Флаг апдейта: " + flag_update);
						}
					}
					if (flag_update == 0) {// начальная цена
						flag_update++;

					} else {

						try {
							TimeUnit.SECONDS.sleep(time);
						} catch (InterruptedException e) {
							// TODO Автоматически созданный блок catch
							e.printStackTrace();
						}
						publishProgress(values);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}


		}

		// Обновлятор
		@Override
		protected void onProgressUpdate(String... values) {
			Double flag = Double.valueOf(values[2]);
			Log.d(LOG_TAG, "Изменение: " + flag);
			if (flag < 0) {
				quotes_cooper_1.setTextColor(Color.RED);
				quotes_cooper_2.setTextColor(Color.RED);
				price_change.setTextColor(Color.RED);
				price_change.startAnimation(anim_ghost);
				price_change.setText(values[2]);
				quotes_cooper_1.startAnimation(anim);
				quotes_cooper_1.setText(Html.fromHtml(values[0] + "<br />"));
				quotes_cooper_2.startAnimation(anim);
				quotes_cooper_2.setText(Html.fromHtml(values[1] + "<br />"));
			} else {
				if (flag == 0) {// начальная цена
					quotes_cooper_1.setTextColor(Color.BLACK);
					quotes_cooper_2.setTextColor(Color.BLACK);
					quotes_cooper_1
							.setText(Html.fromHtml(values[0] + "<br />"));
					quotes_cooper_2
							.setText(Html.fromHtml(values[1] + "<br />"));
				} else {
					quotes_cooper_1.setTextColor(Color.BLUE);
					quotes_cooper_2.setTextColor(Color.BLUE);
					price_change.setTextColor(Color.GREEN);
					price_change.startAnimation(anim_ghost);
					price_change.setText("+" + values[2]);
					quotes_cooper_1.startAnimation(anim);
					quotes_cooper_1
							.setText(Html.fromHtml(values[0] + "<br />"));
					quotes_cooper_2.startAnimation(anim);
					quotes_cooper_2
							.setText(Html.fromHtml(values[1] + "<br />"));
				}

			}
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			tp.cancel(false);
			Log.d(LOG_TAG, "Это в середине AsyncTask : " + tp);
			Toast.makeText(getActivity(), "Биржа не доступна!",
					Toast.LENGTH_LONG).show();
			Log.d(LOG_TAG, "Статус задачи: " + tp.getStatus().toString());
		}

		protected void onCancelled() {
			super.onCancelled();
			Log.d(LOG_TAG, "В конце AsyncTask : " + tp);
			Log.d(LOG_TAG, "Статус задачи: " + tp.getStatus().toString());

		}

	}

}