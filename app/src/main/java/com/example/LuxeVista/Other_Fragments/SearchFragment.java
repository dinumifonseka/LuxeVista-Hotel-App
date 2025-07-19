package com.example.LuxeVista.Other_Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LuxeVista.Adapters.SearchRoomAdapter;
import com.example.LuxeVista.Models.Room;
import com.example.LuxeVista.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerViewPopularRooms;
    private RecyclerView recyclerViewAllRooms;
    private SearchRoomAdapter popularRoomsAdapter;
    private SearchRoomAdapter allRoomsAdapter;
    private List<Room> allRoomsList;
    private List<Room> popularRoomsList;
    private List<Room> filteredRoomsList;
    private EditText editTextSearch;
    private ImageView buttonClearSearch;
    private ImageView buttonBack;
    private LinearLayout layoutNoResults;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        recyclerViewPopularRooms = view.findViewById(R.id.recyclerViewPopularRooms);
        recyclerViewAllRooms = view.findViewById(R.id.recyclerViewAllRooms);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        buttonClearSearch = view.findViewById(R.id.buttonClearSearch);
        buttonBack = view.findViewById(R.id.buttonBack);
        layoutNoResults = view.findViewById(R.id.layoutNoResults);


        setupRecyclerViews();

        prepareRoomData();

        setupSearchFunctionality();

        buttonBack.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    private void setupRecyclerViews() {
        recyclerViewPopularRooms.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewPopularRooms.setNestedScrollingEnabled(false);

        recyclerViewAllRooms.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewAllRooms.setNestedScrollingEnabled(false);
    }

    private void prepareRoomData() {
        allRoomsList = new ArrayList<>();
        popularRoomsList = new ArrayList<>();
        filteredRoomsList = new ArrayList<>();

        allRoomsList.add(new Room(1, "Ocean View Suite", "Luxurious suite overlooking the ocean with private balcony", 450.00, R.drawable.ocean_view));
        allRoomsList.add(new Room(2, "Deluxe Room", "Spacious room with modern amenities and king-size bed", 350.00, R.drawable.deulux));
        allRoomsList.add(new Room(3, "Presidential Suite", "Our finest suite with premium services and panoramic views", 750.00, R.drawable.presidential));
        allRoomsList.add(new Room(4, "Garden View Room", "Peaceful room with garden views and terrace access", 300.00, R.drawable.garden_room));
        allRoomsList.add(new Room(5, "Family Suite", "Perfect for families with extra space and connecting rooms", 500.00, R.drawable.family_room));
        allRoomsList.add(new Room(6, "Executive Suite", "Business-friendly suite with office space and meeting area", 550.00, R.drawable.presidential));
        allRoomsList.add(new Room(7, "Honeymoon Suite", "Romantic suite with jacuzzi and champagne service", 650.00, R.drawable.ocean_view));
        allRoomsList.add(new Room(8, "Standard Room", "Comfortable room with all essential amenities", 250.00, R.drawable.garden_room));

        popularRoomsList.add(allRoomsList.get(0));
        popularRoomsList.add(allRoomsList.get(2));
        popularRoomsList.add(allRoomsList.get(6));
        popularRoomsList.add(allRoomsList.get(4));

        popularRoomsAdapter = new SearchRoomAdapter(popularRoomsList, getContext());
        allRoomsAdapter = new SearchRoomAdapter(allRoomsList, getContext());

        recyclerViewPopularRooms.setAdapter(popularRoomsAdapter);
        recyclerViewAllRooms.setAdapter(allRoomsAdapter);
    }

    private void setupSearchFunctionality() {

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();


                buttonClearSearch.setVisibility(searchText.isEmpty() ? View.GONE : View.VISIBLE);


                filterRooms(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        buttonClearSearch.setOnClickListener(v -> {
            editTextSearch.setText("");
            buttonClearSearch.setVisibility(View.GONE);
            resetSearch();
        });
    }

    private void filterRooms(String searchText) {
        filteredRoomsList.clear();

        if (searchText.isEmpty()) {
            resetSearch();
            return;
        }

        for (Room room : allRoomsList) {
            if (room.getName().toLowerCase().contains(searchText) ||
                    room.getDescription().toLowerCase().contains(searchText)) {
                filteredRoomsList.add(room);
            }
        }

        updateUIWithFilteredResults();
    }

    private void resetSearch() {
        popularRoomsAdapter.updateList(popularRoomsList);
        allRoomsAdapter.updateList(allRoomsList);

        recyclerViewPopularRooms.setVisibility(View.VISIBLE);
        recyclerViewAllRooms.setVisibility(View.VISIBLE);
        ((View) recyclerViewPopularRooms.getParent()).findViewById(R.id.recyclerViewPopularRooms).setVisibility(View.VISIBLE);

        layoutNoResults.setVisibility(View.GONE);
    }

    private void updateUIWithFilteredResults() {
        if (filteredRoomsList.isEmpty()) {
            layoutNoResults.setVisibility(View.VISIBLE);

            recyclerViewPopularRooms.setVisibility(View.GONE);
            recyclerViewAllRooms.setVisibility(View.GONE);
        } else {
            layoutNoResults.setVisibility(View.GONE);

            recyclerViewAllRooms.setVisibility(View.VISIBLE);
            allRoomsAdapter.updateList(filteredRoomsList);

            recyclerViewPopularRooms.setVisibility(View.GONE);
        }
    }
}