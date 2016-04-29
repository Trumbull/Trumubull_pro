package ru.trumbull_pro.rebulicv_1_0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Search_minerals extends Activity implements OnClickListener {

	final String LOG_TAG = "myLogs";
	EditText a1;
	EditText b1;
	private int progress = 0;
	private ProgressBar pbHorizontal;
	private TextView tvProgressHorizontal;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_minerals);
		pbHorizontal = (ProgressBar) findViewById(R.id.pb_horizontal);
		tvProgressHorizontal = (TextView) findViewById(R.id.tv_progress_horizontal);
		a1 = (EditText) findViewById(R.id.editText1);
		b1 = (EditText) findViewById(R.id.editText2);
		a1.setText("0.00");
		b1.setText("0.00");
		Button btnScan = (Button) findViewById(R.id.button1);
		btnScan.setOnClickListener(this);

	}

	public void onClick(View v) {
		Log.d(LOG_TAG, "Button click in Fragment2");
		if (a1.getText().toString().equals("")) {
			a1.setError("¬ведите длину");
			return;
		}
		if (b1.getText().toString().equals("")) {
			b1.setError("¬ведите ширину");
			return;
		}

		Intent intent = new Intent(this, Minerals.class);

		intent.putExtra("a1", a1.getText().toString());
		progress = progress + 50;
		intent.putExtra("b1", b1.getText().toString());
		progress = progress + 50;
		postProgress(progress);
		startActivity(intent);

	}

	private void postProgress(int progress) {
		String strProgress = String.valueOf(progress) + " %";
		pbHorizontal.setProgress(progress);

		if (progress == 0) {
			pbHorizontal.setSecondaryProgress(0);
		} else {
			pbHorizontal.setSecondaryProgress(progress + 5);
		}
		tvProgressHorizontal.setText(strProgress);
	}
}