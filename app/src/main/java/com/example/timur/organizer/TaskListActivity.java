package com.example.timur.organizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Timur on 4/8/2016.
 */
public class TaskListActivity extends Activity{
    TextView txtDesc, txtFrom, txtTo;
    DBService dbService;
    Button btnNewTask;
    Intent receiveIntent;
    String strDate;
    ListView lv;
    final String TASK_DESCRIPTION = "description";
    final String START_DATE = "start";
    final String FINISH_DATE = "finish";
    ArrayList<Map<String, Object>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklist_layout);

        dbService = new DBService(this);
        dbService = dbService.open();

        receiveIntent = getIntent();
        strDate = receiveIntent.getStringExtra("date");

        txtDesc = (TextView)findViewById(R.id.txtDesc);
        txtFrom = (TextView)findViewById(R.id.txtFromDate);
        txtTo = (TextView)findViewById(R.id.txtToDate);

        btnNewTask = (Button)findViewById(R.id.btnGoTask);
        btnNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CalendarActivity.IS_REGISTERED){
                    Toast.makeText(getApplicationContext(), "Пожалуйста, войдите в ситему", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TaskListActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Intent sendIntent = new Intent(TaskListActivity.this, TaskActivity.class);
                    sendIntent.putExtra("date", strDate);
                    startActivity(sendIntent);
                    finish();
                }
            }
        });

        fillList();
    }

    public void fillList(){
        List<String> tasks = new ArrayList<>(dbService.getTasks(strDate));
        List<String> fromDates = new ArrayList<>(dbService.getStartDates(strDate));
        List<String> toDates = new ArrayList<>(dbService.getFinishDates(strDate));
        data = new ArrayList<>(tasks.size());
        Map<String, Object> m;
        if(!(tasks.isEmpty())){
            for (int i=0; i<tasks.size(); i++){
                m = new HashMap<String, Object>();
                m.put(TASK_DESCRIPTION, tasks.get(i));
                m.put(START_DATE, fromDates.get(i));
                m.put(FINISH_DATE, toDates.get(i));
                data.add(m);
            }
            String[] from = {TASK_DESCRIPTION, START_DATE, FINISH_DATE};
            int[] to = {R.id.txtDesc, R.id.txtFromDate, R.id.txtToDate};
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.list_item2, from, to);

            lv = (ListView)findViewById(R.id.lvMain);
            lv.setAdapter(simpleAdapter);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }
}
