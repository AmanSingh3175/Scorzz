package com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class SeriesActivity extends AppCompatActivity {

    private RecyclerView seriesView;

    String seriesUrl = "https://api.cricapi.com/v1/series?apikey=64146273-bada-44b7-b14c-2a92a457f652&offset=0";

    private RecyclerView.Adapter sAdapter;
    private List<series_info> SeriesInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_info);

        seriesView = findViewById(R.id.seriesview);
        seriesView.setHasFixedSize(true);
        seriesView.setLayoutManager(new LinearLayoutManager(this));

        SeriesInfo = new ArrayList<>();

        loadSeriesdata();

    }

    private void loadSeriesdata(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Information");
        pd.show();

        StringRequest seriesStringRequest = new StringRequest(Request.Method.GET,
                seriesUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();

                        try{
                            JSONArray jsonArray = new JSONObject(response).getJSONArray("data");

                            for(int i=0;i< jsonArray.length();i++){
                                try{
                                    String uniqueId = jsonArray.getJSONObject(i).getString("id");
                                    String name = jsonArray.getJSONObject(i).getString("name");
                                    String start = jsonArray.getJSONObject(i).getString("startDate");
                                    String end = jsonArray.getJSONObject(i).getString("endDate");
                                    String odi = jsonArray.getJSONObject(i).getString("odi");
                                    String t20 = jsonArray.getJSONObject(i).getString("t20");
                                    String test = jsonArray.getJSONObject(i).getString("test");

                                    series_info info = new series_info(uniqueId,name,start,end,odi,t20,test);
                                    SeriesInfo.add(info);

                                }

                                catch (Exception e){
                                    Toast.makeText(SeriesActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            sAdapter = new seriesAdapter(SeriesInfo,getApplicationContext());
                            seriesView.setAdapter(sAdapter);

                        }
                        catch (Exception e){
                            Toast.makeText(SeriesActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                Toast.makeText(SeriesActivity.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue seriesRequestQueue = Volley.newRequestQueue(this);
        seriesRequestQueue.add(seriesStringRequest);
    }
}