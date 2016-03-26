package ru.trumbull_pro.rebulicv_1_0.graph_cooper;

import org.achartengine.GraphicalView;
import ru.trumbull_pro.rebulicv_1_0.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class DynamicGraph_Cooper extends Activity {
	private int flag = 0;
	private static GraphicalView view;
	private LineGraph line = new LineGraph();
	private static Thread thread;
	private int i = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copper_chart);
		thread = new Thread() {
			public void run() {
				for (;;) {

					if (flag == 1) {
						break;
					}

					Point p = MockData.getDataFromReceiver(i++); // We got new
																	// data!
					line.addNewPoints(p); // Add it to our graph
					view.repaint();
				}
			}
		};
		thread.start();
	}

	@Override
	protected void onStart() {
		super.onStart();
		view = line.getView(this);
		setContentView(view);
	}

	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("Вeрнуться?")
				.setMessage("Вы действительно хотите выйти?")
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes, new OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// SomeActivity - имя класса Activity для которой
						// переопределяем onBackPressed();
						flag = +1;
						DynamicGraph_Cooper.super.onBackPressed();
					}
				}).create().show();
	}

}