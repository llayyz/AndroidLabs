package com.example.lab3_mpis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // Не забудьте импортировать этот класс

import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    private static final String TAG = "UserDetailsActivity";
    private TextView userInfoTextView, routeTextView;
    private Button setPathButton, callTaxiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Log.d(TAG, "onCreate");

        userInfoTextView = findViewById(R.id.userInfoTextView);
        routeTextView = findViewById(R.id.routeTextView);
        setPathButton = findViewById(R.id.setPathButton);
        callTaxiButton = findViewById(R.id.callTaxiButton);

        String phone = getIntent().getStringExtra("phone");
        String firstName = getIntent().getStringExtra("firstName");
        String lastName = getIntent().getStringExtra("lastName");
        userInfoTextView.setText("Name: " + firstName + " " + lastName + "\nPhone: " + phone);

        setPathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, RouteActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        callTaxiButton.setEnabled(false);
        callTaxiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Отображение сообщения Toast
                Toast.makeText(UserDetailsActivity.this, "Taxi called!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Taxi called!");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String route = data.getStringExtra("route");
            routeTextView.setText(route);
            callTaxiButton.setEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
}