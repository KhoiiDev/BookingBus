package com.example.myappcoach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TripHomeAdapter extends RecyclerView.Adapter<TripHomeAdapter.ViewHolderTicket> {


    private ArrayList<Trip> listTrip;
    private Context context;

    public TripHomeAdapter(Context context, ArrayList<Trip> listTrip) {
        this.listTrip = listTrip;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderTicket onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_in_recycle_home, parent, false);
        return new ViewHolderTicket(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTicket holder, int position) {
        Trip trip = listTrip.get(position);
        if(trip != null){
            holder.name.setText(String.format("%s-%s", trip.getFrom(), trip.getTo()));
            holder.cost.setText(trip.getPrice()+"");
            Glide.with(context).load(trip.getImg()).error(R.drawable.avatar_default).into(holder.imageView);
        }
        holder.ticketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToReceipt(trip);
            }


        });
    }

    private void goToReceipt(Trip trip) {
//        Intent intent = new Intent(context, BuyTicketActivity.class);
//        intent.putExtra("departure", ticket.getDeparture());
//        intent.putExtra("destination", ticket.getDestination());
//        intent.putExtra("date", ticket.getDepartureDate());
//        intent.putExtra("time", ticket.getDepartureTime());
//        intent.putExtra("cost", ticket.getCost());
//        intent.putExtra("slot", ticket.getSlot());
//        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listTrip.size();
    }


    public class ViewHolderTicket extends RecyclerView.ViewHolder {

        private TextView name, cost;
        private ImageView imageView;
        private RelativeLayout ticketLayout;

        public ViewHolderTicket(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameTicket);
            cost = (TextView) itemView.findViewById(R.id.costTicket);
            imageView = (ImageView)  itemView.findViewById(R.id.imgPlace);
            ticketLayout = (RelativeLayout) itemView.findViewById(R.id.layoutTicket);
        }
    }
}
