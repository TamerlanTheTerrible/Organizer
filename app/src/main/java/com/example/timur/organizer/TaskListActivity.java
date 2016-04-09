package com.example.timur.organizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Timur on 4/8/2016.
 */
public class TaskListActivity extends Activity{
    TextView txtTask;
    DBService dbService;
    Button btnNewTask;
    Intent receiveIntent;
    String strDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklist_layout);

        dbService = new DBService(this);
        dbService = dbService.open();

        receiveIntent = getIntent();
        strDate = receiveIntent.getStringExtra("date");

        btnNewTask = (Button)findViewById(R.id.btnGoTask);
        btnNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(TaskListActivity.this, TaskActivity.class);
                sendIntent.putExtra("date", strDate);
                startActivity(sendIntent);
                finish();
            }
        });

        txtTask = (TextView)findViewById(R.id.txtATask);
        txtTask.setText(dbService.getTask(strDate));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }
}
