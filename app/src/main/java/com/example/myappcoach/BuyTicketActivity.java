package com.example.myappcoach;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BuyTicketActivity extends AppCompatActivity {

    public static String departureActiBuy, destinationAvtiBuy, dateActiBuy, timeActiBuy, listBusActiBuy;
    public static int costActiBuy;
    public static ArrayList<String> listSeat = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);


        Intent intent = getIntent();
        departureActiBuy =  intent.getStringExtra("departure");
        destinationAvtiBuy = intent.getStringExtra("destination");
        dateActiBuy=  intent.getStringExtra("date");
        timeActiBuy = intent.getStringExtra("time");
        costActiBuy = intent.getIntExtra("cost", -1);
        listBusActiBuy = intent.getStringExtra("slot");


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Seat_Fragemt seat_fragemt = new Seat_Fragemt();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fragmentTransaction
                    .addToBackStack("BuyTicket")
                    .setReorderingAllowed(true)
                    .replace(R.id.layoutSeat, seat_fragemt, "buyTicket")
                    .commit();
        }

    }
}