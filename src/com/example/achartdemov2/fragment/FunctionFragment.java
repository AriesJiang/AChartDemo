package com.example.achartdemov2.fragment;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.achartdemov2.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class FunctionFragment extends Fragment {
    private static final String TAG = "FunctionFragment";
    
    private RelativeLayout llBarChart;
    private View vChart1;
	private ImageView imge;
	private LinearLayout lin;
	private Context mcontext;
	DisplayMetrics metric;
	
	private String[][] Top10ErrCode={{"ADFU1","5"},{"MBPW2","8"},
			{"ABCDE","12"},{"BLFU1","16"},{"LCVD3","15"},{"ADDK1","11"},
			{"CMFU3","8"},{"LCCR2","7"},{"QBLE1","5"},{"SPNS1","2"}};

    public static FunctionFragment newInstance(String s) {
        FunctionFragment newFragment = new FunctionFragment();
        Log.d(TAG, "FunctionFragment-----" + s);
//        Bundle bundle = new Bundle();
//        bundle.putString("hello", s);
//        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "TestFragment-----onCreate");
//        Bundle args = getArguments();
//        hello = args != null ? args.getString("hello") : defaultHello;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment-----onCreateView");
        mcontext = FunctionFragment.this.getActivity();
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        llBarChart = (RelativeLayout) view.findViewById(R.id.layout1);
        
        vChart1 = getLineDashedwChart("PQC Top 10 ErrCode", "ErrCode", "QTY", Top10ErrCode);
		llBarChart.removeAllViews();
		//llBarChart.addView(vChart);
		llBarChart.addView(vChart1, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
        return view;
    }
    
    @SuppressWarnings("deprecation")
	private View getLineDashedwChart(String chartTitle, String XTitle, String YTitle, String[][] xy){
		
		XYSeries Series1 = new XYSeries(YTitle);
		//line2
		XYSeries Series2 = new XYSeries(YTitle);
		
		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();             
		Dataset.addSeries(Series1);
		//line2
		Dataset.addSeries(Series2);
		
		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer(); 
		XYSeriesRenderer yRenderer1 = new XYSeriesRenderer();        
		XYSeriesRenderer yRenderer2 = new XYSeriesRenderer();        
		Renderer.addSeriesRenderer(yRenderer1);
		Renderer.addSeriesRenderer(yRenderer2);
		
		Renderer.setFitLegend(true);                  //�������ͼ��Ӧ��С���ʺ�
		Renderer.setShowAxes(false);                  //������õ���Ӧ���ǿɼ��ġ�
		Renderer.setShowCustomTextGrid(false);     //�������������Զ���X��Y�ı�ǩӦ���ǿɼ��ġ�
		Renderer.setShowLegend(false);     //��������ߵı�ʶ�Ƿ��ǿɼ��ġ�
		yRenderer1.setFillBelowLine(false);
		yRenderer1.setFillBelowLineColor(getResources().getColor(R.color.chartred_shadow));
		yRenderer2.setFillBelowLine(true);
		yRenderer2.setFillBelowLineColor(getResources().getColor(R.color.chartred_shadow));
		
		Renderer.setApplyBackgroundColor(true);			//�O�������ɫ
		Renderer.setBackgroundColor(Color.WHITE);			//�O���D�ȇ������ɫ
		Renderer.setMarginsColor(Color.WHITE);				//�O���D��������ɫ
		Renderer.setTextTypeface(null, Typeface.BOLD);		//�O������style
		
		Renderer.setShowGrid(true);							//�O���W��
		Renderer.setGridColor(Color.GRAY);					//�O���W���ɫ
		
		Renderer.setChartTitle(chartTitle);					//�O�����^����
		Renderer.setLabelsColor(Color.BLACK);				//�O�����^�����ɫ
		Renderer.setChartTitleTextSize(20);					//�O�����^���ִ�С
		Renderer.setAxesColor(Color.BLACK);					//�O���p�S�ɫ
		Renderer.setBarSpacing(1);						//�O��bar�g�ľ��x
		
		Renderer.setXTitle(XTitle);						//�O��X�S����      
		Renderer.setYTitle(YTitle);						//�O��Y�S���� 
		Renderer.setXLabelsColor(Color.BLACK);				//�O��X�S�����ɫ
		Renderer.setYLabelsColor(0, Color.BLACK);			//�O��Y�S�����ɫ
		Renderer.setXLabelsAlign(Align.CENTER);				//�O��X�S��������
		Renderer.setYLabelsAlign(Align.CENTER);				//�O��Y�S��������
		Renderer.setXLabelsAngle(0); 						//�O��X�S���փAб��
		
		Renderer.setXLabels(0); 							//�O��X�S���@ʾ����, ���Գ�ʽ�O������
		Renderer.setYAxisMin(0);							//�O��Y�S����Сֵ
		Renderer.setYAxisMax(30);							//�O��Y�S����Сֵ
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// �����������϶�,�����������϶�. 
		
		yRenderer1.setColor(getResources().getColor(R.color.chartred));              		//�O��Series�ɫ
		yRenderer1.setStroke(BasicStroke.DASHED);//������߷��
		yRenderer2.setColor(getResources().getColor(R.color.chartred));              		//�O��Series�ɫ
		//yRenderer.setDisplayChartValues(true);			//չ�FSeries��ֵ
		
//		Series.add(0, 0); 
//			Renderer.addXTextLabel(0, "");
		Renderer.setPointSize(5);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
		for(int r=0; r<xy.length; r++) {
			//Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
//			yRenderer1.setPointStyle(PointStyle.CIRCLE);//���������Բ��
//			Renderer.setPointSize(4);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
//			yRenderer1.setFillPoints(true);//���õ��Ƿ�ʵ��  
//			yRenderer.setPointStyle(PointStyle.X);//���������Բ��
//			Renderer.setPointSize(5);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
//			yRenderer.setFillPoints(true);//���õ��Ƿ�ʵ��  
			Renderer.addXTextLabel(r+1, xy[r][0]);
			Series1.add(r+1, Integer.parseInt(xy[r][1]));     
		}
		for(int r=2; r<xy.length-2; r++) {
			//Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			yRenderer2.setPointStyle(PointStyle.CIRCLE);//���������Բ��
			Renderer.setPointSize(4);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
			yRenderer2.setFillPoints(true);//���õ��Ƿ�ʵ��  
//			yRenderer.setPointStyle(PointStyle.X);//���������Բ��
//			Renderer.setPointSize(5);//���õ�Ĵ�С(ͼ����ʾ�ĵ�Ĵ�С��ͼ���е�Ĵ�С���ᱻ����)
//			yRenderer.setFillPoints(true);//���õ��Ƿ�ʵ��  
			Renderer.addXTextLabel(r+1, xy[r][0]);
			Series2.add(r+1, Integer.parseInt(xy[r][1]));     
		}
//		Series.add(11, 0); 
//			Renderer.addXTextLabel(xy.length+1, "");
		
//		View view = ChartFactory.getBarChartView(getBaseContext(), Dataset, Renderer, Type.DEFAULT);  
//		View view = ChartFactory.getLineChartView(getBaseContext(), Dataset, Renderer);
		//�⻬���߶�
		View view = ChartFactory.getCubeLineChartView(mcontext, Dataset, Renderer, 0.2f);
		return view;
	}
    

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TestFragment-----onDestroy");
    }

}
