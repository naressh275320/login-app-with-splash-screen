package com.example.menuorder_a1;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.temporal.TemporalAccessor;


public class FragmentOne extends Fragment {
    View view;
    Button login, newUser;
    EditText id, password;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);
        login = view.findViewById(R.id.login);
        newUser = view.findViewById(R.id.newuser);
        id = view.findViewById(R.id.text0);
        password = view.findViewById(R.id.text1);

        //Login button
        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                String cId = id.getText().toString();
                String cPassword = password.getText().toString();

                //If any field is not entered
                if (cId.isEmpty() || cPassword.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                }

                else {

                    String select = "id =? AND password=?";
                    String args[] = {cId, cPassword};

                    Cursor cursor = getActivity().getContentResolver().query(MyContentProviders.CONTENT_URI,
                            null, select, args, null);

                    //To authenticate the id and password from the content providers
                    if (cursor.moveToFirst()) {
                        String i, p;
                        while (!cursor.isAfterLast()) {
                            i = cursor.getString(cursor.getColumnIndex(MyContentProviders.id));
                            p = cursor.getString(cursor.getColumnIndex(MyContentProviders.password));
                            if (cId.equals(i) && cPassword.equals(p)) {
                                replaceFragment(new menuFragment());
                                Toast.makeText(getActivity().getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    } else {
                        android.widget.Toast.makeText(getActivity().getApplicationContext(), "Invalid login Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //New user button
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cId = id.getText().toString();
                String cPassword = password.getText().toString();

                //If any field is not entered
                if (cId.isEmpty() || cPassword.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {

                    ContentValues values = new ContentValues();

                    // fetching text from user
                    values.put(MyContentProviders.id, cId);
                    values.put(MyContentProviders.password, cPassword);

                    // inserting into database through content URI
                    getActivity().getContentResolver().insert(MyContentProviders.CONTENT_URI, values);

                    // displaying a toast message
                    android.widget.Toast.makeText(getActivity().getApplicationContext(), "New Record Inserted", Toast.LENGTH_SHORT).show();
                }
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