package com.example.timur.organizer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;


public class Organizer extends Activity {
    TextView txtHello;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        txtHello = (TextView)findViewById(R.id.txtHello);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String str = year + "/"+month+"/"+dayOfMonth;
                Intent intent = new Intent(Organizer.this, CalendarActivity.class);
                intent.putExtra("date", str);
                startActivity(intent);
            }
        });
    }


}
