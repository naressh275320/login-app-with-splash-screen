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

public class PastryFragment extends Fragment {
    View view;
    EditText QredVelvet, Qhazelnut, Qtruffle;
    Button save, show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pastry, container, false);

        QredVelvet = view.findViewById(R.id.Qredvelvet);
        Qhazelnut = view.findViewById(R.id.Qhazelnut);
        Qtruffle = view.findViewById(R.id.Qtruffle);
        save = view.findViewById(R.id.save);
        show = view.findViewById(R.id.show);

        //Save button to store the item orders in the table
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eredVelvet = QredVelvet.getText().toString();
                String eHazelnut = Qhazelnut.getText().toString();
                String etruffle = Qtruffle.getText().toString();

                // class to add values in the database
                ContentValues values = new ContentValues();

                // fetching text from user
                values.put(OrderContentProviders.QredVelvet,eredVelvet);
                values.put(OrderContentProviders.Qhazelnut,eHazelnut);
                values.put(OrderContentProviders.Qtruffle,etruffle);

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

        return  view;
    }
//
//    private void replaceFragment(Fragment fragment) {
//
//        FragmentManager fragmentManager = getParentFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout, fragment);
//        fragmentTransaction.addToBackStack("");
//        fragmentTransaction.commit();
//    }
}