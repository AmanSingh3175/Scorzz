package com.example.scorzzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Cricket_Activity;
import com.SeriesActivity;


public class MainActivity extends AppCompatActivity {

    private Button cricket;
    private Button series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cricket = (Button) findViewById(R.id.cricButton);
        cricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        series = (Button) findViewById(R.id.seriesButton);
        series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

    }

    public void openActivity(){
        Intent intent = new Intent(this, Cricket_Activity.class);
        startActivity(intent);
    }

    public void openActivity2(){
        Intent intent = new Intent(this, SeriesActivity.class);
        startActivity(intent);
    }

}