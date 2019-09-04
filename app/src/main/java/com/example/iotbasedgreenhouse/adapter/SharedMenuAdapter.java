package com.example.iotbasedgreenhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iotbasedgreenhouse.R;
import com.example.iotbasedgreenhouse.activities.HumidityActivity;
import com.example.iotbasedgreenhouse.activities.MoistureActivity;
import com.example.iotbasedgreenhouse.activities.TemperatureActivity;
import com.example.iotbasedgreenhouse.activities.WaterFlowActivity;
import com.example.iotbasedgreenhouse.models.SharedMenuModal;

import java.util.ArrayList;

public class SharedMenuAdapter extends RecyclerView.Adapter<SharedMenuAdapter.MyViewHolder> {

    private Context context;
    private String extra;
    private ArrayList<SharedMenuModal> sharedMenuModalArrayList;

    public SharedMenuAdapter(Context context, ArrayList<SharedMenuModal> sharedMenuModalArrayList ,String extra) {
        this.context = context;
        this.sharedMenuModalArrayList = sharedMenuModalArrayList;
        this.extra =extra;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_menu_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return sharedMenuModalArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.menuTitle.setText(sharedMenuModalArrayList.get(position).getTitle());

        holder.itemView.setOnClickListener(v -> {

            String period = sharedMenuModalArrayList.get(position).getTitle();
            switch (extra){

                case "temp" :
                    sendIntent(holder.itemView.getContext(), TemperatureActivity.class, period );
                    break;
                case "hum":
                    sendIntent(holder.itemView.getContext(), HumidityActivity.class, period);
                    break;
                case "water":
                    sendIntent(holder.itemView.getContext(), WaterFlowActivity.class, period);
                    break;
                case "moist":
                    sendIntent(holder.itemView.getContext(), MoistureActivity.class, period);

            }

        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView menuTitle;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        menuTitle = itemView.findViewById(R.id.menuTitle);
    }
}

private void sendIntent(Context context, Class myClass, String extra){

        Intent intent = new Intent(context, myClass);
        intent.putExtra(extra, extra);
        context.startActivity(intent);
}
}

