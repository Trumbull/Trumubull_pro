package ru.trumbull_pro.rebulicv_1_0;

import java.util.Random;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graph_copper extends Activity {

 private static final Random RANDOM = new Random();
 private LineGraphSeries<DataPoint> series;
 private int lastX = 0;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
   setContentView(R.layout.copper_graph);
   // we get graph view instance
   GraphView graph = (GraphView) findViewById(R.id.graph);
   // data
   series = new LineGraphSeries<DataPoint>();
   graph.addSeries(series);
   
   // customize a little bit viewport
   Viewport viewport = graph.getViewport();
   viewport.setYAxisBoundsManual(true);
   viewport.setMinY(0);
   viewport.setMaxY(50);
   viewport.setScrollable(true);
   viewport.setScalable(true);

   
 }

 @Override
 protected void onResume() {
  super.onResume();
  // we're going to simulate real time with thread that append data to the graph
  new Thread(new Runnable() {

    @Override
    public void run() {
      // we add 100 new entries
      for (;;) {
        runOnUiThread(new Runnable() {

          @Override
          public void run() {
            addEntry();
          }
        });

        // sleep to slow down the add of entries
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // manage error ...
        }
      }
    }
   }).start();
  }

  // add random data to graph
  private void addEntry() {
    // здесь выбираем сколько точек сможем прокрутить назад, что бы посмотреть график
		series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 40d),
				true, 1000);
	}

}