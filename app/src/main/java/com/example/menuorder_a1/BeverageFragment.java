package com.example.menuorder_a1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BeverageFragment extends Fragment {
    View view;
    EditText Qmocktail, Qcoke, Qbeer;
    Button save, show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_beverage, container, false);

        Qmocktail = view.findViewById(R.id.Qmocktail);
        Qcoke= view.findViewById(R.id.Qcoke);
        Qbeer = view.findViewById(R.id.Qbeer);
        save = view.findViewById(R.id.save);
        show = view.findViewById(R.id.show);

        //Save button to store the item orders in the table
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emocktail = Qmocktail.getText().toString();
                String ecoke = Qcoke.getText().toString();
                String ebeer = Qbeer.getText().toString();

                // class to add values in the database
                ContentValues values = new ContentValues();

                // fetching text from user
                values.put(OrderContentProviders.QredVelvet,emocktail);
                values.put(OrderContentProviders.Qhazelnut,ecoke);
                values.put(OrderContentProviders.Qtruffle,ebeer);

                // inserting into database through content URI
                getActivity().getContentResolver().insert(OrderContentProviders.CONTENT_URI, values);

                // displaying a toast message
                android.widget.Toast.makeText(getActivity().getApplicationContext(), "New Record Inserted", Toast.LENGTH_LONG).show();

            }
        });

        //Show button to view the item orders from the table
        show.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {

                TextView resultView= view.findViewById(R.id.res);

                Cursor cursor = getActivity().getContentResolver().query(OrderContentProviders.CONTENT_URI,
                        null, null, null, null);

                if(cursor.moveToFirst()) {
                    StringBuilder strBuild=new StringBuilder();
                    while (!cursor.isAfterLast()) {
                        strBuild.append("\n").
                                append(cursor.getString(cursor.getColumnIndex(OrderContentProviders.QredVelvet))).
                                append("-").append(cursor.getString(cursor.getColumnIndex(OrderContentProviders.Qhazelnut))).
                                append("-").append(cursor.getString(cursor.getColumnIndex(OrderContentProviders.Qtruffle)));
                        cursor.moveToNext();
                    }
                    //Displaying the orders
                    resultView.setText(strBuild);
                }
                else {
                    resultView.setText("No Records Found");
                }
            }
        });


        return view;
    }
}