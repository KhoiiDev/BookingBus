package com.example.myappcoach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

public class Search_Fragment extends Fragment {

    private TextView dep, des, da;
    private EditText num;
    private Button btSearch;
    private ListView listPlace;
    private ArrayList<Search> listSearch = new ArrayList<>();

    private RecyclerView recyclerViewSearchTicket;
    private SearchTicketAdapter searchTicketAdapter;
    private ArrayList<Search> listSearchTicket = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initialize(view);
        getSearch();
        createSearch();
        return view;
    }

    private void getSearch(){

        FirebaseDatabase mDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference myTopPostsQuery = mDatabase2.getReference("Search");

//        ArrayList<Bus> listBus = new ArrayList<>();
//        listBus.add(new Bus("0", "0"));
//        @SuppressLint("DefaultLocale") Search search = new Search("Hồ Chí Minh", "An Giang", "2023-01-30", "07:00",  15000, listBus);
//        String strPath  = String.valueOf(Calendar.getInstance().getTime()+"1");
//        myTopPostsQuery.child(strPath).setValue(search);
//        @SuppressLint("DefaultLocale") Search search1 = new Search("Hồ Chí Minh", "An Giang", "2023-01-30", "10:00" ,  15000, listBus);
//        strPath  = String.valueOf(Calendar.getInstance().getTime()+"2");
//        myTopPostsQuery.child(strPath).setValue(search1);
//        @SuppressLint("DefaultLocale") Search search2 = new Search("Hồ Chí Minh", "An Giang", "2023-01-30","13:00",  15000, listBus);
//        strPath  = String.valueOf(Calendar.getInstance().getTime()+"3");
//        myTopPostsQuery.child(strPath).setValue(search2);
//        @SuppressLint("DefaultLocale") Search search3 = new Search("Hồ Chí Minh", "An Giang", "2023-01-30", "16:00" ,  15000, listBus);
//        strPath  = String.valueOf(Calendar.getInstance().getTime()+"4");
//        myTopPostsQuery.child(strPath).setValue(search3);
//        @SuppressLint("DefaultLocale") Search search4 = new Search("Hồ Chí Minh", "An Giang", "2023-01-30","19:00" ,  15000, listBus);
//        strPath  = String.valueOf(Calendar.getInstance().getTime()+"5");
//        myTopPostsQuery.child(strPath).setValue(search4);


        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Search search = postSnapshot.getValue(Search.class);
                    listSearch.add(search);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void createSearch(){
        createListTicket();

        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeparture();
            }
        });
        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDestination();
            }
        });
        da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedYear = Calendar.getInstance().get(Calendar.YEAR);
                int selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
                int selectedDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        da.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

                datePickerDialog.show();
            }
        });
//        String departure, String destination, String departureDate, String departureTime, String slot
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromInput =  dep.getText().toString();
                String toInput =  des.getText().toString();
                String dateInput =  da.getText().toString();
                String numInput = num.getText().toString();
                if(!checkInput(fromInput,toInput,dateInput,numInput)){
                    searchTicket(fromInput,toInput,dateInput);
                }
            }
        });

    }

    private boolean checkInput(String fromInput,String toInput, String dateInput, String numInput){
       return fromInput.isEmpty() || toInput.isEmpty() || dateInput.isEmpty() || numInput.isEmpty();
    }


    private void initialize(View view){
        dep = (TextView) view.findViewById(R.id.departureTicket);
        des = (TextView) view.findViewById(R.id.destinationTicket);
        da = (TextView) view.findViewById(R.id.dateTicket);
        num = (EditText) view.findViewById(R.id.numTicket);
        btSearch = (Button) view.findViewById(R.id.btsearch);
        recyclerViewSearchTicket = (RecyclerView) view.findViewById(R.id.rcvSearch);

    }
    private void setDeparture() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.list_place_search);

        int sizeListTiccket = listSearch.size();
        String[] place = new String[sizeListTiccket];
        for(int i=0 ; i<sizeListTiccket ; i++){
            place[i] =listSearch.get(i).getFrom();
        }
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(place));
        place = hashSet.toArray(new String[0]);

        listPlace = (ListView) dialog.findViewById(R.id.lvPlace);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, place);
        listPlace.setAdapter(arrayAdapter);

        String[] finalPlace = place;
        listPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dep.setText(finalPlace[i]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setDestination() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.list_place_search);

        int sizeListTiccket = listSearch.size();
        String[] place = new String[sizeListTiccket];
        for(int i=0 ; i<sizeListTiccket ; i++){
            place[i] = listSearch.get(i).getTo();
        }
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(place));
        place = hashSet.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        listPlace = (ListView) dialog.findViewById(R.id.lvPlace);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, place);
        listPlace.setAdapter(arrayAdapter);

        String[] finalPlace = place;
        listPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                des.setText(finalPlace[i]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void createListTicket(){
        searchTicketAdapter = new SearchTicketAdapter(getActivity(), listSearchTicket);
        recyclerViewSearchTicket.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSearchTicket.setHasFixedSize(true);
        recyclerViewSearchTicket.setAdapter(searchTicketAdapter);

    }

    private void searchTicket(String from, String to, String fromDate) {

        FirebaseDatabase mDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference myTopPostsQuery1 = mDatabase1.getReference("Search");

        Search searchInput = new Search(from, to, fromDate);
        FirebaseDatabase mDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference myTopPostsQuery2 = mDatabase2.getReference("Search");

        // My top posts by number of stars
        myTopPostsQuery2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listSearchTicket.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Search search = postSnapshot.getValue(Search.class);
                    if (searchInput.equals(search)) {
                        String dtStart = search.getFromDate()+"T"+search.getFromTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        try {
                            Date date = format.parse(dtStart);
                            Date currentTime = Calendar.getInstance().getTime();
                            if(date.after(currentTime)){
                                listSearchTicket.add(search);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(!listSearchTicket.isEmpty()){
                    searchTicketAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        Boolean fla = false;
//        String dtStart = fromDate+"T19:00";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//        try {
//            Date date = format.parse(dtStart);
//            Date currentTime = Calendar.getInstance().getTime();
//            if(date.after(currentTime)){
//                fla = true;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        if(listSearchTicket.isEmpty()){
            String fromInput =  dep.getText().toString();
            String toInput =  des.getText().toString();
            String dateInput =  da.getText().toString();
            ArrayList<Bus> listBus = new ArrayList<>();
            listBus.add(new Bus("0", "0"));
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myTopPostsQuery = mDatabase.getReference("Search");

            @SuppressLint("DefaultLocale") Search search = new Search(fromInput, toInput, dateInput, "07:00",  15000, listBus);
            String strPath  = String.valueOf(Calendar.getInstance().getTime())+1;
            myTopPostsQuery.child(strPath).setValue(search);
            @SuppressLint("DefaultLocale") Search search1 = new Search(fromInput, toInput, dateInput, "10:00" ,  15000, listBus);
            strPath  = String.valueOf(Calendar.getInstance().getTime())+1;
            myTopPostsQuery.child(strPath).setValue(search1);
            @SuppressLint("DefaultLocale") Search search2 = new Search(fromInput, toInput, dateInput,"13:00",  15000, listBus);
            strPath  = String.valueOf(Calendar.getInstance().getTime())+1;
            myTopPostsQuery.child(strPath).setValue(search2);
            @SuppressLint("DefaultLocale") Search search3 = new Search(fromInput, toInput, dateInput, "16:00" ,  15000, listBus);
            strPath  = String.valueOf(Calendar.getInstance().getTime())+1;
            myTopPostsQuery.child(strPath).setValue(search3);
            @SuppressLint("DefaultLocale") Search search4 = new Search(fromInput, toInput, dateInput,"19:00" ,  15000, listBus);
            strPath  = String.valueOf(Calendar.getInstance().getTime())+1;
            myTopPostsQuery.child(strPath).setValue(search4);
            listSearchTicket.add(search1);
            listSearchTicket.add(search2);
            listSearchTicket.add(search3);
            listSearchTicket.add(search4);
            searchTicketAdapter.notifyDataSetChanged();
        }
    }


}