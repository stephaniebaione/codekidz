package com.example.regina.ratapp.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.regina.ratapp.Model.GraphQueryManager;
import com.example.regina.ratapp.Model.Month;
import com.example.regina.ratapp.Model.QueryManager;
import com.example.regina.ratapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphActivity extends AppCompatActivity {

    private Spinner stM;
    private Spinner stY;
    private Spinner endY;
    private Spinner endM;
    private GraphView graph;
    //DataPoint[] dataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        createSpinners();

        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true);
        graphManipulate();
    }

    /**
     * gets the data from the spinners and passes into the GraphQueryManager Class to manipulate it.
     */
    private void graphManipulate(){
        Button grapher = (Button) findViewById(R.id.graphAgain);
        final GraphQueryManager dateSearch = new GraphQueryManager(GraphActivity.this);
        grapher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Month startM = (Month) stM.getSelectedItem();
                int startMV = Integer.parseInt(startM.getMonthCode());
                int startYear = Integer.parseInt((String)stY.getSelectedItem());
                Month endMonth = (Month) endM.getSelectedItem();
                int endMV = Integer.parseInt(endMonth.getMonthCode());
                int endYear = Integer.parseInt((String)endY.getSelectedItem());

                if (dateSearch.validDates(startMV, endMV, startYear,endYear)){
                    graph.removeAllSeries();
                    GraphQueryManager.DateSearcher newSearch = dateSearch.getDateSearcherTask();
                    newSearch.execute(startMV, endMV, startYear, endYear);

                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(GraphActivity.this);
                    dialog.setCancelable(true);
                    dialog.setTitle("Oh rats! Something is not right");
                    dialog.setMessage("The dates entered are not in the right order." +
                            " Please make sure your start date is before the end date");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setCancelable(true);
                    AlertDialog alertDialog = dialog.show();
                }

            }
        });
    }

    /**
     * creates the bars on the graph and graph.
     * @param dataList list of number of reports made each month
     */
    public void createGraph(HashMap<String,Integer> dataList, Boolean sameYear){
        ArrayList<DataPoint> dataArray = new ArrayList<DataPoint>();
        double minX = 500000;
        if (sameYear) {
            dataArray.add(new DataPoint(0, 0));
            Log.d("graphing", "datapoint is 0");
        } else {
            dataArray.add(new DataPoint(201000,0));
            Log.d("graphing", "datapoint is 09");
        }
        double maxX = 0;
        for (Map.Entry<String, Integer> entry: dataList.entrySet()) {
            DataPoint point = new DataPoint(Double.parseDouble(entry.getKey()),entry.getValue());
            dataArray.add(point);
            if (Double.parseDouble(entry.getKey()) > maxX){
                maxX = Double.parseDouble(entry.getKey());
            }
            if (Double.parseDouble(entry.getKey()) < minX){
                minX = Double.parseDouble(entry.getKey());
            }
            Log.d("graphing", entry.getKey() + " "+ entry.getValue());
        }
        DataPoint[] points = dataArray.toArray(new DataPoint[dataArray.size()]);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points);
        series.setSpacing(50);
        graph.addSeries(series);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        //graph.onDataChanged(false,false);
        graph.getViewport().setMaxX(maxX );
        graph.getViewport().setMinX(minX );


    }

    /**
     * creates the spinners and populates them with choices
     */
    public void createSpinners(){
        stM = (Spinner) findViewById(R.id.spinner2);
        stY = (Spinner) findViewById(R.id.spinner3);
        endM = (Spinner) findViewById(R.id.spinner);
        endY = (Spinner) findViewById(R.id.spinner4);

        ArrayAdapter<CharSequence> yearAdapt = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> monthAdapt = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, Month.values());

        yearAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stY.setAdapter(yearAdapt);
        endY.setAdapter(yearAdapt);
        stM.setAdapter(monthAdapt);
        endM.setAdapter(monthAdapt);
    }
}
