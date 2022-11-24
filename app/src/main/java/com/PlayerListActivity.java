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

public class PlayerListActivity extends AppCompatActivity {

    String playersUrl = "https://api.cricapi.com/v1/match_squad?apikey=64146273-bada-44b7-b14c-2a92a457f652&id=";
    TextView t1Name,t2Name,t1playerName,t2playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        t1Name = findViewById(R.id.t1Name);
        t2Name = findViewById(R.id.t2Name);
        t1playerName = findViewById(R.id.t1PlayerName);
        t2playerName = findViewById(R.id.t2PlayerName);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Players List");
        actionBar.setDisplayShowHomeEnabled(true);  // enabling back button from this activity.
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("mId");
        playersUrl += id;

        loadPlayerList();

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();    //go to previous activity when back button pressed.
        return super.onSupportNavigateUp();
    }

    private void loadPlayerList(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Players List");
        pd.show();

        StringRequest playerListRequest = new StringRequest(Request.Method.GET,
                playersUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();

                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
                    JSONObject t1 = jsonArray.getJSONObject(0);
                    JSONObject t2 = jsonArray.getJSONObject(1);

                    String team1 = t1.getString("teamName");
                    String team2 = t2.getString("teamName");

                    JSONArray t1playerlist = t1.getJSONArray("players");
                    JSONArray t2playerlist = t2.getJSONArray("players");

                    // name of both teams are set up.
                    t1Name.setText(team1);
                    t2Name.setText(team2);

                    // player List of team1
                    for(int i=0;i<t1playerlist.length();i++){
                        String t1Player = t1playerlist.getJSONObject(i).getString("name");
                        t1playerName.append((i+1)+". "+t1Player +"\n");
                    }

                    // player List of team1
                    for(int i=0;i<t2playerlist.length();i++){
                        String t2Player = t2playerlist.getJSONObject(i).getString("name");
                        t2playerName.append((i+1)+"."+t2Player+"\n");
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlayerListActivity.this, "Sorry!!Not able to load Players List at the moment", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue playerListQueue = Volley.newRequestQueue(this);
        playerListQueue.add(playerListRequest);
    }
}