package com.example.scorzzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Cricket_Activity;

public class MainActivity extends AppCompatActivity {

    private Button cricket;

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

    }

    public void openActivity(){
        Intent intent = new Intent(this, Cricket_Activity.class);
        startActivity(intent);
    }
}