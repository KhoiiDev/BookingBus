package com.example.myappcoach;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class Home_Fragment extends Fragment {

    public static final ArrayList<Trip> listTrip = new ArrayList<>();
    private RecyclerView recyclerViewTicket;
    private TripHomeAdapter tripHomeAdapter;
    private CircleIndicator circleIndicator;

    private ViewPager viewPager;
    private Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if(viewPager.getCurrentItem() == getlistPhoto().size()-1){
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout,container,false);
        initView(view);
        createSlideShow();
        createPoplarPlace();

        return view;
    }

    public ArrayList<Image_SlideShow> getlistPhoto(){
        ArrayList<Image_SlideShow> list = new ArrayList<>();
        list = new ArrayList<>();
        list.add(new Image_SlideShow(R.drawable.img_carousel_a));
        list.add(new Image_SlideShow(R.drawable.img_carousel_b));
        list.add(new Image_SlideShow(R.drawable.img_carousel_c));
        list.add(new Image_SlideShow(R.drawable.img_carousel_d));
        list.add(new Image_SlideShow(R.drawable.img_carousel_e));

        return list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createPoplarPlace(){

        tripHomeAdapter = new TripHomeAdapter(getActivity(), listTrip);
        recyclerViewTicket.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTicket.setHasFixedSize(true);
        recyclerViewTicket.setAdapter(tripHomeAdapter);
        getTicketAtDataBase();

    }

    private void getTicketAtDataBase() {

//        FirebaseDatabase mDatabase1 = FirebaseDatabase.getInstance();
//        DatabaseReference myTopPostsQuery1 = mDatabase1.getReference("Trip");
//
//        Trip trip = new Trip("AG", "HCM", "https://firebasestorage.googleapis.com/v0/b/fir-f802f.appspot.com/o/img_ticket%2Fangiang.png?alt=media&token=b582d7be-fa36-4390-b2b0-499e7b5e5b63", 150000);
//
//        // My top posts by number of stars
//        String strPath  = trip.getTo();
//        myTopPostsQuery1.child(strPath).setValue(trip, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        FirebaseDatabase mDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference myTopPostsQuery2 = mDatabase2.getReference("Trip");

        // My top posts by number of stars
        myTopPostsQuery2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listTrip.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Trip trip = postSnapshot.getValue(Trip.class);
                    listTrip.add(trip);
                }
                tripHomeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void createSlideShow(){
        ArrayList<Image_SlideShow> list = getlistPhoto();

        SlideShowHomeAdapter slideShowAdapter = new SlideShowHomeAdapter(getActivity(), list);
        viewPager.setAdapter(slideShowAdapter);

        circleIndicator.setViewPager(viewPager);

        handler.postDelayed(runnable, 2000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        slideShowAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }

    public void initView(View view){
        recyclerViewTicket = (RecyclerView) view.findViewById(R.id.rcvTicket);
        viewPager = view.findViewById(R.id.viewPagerID);
        circleIndicator = view.findViewById(R.id.circle_indicator);
    }
}
