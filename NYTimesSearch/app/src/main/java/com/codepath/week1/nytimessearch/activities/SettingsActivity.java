package com.codepath.week1.nytimessearch.activities;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.week1.nytimessearch.R;

import java.text.ParseException;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {
    TextView tvBeginDate;
    Spinner sortOrder;
    CheckBox cbArts, cbFashion, cbSports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");

        tvBeginDate = (TextView) findViewById(R.id.etBeginDate);
        sortOrder = (Spinner) findViewById(R.id.sortOrder);

        cbArts = (CheckBox) findViewById(R.id.cbArts);
        cbFashion = (CheckBox) findViewById(R.id.cbFashion);
        cbSports = (CheckBox) findViewById(R.id.cbSports);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void submitMessage(View v) throws ParseException {
        String beginDate = tvBeginDate.getText().toString();
        String order = sortOrder.getSelectedItem().toString();
        Boolean arts = cbArts.isChecked();
        Boolean fashion = cbFashion.isChecked();
        Boolean sports = cbSports.isChecked();

        SimpleDateFormat spf=new SimpleDateFormat("dd/mm/yyyy");
        Date newDate=spf.parse(beginDate);
        spf= new SimpleDateFormat("yyyymmdd");
        String new_date = spf.format(newDate);

        Intent intent = new Intent();
        intent.putExtra("begin_date", new_date);
        intent.putExtra("order", order);

        intent.putExtra("arts",arts);
        intent.putExtra("fashion",fashion);
        intent.putExtra("sports",sports);

        setResult(2,intent);
        finish();
    }
}
