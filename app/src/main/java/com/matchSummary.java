package com;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scorzzz.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class matchSummary extends AppCompatActivity {

    String scoreUrl = "https://api.cricapi.com/v1/match_info?apikey=64146273-bada-44b7-b14c-2a92a457f652&id=";

    TextView name,status, matchtype,toss,mDate;
    private Button playerList;
    private Button inningFirst;
    private Button inningSecond;
    private Button bowlingFirst;
    private Button bowlingSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_summary);

        playerList =  (Button) findViewById(R.id.playerListBtn);

        playerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        inningFirst = (Button) findViewById(R.id.inning1Btn);

        inningFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        inningSecond = (Button) findViewById(R.id.inning2Btn);

        inningSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();
            }
        });

        bowlingFirst = (Button) findViewById(R.id.i1BowlingBtn);

        bowlingFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity4();
            }
        });

        bowlingSecond = (Button) findViewById(R.id.i2BowlingBtn);

        bowlingSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity5();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Match Summary");
        actionBar.setDisplayShowHomeEnabled(true);  // enabling back button from this activity.
        actionBar.setDisplayHomeAsUpEnabled(true);  // put < button at top left of activity.

        Intent intent = getIntent();
        String id = intent.getStringExtra("matchId");
        String date = intent.getStringExtra("matchDate");
        scoreUrl = scoreUrl+id;


        name = findViewById(R.id.name);
        status = findViewById(R.id.status);
        matchtype = findViewById(R.id.match_type);
        toss = findViewById(R.id.match_toss);
        mDate = findViewById(R.id.match_date);

        mDate.setText(date);

        loadSummary();

    }

    private void loadSummary(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching Summary");
        pd.show();

        StringRequest summaryStringRequest = new StringRequest(Request.Method.GET,
                scoreUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();

                        try{
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("data");
                            String matchName = jsonObject.getString("name");
                            String matchStatus = jsonObject.getString("status");
                            String type = jsonObject.getString("matchType");
                            String tossWin = jsonObject.getString("tossWinner");

                            name.setText(matchName);
                            status.setText(matchStatus);
                            matchtype.setText(type);
                            toss.setText(tossWin);
                        }
                        catch(Exception e){
                            Toast.makeText(matchSummary.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(matchSummary.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue summaryQueue = Volley.newRequestQueue(this);
        summaryQueue.add(summaryStringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();    //go to previous activity when back button pressed.
        return super.onSupportNavigateUp();
    }

    // enabling player list button so when clicked it will jump to playerList Activity...
    public void openActivity(){
        String mId = getIntent().getStringExtra("matchId");
        Intent i = new Intent(getApplicationContext(),PlayerListActivity.class);
        i.putExtra("mId",mId);
        startActivity(i);
    }

    public void openActivity2(){
        String mId = getIntent().getStringExtra("matchId");
        Intent i = new Intent(getApplicationContext(),firstInningScore.class);
        i.putExtra("mId",mId);
        startActivity(i);
    }

    public void openActivity3(){
        String mId = getIntent().getStringExtra("matchId");
        Intent i = new Intent(getApplicationContext(),secondInningScore.class);
        i.putExtra("mId",mId);
        startActivity(i);
    }

    public void openActivity4(){
        String mId = getIntent().getStringExtra("matchId");
        Intent i = new Intent(getApplicationContext(),firstInningBowling.class);
        i.putExtra("mId",mId);
        startActivity(i);
    }

    public void openActivity5() {
        String mId = getIntent().getStringExtra("matchId");
        Intent i = new Intent(getApplicationContext(), secondInningBowling.class);
        i.putExtra("mId", mId);
        startActivity(i);
    }
}