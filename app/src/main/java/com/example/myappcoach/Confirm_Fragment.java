package com.example.myappcoach;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Confirm_Fragment extends Fragment {

    private TextView dep, des, da, tim, userAccount, money, tickets, phone;
    private Button btconfirm;

    private String nameUser,phoneUser, departure, destination, date, time, emailUser;
    private ArrayList<String> listSeat;
    private int cost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);

        dep = view.findViewById(R.id.contextDeparture);
        des = view.findViewById(R.id.contextDestination);
        da = view.findViewById(R.id.contextDate);
        tim = view.findViewById(R.id.contextTime);
        money = view.findViewById(R.id.contextMoney);
        tickets = view.findViewById(R.id.contextTicket);
        userAccount = view.findViewById(R.id.contextCustom);
        phone = view.findViewById(R.id.contextPhone);
        btconfirm = view.findViewById(R.id.btConfirm);

        departure = BuyTicketActivity.departureActiBuy;
        destination = BuyTicketActivity.destinationAvtiBuy;
        date = BuyTicketActivity.dateActiBuy;
        time = BuyTicketActivity.timeActiBuy;
        listSeat = BuyTicketActivity.listSeat;
        cost = BuyTicketActivity.costActiBuy*listSeat.size();

        dep.setText(departure);
        des.setText(destination);
        da.setText(date);
        tim.setText(time);
        money.setText(cost+"");

        dialogInputName();

        String strSeat = "";
        for(int i=0; i<listSeat.size(); i++){
            strSeat = strSeat+" "+listSeat.get(i);
        }
        tickets.setText(strSeat);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                emailUser = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();

            }
        }

        btconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase mDatabase1 = FirebaseDatabase.getInstance();
                DatabaseReference myTopPostsQuery = mDatabase1.getReference("Tickets");
//                (int cost, String date, String from, String to, String name, String phone, String time, String email, ArrayList<String> seat
                Tickets tickets = new Tickets(cost, date, departure, destination, nameUser, phoneUser, time,emailUser, listSeat);
                // My top posts by number of stars
                String path = String.valueOf(Calendar.getInstance().getTime()).trim();
                myTopPostsQuery.child(path).setValue(tickets, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        insertSearch();

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            fragmentTransaction
                                    .addToBackStack("BackStackHome")
                                    .setReorderingAllowed(true)
                                    .replace(R.id.layoutFragmentHome, new Home_Fragment(), "nameFragmentHome")
                                    .commit();
                        }
                    }
                });
            }
        });

        return view;
    }

    private void dialogInputName(){
        Button btSent ;
        TextInputEditText nameinput, phoneInput;
        TextView nofitication;

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialoginputname_view);

        nameinput = dialog.findViewById(R.id.inputNameUpdate);
        phoneInput = dialog.findViewById(R.id.inputPhoneUpdate);
        btSent = dialog.findViewById(R.id.btSentName);
        nofitication = dialog.findViewById(R.id.textViewNotification_forgotpass);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        btSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameUser = nameinput.getText().toString();
                phoneUser = phoneInput.getText().toString();
                userAccount.setText(nameUser);
                phone.setText(phoneUser);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nameUser)
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    dialog.dismiss();
                                }
                            }
                        });
            }
        });
    }


    private void insertSearch(){
        FirebaseDatabase mDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference myTopPostsQuery2 = mDatabase2.getReference("Search");

        ArrayList<Bus> listBus = new ArrayList<>();
        for( int i=0; i<listSeat.size() ;i++){
            listBus.add(new Bus("251452",listSeat.get(i) ));
        }

        Search searchInput = new Search(departure, destination, date, time);

        myTopPostsQuery2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Search search = postSnapshot.getValue(Search.class);
                    if(search.equals4(searchInput)){
                        myTopPostsQuery2.child(postSnapshot.getKey()+"/listBuss").setValue(listBus, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            }
                        });
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}