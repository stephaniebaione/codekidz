package com.example.regina.ratapp.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.regina.ratapp.Model.Month;
import com.example.regina.ratapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    private Spinner stM;
    private Spinner stY;
    private Spinner endY;
    private Spinner endM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        createSpinners();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

    public void createSpinners(){
        stM = (Spinner) findViewById(R.id.spinner2);
        stY = (Spinner) findViewById(R.id.spinner3);
        endM = (Spinner) findViewById(R.id.spinner);
        endY = (Spinner) findViewById(R.id.spinner4);

        ArrayAdapter<CharSequence> yearAdapt = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> monthAdapt = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Month.values());

        yearAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stY.setAdapter(yearAdapt);
        endY.setAdapter(yearAdapt);
        stM.setAdapter(monthAdapt);
        endM.setAdapter(monthAdapt);
    }
}
