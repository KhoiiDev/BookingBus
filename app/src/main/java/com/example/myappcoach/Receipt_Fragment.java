package com.example.myappcoach;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Receipt_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Receipt> listReceipt;
    private String userEmail = "";
    private ReceiptAdapter receiptAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt,container, false);

        initView(view);


        createReceipt();
        return view;
    }

    private void createReceipt(){
        listReceipt = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                userEmail = profile.getEmail();
            }
        }


        receiptAdapter = new ReceiptAdapter(getActivity(), listReceipt);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(receiptAdapter);



        FirebaseDatabase mDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference myTopPostsQuery = mDatabase2.getReference("Tickets");


        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Tickets tickets = postSnapshot.getValue(Tickets.class);
                    if( tickets.getEmail().equals(userEmail)){
                        listReceipt.add(new Receipt( tickets.getDate() , tickets.getFrom(), tickets.getTo(), tickets.getSeat().toString(), tickets.getCost()));
                    }
                }
                receiptAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public void initView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_receipt);
    }
}