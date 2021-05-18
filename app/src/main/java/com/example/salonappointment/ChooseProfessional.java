package com.example.salonappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class ChooseProfessional extends AppCompatActivity {

    // Object declaration
    Button prof1, prof2, prof3, logout;
    TextView title;

    // List of professionals & services
    List<Button> profs;

    // Google sign in client object
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_professional);
        HideActionBar();

        profs = new ArrayList<Button>();
        title = findViewById(R.id.txtTitle);
        logout = findViewById(R.id.btnLogout);

        // Update professional information
        Professionals professional1 = new Professionals("Mary");
        Professionals professional2 = new Professionals("John");
        Professionals professional3 = new Professionals("David");

        // Update UI
        prof1 = findViewById(R.id.service1Btn);
        prof2 = findViewById(R.id.service2Btn);
        prof3 = findViewById(R.id.service3Btn);

        prof1.setText(professional1.getName());
        prof2.setText(professional2.getName());
        prof3.setText(professional3.getName());

        profs.add(prof1);
        profs.add(prof2);
        profs.add(prof3);

        for(Button prof : profs){
            prof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransferDataToChooseService(String.valueOf(prof.getText()));
                }
            });
        }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set onCLick listener for Logout button
        // to reset "remember" value in shared preference from true to false
        // also reset Google API
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out from Google API
                switch (v.getId()) {
                    case R.id.btnLogout:
                        signOut();
                        break;
                }

                // Set "remember" string to false for sign in by shared preference
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();

                // Close current and all advance activities with finish()
                finish();
            }
        });

    }

    // Transfer data to Choose Professional activity
    private void TransferDataToChooseService(String name){
        Intent intent = new Intent(ChooseProfessional.this, ChooseService.class);
        intent.putExtra("Professional Name" ,name);
        startActivity(intent);
    }

    // Sign out from Google API
    private void signOut() {
        if(mGoogleSignInClient != null){
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ChooseProfessional.this, "Signed out", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}