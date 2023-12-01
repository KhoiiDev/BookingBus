package com.example.myappcoach;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class Seat_Fragemt extends Fragment {

    private RecyclerView upstairs, downstairs;
    private ArrayList<String> listSeatA = new ArrayList<>();
    private ArrayList<String> listSeatB = new ArrayList<>();
    private SeatAdapter seatAdapterA, seatAdapterB;
    private Button continueBuy;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat, container, false);

        upstairs = view.findViewById(R.id.recyclerUpstairs);
        downstairs = view.findViewById(R.id.recyclerDownstairs);
        continueBuy = view.findViewById(R.id.btContinue);

        setSeat();

        seatAdapterA = new SeatAdapter(getActivity(), listSeatA, BuyTicketActivity.listBusActiBuy);
        upstairs.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        upstairs.setHasFixedSize(true);
        upstairs.setAdapter(seatAdapterA);

        seatAdapterB = new SeatAdapter(getActivity(), listSeatB, BuyTicketActivity.listBusActiBuy);
        downstairs.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        downstairs.setHasFixedSize(true);
        downstairs.setAdapter(seatAdapterB);


        continueBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuyTicketActivity.listSeat = getSeat();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fragmentTransaction
                        .addToBackStack("seatFragment")
                        .setReorderingAllowed(true)
                        .replace(R.id.layoutSeat, new Confirm_Fragment(), "SeatFragment")
                        .commit();
                }
            }
        });
        return view;
    }

    @SuppressLint("DefaultLocale")
    public void  setSeat(){
        for(int i=2 ; i<=16 ; i++){
            listSeatA.add(String.format("A%02d", i));
            listSeatB.add(String.format("B%02d", i));
        }
    }

    @SuppressLint("DefaultLocale")
    private ArrayList<String> getSeat(){
        ArrayList<String> listSeat = new ArrayList<>();
        for(int i=upstairs.getChildCount()-1; i>=0 ; i--) {
            View viewupstairs = upstairs.getChildAt(i);
            ToggleButton toggleButtonupstairs = (ToggleButton) viewupstairs.findViewById(R.id.toggleButtonSeat);
            if (toggleButtonupstairs.isChecked()) {

                listSeat.add(String.format("A%02d", i - 1));
            }
        }
        for (int i=downstairs.getChildCount()-1; i>=0 ; i--){
            View viewdownstairs = downstairs.getChildAt(i);
            ToggleButton toggleButtondownstairs = (ToggleButton) viewdownstairs.findViewById(R.id.toggleButtonSeat);
            if(toggleButtondownstairs.isChecked()){
                listSeat.add(String.format("B%02d", i-1));
            }
        }
        return listSeat;
    }

}
