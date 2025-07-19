package com.example.LuxeVista.Main_Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LuxeVista.Data_Base.DBHelper;
import com.example.LuxeVista.R;
import com.example.LuxeVista.Models.SessionManager;
import com.example.LuxeVista.Activity_Pages.UpdateProfileActivity;
import com.example.LuxeVista.Models.UserModel;
import com.example.LuxeVista.Adapters.BookingHistoryAdapter;
import com.example.LuxeVista.Models.BookingHistoryItem;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private LinearLayout layoutLogin, layoutSignup, layoutProfile;
    private Button buttonLogin, buttonCreateAccount, buttonLogout, buttonUpdateProfile;
    private TextView buttonSignup, buttonBackToLogin, textViewNoBookings;
    private EditText editTextEmailLogin, editTextPasswordLogin;
    private EditText editTextFirstNameSignup, editTextLastNameSignup, editTextEmailSignup,
            editTextPhoneSignup, editTextNicSignup, editTextPasswordSignup;
    private TextView textViewFirstName, textViewLastName, textViewEmail, textViewPhone, textViewNic;
    private ImageView imageViewProfile;
    private DBHelper dbHelper;
    private SessionManager sessionManager;
    private RecyclerView recyclerViewBookings;
    private BookingHistoryAdapter bookingHistoryAdapter;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dbHelper = new DBHelper(getContext());
        sessionManager = new SessionManager(getContext());


        layoutLogin = view.findViewById(R.id.layoutLogin);
        layoutSignup = view.findViewById(R.id.layoutSignup);
        layoutProfile = view.findViewById(R.id.layoutProfile);

        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonSignup = view.findViewById(R.id.buttonSignup);
        buttonCreateAccount = view.findViewById(R.id.buttonCreateAccount);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonUpdateProfile = view.findViewById(R.id.buttonUpdateProfile);
        buttonBackToLogin = view.findViewById(R.id.buttonBackToLogin);

        editTextEmailLogin = view.findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = view.findViewById(R.id.editTextPasswordLogin);

        editTextFirstNameSignup = view.findViewById(R.id.editTextFirstNameSignup);
        editTextLastNameSignup = view.findViewById(R.id.editTextLastNameSignup);
        editTextEmailSignup = view.findViewById(R.id.editTextEmailSignup);
        editTextPhoneSignup = view.findViewById(R.id.editTextPhoneSignup);
        editTextNicSignup = view.findViewById(R.id.editTextNicSignup);
        editTextPasswordSignup = view.findViewById(R.id.editTextPasswordSignup);

        textViewFirstName = view.findViewById(R.id.textViewFirstName);
        textViewLastName = view.findViewById(R.id.textViewLastName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewPhone = view.findViewById(R.id.textViewPhone);
        textViewNic = view.findViewById(R.id.textViewNic);

        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        recyclerViewBookings = view.findViewById(R.id.recyclerViewBookings);
        textViewNoBookings = view.findViewById(R.id.textViewNoBookings);

        // Setup RecyclerView
        recyclerViewBookings.setLayoutManager(new LinearLayoutManager(getContext()));
        bookingHistoryAdapter = new BookingHistoryAdapter(new ArrayList<>());
        recyclerViewBookings.setAdapter(bookingHistoryAdapter);

        updateUI();

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmailLogin.getText().toString().trim();
            String password = editTextPasswordLogin.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                UserModel user = dbHelper.loginUser(email, password);
                if (user != null) {
                    sessionManager.saveLoginSession(user.getId(), user.getFirstName(), user.getLastName(),
                            user.getEmail(), user.getPhone(), user.getNic());
                    Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    updateUI();
                } else {
                    Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCreateAccount.setOnClickListener(v -> {
            String firstName = editTextFirstNameSignup.getText().toString().trim();
            String lastName = editTextLastNameSignup.getText().toString().trim();
            String email = editTextEmailSignup.getText().toString().trim();
            String phone = editTextPhoneSignup.getText().toString().trim();
            String nic = editTextNicSignup.getText().toString().trim();
            String password = editTextPasswordSignup.getText().toString().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                    phone.isEmpty() || nic.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                long userId = dbHelper.addUser(firstName, lastName, email, phone, nic, password);
                if (userId != -1) {
                    sessionManager.saveLoginSession((int)userId, firstName, lastName, email, phone, nic);
                    Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                    updateUI();
                } else {
                    Toast.makeText(getContext(), "Failed to create account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSignup.setOnClickListener(v -> {
            layoutLogin.setVisibility(View.GONE);
            layoutSignup.setVisibility(View.VISIBLE);
        });

        buttonBackToLogin.setOnClickListener(v -> {
            layoutSignup.setVisibility(View.GONE);
            layoutLogin.setVisibility(View.VISIBLE);
        });

        buttonLogout.setOnClickListener(v -> {
            sessionManager.clearSession();
            Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            updateUI();
        });

        buttonUpdateProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void updateUI() {
        if (sessionManager.isLoggedIn()) {
            layoutLogin.setVisibility(View.GONE);
            layoutSignup.setVisibility(View.GONE);
            layoutProfile.setVisibility(View.VISIBLE);

            // Set user profile information
            textViewFirstName.setText(sessionManager.getFirstName());
            textViewLastName.setText(sessionManager.getLastName());
            textViewEmail.setText(sessionManager.getEmail());
            textViewPhone.setText(sessionManager.getPhone());
            textViewNic.setText(sessionManager.getNic());

            // Load booking history
            loadBookingHistory();
        } else {
            layoutLogin.setVisibility(View.VISIBLE);
            layoutSignup.setVisibility(View.GONE);
            layoutProfile.setVisibility(View.GONE);

            // Clear input fields
            editTextEmailLogin.setText("");
            editTextPasswordLogin.setText("");
            editTextFirstNameSignup.setText("");
            editTextLastNameSignup.setText("");
            editTextEmailSignup.setText("");
            editTextPhoneSignup.setText("");
            editTextNicSignup.setText("");
            editTextPasswordSignup.setText("");
        }
    }

    private void loadBookingHistory() {
        // In a real app, you would fetch this from your database/API
        // Here we're using hardcoded data for demonstration
        List<BookingHistoryItem> bookingList = getHardcodedBookingHistory();

        if (bookingList.isEmpty()) {
            recyclerViewBookings.setVisibility(View.GONE);
            textViewNoBookings.setVisibility(View.VISIBLE);
        } else {
            recyclerViewBookings.setVisibility(View.VISIBLE);
            textViewNoBookings.setVisibility(View.GONE);
            bookingHistoryAdapter = new BookingHistoryAdapter(bookingList);
            recyclerViewBookings.setAdapter(bookingHistoryAdapter);
        }
    }

    private List<BookingHistoryItem> getHardcodedBookingHistory() {
        List<BookingHistoryItem> bookingList = new ArrayList<>();

        // Add sample booking items
        bookingList.add(new BookingHistoryItem(
                "LuxeVista Colombo",
                "Deluxe Ocean View Suite",
                "15 Apr - 20 Apr 2023",
                "Completed",
                "$1,250.00",
                R.drawable.h1
        ));

        bookingList.add(new BookingHistoryItem(
                "Grand Sapphire Resort",
                "Executive King Room",
                "22 May - 25 May 2023",
                "Completed",
                "$850.00",
                R.drawable.h2
        ));

        bookingList.add(new BookingHistoryItem(
                "Emerald Bay Hotel",
                "Premium Suite with Balcony",
                "10 Jul - 15 Jul 2023",
                "Upcoming",
                "$1,100.00",
                R.drawable.h3
        ));

        bookingList.add(new BookingHistoryItem(
                "Royal Palm Beach",
                "Standard Double Room",
                "05 Mar - 08 Mar 2023",
                "Cancelled",
                "$600.00",
                R.drawable.h4
        ));

        return bookingList;
    }
}