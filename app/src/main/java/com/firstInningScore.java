package com;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scorzzz.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class firstInningScore extends AppCompatActivity {

    String firstInningScoreUrl = "https://api.cricapi.com/v1/match_scorecard?apikey=64146273-bada-44b7-b14c-2a92a457f652&id=";
    TextView t1Batting,Name,Runs,Balls,Dismissal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_inning_score);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inning1 Batting Card");
        actionBar.setDisplayShowHomeEnabled(true);  // enabling back button from this activity.
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("mId");
        firstInningScoreUrl += id;

        t1Batting = findViewById(R.id.t1Batting);
        Name = findViewById(R.id.PName);
        Runs = findViewById(R.id.Runs);
        Balls = findViewById(R.id.balls);
        Dismissal = findViewById(R.id.dismissal);

        loadT1BattingCard();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();    //go to previous activity when back button pressed.
        return super.onSupportNavigateUp();
    }

    private void loadT1BattingCard(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Batting Card");
        pd.show();

        StringRequest t1CardRequest = new StringRequest(Request.Method.GET, firstInningScoreUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONArray scoreArray = dataObject.getJSONArray("score");
                    JSONObject score0 = scoreArray.getJSONObject(0);
                    String inning1 = score0.getString("inning");
                    String runs = score0.getString("r");
                    String wickets = score0.getString("w");
                    String overs = score0.getString("o");

                    t1Batting.append(inning1+" : "+runs+"/"+wickets+"("+overs+")");

                    JSONArray scoreCardArray = dataObject.getJSONArray("scorecard");
                    JSONObject card = scoreCardArray.getJSONObject(0);
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
                Toast.makeText(firstInningScore.this, "Unable to load Batting Card", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue battingCard = Volley.newRequestQueue(this);
        battingCard.add(t1CardRequest);
    }
}
