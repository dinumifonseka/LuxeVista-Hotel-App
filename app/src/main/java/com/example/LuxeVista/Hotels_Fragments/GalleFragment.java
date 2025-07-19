package com.example.LuxeVista.Hotels_Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.LuxeVista.R;

public class GalleFragment extends Fragment {

    Button aboutgalle;


    public GalleFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_galle, container, false);

        aboutgalle = view.findViewById(R.id.buttonBookGalle);


        aboutgalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_galle, new AboutGalleFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}