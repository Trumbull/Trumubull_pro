package ru.trumbull_pro.rebulicv_1_0.graph_cooper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.trumbull_pro.rebulicv_1_0.fragment.JSONParser;
public class MockData {
	static JSONParser jParser = new JSONParser();

	// укажите свой адрес
	private static String url_all_products = "http://192.168.1.34/get_all_products.php";

	// Имена узлов JSON
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "price";
	private static final String TAG_PRICE = "price_m";
	private static final String TAG_TIME = "time";

	// массив товаров JSONArray
	static JSONArray price = null;

	// x is the day number, 0, 1, 2, 3
	public static Point getDataFromReceiver(int x) {

		return new Point(x, generateRandomData());
	}

	static double generateRandomData() {
		// регулятор рыночной цены металлов в программе БП "Бизнес" Ver.1.1
		// Долгушин В.А. 27.06.15
		double result_level_graph = 0;
		DecimalFormatSymbols format = new DecimalFormatSymbols();
		format.setDecimalSeparator('.');
		DecimalFormat format_number = new DecimalFormat("#,##0.00", format);
		int time = 0;

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// получим строку JSON из URL
		JSONObject json = jParser.makeHttpRequest(url_all_products, "GET",
				params);

		// Check your log cat for JSON reponse
		// Log.d("All Products: ", json.toString());

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
					String price_cooper = c.getString(TAG_PRICE);
					time = c.getInt(TAG_TIME);

					result_level_graph = new Double(format_number.format(Double
							.valueOf(price_cooper)));
				}
			}
			try {
				TimeUnit.SECONDS.sleep(time);
			} catch (InterruptedException e) {
				// TODO Автоматически созданный блок catch
				e.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result_level_graph;

	}
}