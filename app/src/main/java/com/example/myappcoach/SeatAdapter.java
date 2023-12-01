package com.example.myappcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> listSeat;
    private String listBus;

    public SeatAdapter(Context context, ArrayList<String> listSeat, String listBus) {
        this.context = context;
        this.listSeat = listSeat;
        this.listBus = listBus.trim();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seat_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] arraySeats = listBus.split(" ");
        String seat = listSeat.get(position);
        for(int i=0 ; i< arraySeats.length; i++){

            if(seat.equals(arraySeats[i])){
                holder.toggleButton.setBackgroundResource(R.drawable.icon_seat_grey);
                holder.toggleButton.setEnabled(false);
            }
        }
        holder.tvSeat.setText(seat);
    }

    @Override
    public int getItemCount() {
        return listSeat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ToggleButton toggleButton;
        private TextView tvSeat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSeat = (TextView) itemView.findViewById(R.id.nameSeat);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.toggleButtonSeat);
        }
    }
}
