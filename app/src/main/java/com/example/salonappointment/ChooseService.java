package com.example.salonappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

public class ChooseService extends AppCompatActivity {
    // Object declaration
    //final int numberOfServices = 6;
    List<Services> services;
    Button back, service1Btn, service2Btn, service3Btn, service4Btn, service5Btn, service6Btn;
    TextView label, userName, profName;
    GoogleSignInAccount acct;
    String professionalName, serviceName;
    Double servicePrice;
    List<Button> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);

        HideActionBar();

        // Find objects by Id
        label = findViewById(R.id.txtTitle);
        userName = findViewById(R.id.txtName);
        profName = findViewById(R.id.txtProf);
        service1Btn = findViewById(R.id.service1Btn);
        service2Btn = findViewById(R.id.service2Btn);
        service3Btn = findViewById(R.id.service3Btn);
        service4Btn = findViewById(R.id.service4Btn);
        service5Btn = findViewById(R.id.service5Btn);
        service6Btn = findViewById(R.id.service6Btn);
        back = findViewById(R.id.btnBack);

        // Declare service list & buttons
        services = new ArrayList<Services>();
        buttons = new ArrayList<Button>();

        // Get data from Choose Professional activity
        Bundle bundle = getIntent().getExtras();
        professionalName =bundle.getString("Professional Name");

        // Update professional name
        profName.setText(professionalName);

        // Create service objects and add into services arrayList
        Services service1 = new Services("Excel Expert", 49.95);
        Services service2 = new Services("Pro PowerPoint", 39.95);
        Services service3 = new Services("Magic PhotoShop", 59.95);
        Services service4 = new Services("Pro Illustrator", 55.95);
        Services service5 = new Services("App Development", 95.95);
        Services service6 = new Services("Web Development", 89.95);
        services.add(service1);
        services.add(service2);
        services.add(service3);
        services.add(service4);
        services.add(service5);
        services.add(service6);

        // Add service buttons into list
        // Show service infos on service buttons
        buttons.add(service1Btn);
        buttons.add(service2Btn);
        buttons.add(service3Btn);
        buttons.add(service4Btn);
        buttons.add(service5Btn);
        buttons.add(service6Btn);
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).setText(String.valueOf(services.get(i).ServiceInfo()));
        }

        // OnClick event listener for all service buttons
        for(Button btn :  buttons){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get button id (service number and price)
                    int baseId = service1Btn.getId();
                    switch (v.getId() - baseId){
                        case 0:
                            serviceName = service1.getServiceName();
                            servicePrice = service1.getServicePrice();
                            break;
                        case 1:
                            serviceName = service2.getServiceName();
                            servicePrice = service2.getServicePrice();
                            break;
                        case 2:
                            serviceName = service3.getServiceName();
                            servicePrice = service3.getServicePrice();
                            break;
                        case 3:
                            serviceName = service4.getServiceName();
                            servicePrice = service4.getServicePrice();
                            break;
                        case 4:
                            serviceName = service5.getServiceName();
                            servicePrice = service5.getServicePrice();
                            break;
                        case 5:
                            serviceName = service6.getServiceName();
                            servicePrice = service6.getServicePrice();
                            break;
                    }

                    // Send data (service number, professional name, price) to MainActivity
                    TransferDataToMainActivity();

                }
            });
        }


        // OnClick event listener for Back button to go back to choose profesional service
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseService.this, ChooseProfessional.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Set user name from Google API
        userName.setText(RetrieveGoogleSignInProfile());
    }

    // Get all service quantity from services list and store into arrayList
    // to transfer data to new activity with intent.putIntegerArrayListExtra()
    private ArrayList<Integer> GetAllServiceQuantity(List<Services> services){
        // Create new empty arrayList of service quantity
        ArrayList<Integer> tmp = new ArrayList<Integer>();

        // Loop though services list and add service quantity to tmp arraylist
        for(Services service : services){
            tmp.add(service.getServiceQuantity());
        }

        return tmp;
    }

    // Transfer data to Choose Professional activity
    private void TransferDataToMainActivity(){
        Intent intent = new Intent(ChooseService.this, MainActivity.class);
        intent.putExtra("Professional", professionalName);
        intent.putExtra("Service", serviceName);
        intent.putExtra("Price", servicePrice);
        startActivity(intent);
    }

    // Check empty services, return true if no service has been selected
    private boolean CheckEmptyServices(List<Services> services){
        for(Services service : services){
            if(service.getServiceQuantity() > 0){
                return false;
            }
        }
        return true;
    }

    // Update selection choice for Spinners
    private void UpdateDataForSpinner(Spinner spinner){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.serviceQuantity, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    // Retrieve Google Sign In profile
    private String RetrieveGoogleSignInProfile(){
        String personName;
        acct = GoogleSignIn.getLastSignedInAccount(ChooseService.this);
        if (acct != null) {
            personName = acct.getDisplayName();
            /*String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();*/
        }else{
            personName = null;
        }
        return personName;
    }
}