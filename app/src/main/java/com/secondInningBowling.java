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

public class secondInningBowling extends AppCompatActivity {

    String secondInningBowlingUrl = "https://api.cricapi.com/v1/match_scorecard?apikey=64146273-bada-44b7-b14c-2a92a457f652&id=";
    TextView t2Bowling,Bowler,Overs,Runs,Wickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_inning_bowling);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inning2 Bowling Card");
        actionBar.setDisplayShowHomeEnabled(true);  // enabling back button from this activity.
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("mId");
        secondInningBowlingUrl += id;

        t2Bowling = findViewById(R.id.t2Bowling);
        Bowler = findViewById(R.id.t2BowlerName);
        Overs = findViewById(R.id.t2overs);
        Runs = findViewById(R.id.t2givenRuns);
        Wickets = findViewById(R.id.t2wickets);

        loadT2BowlingCard();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();    //go to previous activity when back button pressed.
        return super.onSupportNavigateUp();
    }

    private void loadT2BowlingCard(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Batting Card");
        pd.show();

        StringRequest t2BCardRequest = new StringRequest(Request.Method.GET, secondInningBowlingUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONArray scoreArray = dataObject.getJSONArray("score");
                    JSONObject score0 = scoreArray.getJSONObject(1);
                    String inning2 = score0.getString("inning");
                    String runs = score0.getString("r");
                    String wickets = score0.getString("w");
                    String overs = score0.getString("o");

                    t2Bowling.append(inning2+" : "+runs+"/"+wickets+"("+overs+")");

                    JSONArray scoreCardArray = dataObject.getJSONArray("scorecard");
                    JSONObject card = scoreCardArray.getJSONObject(1);
                    JSONArray bowlingArray = card.getJSONArray("bowling");
                    for(int i=0;i<bowlingArray.length();i++){

                        String bowler = bowlingArray.getJSONObject(i).getJSONObject("bowler").getString("name");
                        Bowler.append(bowler+"\n");

                        String over = bowlingArray.getJSONObject(i).getString("o");
                        Overs.append(over+"\n");

                        String run = bowlingArray.getJSONObject(i).getString("r");
                        Runs.append(run+"\n");

                        String wicket = bowlingArray.getJSONObject(i).getString("w");
                        Wickets.append(wicket+"\n");
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(secondInningBowling.this, "Unable to load Bowling Card", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue bowlingCard = Volley.newRequestQueue(this);
        bowlingCard.add(t2BCardRequest);
    }
}