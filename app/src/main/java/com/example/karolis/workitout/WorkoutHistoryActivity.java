package com.example.karolis.workitout;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.karolis.workitout.dao.Database;
import com.example.karolis.workitout.dataobjects.HistoryElement;

import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {

    List<HistoryElement> historyElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("History");
        setSupportActionBar(toolbar);




        TableLayout table=(TableLayout)findViewById(R.id.history_table);
        loadData();
        for(HistoryElement he : historyElements)
        {
            // Inflate your row "template" and fill out the fields.
            TableRow row = (TableRow) LayoutInflater.from(WorkoutHistoryActivity.this).inflate(R.layout.history_table_row, null);
            ((TextView)row.findViewById(R.id.history_date)).setText(he.getDate());
            ((TextView)row.findViewById(R.id.history_name)).setText(he.getName());
            ((TextView)row.findViewById(R.id.history_duration)).setText(he.getDuration());
            table.addView(row);
        }
        table.requestLayout();
    }

    private void loadData(){
        Database db = new Database(getApplication().openOrCreateDatabase("workout.db", Context.MODE_PRIVATE,null));
        historyElements = db.selectWorkoutHistory(10);
    }

}
