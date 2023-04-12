package com.aureliennioche.stepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    String tag = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "Creating");
        Context context = getApplicationContext();
        Intent intent = new Intent(this, StepService.class);
        context.startService(intent);

        Log.d(tag, "Service is supposed to be running now");
    }
}