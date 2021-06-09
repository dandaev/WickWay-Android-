package com.example.wikway.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.wikway.MainActivity;
import com.example.wikway1.R;

public class NoInternet extends Activity {
    private Button retryBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nointernet);
        retryBtn = findViewById(R.id.retryInternet);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoInternet.this, MainActivity.class));
            }
        });
    }
}
