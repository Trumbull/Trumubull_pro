package ru.trumbull_pro.rebulicv_1_0.fragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
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
import ru.trumbull_pro.rebulicv_1_0.DatabaseHelper;
import ru.trumbull_pro.rebulicv_1_0.R;

public class Price_titan extends Fragment {
	JSONParser jParser = new JSONParser();

	// укажите свой адрес
	private static String all_price_titan = "http://192.168.1.35/titan_price.php";

	// Имена узлов JSON
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB_PRICE = "price";
	private static final String TAG_NAME = "name";
	private static final String TAG_PRICE = "price_m";
	private static final String TAG_CHANGE_PRICE = "change_price";
	private static final String TAG_TIME = "time";

	// массив товаров JSONArray
	JSONArray price = null;
	int flag = 0;
	DatabaseHelper dbHelper;
	SQLiteDatabase db;
	TaskPriceTitan tp_titan;
	Timer timer;
	TimerTask timerTask;
	int time;
	double level_graph_titan = 0;
	Animation anim, anim_ghost;
	final String LOG_TAG = "myLogs";
	final Handler handler = new Handler();
	private static final int MENU_NEW_TRADE = 3;
	private static final int MENU_OPEN_CHART = 2;
	TextView quotes_titan, quotes_titan_1, quotes_titan_2, price_change;
	RelativeLayout titan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.price_titan, container, false);
		quotes_titan = (TextView) view.findViewById(R.id.quotes_titan);
		quotes_titan_1 = (TextView) view.findViewById(R.id.quotes_titan_1);
		quotes_titan_2 = (TextView) view.findViewById(R.id.quotes_titan_2);

		price_change = (TextView) view.findViewById(R.id.price_change_titan);
		price_change.setVisibility(View.INVISIBLE);
		titan = (RelativeLayout) view.findViewById(R.id.titan);
		anim = AnimationUtils.loadAnimation(getActivity(), R.anim.myalpha);
		anim_ghost = AnimationUtils.loadAnimation(getActivity(), R.anim.ghost);
		registerForContextMenu(titan);
		quotes_titan.setText(Html.fromHtml("Титан" + "<br />"));

		// регулятор рыночной цены металлов в программе БП "Бизнес" Ver.1.1
		// (устар.)Долгушин В.А. 27.06.15
		// Анализатор биржевой активности в программе БП "Бизнес" Ver. 1.4
		// Долгушин В.А. 1.11.15
		tp_titan = new TaskPriceTitan();
		tp_titan.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		return view;
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.titan:
			menu.setHeaderTitle(Html.fromHtml("<font color=#2c2c2c>" + "Титан" + "</font>"));

			menu.add(0, MENU_NEW_TRADE, 0, "Новая сделка");
			menu.add(0, MENU_OPEN_CHART, 0, "Открыть график");
			break;
		}
	}

	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case MENU_NEW_TRADE:
			Toast.makeText(getActivity(), "Новая сделка", Toast.LENGTH_LONG).show();
			return true;
		case MENU_OPEN_CHART:

			return true;
		}
		return super.onContextItemSelected(item);
	}

	private class TaskPriceTitan extends AsyncTask<String, String, String> {
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
			for (;;) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// получим строку JSON из URL
				JSONObject json = jParser.makeHttpRequest(all_price_titan, "GET", params);

				// Check your log cat for JSON reponse
				// Log.d("All Products: ", json.toString());

				try {

					// Проверяем переменную SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// товар найден
						// получаем массив товаров
						price = json.getJSONArray(TAG_TAB_PRICE);
						// проходим в цикле через все товары
						for (int i = 0; i < price.length(); i++) {
							JSONObject c = price.getJSONObject(i);

							// сохраняем каждый json-элемент в переменной;
							String name = c.getString(TAG_NAME);
							String price_titan = c.getString(TAG_PRICE);
							String change_price = c.getString(TAG_CHANGE_PRICE);
							time = c.getInt(TAG_TIME);

							min_price = new Double(format_number.format(Double.valueOf(price_titan) - 0.1));
							max_price = new Double(format_number.format(Double.valueOf(price_titan) + 0.1)); // 0.1
																												// разница
																												// цену
																												// продажи
																												// и
																												// покупки,
																												// по
																												// умолчанию.
																												// Надо
																												// редактировать.
							base_price = Double.valueOf(price_titan);
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
								Log.d(LOG_TAG, "Биржа не работает: " + flag_price_comparison);
							}
							price_comparison = base_price;

							values[0] = Double.toString(min_price);
							values[1] = Double.toString(max_price);
							values[2] = change_price;

							Log.d(LOG_TAG, "Имя: " + name);
							Log.d(LOG_TAG, "Цена: " + price_titan);
							Log.d(LOG_TAG, "Время: " + time);
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
			if (flag < 0) {
				quotes_titan_1.setTextColor(Color.RED);
				quotes_titan_2.setTextColor(Color.RED);
				price_change.setTextColor(Color.RED);
				price_change.startAnimation(anim_ghost);
				price_change.setText(values[2]);
				quotes_titan_1.startAnimation(anim);
				quotes_titan_1.setText(Html.fromHtml(values[0] + "<br />"));
				quotes_titan_2.startAnimation(anim);
				quotes_titan_2.setText(Html.fromHtml(values[1] + "<br />"));
			} else {
				if (flag == 0) {// начальная цена
					quotes_titan_1.setTextColor(Color.BLACK);
					quotes_titan_2.setTextColor(Color.BLACK);
					quotes_titan_1.setText(Html.fromHtml(values[0] + "<br />"));
					quotes_titan_2.setText(Html.fromHtml(values[1] + "<br />"));
				} else {
					quotes_titan_1.setTextColor(Color.BLUE);
					quotes_titan_2.setTextColor(Color.BLUE);
					price_change.setTextColor(Color.GREEN);
					price_change.startAnimation(anim_ghost);
					price_change.setText("+" + values[2]);
					quotes_titan_1.startAnimation(anim);
					quotes_titan_1.setText(Html.fromHtml(values[0] + "<br />"));
					quotes_titan_2.startAnimation(anim);
					quotes_titan_2.setText(Html.fromHtml(values[1] + "<br />"));
				}

			}
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			tp_titan.cancel(false);
			Log.d(LOG_TAG, "Это в середине AsyncTask : " + tp_titan);
			Toast.makeText(getActivity(), "Биржа не доступна!", Toast.LENGTH_LONG).show();
			Log.d(LOG_TAG, "Статус задачи: " + tp_titan.getStatus().toString());
		}

		protected void onCancelled() {
			super.onCancelled();
			Log.d(LOG_TAG, "В конце AsyncTask : " + tp_titan);
			Log.d(LOG_TAG, "Статус задачи: " + tp_titan.getStatus().toString());

		}

	}

}