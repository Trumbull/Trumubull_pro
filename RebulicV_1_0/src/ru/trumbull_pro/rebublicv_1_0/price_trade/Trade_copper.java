package ru.trumbull_pro.rebublicv_1_0.price_trade;

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

import ru.trumbull_pro.rebulicv_1_0.Bourse_main;
import ru.trumbull_pro.rebulicv_1_0.R;
import ru.trumbull_pro.rebulicv_1_0.fragment.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Trade_copper extends Activity {
	JSONParser jParser = new JSONParser();
	JSONParser jsonParser = new JSONParser();
	private static String transaction = "http://192.168.1.33/create_product.php";
	private static String price_php = "http://192.168.1.33/get_all_products.php";

	// Имена узлов JSON
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "price";
	private static final String TAG_NAME = "name";
	private static final String TAG_PRICE = "price_m";
	private static final String TAG_CHANGE_PRICE = "change_price";
	private static final String TAG_TIME = "time";

	final String LOG_TAG = "myLogs";
	private ProgressDialog pDialog;

	// массив товаров JSONArray
	int flag = 0;
	double price_old = 0;
	int flag_status_minerals = 0;
	int flag_status_transaction = 0;
	JSONArray price = null;
	double min_price;
	double max_price;
	String price_online;

	Button buy, sell, install;
	TextView take_profit_view, stop_loss_view, price_min, price_max;
	EditText stop_loss, take_profit, price_editor_up, volume_view;
	String[] order_type = { "Немедленное исполнение", "Buy Limit", "Sell Limit", "Buy Stop", "Sell Stop" };
	String[] minerals_type = { "Медь", "Титан" };
	String change_price;
	Animation anim;
	Handler h;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.trade_copper);
		h = new Handler();
		anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
		buy = (Button) findViewById(R.id.buy);
		sell = (Button) findViewById(R.id.sell);
		install = (Button) findViewById(R.id.install);

		price_min = (TextView) findViewById(R.id.price_min_view);
		price_max = (TextView) findViewById(R.id.price_max_view);
		take_profit_view = (TextView) findViewById(R.id.Take_profit_view);
		stop_loss_view = (TextView) findViewById(R.id.Stop_loss_view);

		take_profit = (EditText) findViewById(R.id.take_profit);
		stop_loss = (EditText) findViewById(R.id.stop_loss_textview);
		price_editor_up = (EditText) findViewById(R.id.price_editor_up);
		volume_view = (EditText) findViewById(R.id.volume);

		take_profit_view.setText(Html.fromHtml("Take Profit" + "<br />"));
		stop_loss_view.setText(Html.fromHtml("Stop Loss" + "<br />"));

		volume_view.setText("1.00");
		stop_loss.setText("0.00");
		take_profit.setText("0.00");
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				minerals_type);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner spinner1 = (Spinner) findViewById(R.id.Spinner01);
		spinner1.setAdapter(adapter1);
		// заголовок
		spinner1.setPrompt("Minerals");
		// выделяем элемент
		spinner1.setSelection(0);
		// устанавливаем обработчик нажатия
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// показываем позиция нажатого элемента
				Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
				switch (position) {
				case 0:
					flag_status_minerals = 1;
					price_php = "http://192.168.1.33/get_all_products.php";
					break;
				case 1:
					flag_status_minerals = 2;
					price_php = "http://192.168.1.33/titan_price.php";
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// адаптер
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, order_type);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setAdapter(adapter);
		// заголовок
		spinner.setPrompt("Тип ордера");
		// выделяем элемент
		spinner.setSelection(0);
		// устанавливаем обработчик нажатия
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// показываем позиция нажатого элемента
				Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
				switch (position) {
				case 0:
					install.setVisibility(View.INVISIBLE);
					buy.setVisibility(View.VISIBLE);
					sell.setVisibility(View.VISIBLE);
					price_editor_up.setVisibility(View.INVISIBLE);
					buy.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							// создаем новый товар в другом потоке
							new BuyTask().execute();
						}
					});
					sell.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							// создаем новый товар в другом потоке
							new SellTask().execute();
						}
					});
					break;
				case 1:// buy limit
					flag_status_transaction = 1;
					install.setVisibility(View.VISIBLE);
					buy.setVisibility(View.INVISIBLE);
					sell.setVisibility(View.INVISIBLE);
					price_editor_up.setVisibility(View.VISIBLE);

					install.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {

							if (price_editor_up.getText().toString().equals("")) {
								price_editor_up.setError("Цена пуста");
								return;
							} else {
								double double_take_profit = (Double.valueOf(price_editor_up.getText().toString()) + 2);

								String string_take_profit = Double.toString(double_take_profit);

								double double_stop_loss = (Double.valueOf(price_editor_up.getText().toString()) - 2);
								String string_stop_loss = Double.toString(double_stop_loss);
								take_profit.setText(string_take_profit);
								stop_loss.setText(string_stop_loss);

							}
							if (take_profit.getText().toString().equals("")) {
								take_profit.setError("Не задан Take Profit");
								return;
							}
							if (stop_loss.getText().toString().equals("")) {
								stop_loss.setError("Не задан Stop Loss");
								return;
							}
							if (Double.valueOf(price_editor_up.getText().toString()) > Double.valueOf(price_online)) { // проверяем
																														// установленную
																														// цену.
																														// Так
																														// как
																														// у
																														// нас
																														// buy
																														// limit
																														// цена
																														// должна
																														// быть
																														// больше
																														// существующей
								price_editor_up
										.setError("Текущая цена должна быть больше уровня установленного ордера");
								return;
							}

							// создаем новый товар в другом потоке
							new InstallTask().execute();
						}
					});
					break;
				case 2:// sell limit
					flag_status_transaction = 2;
					install.setVisibility(View.VISIBLE);
					buy.setVisibility(View.INVISIBLE);
					sell.setVisibility(View.INVISIBLE);
					price_editor_up.setVisibility(View.VISIBLE);
					install.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (price_editor_up.getText().toString().equals("")) {
								price_editor_up.setError("Цена пуста");
								return;
							} else {
								double double_take_profit = (Double.valueOf(price_editor_up.getText().toString()) - 2);

								String string_take_profit = Double.toString(double_take_profit);

								double double_stop_loss = (Double.valueOf(price_editor_up.getText().toString()) + 2);
								String string_stop_loss = Double.toString(double_stop_loss);
								take_profit.setText(string_take_profit);
								stop_loss.setText(string_stop_loss);

							}
							if (take_profit.getText().toString().equals("")) {
								take_profit.setError("Не задан Take Profit");
								return;
							}
							if (stop_loss.getText().toString().equals("")) {
								stop_loss.setError("Не задан Stop Loss");
								return;
							}

							if (Double.valueOf(price_editor_up.getText().toString()) < Double.valueOf(price_online)) { // проверяем
																														// установленную
																														// цену.
																														// Так
																														// как
																														// у
																														// нас
																														// buy
																														// limit
																														// цена
																														// должна
																														// быть
																														// больше
																														// существующей
								price_editor_up
										.setError("Текущая цена должна быть меньше уровня установленного ордера");
								return;
							}
							// создаем новый товар в другом потоке
							new InstallTask().execute();
						}
					});
					break;
				case 3:// buy stop
					flag_status_transaction = 3;
					install.setVisibility(View.VISIBLE);
					buy.setVisibility(View.INVISIBLE);
					price_editor_up.setVisibility(View.VISIBLE);
					install.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (price_editor_up.getText().toString().equals("")) {
								price_editor_up.setError("Цена пуста");
								return;
							} else {
								double double_take_profit = (Double.valueOf(price_editor_up.getText().toString()) + 2);

								String string_take_profit = Double.toString(double_take_profit);

								double double_stop_loss = (Double.valueOf(price_editor_up.getText().toString()) - 2);
								String string_stop_loss = Double.toString(double_stop_loss);
								take_profit.setText(string_take_profit);
								stop_loss.setText(string_stop_loss);

							}
							if (take_profit.getText().toString().equals("")) {
								take_profit.setError("Не задан Take Profit");
								return;
							}
							if (stop_loss.getText().toString().equals("")) {
								stop_loss.setError("Не задан Stop Loss");
								return;
							}
							if (Double.valueOf(price_editor_up.getText().toString()) < Double.valueOf(price_online)) { // проверяем
																														// установленную
																														// цену.
																														// Так
																														// как
																														// у
																														// нас
																														// buy
																														// limit
																														// цена
																														// должна
																														// быть
																														// больше
																														// существующей
								price_editor_up
										.setError("Текущая цена должна быть меньше уровня установленного ордера");
								return;
							}
							// создаем новый товар в другом потоке
							new InstallTask().execute();
						}
					});
					break;
				case 4:// sell stop
					flag_status_transaction = 4;
					install.setVisibility(View.VISIBLE);
					buy.setVisibility(View.INVISIBLE);
					sell.setVisibility(View.INVISIBLE);
					price_editor_up.setVisibility(View.VISIBLE);
					install.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (price_editor_up.getText().toString().equals("")) {
								price_editor_up.setError("Цена пуста");
								return;
							} else {
								double double_take_profit = (Double.valueOf(price_editor_up.getText().toString()) - 2);

								String string_take_profit = Double.toString(double_take_profit);

								double double_stop_loss = (Double.valueOf(price_editor_up.getText().toString()) + 2);
								String string_stop_loss = Double.toString(double_stop_loss);
								take_profit.setText(string_take_profit);
								stop_loss.setText(string_stop_loss);

							}
							if (take_profit.getText().toString().equals("")) {
								take_profit.setError("Не задан Take Profit");
								return;
							}
							if (stop_loss.getText().toString().equals("")) {
								stop_loss.setError("Не задан Stop Loss");
								return;
							}
							if (Double.valueOf(price_editor_up.getText().toString()) > Double.valueOf(price_online)) { // проверяем
																														// установленную
																														// цену.
																														// Так
																														// как
																														// у
																														// нас
																														// buy
																														// limit
																														// цена
																														// должна
																														// быть
																														// больше
																														// существующей
								price_editor_up.setError("Текущая цена должна выше уровня установленного ордера");
								return;
							}
							// создаем новый товар в другом потоке
							new InstallTask().execute();
						}
					});
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		Thread t = new Thread(new Runnable() {
			public void run() {
				DecimalFormatSymbols format = new DecimalFormatSymbols();
				format.setDecimalSeparator('.');
				DecimalFormat format_number = new DecimalFormat("#,##0.00", format);
				int time = 0;
				for (;;) {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					// получим строку JSON из URL
					JSONObject json = jParser.makeHttpRequest(price_php, "GET", params);

					try {

						// Проверяем переменную SUCCESS TAG
						int success = json.getInt(TAG_SUCCESS);

						if (success == 1) {
							price = json.getJSONArray(TAG_PRODUCTS);
							// проходим в цикле через все товары
							for (int i = 0; i < price.length(); i++) {
								JSONObject c = price.getJSONObject(i);

								// сохраняем каждый json-элемент в переменной;
								price_online = c.getString(TAG_PRICE);

								change_price = c.getString(TAG_CHANGE_PRICE);
								// time = c.getInt(TAG_TIME);

								min_price = new Double(format_number.format(Double.valueOf(price_online) - 0.1));
								max_price = new Double(format_number.format(Double.valueOf(price_online) + 0.1)); // 0.1
																													// разница
																													// цену
																													// продажи
																													// и
																													// покупки,
																													// по
																													// умолчанию.
																													// Надо
																													// редактировать.

							}
							if (price_old == Double.valueOf(price_online)) {
								change_price = "0";
							}
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								// TODO Автоматически созданный блок catch
								e.printStackTrace();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					h.post(updateProgress);
				}

			}
		});
		t.start();

	}

	Runnable updateProgress = new Runnable() {
		public void run() {
			Double flag_change = Double.valueOf(change_price);
			if (flag_change < 0) {
				price_min.setTextColor(Color.RED);
				price_max.setTextColor(Color.RED);
				// price_min.startAnimation(anim);
				price_min.setText(Html.fromHtml(min_price + "<br />"));
				// price_max.startAnimation(anim);
				price_max.setText(Html.fromHtml(max_price + "<br />"));
			} else {
				if (flag_change == 0) {// начальная цена
					price_min.setTextColor(Color.BLACK);
					price_max.setTextColor(Color.BLACK);
					price_min.setText(Html.fromHtml(min_price + "<br />"));
					price_max.setText(Html.fromHtml(max_price + "<br />"));
				} else {
					price_min.setTextColor(Color.BLUE);
					price_max.setTextColor(Color.BLUE);
					// price_min.startAnimation(anim);
					price_min.setText(Html.fromHtml(min_price + "<br />"));
					// price_max.startAnimation(anim);
					price_max.setText(Html.fromHtml(max_price + "<br />"));
				}

			}
		}
	};

	class BuyTask extends AsyncTask<String, String, String> {
		// Сначала запустим окно с индикатором прогресса
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Trade_copper.this);
			pDialog.setMessage("Установка позиции");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		// Создаем товар
		protected String doInBackground(String... args) {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String devicIMEI = telephonyManager.getDeviceId();
			String take_profit_data = take_profit.getText().toString();
			String stop_loss_data = stop_loss.getText().toString();
			String volume = volume_view.getText().toString();

			// Подготавливаем параметры
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("take_profit", take_profit_data));
			params.add(new BasicNameValuePair("stop_loss", stop_loss_data));
			params.add(new BasicNameValuePair("identification_user", devicIMEI));
			params.add(new BasicNameValuePair("price", price_online));
			if (flag_status_minerals == 1) {
				params.add(new BasicNameValuePair("name", "copper"));
			}
			if (flag_status_minerals == 2) {
				params.add(new BasicNameValuePair("name", "titan"));
			}
			params.add(new BasicNameValuePair("status_transaction", "buy"));
			params.add(new BasicNameValuePair("volume", volume));

			// получаем объект JSON через POST
			JSONObject json = jsonParser.makeHttpRequest(transaction, "POST", params);

			// Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// при успешном создании товара
					// запускаем активность всех товаров
					Intent i = new Intent(getApplicationContext(), Bourse_main.class);
					startActivity(i);

					// закрываем экран активности
					finish();
				} else {
					// не получилось создать товар
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// закрываем диалоговое окно с индикатором
			pDialog.dismiss();
		}
	}

	class SellTask extends AsyncTask<String, String, String> {
		// Сначала запустим окно с индикатором прогресса
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Trade_copper.this);
			pDialog.setMessage("Установка позиции");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		// Создаем товар
		protected String doInBackground(String... args) {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String devicIMEI = telephonyManager.getDeviceId();
			String take_profit_data = take_profit.getText().toString();
			String stop_loss_data = stop_loss.getText().toString();
			String volume = volume_view.getText().toString();

			// Подготавливаем параметры
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("take_profit", take_profit_data));
			params.add(new BasicNameValuePair("stop_loss", stop_loss_data));
			params.add(new BasicNameValuePair("identification_user", devicIMEI));
			params.add(new BasicNameValuePair("price", price_online));
			if (flag_status_minerals == 1) {
				params.add(new BasicNameValuePair("name", "copper"));
			}
			if (flag_status_minerals == 2) {
				params.add(new BasicNameValuePair("name", "titan"));
			}
			params.add(new BasicNameValuePair("status_transaction", "sell"));
			params.add(new BasicNameValuePair("volume", volume));

			// получаем объект JSON через POST
			JSONObject json = jsonParser.makeHttpRequest(transaction, "POST", params);

			// Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// при успешном создании товара
					// запускаем активность всех товаров
					Intent i = new Intent(getApplicationContext(), Bourse_main.class);
					startActivity(i);

					// закрываем экран активности
					finish();
				} else {
					// не получилось создать товар
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// закрываем диалоговое окно с индикатором
			pDialog.dismiss();
		}
	}

	class InstallTask extends AsyncTask<String, String, String> {
		// Сначала запустим окно с индикатором прогресса
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Trade_copper.this);
			pDialog.setMessage("Установка позиции");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		// Создаем товар
		protected String doInBackground(String... args) {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String devicIMEI = telephonyManager.getDeviceId();
			String take_profit_data = take_profit.getText().toString();
			String stop_loss_data = stop_loss.getText().toString();
			String volume = volume_view.getText().toString();
			String price = price_editor_up.getText().toString();

			// Подготавливаем параметры
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("take_profit", take_profit_data));
			params.add(new BasicNameValuePair("stop_loss", stop_loss_data));
			params.add(new BasicNameValuePair("identification_user", devicIMEI));
			params.add(new BasicNameValuePair("price", price));
			if (flag_status_minerals == 1) {
				params.add(new BasicNameValuePair("name", "copper"));
			}
			if (flag_status_minerals == 2) {
				params.add(new BasicNameValuePair("name", "titan"));
			}

			if (flag_status_transaction == 1) {
				params.add(new BasicNameValuePair("status_transaction", "buy limit"));
			}
			if (flag_status_transaction == 2) {
				params.add(new BasicNameValuePair("status_transaction", "sell limit"));
			}
			if (flag_status_transaction == 3) {
				params.add(new BasicNameValuePair("status_transaction", "buy stop"));
			}
			if (flag_status_transaction == 4) {
				params.add(new BasicNameValuePair("status_transaction", "sell stop"));
			}

			params.add(new BasicNameValuePair("volume", volume));

			// получаем объект JSON через POST
			JSONObject json = jsonParser.makeHttpRequest(transaction, "POST", params);

			// Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// при успешном создании товара
					// запускаем активность всех товаров
					Intent i = new Intent(getApplicationContext(), Bourse_main.class);
					startActivity(i);

					// закрываем экран активности
					finish();
				} else {
					// не получилось создать товар
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// закрываем диалоговое окно с индикатором
			pDialog.dismiss();
		}
	}

}