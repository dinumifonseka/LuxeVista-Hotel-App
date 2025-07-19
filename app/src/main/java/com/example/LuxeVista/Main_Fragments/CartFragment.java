package com.example.LuxeVista.Main_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LuxeVista.Adapters.CartAdapter;
import com.example.LuxeVista.R;
import com.example.LuxeVista.Models.Room;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<Room> cartRoomList;

    private TextView textViewRoomCharges, textViewMealCharges, textViewActivityCharges, textViewTaxes, textViewTotal, textViewEmptyCart;

    private static final double MEAL_CHARGE = 50.0;
    private static final double ACTIVITY_CHARGE = 75.0;
    private static final double TAX_PERCENT = 0.1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));

        textViewRoomCharges = view.findViewById(R.id.textViewRoomCharges);
        textViewMealCharges = view.findViewById(R.id.textViewMealCharges);
        textViewActivityCharges = view.findViewById(R.id.textViewActivityCharges);
        textViewTaxes = view.findViewById(R.id.textViewTaxes);
        textViewTotal = view.findViewById(R.id.textViewTotal);
        textViewEmptyCart = view.findViewById(R.id.textViewEmptyCart);


        cartRoomList = new ArrayList<>();
        cartRoomList.add(new Room(1, "Ocean View Suite", "Apr 15 - Apr 20", 450.00, R.drawable.ocean_view));
        cartRoomList.add(new Room(2, "Deluxe Room", "Apr 15 - Apr 17", 350.00, R.drawable.deulux));

        cartAdapter = new CartAdapter(cartRoomList, this::removeFromCart);
        recyclerViewCart.setAdapter(cartAdapter);

        updateSummary();

        return view;
    }

    private void removeFromCart(int position) {
        cartRoomList.remove(position);
        cartAdapter.notifyItemRemoved(position);
        updateSummary();

        if (cartRoomList.isEmpty()) {
            textViewEmptyCart.setVisibility(View.VISIBLE);
        } else {
            textViewEmptyCart.setVisibility(View.GONE);
        }

        Toast.makeText(getContext(), "Room removed from cart", Toast.LENGTH_SHORT).show();
    }

    private void updateSummary() {
        double roomCharges = 0;
        double mealCharges = 0;
        double activityCharges = 0;

        for (Room room : cartRoomList) {
            roomCharges += room.getPrice();
            mealCharges += MEAL_CHARGE;
            activityCharges += ACTIVITY_CHARGE;
        }

        double subtotal = roomCharges + mealCharges + activityCharges;
        double taxes = subtotal * TAX_PERCENT;
        double total = subtotal + taxes;

        textViewRoomCharges.setText(String.format("$%.2f", roomCharges));
        textViewMealCharges.setText(String.format("$%.2f", mealCharges));
        textViewActivityCharges.setText(String.format("$%.2f", activityCharges));
        textViewTaxes.setText(String.format("$%.2f", taxes));
        textViewTotal.setText(String.format("$%.2f", total));
    }
}
