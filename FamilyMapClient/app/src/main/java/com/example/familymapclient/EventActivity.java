package com.example.familymapclient;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;

import async.*;
import fragments.*;
import Model.*;

public class EventActivity extends AppCompatActivity {
    public static Events currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        View view = getLayoutInflater().inflate(R.layout.activity_event, null);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {  //Nonnull necessary?
        if (android.R.id.home == menuItem.getItemId()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}
