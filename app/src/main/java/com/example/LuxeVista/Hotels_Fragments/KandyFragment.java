package com.example.LuxeVista.Hotels_Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.LuxeVista.R;


public class KandyFragment extends Fragment {

    Button aboutkandy;


    public KandyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_kandy, container, false);

        aboutkandy = view.findViewById(R.id.buttonBookKandy);


        aboutkandy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.kandy, new AboutKandyFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;

    }
}