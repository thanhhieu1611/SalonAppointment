package com.example.salonappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Confirmation extends AppCompatActivity {

    // Variables declaration
    String serviceBooked, providerBooked, dateBooked, timeBooked, noteBooked;
    Double priceBooked;
    private static final int REQUEST_CALL = 1;      // Use to identify call permission request, can be any positive number

    // Object declaration
    TextView serviceTv, providerTv, dateTv, timeTv, noteTv, priceTv, callNumber;
    Button closeBtn;
    ImageView imgCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        HideActionBar();

        // Get data from Main Activity
        Bundle bundle = getIntent().getExtras();
        serviceBooked = bundle.getString("Service Booked");
        priceBooked = bundle.getDouble("Price Booked");
        providerBooked = bundle.getString("Provider Booked");
        dateBooked = bundle.getString("Date Booked");
        timeBooked = bundle.getString("Time Booked");
        noteBooked = bundle.getString("Note Booked");

        // Find GUI objects by Id
        serviceTv = findViewById(R.id.serviceBooked);
        priceTv = findViewById(R.id.servicePriceBooked);
        providerTv = findViewById(R.id.providerBooked);
        dateTv = findViewById(R.id.dateBooked);
        timeTv = findViewById(R.id.timeBooked);
        noteTv = findViewById(R.id.noteBooked);
        closeBtn = findViewById(R.id.close);
        imgCall = findViewById(R.id.imgCall);
        callNumber = findViewById(R.id.number);

        // Update confirmation page
        serviceTv.setText(serviceBooked);
        priceTv.setText(String.valueOf(priceBooked));
        providerTv.setText(providerBooked);
        dateTv.setText(dateBooked);
        timeTv.setText(timeBooked);
        noteTv.setText(noteBooked);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quit application
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

        // Phone call click event listener
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakePhoneCall();
            }
        });

    }

    private void MakePhoneCall() {
        String number = String.valueOf(callNumber.getText());
        String callPermission = Manifest.permission.CALL_PHONE;     // Make sure select Manifest of android

        // Check permission
        if(ContextCompat.checkSelfPermission(Confirmation.this, callPermission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Confirmation.this,
                    new String[] {callPermission}, REQUEST_CALL);
        }else{
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MakePhoneCall();
            }else{
                Toast.makeText(this, "Phone Call Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}