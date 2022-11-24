package com;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class secondInningScore extends AppCompatActivity {

    String secondInningUrl="https://api.cricapi.com/v1/match_scorecard?apikey=64146273-bada-44b7-b14c-2a92a457f652&id=";
    TextView t2Batting,Name,Runs,Balls,Dismissal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_inning_score);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inning2 Batting Card");
        actionBar.setDisplayShowHomeEnabled(true);  // enabling back button from this activity.
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("mId");
        secondInningUrl += id;

        t2Batting = findViewById(R.id.t2Batting);
        Name = findViewById(R.id.P2Name);
        Runs = findViewById(R.id.T2Runs);
        Balls = findViewById(R.id.T2balls);
        Dismissal = findViewById(R.id.T2dismissal);

        loadSecondInning();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();    //go to previous activity when back button pressed.
        return super.onSupportNavigateUp();
    }

    private  void loadSecondInning(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Batting Card");
        pd.show();

        StringRequest t2CardRequest = new StringRequest(Request.Method.GET, secondInningUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONArray scoreArray = dataObject.getJSONArray("score");
                    JSONObject score1 = scoreArray.getJSONObject(1);
                    String inning2 = score1.getString("inning");
                    String runs = score1.getString("r");
                    String wickets = score1.getString("w");
                    String overs = score1.getString("o");

                    t2Batting.append(inning2+" : "+runs+"/"+wickets+"("+overs+")");

                    JSONArray scoreCardArray = dataObject.getJSONArray("scorecard");
                    JSONObject card = scoreCardArray.getJSONObject(1);
                    JSONArray battingArray = card.getJSONArray("batting");
                    for(int i=0;i<battingArray.length();i++){
                        String run = battingArray.getJSONObject(i).getString("r");
                        String ball = battingArray.getJSONObject(i).getString("b");

                        Runs.append(run+"\n");
                        Balls.append(ball+"\n");

                        String batsman = battingArray.getJSONObject(i).getJSONObject("batsman").getString("name");
                        Name.append(batsman+"\n");

                        String dismissal = battingArray.getJSONObject(i).getString("dismissal-text");
                        Dismissal.append(dismissal+"\n");
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(secondInningScore.this, "Unable to load Batting Card", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue battingCard = Volley.newRequestQueue(this);
        battingCard.add(t2CardRequest);
    }
}