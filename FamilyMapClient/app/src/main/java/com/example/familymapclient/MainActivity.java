package com.example.familymapclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.*;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import fragments.*;

public class MainActivity extends AppCompatActivity{
    public static String changeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fManager = getSupportFragmentManager();
        Fragment fragment = fManager.findFragmentById(R.id.fragment_container);;

        if (getIntent().getBooleanExtra(changeFragment, false)) {
            if (fragment == null) {
                fragment = new MapFragment();
            }
            fManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
        else {
            if (fragment == null) {
                fragment = new LoginFragment();
            }
            fManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}