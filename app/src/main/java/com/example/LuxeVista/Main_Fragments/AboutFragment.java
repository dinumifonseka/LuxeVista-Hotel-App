package com.example.LuxeVista.Main_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.LuxeVista.Hotels_Fragments.ColomboFragment;
import com.example.LuxeVista.Hotels_Fragments.GalleFragment;
import com.example.LuxeVista.Hotels_Fragments.KandyFragment;
import com.example.LuxeVista.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AboutFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 locationViewPager;
    private LocationPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);


        tabLayout = view.findViewById(R.id.tabLayout);
        locationViewPager = view.findViewById(R.id.locationViewPager);


        setupViewPager();

        return view;
    }

    private void setupViewPager() {

        pagerAdapter = new LocationPagerAdapter(getChildFragmentManager(), getLifecycle());
        locationViewPager.setAdapter(pagerAdapter);


        new TabLayoutMediator(tabLayout, locationViewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Colombo");
                            break;
                        case 1:
                            tab.setText("Kandy");
                            break;
                        case 2:
                            tab.setText("Galle");
                            break;
                    }
                }
        ).attach();
    }


    private static class LocationPagerAdapter extends FragmentStateAdapter {

        public LocationPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ColomboFragment();
                case 1:
                    return new KandyFragment();
                case 2:
                    return new GalleFragment();
                default:
                    return new ColomboFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }



}