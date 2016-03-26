package ru.trumbull_pro.rebulicv_1_0.graph_cooper;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.graphics.Color;

public class LineGraph {

	private GraphicalView view;

	private TimeSeries dataset = new TimeSeries("Цена Меди");
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

	private XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be
																// used to
																// customize
																// line 1
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds
																					// a
																					// collection
																					// of
																					// XYSeriesRenderer
																					// and
																					// customizes
																					// the
																					// graph

	public LineGraph() {
		// Add single dataset to multiple dataset
		mDataset.addSeries(dataset);

		// Customization time for line 1!
		renderer.setColor(Color.WHITE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		renderer.setLineWidth(5);
		renderer.setDisplayChartValues(true);

		// Enable Zoom
		mRenderer.setZoomButtonsVisible(true);
		// mRenderer.setXTitle("Время");
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setYTitle("Цена");
		//mRenderer.setYLabelsColor(0, color.menu);
		mRenderer.setShowGrid(true);
		mRenderer.setZoomEnabled(true);
		mRenderer.setPanEnabled(true);
		mRenderer.setGridColor(Color.WHITE);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLACK);
		// mRenderer.setBackgroundColor(Color.TRANSPARENT);
		mRenderer.setXLabels(20);

		// mRenderer.setAxisTitleTextSize(40);
		mRenderer.setLabelsTextSize(18); // размер шрифта указателей на осях
		// mRenderer.setLegendTextSize(40);
		// Add single renderer to multiple renderer
		mRenderer.addSeriesRenderer(renderer);
	}

	public GraphicalView getView(Context context) {
		view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return view;
	}

	public void addNewPoints(Point p) {
		dataset.add(p.getX(), p.getY());
	}

}
