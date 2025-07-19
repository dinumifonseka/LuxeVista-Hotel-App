package com.example.LuxeVista.Main_Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LuxeVista.Activity_Pages.ExploreActivity;
import com.example.LuxeVista.Activity_Pages.PoolActivity;
import com.example.LuxeVista.R;
import com.example.LuxeVista.Models.Room;
import com.example.LuxeVista.Adapters.RoomAdapter;
import com.example.LuxeVista.Activity_Pages.SpaActivity;
import com.example.LuxeVista.Other_Fragments.SearchFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewRooms;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;
    private MaterialButton buttonExploreSpa, buttonExplorePool,buttonGetStarted;
    private TextView textViewSeeAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerViewRooms = view.findViewById(R.id.recyclerViewRooms);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        buttonExploreSpa = view.findViewById(R.id.buttonExploreSpa);
        buttonExplorePool = view.findViewById(R.id.buttonExplorePool);
        buttonGetStarted = view.findViewById(R.id.buttonGetStarted);
        textViewSeeAll = view.findViewById(R.id.textViewSeeAll);


        roomList = new ArrayList<>();
        prepareRoomData();
        roomAdapter = new RoomAdapter(roomList, getContext());
        recyclerViewRooms.setAdapter(roomAdapter);


        setupClickListeners(view);

        return view;
    }

    private void setupClickListeners(View view) {

        buttonExplorePool.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PoolActivity.class);
            startActivity(intent);
        });


        buttonExploreSpa.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SpaActivity.class);
            startActivity(intent);
        });

        buttonGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ExploreActivity.class);
            startActivity(intent);

        });

        textViewSeeAll.setOnClickListener(v -> {

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .addToBackStack(null)
                    .commit();
        });

//        textViewSeeAll.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), SearchActivity.class);
//            startActivity(intent);
//        });

    }

    private void prepareRoomData() {
        roomList.add(new Room(1, "Ocean View Suite", "Luxurious suite overlooking the ocean", 450.00, R.drawable.ocean_view));
        roomList.add(new Room(2, "Deluxe Room", "Spacious room with modern amenities", 350.00, R.drawable.deulux));
        roomList.add(new Room(3, "Presidential Suite", "Our finest suite with premium services", 750.00, R.drawable.presidential));
        roomList.add(new Room(4, "Garden View Room", "Peaceful room with garden views", 300.00, R.drawable.garden_room));
        roomList.add(new Room(5, "Family Suite", "Perfect for families with extra space", 500.00, R.drawable.family_room));
    }
}