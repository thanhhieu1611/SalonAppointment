package com.example.salonappointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.auth.User;

public class UserAuthentication extends AppCompatActivity {

    // Object declaration for device sign in
    EditText email, password;
    CheckBox remember;
    Button login;

    // Object declaration for Google API sign in
    SignInButton signin;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);
        HideActionBar();

        /****************************** Sign in with Google API *************************************/
        signin = findViewById(R.id.sign_in_button);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // OnClick listener for Google SignIn button
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });

        /****************************** Sign in on Device *************************************/
        // Find objects by ID
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        remember = findViewById(R.id.cbRememberMe);
        login = findViewById(R.id.btnLogin);

        // Check SharePreference object to see if user has saved user authentication before
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if(checkbox.equals("true")){
            silentSignIn();
        }

        // Onclick event listener for login button object
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(email.getText()).matches("")){
                    Toast.makeText(UserAuthentication.this, "Please enter the email", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(password.getText()).matches("")){
                    Toast.makeText(UserAuthentication.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                }else{
                    silentSignIn();
                }
            }
        });

        // On checked change event listener for Remember Me checkbox object
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked){
                  SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                  SharedPreferences.Editor editor = preferences.edit();
                  editor.putString("remember", "true");
                  editor.apply();
              }else{
                  SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                  SharedPreferences.Editor editor = preferences.edit();
                  editor.putString("remember", "false");
                  editor.apply();
              }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            silentSignIn();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(UserAuthentication.this, e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    // Update Google API sign in result and show result to users
    private void updateUI(GoogleSignInAccount account) {
        if(account != null){
            silentSignIn();
        }
    }

    // Call sign intent from Google Sign in client
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Sign in based on last information
    private void silentSignIn(){
        // Signed in successfully, show authenticated UI.
        Intent intent = new Intent(UserAuthentication.this, ChooseProfessional.class);
        startActivity(intent);
    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}