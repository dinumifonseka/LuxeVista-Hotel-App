package com.example.LuxeVista.Activity_Pages;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.LuxeVista.R;
import com.example.LuxeVista.Main_Fragments.AboutFragment;
import com.example.LuxeVista.Main_Fragments.CartFragment;
import com.example.LuxeVista.Main_Fragments.HomeFragment;
import com.example.LuxeVista.Main_Fragments.NotificationFragment;
import com.example.LuxeVista.Main_Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int x = item.getItemId();
            if (x == R.id.navigation_home) {
                selectedFragment = new HomeFragment();
            } else if (x == R.id.navigation_about) {
                selectedFragment = new AboutFragment();
            } else if (x == R.id.navigation_cart) {
                selectedFragment = new CartFragment();
            } else if (x == R.id.navigation_notifications) {
                selectedFragment = new NotificationFragment();
            } else if (x == R.id.navigation_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });

        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
