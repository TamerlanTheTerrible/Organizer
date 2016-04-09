package com.example.timur.organizer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Timur on 4/8/2016.
 */
public class TaskActivity extends Activity {
    TextView txtTask, txtFromDate, txtToDate;
    Button btnAdd, btnCancel;
    EditText edtTask;
    DBService dbService;

    int DIALOG_DATE;
    int myYear = 2016;
    int myMonth = 05;
    int myDay = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_layout);

        dbService = new DBService(this);
        dbService = dbService.open();

        txtTask = (TextView)findViewById(R.id.txtTask);
        txtFromDate = (TextView)findViewById(R.id.txtFromDate);
        txtToDate = (TextView)findViewById(R.id.txtToDate);
        edtTask = (EditText)findViewById(R.id.edtTask);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        txtTask.setText("New Task");

        Intent receiveIntent = getIntent();
        String strDate = receiveIntent.getStringExtra("date");
        txtFromDate.setText(strDate);
        txtToDate.setText(strDate);




        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnAdd:
                        addNewTask();
                        Intent sendIntent = new Intent(TaskActivity.this, CalendarActivity.class);
                        sendIntent.putExtra("date", txtFromDate.getText().toString());
                        startActivity(sendIntent);
                        finish();
                        break;
                    case R.id.btnCancel:
                        cancel();
                        break;
                    case R.id.txtFromDate:
                        showDialog(DIALOG_DATE);
                        break;
                    case R.id.txtToDate:
                        showDialog(DIALOG_DATE);
                        break;
                }
            }
        };

        btnAdd.setOnClickListener(onClickListener);
        btnCancel.setOnClickListener(onClickListener);
        txtToDate.setOnClickListener(onClickListener);
        txtToDate.setOnClickListener(onClickListener);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            txtToDate.setText(myDay + "-" + myMonth + "-" + myYear);
        }
    };

    void addNewTask(){
        String desc = edtTask.getText().toString();
        String toDate = txtToDate.getText().toString();
        String fromDate = txtFromDate.getText().toString();
        dbService.addTask(desc, toDate, fromDate);
        Toast.makeText(getApplicationContext(), "задача добавлена", Toast.LENGTH_SHORT).show();
    }

    void cancel(){
        edtTask.setText("");
        startActivity(new Intent(TaskActivity.this, CalendarActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbService.close();
    }
}
