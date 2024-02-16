package com.example.menuorder_a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Calling the first fragment
        Fragment fragment = new FragmentOne();
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, fragment).commit();
        //Toast.makeText(getApplicationContext(), "Welcome to main ", Toast.LENGTH_SHORT).show();
    }
}