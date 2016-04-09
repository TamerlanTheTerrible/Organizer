package com.example.timur.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Timur on 4/8/2016.
 */
public class CalendarActivity extends ActionBarActivity{
    private CalendarPickerView calendar;
    DBService dbService;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        sdf = new SimpleDateFormat("dd-M-yyyy");

        dbService = new DBService(this);
        dbService = dbService.open();

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today)
                .inMode(CalendarPickerView.SelectionMode.SINGLE);
        if(!(getDates().isEmpty())){
            calendar.highlightDates(getDates());
        }


        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Intent intent = new Intent(CalendarActivity.this, TaskListActivity.class);
                String strDate = sdf.format(date);
                intent.putExtra("date", strDate);
                startActivity(intent);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                return true;
            case R.id.action_next:
                ArrayList<Date> selectedDates = (ArrayList<Date>)calendar
                        .getSelectedDates();
                Toast.makeText(CalendarActivity.this, selectedDates.toString(),
                        Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private ArrayList<Date> getHolidays(){
        String dateInString = "10-05-2016";
        Date date1=null;
        Date date = null;
        try {
            date = sdf.parse(dateInString);
            date1 = getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Date> holidays = new ArrayList<>();
        holidays.add(date);
        if (!(date1==null)){
            holidays.add(date1);
        }
        return holidays;
    }

    private Date getDate() throws ParseException {
        Intent receiveIntent = getIntent();
        String strDate = receiveIntent.getStringExtra("date");
        Date date=null;
        if(!(strDate==null)){
            try {
                date = sdf.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    private ArrayList<Date> getDates(){
        ArrayList<Date> dates = new ArrayList<Date>();
        ArrayList<String> strDates;
        if(!(dbService.getAllDates()).isEmpty()){
            strDates = new ArrayList<String>(dbService.getAllDates());
            for (int i=0; i<strDates.size()-1; i++){
                try {
                    dates.add(sdf.parse(strDates.get(i)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return dates;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }
}
