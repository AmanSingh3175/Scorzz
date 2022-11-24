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

public class seriesAdapter extends RecyclerView.Adapter<seriesAdapter.ViewHolder> {
    private List<series_info> seriesInfo;
    private Context context;

    public seriesAdapter(List<series_info> seriesInfo, Context Context) {
        this.seriesInfo = seriesInfo;
        this.context = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull seriesAdapter.ViewHolder holder, int position) {

        series_info SeriesInfo = seriesInfo.get(position);
        holder.name.setText(SeriesInfo.getName());
        holder.startDate.setText(SeriesInfo.getStartDate());
        holder.endDate.setText(SeriesInfo.getEndDate());
        holder.odis.setText(SeriesInfo.getODIs());
        holder.t20s.setText(SeriesInfo.getT20s());
        holder.tests.setText(SeriesInfo.getTESTS());

    }

    @Override
    public int getItemCount() {
        return seriesInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,startDate,endDate,odis,t20s,tests;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.teams);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            odis = itemView.findViewById(R.id.odiMatches);
            t20s = itemView.findViewById(R.id.t20Matches);
            tests = itemView.findViewById(R.id.testMatches);
            cardView = itemView.findViewById(R.id.seriesRow);
        }
    }
}
