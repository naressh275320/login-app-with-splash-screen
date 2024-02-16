package com.example.menuorder_a1;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.menuorder_a1.R;

public class menuFragment extends Fragment {
    View view;

    CardView pastry, beverage, snacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        pastry = view.findViewById(R.id.card_view1);
        snacks = view.findViewById(R.id.card_view2);
        beverage = view.findViewById(R.id.card_view3);


        //pastry card view
        pastry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new PastryFragment());
            }
        });

        //snacks card view
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SnacksFragment());
            }
        });

        //beverage card view
        beverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BeverageFragment());
            }
        });
        return view;
    }

    //Fragment manager
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}