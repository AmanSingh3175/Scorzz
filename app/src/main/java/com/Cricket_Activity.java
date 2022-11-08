package com;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scorzzz.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cricket_Activity extends AppCompatActivity {

    private RecyclerView cricView;

    String cricUrl = "https://api.cricapi.com/v1/currentMatches?apikey=9b86db1d-cc04-4b7b-bbb9-2c5973976b94&offset=0";

    private RecyclerView.Adapter myAdapter;
    private List<match_info> MatchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cricactivity_main);

        cricView = findViewById(R.id.cricview);
        cricView.setHasFixedSize(true);
        cricView.setLayoutManager(new LinearLayoutManager(this));

        MatchInfo = new ArrayList<>();

        loadCricdata();
    }

    private void loadCricdata(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Information");
        pd.show();

        StringRequest cricStringRequest = new StringRequest(Request.Method.GET,
                cricUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();

                        try{
                            JSONArray jsonArray = new JSONObject(response).getJSONArray("data");

                            for(int i=0;i< jsonArray.length();i++){
                                try{
                                    String uniqueId = jsonArray.getJSONObject(i).getString("id");
                                    String team1 = jsonArray.getJSONObject(i).getJSONArray("teams").getString(0);
                                    String team2 = jsonArray.getJSONObject(i).getJSONArray("teams").getString(1);
                                    String date = jsonArray.getJSONObject(i).getString("date");
                                    String venue = jsonArray.getJSONObject(i).getString("venue");
                                    String matchStatus = jsonArray.getJSONObject(i).getString("matchStarted");
                                    if(matchStatus.equals("true")){
                                        matchStatus = "Match Started";
                                    }else{
                                        matchStatus = "Match Not Started Yet";
                                    }

                                    match_info info = new match_info(uniqueId,team1,team2,date,venue,matchStatus);
                                    MatchInfo.add(info);

                                }

                                catch (Exception e){
                                    Toast.makeText(Cricket_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            myAdapter = new cricAdapter(MatchInfo,getApplicationContext());
                            cricView.setAdapter(myAdapter);

                        }
                        catch (Exception e){
                            Toast.makeText(Cricket_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },new Response.ErrorListener(){
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(Cricket_Activity.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
        });

        RequestQueue cricRequestQueue = Volley.newRequestQueue(this);
        cricRequestQueue.add(cricStringRequest);
    }
}
