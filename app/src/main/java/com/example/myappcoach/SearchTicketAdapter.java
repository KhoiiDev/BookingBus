package com.example.myappcoach;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchTicketAdapter extends RecyclerView.Adapter<SearchTicketAdapter.ViewHolderTicket>{

    private ArrayList<Search> listTicket;
    private Context context;

    public SearchTicketAdapter(Context context, ArrayList<Search> listTicket) {
        this.listTicket = listTicket;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderTicket onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_search_view_result, parent, false);
        return new ViewHolderTicket(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTicket holder, int position) {
        Search search = listTicket.get(position);
        if(search != null){
            holder.depSearch.setText(search.getFrom());
            holder.desSearch.setText(search.getTo());
            holder.daSearch.setText("Date:" + search.getFromDate());
            holder.tiSearch.setText("Time:" + search.getFromTime());
            holder.coSearch.setText("Cost:"+search.getCost());
            holder.sloSearch.setText("Slot:" + search.getListBuss().size());
        }

        holder.ticketSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String slot = "";
                for(int i=0 ; i< search.getListBuss().size();i++){
                    slot = slot +" "+ search.getListBuss().get(i).getSeat();
                }
                
                Toast.makeText(context, "ticketSearchLayout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BuyTicketActivity.class);
                intent.putExtra("departure", search.getFrom());
                intent.putExtra("destination", search.getTo());
                intent.putExtra("date", search.getFromDate());
                intent.putExtra("time", search.getFromTime());
                intent.putExtra("cost", search.getCost());
                intent.putExtra("slot", slot);

                Toast.makeText(context, slot, Toast.LENGTH_SHORT).show();


                context.startActivity(intent);
            }
        });


    }

   @Override
    public int getItemCount() {
        return listTicket.size();
    }

    public static class ViewHolderTicket extends RecyclerView.ViewHolder {

        private TextView depSearch, desSearch, daSearch, tiSearch, coSearch, sloSearch;
        private ConstraintLayout ticketSearchLayout;

        public ViewHolderTicket(@NonNull View itemView) {
            super(itemView);

            depSearch = (TextView) itemView.findViewById(R.id.departureSearch);
            desSearch = (TextView) itemView.findViewById(R.id.destinationSearch);
            daSearch = (TextView) itemView.findViewById(R.id.dateSearch);
            tiSearch = (TextView) itemView.findViewById(R.id.timeSearch);
            sloSearch = (TextView) itemView.findViewById(R.id.slotSearch);
            coSearch = (TextView) itemView.findViewById(R.id.costSearch);
            ticketSearchLayout = (ConstraintLayout) itemView.findViewById(R.id.ticketLayoutSearch);

        }
    }
}
