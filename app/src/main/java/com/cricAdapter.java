package com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scorzzz.R;

import java.util.List;

public class cricAdapter extends RecyclerView.Adapter<cricAdapter.ViewHolder>{

    private List<match_info> matchInfoList;
    private Context context;

    public cricAdapter(List<match_info> matchInfoList, Context context) {
        this.matchInfoList = matchInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cric_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        match_info MatchInfo = matchInfoList.get(position);
        holder.team1.setText(MatchInfo.getTeam1());
        holder.team2.setText(MatchInfo.getTeam2());
        holder.date.setText(MatchInfo.getDate());
        holder.venue.setText(MatchInfo.getVenue());
        holder.status.setText(MatchInfo.getMatchStatus());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return matchInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView team1,team2,date,venue,status;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            team1 = itemView.findViewById(R.id.team1);
            team2 = itemView.findViewById(R.id.team2);
            date = itemView.findViewById(R.id.match_date);
            venue = itemView.findViewById(R.id.match_venue);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.cricRow);
        }
    }
}
