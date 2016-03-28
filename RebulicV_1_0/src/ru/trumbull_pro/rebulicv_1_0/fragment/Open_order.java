package ru.trumbull_pro.rebulicv_1_0.fragment;

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

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ru.trumbull_pro.rebulicv_1_0.Bourse_main;
import ru.trumbull_pro.rebulicv_1_0.DatabaseHelper;
import ru.trumbull_pro.rebulicv_1_0.R;
import ru.trumbull_pro.rebulicv_1_0.R.id;
import ru.trumbull_pro.rebulicv_1_0.R.layout;
import ru.trumbull_pro.rebulicv_1_0.R.menu;

public class Open_order extends Fragment {
	JSONParser jsonParser = new JSONParser();

	// укажите свой адрес
	private static final String url_order = "http://192.168.1.34/order_alpha.php";
	// Имена узлов JSON
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TAB_PRICE = "transaction";
	private static final String TAG_PRICE = "price";
	private static final String TAG_NAME = "name";
	private static final String TAG_TAKE_PROFIT = "take_profit";
	private static final String TAG_STOP_LOSS = "stop_loss";
	private static final String TAG_STATUS = "status_transaction";
	private static final String TAG_VOLUME = "volume";
	private static final String TAG_CHANGE_SUM = "change_sum";
	double change_comparison = 0;
	int flag_repeat = 0;
	final Handler handler = new Handler();
	Animation anim;
	String id_transaction;
	JSONArray transactionObj = null;
	String name;
	TextView tp_order_open, text_tp, arrow, stop_loss_text, id_order, name_textview, stop_loss, view_order, begin_price,
			end_price, price_change, volume_order;
	final String LOG_TAG = "myLogs";
	JSONObject json = null;
	int flag = 0;
	DatabaseHelper dbHelper;
	SQLiteDatabase db;
	TaskOrder tp_order;
	int[] id_order_massiv = new int[15];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_open_order, container, false);
		anim = AnimationUtils.loadAnimation(getActivity(), R.anim.myalpha);
		id_order = (TextView) view.findViewById(R.id.id_order);
		name_textview = (TextView) view.findViewById(R.id.name);
		view_order = (TextView) view.findViewById(R.id.view_order);
		begin_price = (TextView) view.findViewById(R.id.begin_price);
		end_price = (TextView) view.findViewById(R.id.end_price);
		price_change = (TextView) view.findViewById(R.id.price_change);
		stop_loss = (TextView) view.findViewById(R.id.stop_loss_order);
		volume_order = (TextView) view.findViewById(R.id.volume_order);
		stop_loss_text = (TextView) view.findViewById(R.id.stop_loss_textview);
		text_tp = (TextView) view.findViewById(R.id.text_tp);
		tp_order_open = (TextView) view.findViewById(R.id.tp_order_open);
		arrow = (TextView) view.findViewById(R.id.textView1);
		stop_loss_text.setText(Html.fromHtml("S/L:" + "<br />"));
		text_tp.setText(Html.fromHtml("T/P:" + "<br />"));
		arrow.setText(Html.fromHtml("&#8594" + "<br />"));

		Bundle args = getArguments();
		if (args != null) {

			id_transaction = args.getString("test_text");
		}
		Log.d(LOG_TAG, "Переданная переменная: " + id_transaction);
		tp_order = new TaskOrder();
		tp_order.execute();

		return view;
	}

	private class TaskOrder extends AsyncTask<String, String, String> {
		// Generates dummy data in a non-ui thread

		@Override
		protected String doInBackground(String... args) {
			String[] values = new String[7];
			for (;;) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id_transaction", id_transaction));

				// получаем информацию через запрос HTTP GET
				json = jsonParser.makeHttpRequest(url_order, "GET", params);
				// Log.d("Single Product Details", json.toString());
				// ответ от json о товаре

				try {
					// json success tag
					int success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						// если получили информацию о товаре
						transactionObj = json.getJSONArray(TAG_TAB_PRICE);
						for (int i = 0; i < transactionObj.length(); i++) {
							// получим первый объект из массива JSON Array
							JSONObject transaction = transactionObj.getJSONObject(i);

							values[0] = transaction.getString(TAG_NAME);
							values[1] = transaction.getString(TAG_PRICE);
							values[2] = transaction.getString(TAG_TAKE_PROFIT);
							values[3] = transaction.getString(TAG_STOP_LOSS);
							values[4] = transaction.getString(TAG_STATUS);
							values[5] = transaction.getString(TAG_VOLUME);
							values[6] = transaction.getString(TAG_CHANGE_SUM);
						}
					} else {
						// не нашли товар по pid
					}
					if (flag_repeat == 0) {
						
						publishProgress(values);
					} else {
						try {
							TimeUnit.SECONDS.sleep(2);
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
		protected void onProgressUpdate(String... values) {

			DecimalFormatSymbols format = new DecimalFormatSymbols();
			format.setDecimalSeparator('.');
			DecimalFormat format_number = new DecimalFormat("#,##0.00", format);
			double change = Double.valueOf(values[6]);// сравнение
			int flag_up = 0;// флаг обновления
			id_order.setText(Html.fromHtml(values[0] + "<br />"));
			
			tp_order_open.setText(Html.fromHtml(values[2] + "<br />"));
			double end_price_open;
			end_price_open = new Double(format_number
					.format(((Double.valueOf(values[6]) / Double.valueOf(values[5])) + Double.valueOf(values[1]))));
			view_order.setTextColor(Color.BLUE);

			if ("sell".equals(values[4])) {
				view_order.setTextColor(Color.RED);
				view_order.setText(Html.fromHtml(values[4] + "<br />"));
			} else {
				view_order.setTextColor(Color.BLUE);
				view_order.setText(Html.fromHtml(values[4] + "<br />"));
			}
			begin_price.setText(Html.fromHtml(values[1] + "<br />"));
			end_price.setText(Html.fromHtml(end_price_open + "<br />"));
			stop_loss.setText(Html.fromHtml(values[3] + "<br />"));
			volume_order.setText(Html.fromHtml(values[5] + "<br />"));
			if (flag_repeat != 0) {
				if (change == change_comparison) {
					flag_up = 0;
				} else {
					flag_up = 1;
				}
				change_comparison = change;
			} else {
				change_comparison = change;
				flag_repeat++;
			}
			Log.d(LOG_TAG, "Изменение: " + change);
			
			if (change < 0) {
				if (flag_up == 1) {
					price_change.setTextColor(Color.RED);
					price_change.startAnimation(anim);
				} else {
					price_change.setTextColor(Color.RED);
				}
				price_change.setText(Html.fromHtml(values[6] + "<br />"));
			} else {
				if (flag_up == 1) {
				price_change.setTextColor(Color.BLUE);
				price_change.startAnimation(anim);
				}
				else{
					price_change.setTextColor(Color.BLUE);
				}
				price_change.setText(Html.fromHtml(values[6] + "<br />"));
			}
			

		}

	}

}
