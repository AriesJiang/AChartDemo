package com.example.achartdemov2;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.achartdemov2.fragment.FunctionFragment;
import com.example.achartdemov2.fragment.MyFragmentPagerAdapter;
import com.example.achartdemov2.fragment.TestFragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener {

	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private Fragment mainFragment, functionFragment, recentFragment;

	private RelativeLayout llBarChart, llBarChart2, llBarChart3, llBarChart4,
			llBarChart5;
	private View vChart1, vChart2, vChart3, vChart4, vChart5;
	/**
	 * ��һ����ѡ�е�fragment
	 */
	private int previousSelectPosition = 0;
	private LinearLayout llPoints;

	private String[][] Top10ErrCode = { { "ADFU1", "5" }, { "MBPW2", "8" },
			{ "ABCDE", "12" }, { "BLFU1", "16" }, { "LCVD3", "15" },
			{ "ADDK1", "11" }, { "CMFU3", "8" }, { "LCCR2", "7" },
			{ "QBLE1", "5" }, { "SPNS1", "2" }, { "ABCDE", "12" },
			{ "BLFU1", "16" }, { "LCVD3", "15" }, { "ADDK1", "11" },
			{ "CMFU3", "8" }, { "LCCR2", "7" }, { "QBLE1", "5" },
			{ "SPNS1", "2" } };
	int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViewPager();
	}

	private void initViewPager() {
		screenWidth = (getWindowManager().getDefaultDisplay().getWidth() - 50) / 7; // ��Ļ�����أ��磺480px��
		Log.e("123123", "**********" + screenWidth);
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();
		// LayoutInflater mInflater = getLayoutInflater();
		// View activityView = mInflater.inflate(R.layout.lay1, null);

		// mainFragment = TestFragment.newInstance("Hello Activity.");
		mainFragment = TestFragment.newInstance("Hello Main.");
		recentFragment = TestFragment.newInstance("Hello Group.");
		// functionFragment=TestFragment.newInstance("Hello Friends.");
		functionFragment = FunctionFragment.newInstance("Hello Function");
		// setupFragment = TestFragment.newInstance("Hello Chat.");

		fragmentsList.add(mainFragment);
		fragmentsList.add(functionFragment);
		fragmentsList.add(recentFragment);
		// fragmentsList.add(setupFragment);

		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(this);

		llPoints = (LinearLayout) findViewById(R.id.ll_points);
		prepareData();
		llPoints.getChildAt(previousSelectPosition).setEnabled(true);

		llBarChart = (RelativeLayout) findViewById(R.id.layout);
		llBarChart2 = (RelativeLayout) findViewById(R.id.layout2);
		llBarChart3 = (RelativeLayout) findViewById(R.id.layout3);
		llBarChart4 = (RelativeLayout) findViewById(R.id.layout4);
		llBarChart5 = (RelativeLayout) findViewById(R.id.layout5);

		vChart1 = getBarChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		llBarChart.removeAllViews();
		// llBarChart.addView(vChart);
		llBarChart.addView(vChart1, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		vChart2 = getLineChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		llBarChart2.removeAllViews();
		// llBarChart.addView(vChart);
		llBarChart2.addView(vChart2, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		vChart3 = getLineShadowChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		final GraphicalView graphicalView = (GraphicalView) vChart3;
		graphicalView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��δ��봦����һ�����,���������ĵ����ĸ��������Լ��������.
				// -->start
				SeriesSelection seriesSelection = graphicalView
						.getCurrentSeriesAndPoint();
				double[] xy = graphicalView.toRealPoint(0);
				if (seriesSelection == null) {
					Toast.makeText(MainActivity.this,
							"No chart element was clicked", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(
							MainActivity.this,
							"Chart element in series index "
									+ seriesSelection.getSeriesIndex()
									+ " data point index "
									+ seriesSelection.getPointIndex()
									+ " was clicked"
									+ " closest point value X="
									+ seriesSelection.getXValue() + ", Y="
									+ seriesSelection.getValue()
									+ " clicked point value X=" + (float) xy[0]
									+ ", Y=" + (float) xy[1],
							Toast.LENGTH_SHORT).show();
				}
				// -->end

			}
		});
		llBarChart3.removeAllViews();
		// llBarChart.addView(vChart);
		llBarChart3.addView(vChart3, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		vChart4 = getLine2ShadowChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		llBarChart4.removeAllViews();
		// llBarChart.addView(vChart);
		final GraphicalView graphicalView2 = (GraphicalView) vChart4;
		graphicalView2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��δ��봦����һ�����,���������ĵ����ĸ��������Լ��������.
				// -->start
				SeriesSelection seriesSelection = graphicalView2
						.getCurrentSeriesAndPoint();
				double[] xy = graphicalView2.toRealPoint(0);
				if (seriesSelection == null) {
					Toast.makeText(MainActivity.this,
							"No chart element was clicked", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(
							MainActivity.this,
							"Chart element in series index "
									+ seriesSelection.getSeriesIndex()
									+ " data point index "
									+ seriesSelection.getPointIndex()
									+ " was clicked"
									+ " closest point value X="
									+ seriesSelection.getXValue() + ", Y="
									+ seriesSelection.getValue()
									+ " clicked point value X=" + (float) xy[0]
									+ ", Y=" + (float) xy[1],
							Toast.LENGTH_SHORT).show();
				}
				// -->end

			}
		});
		llBarChart4.addView(vChart4, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		vChart5 = getLineDashedwChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		llBarChart5.removeAllViews();
		// llBarChart.addView(vChart);
		llBarChart5.addView(vChart5, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	/**
	 * viewpagerԲ���ʼ��
	 */
	@SuppressWarnings("deprecation")
	private void prepareData() {

		View view;
		for (int i = 0; i < fragmentsList.size(); i++) {
			// ��ӵ�view����
			view = new View(getBaseContext());
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.point_background));
			LayoutParams lp = new LayoutParams(5, 5);
			lp.leftMargin = 10;
			view.setLayoutParams(lp);
			view.setEnabled(false);
			llPoints.addView(view);
		}
	}

	private View getBarChart(String chartTitle, String XTitle, String YTitle,
			String[][] xy) {

		XYSeries Series = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer);

		Renderer.setApplyBackgroundColor(true); // �O�������ɫ
		Renderer.setBackgroundColor(Color.TRANSPARENT); // �O���D�ȇ������ɫ
		Renderer.setMarginsColor(getResources()
				.getColor(R.color.chartred_white)); // �O���D��������ɫ
		Renderer.setTextTypeface(null, Typeface.BOLD); // �O������style

		Renderer.setShowGrid(true); // �O���W��
		Renderer.setGridColor(Color.GRAY); // �O���W���ɫ

		Renderer.setChartTitle(chartTitle); // �O�����^����
		Renderer.setLabelsColor(Color.BLACK); // �O�����^�����ɫ
		Renderer.setChartTitleTextSize(20); // �O�����^���ִ�С
		Renderer.setAxesColor(Color.BLACK); // �O���p�S�ɫ
		Renderer.setBarSpacing(1); // �O��bar�g�ľ��x

		Renderer.setXTitle(XTitle); // �O��X�S����
		Renderer.setYTitle(YTitle); // �O��Y�S����
		Renderer.setXLabelsColor(Color.BLACK); // �O��X�S�����ɫ
		Renderer.setYLabelsColor(0, Color.BLACK); // �O��Y�S�����ɫ
		Renderer.setXLabelsAlign(Align.CENTER); // �O��X�S��������
		Renderer.setYLabelsAlign(Align.CENTER); // �O��Y�S��������
		Renderer.setXLabelsAngle(0); // �O��X�S���փAб��

		Renderer.setXLabels(0); // �O��X�S���@ʾ����, ���Գ�ʽ�O������
		Renderer.setYAxisMin(0); // �O��Y�S����Сֵ
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// �����������϶�,�����������϶�.

		yRenderer.setColor(getResources().getColor(R.color.chartred)); // �O��Series�ɫ
		yRenderer.setDisplayChartValues(true); // չ�FSeries��ֵ

		Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		for (int r = 0; r < xy.length; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			Renderer.addXTextLabel(r + 1, xy[r][0]);
			Series.add(r + 1, Integer.parseInt(xy[r][1]));
		}
		Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
				Renderer, Type.DEFAULT);
		return view;
	}

	private View getLineChart(String chartTitle, String XTitle, String YTitle,
			String[][] xy) {

		XYSeries Series = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer);

		Renderer.setFitLegend(true); // �������ͼ��Ӧ��С���ʺ�
		Renderer.setShowAxes(true); // ������õ���Ӧ���ǿɼ��ġ�

		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setMaximumFractionDigits(2); // ������С�����λ ��ʾ��47.00
		nf.setMinimumFractionDigits(2);
		// setMaximumFractionDigits(int) ������ֵ��С��������������λ����
		// setMaximumIntegerDigits(int) ������ֵ������������������λ����
		// setMinimumFractionDigits(int) ������ֵ��С�������������Сλ����
		// setMinimumIntegerDigits(int) ������ֵ�����������������Сλ��.

		// NumberFormat nf = NumberFormat.getPercentInstance();
		// nf.setMinimumFractionDigits(2); // ������С�����λ ��ʾ��47.00%

		// Sets the number format for displaying labels.
		Renderer.setLabelFormat(nf);
		Renderer.setShowCustomTextGrid(false); // �������������Զ���X��Y�ı�ǩӦ���ǿɼ��ġ�
		Renderer.setShowLegend(false); // ��������ߵı�ʶ�Ƿ��ǿɼ��ġ�

		Renderer.setApplyBackgroundColor(true); // �O�� �����ɫ
		Renderer.setBackgroundColor(Color.WHITE); // �O���D�ȇ������ɫ
		Renderer.setMarginsColor(Color.WHITE); // �O���D��������ɫ
		// Renderer.setMargins(margins)
		Renderer.setTextTypeface(null, Typeface.BOLD); // �O������style

		Renderer.setShowGrid(true); // �O���W��
		Renderer.setGridColor(Color.GRAY); // �O���W���ɫ

		Renderer.setChartTitle(chartTitle); // �O�����^����
		Renderer.setLabelsColor(Color.BLACK); // �O�����^�����ɫ
		Renderer.setChartTitleTextSize(20); // �O�����^���ִ�С
		Renderer.setAxesColor(Color.BLACK); // �O���p�S�ɫ
		Renderer.setBarSpacing(1); // �O��bar�g�ľ��x

		Renderer.setXTitle(XTitle); // �O��X�S����
		Renderer.setYTitle(YTitle); // �O��Y�S����
		Renderer.setXLabelsColor(Color.BLACK); // �O��X�S�����ɫ
		Renderer.setYLabelsColor(0, Color.BLACK); // �O��Y�S�����ɫ
		Renderer.setXLabelsAlign(Align.CENTER); // �O��X�S��������
		Renderer.setYLabelsAlign(Align.CENTER); // �O��Y�S��������
		Renderer.setXLabelsAngle(0); // �O��X�S���փAб��

		Renderer.setXLabels(0); // �O��X�S���@ʾ����, ���Գ�ʽ�O������
		Renderer.setYAxisMin(0); // �O��Y�S����Сֵ
		Renderer.setYAxisMax(30); // �O��Y�S����Сֵ
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// �����������϶�,�����������϶�.

		yRenderer.setColor(getResources().getColor(R.color.chartred)); // �O��Series�ɫ
		yRenderer.setDisplayChartValues(true); // չ�FSeries��ֵ
		
		yRenderer.setChartValuesTextSize(30);
		//���������Ŀ���ǣ�ʹ�����е��ֵ������ʾ��Ĭ�ϵľ���Ϊ100���Ǹ�bug
		yRenderer.setDisplayChartValuesDistance(30);//���������۵��ľ��룬ʹ�����е��ֵ������ʾ
		yRenderer.setChartValuesSpacing(8);
				
		Renderer.setLabelsTextSize(20);
		Renderer.setLegendTextSize(0);
		Renderer.setAxisTitleTextSize(20);

		// Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		Renderer.setPointSize(5);// ���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
		for (int r = 0; r < xy.length; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			if (r == 6) {
				yRenderer.setPointStyle(PointStyle.CIRCLE);// ���������Բ��
				Renderer.setPointSize(4);// ���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
				yRenderer.setFillPoints(false);// ���õ��Ƿ�ʵ��
			} else {
				// yRenderer.setPointStyle(PointStyle.X);//���������Բ��
				// Renderer.setPointSize(5);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
				// yRenderer.setFillPoints(true);//���õ��Ƿ�ʵ��
			}
			// Renderer.addXTextLabel(r + 1, xy[r][0]);
			Series.add(r + 1, Integer.parseInt(xy[r][1]));
		}
		// Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		// View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
		// Renderer, Type.DEFAULT);
		// View view = ChartFactory.getLineChartView(getBaseContext(), Dataset,
		// Renderer);
		// �⻬���߶�
		View view = ChartFactory.getCubeLineChartView(getBaseContext(),
				Dataset, Renderer, 0.2f);
		return view;
	}

	@SuppressWarnings("deprecation")
	private View getLineShadowChart(String chartTitle, String XTitle,
			String YTitle, String[][] xy) {

		XYSeries Series = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer);

		Renderer.setFitLegend(true); // �������ͼ��Ӧ��С���ʺ�
		Renderer.setShowAxes(false); // ������õ���Ӧ���ǿɼ��ġ�
		Renderer.setShowCustomTextGrid(false); // �������������Զ���X��Y�ı�ǩӦ���ǿɼ��ġ�
		Renderer.setShowLegend(false); // ��������ߵı�ʶ�Ƿ��ǿɼ��ġ�
		yRenderer.setFillBelowLine(true);
		yRenderer.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));

		Renderer.setApplyBackgroundColor(true); // �O�������ɫ
		Renderer.setBackgroundColor(Color.WHITE); // �O���D�ȇ������ɫ
		Renderer.setMarginsColor(Color.WHITE); // �O���D��������ɫ
		Renderer.setTextTypeface(null, Typeface.BOLD); // �O������style

		Renderer.setShowGrid(true); // �O���W��
		Renderer.setGridColor(Color.GRAY); // �O���W���ɫ

		Renderer.setChartTitle(chartTitle); // �O�����^����
		Renderer.setLabelsColor(Color.BLACK); // �O�����^�����ɫ
		Renderer.setChartTitleTextSize(20); // �O�����^���ִ�С
		Renderer.setAxesColor(Color.BLACK); // �O���p�S�ɫ
		Renderer.setBarSpacing(1); // �O��bar�g�ľ��x

		Renderer.setXTitle(XTitle); // �O��X�S����
		Renderer.setYTitle(YTitle); // �O��Y�S����
		Renderer.setXLabelsColor(Color.BLACK); // �O��X�S�����ɫ
		Renderer.setYLabelsColor(0, Color.BLACK); // �O��Y�S�����ɫ
		Renderer.setXLabelsAlign(Align.CENTER); // �O��X�S��������
		Renderer.setYLabelsAlign(Align.CENTER); // �O��Y�S��������
		Renderer.setXLabelsAngle(0); // �O��X�S���փAб��

		Renderer.setXLabels(0); // �O��X�S���@ʾ����, ���Գ�ʽ�O������
		Renderer.setYAxisMin(0); // �O��Y�S����Сֵ
		Renderer.setYAxisMax(30); // �O��Y�S����Сֵ
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// �����������϶�,�����������϶�.
		Renderer.setPointSize(5);// ���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
		Renderer.setClickEnabled(true);//
		Renderer.setSelectableBuffer(screenWidth);

		yRenderer.setColor(getResources().getColor(R.color.chartred)); // �O��Series�ɫ
		yRenderer.setPointStyle(PointStyle.CIRCLE);// ���������Բ��
		yRenderer.setFillPoints(true);// ���õ��Ƿ�ʵ��
		// yRenderer.setDisplayChartValues(true); //չ�FSeries��ֵ

		// Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		int start = 0;
		int end = start + 6;
		for (; start < end; start++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			Renderer.addXTextLabel(start, xy[start][0]);
			Series.add(start, Integer.parseInt(xy[start][1]));
		}
		// Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		// View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
		// Renderer, Type.DEFAULT);
		// View view = ChartFactory.getLineChartView(getBaseContext(), Dataset,
		// Renderer);
		// �⻬���߶�
		View view = ChartFactory.getCubeLineChartView(getBaseContext(),
				Dataset, Renderer, 0.3f);
		return view;
	}

	@SuppressWarnings("deprecation")
	private View getLine2ShadowChart(String chartTitle, String XTitle,
			String YTitle, String[][] xy) {

		XYSeries Series1 = new XYSeries(YTitle);
		// line2
		XYSeries Series2 = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series1);
		// line2
		Dataset.addSeries(Series2);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer1 = new XYSeriesRenderer();
		XYSeriesRenderer yRenderer2 = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer1);
		Renderer.addSeriesRenderer(yRenderer2);

		Renderer.setFitLegend(true); // �������ͼ��Ӧ��С���ʺ�
		Renderer.setShowAxes(false); // ������õ���Ӧ���ǿɼ��ġ�
		Renderer.setShowCustomTextGrid(false); // �������������Զ���X��Y�ı�ǩӦ���ǿɼ��ġ�
		Renderer.setShowLegend(false); // ��������ߵı�ʶ�Ƿ��ǿɼ��ġ�
		yRenderer1.setFillBelowLine(false);
		yRenderer1.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));
		yRenderer2.setFillBelowLine(true);
		yRenderer2.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));

		Renderer.setApplyBackgroundColor(true); // �O�������ɫ
		Renderer.setBackgroundColor(Color.WHITE); // �O���D�ȇ������ɫ
		Renderer.setMarginsColor(Color.WHITE); // �O���D��������ɫ
		Renderer.setTextTypeface(null, Typeface.BOLD); // �O������style

		Renderer.setShowGrid(true); // �O���W��
		Renderer.setGridColor(Color.GRAY); // �O���W���ɫ

		Renderer.setChartTitle(chartTitle); // �O�����^����
		Renderer.setLabelsColor(Color.BLACK); // �O�����^�����ɫ
		Renderer.setChartTitleTextSize(20); // �O�����^���ִ�С
		Renderer.setAxesColor(Color.BLACK); // �O���p�S�ɫ
		Renderer.setBarSpacing(1); // �O��bar�g�ľ��x

		Renderer.setXTitle(XTitle); // �O��X�S����
		Renderer.setYTitle(YTitle); // �O��Y�S����
		Renderer.setXLabelsColor(Color.BLACK); // �O��X�S�����ɫ
		Renderer.setYLabelsColor(0, Color.BLACK); // �O��Y�S�����ɫ
		Renderer.setXLabelsAlign(Align.CENTER); // �O��X�S��������
		Renderer.setYLabelsAlign(Align.CENTER); // �O��Y�S��������
		Renderer.setXLabelsAngle(0); // �O��X�S���փAб��

		Renderer.setXLabels(0); // �O��X�S���@ʾ����, ���Գ�ʽ�O������
		Renderer.setYAxisMin(0); // �O��Y�S����Сֵ
		Renderer.setYAxisMax(30); // �O��Y�S����Сֵ
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// �����������϶�,�����������϶�.
		Renderer.setClickEnabled(true);//
		Renderer.setSelectableBuffer(50);

		yRenderer1.setColor(getResources().getColor(R.color.chartred)); // �O��Series�ɫ
		yRenderer2.setColor(getResources().getColor(R.color.chartred)); // �O��Series�ɫ
		// yRenderer.setDisplayChartValues(true); //չ�FSeries��ֵ

		// Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		Renderer.setPointSize(5);// ���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
		for (int r = 0; r < xy.length; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			// yRenderer1.setPointStyle(PointStyle.CIRCLE);//���������Բ��
			// Renderer.setPointSize(4);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
			// yRenderer1.setFillPoints(true);//���õ��Ƿ�ʵ��
			// yRenderer.setPointStyle(PointStyle.X);//���������Բ��
			// Renderer.setPointSize(5);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
			// yRenderer.setFillPoints(true);//���õ��Ƿ�ʵ��
			Renderer.addXTextLabel(r, xy[r][0]);
			// Renderer.addYTextLabel(r, xy[r][0]);
			Series1.add(r, Integer.parseInt(xy[r][1]));
		}
		yRenderer2.setPointStyle(PointStyle.CIRCLE);// ���������Բ��
		yRenderer2.setFillPoints(true);// ���õ��Ƿ�ʵ��
		for (int r = 2; r < xy.length - 2; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			// yRenderer.setPointStyle(PointStyle.X);//���������Բ��
			// Renderer.setPointSize(5);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
			// yRenderer.setFillPoints(true);//���õ��Ƿ�ʵ��
			Renderer.addXTextLabel(r, xy[r][0]);// Ϊ����Ϊr�ĵ�����xy[r][0]
			// Renderer.addYTextLabel(r, xy[r][0]);
			Series2.add(r, Integer.parseInt(xy[r][1]));// �������
		}
		// Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		// View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
		// Renderer, Type.DEFAULT);
		// View view = ChartFactory.getLineChartView(getBaseContext(), Dataset,
		// Renderer);
		// �⻬���߶�
		View view = ChartFactory.getCubeLineChartView(getBaseContext(),
				Dataset, Renderer, 0.2f);
		return view;
	}

	@SuppressWarnings("deprecation")
	private View getLineDashedwChart(String chartTitle, String XTitle,
			String YTitle, String[][] xy) {

		XYSeries Series1 = new XYSeries(YTitle);
		// line2
		XYSeries Series2 = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series1);
		// line2
		Dataset.addSeries(Series2);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer1 = new XYSeriesRenderer();
		XYSeriesRenderer yRenderer2 = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer1);
		Renderer.addSeriesRenderer(yRenderer2);

		Renderer.setFitLegend(true); // �������ͼ��Ӧ��С���ʺ�
		Renderer.setShowAxes(false); // ������õ���Ӧ���ǿɼ��ġ�
		Renderer.setShowCustomTextGrid(false); // �������������Զ���X��Y�ı�ǩӦ���ǿɼ��ġ�
		Renderer.setShowLegend(false); // ��������ߵı�ʶ�Ƿ��ǿɼ��ġ�
		yRenderer1.setFillBelowLine(false);
		yRenderer1.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));
		yRenderer2.setFillBelowLine(true);
		yRenderer2.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));

		Renderer.setApplyBackgroundColor(true); // �O�������ɫ
		Renderer.setBackgroundColor(Color.WHITE); // �O���D�ȇ������ɫ
		Renderer.setMarginsColor(Color.WHITE); // �O���D��������ɫ
		Renderer.setTextTypeface(null, Typeface.BOLD); // �O������style

		Renderer.setShowGrid(true); // �O���W��
		Renderer.setGridColor(Color.GRAY); // �O���W���ɫ

		Renderer.setChartTitle(chartTitle); // �O�����^����
		Renderer.setLabelsColor(Color.BLACK); // �O�����^�����ɫ
		Renderer.setChartTitleTextSize(20); // �O�����^���ִ�С
		Renderer.setAxesColor(Color.BLACK); // �O���p�S�ɫ
		Renderer.setBarSpacing(1); // �O��bar�g�ľ��x

		Renderer.setXTitle(XTitle); // �O��X�S����
		Renderer.setYTitle(YTitle); // �O��Y�S����
		Renderer.setXLabelsColor(Color.BLACK); // �O��X�S�����ɫ
		Renderer.setYLabelsColor(0, Color.BLACK); // �O��Y�S�����ɫ
		Renderer.setXLabelsAlign(Align.CENTER); // �O��X�S��������
		Renderer.setYLabelsAlign(Align.CENTER); // �O��Y�S��������
		Renderer.setXLabelsAngle(0); // �O��X�S���փAб��

		Renderer.setXLabels(0); // �O��X�S���@ʾ����, ���Գ�ʽ�O������
		Renderer.setYAxisMin(0); // �O��Y�S����Сֵ
		Renderer.setYAxisMax(30); // �O��Y�S����Сֵ
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// �����������϶�,�����������϶�.

		yRenderer1.setColor(getResources().getColor(R.color.chartred)); // �O��Series�ɫ
		yRenderer1.setStroke(BasicStroke.DASHED);// ������߷��
		yRenderer2.setColor(getResources().getColor(R.color.chartred)); // �O��Series�ɫ
		// yRenderer.setDisplayChartValues(true); //չ�FSeries��ֵ

		// Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		Renderer.setPointSize(5);// ���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
		for (int r = 0; r < xy.length; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			// yRenderer1.setPointStyle(PointStyle.CIRCLE);//���������Բ��
			// Renderer.setPointSize(4);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
			// yRenderer1.setFillPoints(true);//���õ��Ƿ�ʵ��
			// yRenderer.setPointStyle(PointStyle.X);//���������Բ��
			// Renderer.setPointSize(5);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
			// yRenderer.setFillPoints(true);//���õ��Ƿ�ʵ��
			Renderer.addXTextLabel(r + 1, xy[r][0]);
			Series1.add(r + 1, Integer.parseInt(xy[r][1]));
		}
		for (int r = 2; r < xy.length - 2; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			yRenderer2.setPointStyle(PointStyle.CIRCLE);// ���������Բ��
			Renderer.setPointSize(4);// ���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
			yRenderer2.setFillPoints(true);// ���õ��Ƿ�ʵ��
			// yRenderer.setPointStyle(PointStyle.X);//���������Բ��
			// Renderer.setPointSize(5);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
			// yRenderer.setFillPoints(true);//���õ��Ƿ�ʵ��
			Renderer.addXTextLabel(r + 1, xy[r][0]);
			Series2.add(r + 1, Integer.parseInt(xy[r][1]));
		}
		// Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		// View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
		// Renderer, Type.DEFAULT);
		// View view = ChartFactory.getLineChartView(getBaseContext(), Dataset,
		// Renderer);
		// �⻬���߶�
		View view = ChartFactory.getCubeLineChartView(getBaseContext(),
				Dataset, Renderer, 0.2f);
		return view;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// �л�ѡ�еĵ�
		llPoints.getChildAt(previousSelectPosition).setEnabled(false); // ��ǰһ������Ϊnormal״̬
		llPoints.getChildAt(position).setEnabled(true); // �ѵ�ǰѡ�е�position��Ӧ�ĵ���Ϊenabled״̬
		previousSelectPosition = position;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
