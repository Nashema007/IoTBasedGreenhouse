package com.example.iotbasedgreenhouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iotbasedgreenhouse.R;
import com.example.iotbasedgreenhouse.models.LegendModal;

import java.util.ArrayList;

public class LegendAdapter extends RecyclerView.Adapter<LegendAdapter.MyViewHolder>{


    Context context;
    ArrayList<LegendModal> legendModals;

    public LegendAdapter(Context context, ArrayList<LegendModal> legendModals) {
        this.context = context;
        this.legendModals = legendModals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.legend_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.legendValue.setText(legendModals.get(position).getValue());
        holder.legendLabel.setText(legendModals.get(position).getLabel());
    }

    @Override
    public int getItemCount() {
        return legendModals.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
private TextView legendLabel;
private TextView legendValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            legendLabel = itemView.findViewById(R.id.legendValueLabel);
            legendValue = itemView.findViewById(R.id.legendValue);
        }
    }
}
