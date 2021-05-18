package com.example.salonappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Object declaration
    CalendarView calendarView1;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    TextView customerNote;
    Button timeSlot1,timeSlot2,timeSlot3,timeSlot4,timeSlot5,timeSlot6,timeSlot7,timeSlot8,timeSlot9, book, back;
    Boolean timeSlot1Status, timeSlot2Status, timeSlot3Status, timeSlot4Status, timeSlot5Status, timeSlot6Status, timeSlot7Status, timeSlot8Status, timeSlot9Status;
    List<String> timeSlotValues, professionals;
    List<Button> timeSlots;
    List<Boolean> timeSlotStatuses;
    Appointments appointment;
    List<TimeSlots> timeSlotNumbers;
    TimeSlots timeSlot;
    TextView info;

    // Variable declaration
    String professionalName, serviceName, today, professionalNumber, timeSlotValueSelected;
    Double servicePrice;
    int dateSelected, monthSelected, yearSelected, timeSlotSelected, numberOfTimeSlots;

    // Database declaration
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HideActionBar();

        //Find Objects
        calendarView1 = findViewById(R.id.calendarView1);
        customerNote = findViewById(R.id.customerNotes);
        timeSlot1 = findViewById(R.id.timeslot1);
        timeSlot2 = findViewById(R.id.timeslot2);
        timeSlot3 = findViewById(R.id.timeslot3);
        timeSlot4 = findViewById(R.id.timeslot4);
        timeSlot5 = findViewById(R.id.timeslot5);
        timeSlot6 = findViewById(R.id.timeslot6);
        timeSlot7 = findViewById(R.id.timeslot7);
        timeSlot8 = findViewById(R.id.timeslot8);
        timeSlot9 = findViewById(R.id.timeslot9);
        book = findViewById(R.id.book);
        back = findViewById(R.id.backBtn);
        info = findViewById(R.id.info);

        // Variable declaration
        timeSlots = new ArrayList<Button>();
        timeSlotNumbers = new ArrayList<TimeSlots>();
        professionals = new ArrayList<String>(Arrays.asList("Mary", "John", "David"));
        numberOfTimeSlots = 9;
        timeSlotValues = new ArrayList<String>(Arrays.asList("8am - 9am", "9am - 10am", "10am - 11am", "11am - 12am", "12am - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm", "4pm - 5pm"));
        timeSlot1Status = timeSlot2Status = timeSlot3Status= timeSlot4Status= timeSlot5Status= timeSlot6Status= timeSlot7Status= timeSlot8Status= timeSlot9Status = false;
        timeSlotStatuses = new ArrayList<Boolean>(Arrays.asList(timeSlot1Status, timeSlot2Status, timeSlot3Status, timeSlot4Status, timeSlot5Status, timeSlot6Status, timeSlot7Status, timeSlot8Status, timeSlot9Status));

        // Get data from ChooseService activity
        Bundle bundle = getIntent().getExtras();
        professionalName = bundle.getString("Professional");
        serviceName = bundle.getString("Service");
        servicePrice = bundle.getDouble("Price");

        // Set up database from Firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Professionals");

        // Get current date information
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        today = dateFormat.format(calendar.getTime());
        dateSelected = Integer.parseInt(today.substring(3,5));
        monthSelected = Integer.parseInt(today.substring(0,2));
        yearSelected = Integer.parseInt(today.substring(6,10));

        // Show calendar and get selected date
        calendarView1.setShownWeekCount(1);
        calendarView1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Update selected dayOfMonth, Month + 1, Year
                dateSelected = dayOfMonth;
                monthSelected = month + 1;
                yearSelected = year;

                // Update time slots' availability of professional during the selected date
                // by change background color of time slot buttons
                UpdateAvailabilityFromFireBase(rootNode,reference, yearSelected, monthSelected, dateSelected, professionalName, professionals, timeSlotNumbers);
            }
        });

        // Add time slots into its list and create click event button
        timeSlots.add(timeSlot1);
        timeSlots.add(timeSlot2);
        timeSlots.add(timeSlot3);
        timeSlots.add(timeSlot4);
        timeSlots.add(timeSlot5);
        timeSlots.add(timeSlot6);
        timeSlots.add(timeSlot7);
        timeSlots.add(timeSlot8);
        timeSlots.add(timeSlot9);

        for(Button timeSlot : timeSlots){
            int startIndex = timeSlot1.getId();
            timeSlot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId() - startIndex) {
                        case 0:
                            timeSlotSelected = 1;
                            timeSlot1Status = SwitchStatus(timeSlot1Status, timeSlotSelected);
                            break;
                        case 1:
                            timeSlotSelected = 2;
                            timeSlot2Status = SwitchStatus(timeSlot2Status, timeSlotSelected);
                            break;
                        case 2:
                            timeSlotSelected = 3;
                            timeSlot3Status = SwitchStatus(timeSlot3Status,timeSlotSelected);
                            break;
                        case 3:
                            timeSlotSelected = 4;
                            timeSlot4Status = SwitchStatus(timeSlot4Status, timeSlotSelected);
                            break;
                        case 4:
                            timeSlotSelected = 5;
                            timeSlot5Status = SwitchStatus(timeSlot5Status, timeSlotSelected);
                            break;
                        case 5:
                            timeSlotSelected = 6;
                            timeSlot6Status = SwitchStatus(timeSlot6Status, timeSlotSelected);
                            break;
                        case 6:
                            timeSlotSelected = 7;
                            timeSlot7Status = SwitchStatus(timeSlot7Status, timeSlotSelected);
                            break;
                        case 7:
                            timeSlotSelected = 8;
                            timeSlot8Status = SwitchStatus(timeSlot8Status, timeSlotSelected);
                            break;
                        case 8:
                            timeSlotSelected = 9;
                            timeSlot9Status = SwitchStatus(timeSlot9Status, timeSlotSelected);
                            break;
                    }
                    timeSlotValueSelected = timeSlotValues.get(timeSlotSelected - 1);
                }

            });
        }

        // Add time slot numbers into its list
        for(int i = 1; i <=numberOfTimeSlots; i++){
            timeSlot = new TimeSlots(true, i);
            timeSlotNumbers.add(timeSlot);
        }

        // Book On Click event listener
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get appointment data
                appointment = new Appointments(professionalName, dateSelected, monthSelected, yearSelected, timeSlotSelected);

                // One time creation for FireBase database
                /*PopularProfessionalDataIntoFireBase(rootNode, reference, professionals);
                PopularAYearDataIntoFireBase(rootNode, reference, 2021, timeSlotNumbers, professionals);
                PopularAYearDataIntoFireBase(rootNode, reference, 2022, timeSlotNumbers, professionals);
                PopularAYearDataIntoFireBase(rootNode, reference, 2023, timeSlotNumbers, professionals);
                PopularAYearDataIntoFireBase(rootNode, reference, 2024, timeSlotNumbers, professionals);
                PopularAYearDataIntoFireBase(rootNode, reference, 2025, timeSlotNumbers, professionals);*/

                // Update appointment
                UpdateAppointmentOnFireBase(rootNode, reference, appointment, professionals);

            }
        });

        // Back Btn click event - return to select service activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseService.class);
                startActivity(intent);
            }
        });

        // Update availability fro current date from FireBase
        UpdateAvailabilityFromFireBase(rootNode, reference, yearSelected, monthSelected, dateSelected, professionalName, professionals, timeSlotNumbers);

    }

    // Popular a whole year data into FireBase Database
    private void PopularAYearDataIntoFireBase(FirebaseDatabase rootNode, DatabaseReference reference, int year, List<TimeSlots> timeSlotNumbers, List<String> professionals){
        // Get year
        Years _year = new Years(year);
        Months _month = new Months();
        int len = professionals.size();

        // Update professional
        for(int k = 0; k < len; k++){
            //Update years
            reference.child(String.valueOf(k)).child(String.valueOf(year)).setValue(_year);

            // Update dates in months
            for(Integer i : _year.getMonths()){
                reference.child(String.valueOf(k)).child(String.valueOf(year)).child("months").child(String.valueOf(i - 1)).setValue(_month);
            }

            // Update time slots in dates
            for(Integer i : _year.getMonths()){
                for(Integer j : _month.getDates()){
                    reference.child(String.valueOf(k)).child(String.valueOf(year)).child("months").child(String.valueOf(i - 1)).child("dates").child(String.valueOf(j-1)).setValue(timeSlotNumbers);
                }
            }
        }
    }

    // Populate professionals into Firebase Database
    private void PopularProfessionalDataIntoFireBase(FirebaseDatabase rootNode, DatabaseReference reference, List<String> professionals){
        // Update professionals
        reference.setValue(professionals);
    }

    // Update availability from database based on date selected
    private void UpdateAvailabilityFromFireBase(FirebaseDatabase rootNode, DatabaseReference reference, int yearSelected, int monthSelected, int dateSelected, String professionalName, List<String> professionals, List<TimeSlots> timeSlotNumbers){
        String _year = String.valueOf(yearSelected);
        String _month = String.valueOf(monthSelected - 1);
        String _date = String.valueOf(dateSelected - 1);

        // Specify which professional
        if(professionalName.equals(professionals.get(0))){
            professionalNumber = "0";
        }else if(professionalName.equals(professionals.get(1))){
            professionalNumber = "1";
        }else if(professionalName.equals(professionals.get(2))){
            professionalNumber = "2";
        }
        UpdateAvailabilityOnFirebaseDatabase(professionalNumber, reference, _year, _month, _date);
    }

    // Update appointment on FireBase Database
    private void UpdateAppointmentOnFireBase(FirebaseDatabase rootNode, DatabaseReference reference, Appointments appointment, List<String> professionals){
        String _year = String.valueOf(appointment.getSelectedYear());
        String _month = String.valueOf(appointment.getSelectedMonth() - 1);
        String _date = String.valueOf(appointment.getSelectedDate() - 1);
        String _slot = String.valueOf(appointment.getSelectedTimeSlot() - 1);

        String professionalInfo = appointment.getProfessionals();
        if(professionalInfo.equals(professionals.get(0))){
            professionalNumber = "0";
        }else if(professionalInfo.equals(professionals.get(1))){
            professionalNumber = "1";
        }else if(professionalInfo.equals(professionals.get(2))){
            professionalNumber = "2";
        }
        UpdateAppointmentOnFireBaseDatabase(professionalNumber, reference, _year, _month, _date, _slot);
    }

    // Transfer data to Choose Professional activity
    private void TransferDataToConfirmationActivity(String serviceBooked, Double priceBooked, String providerBooked, String dateBooked, String timeBooked, String noteBooked){
        Intent intent = new Intent(MainActivity.this, Confirmation.class);
        intent.putExtra("Service Booked", serviceBooked);
        intent.putExtra("Price Booked", priceBooked);
        intent.putExtra("Provider Booked", providerBooked);
        intent.putExtra("Date Booked", dateBooked);
        intent.putExtra("Time Booked", timeBooked);
        intent.putExtra("Note Booked", noteBooked);
        startActivity(intent);
    }

    // Update Availability On Firebase database
    private void UpdateAvailabilityOnFirebaseDatabase(String professionalNumber, DatabaseReference reference, String _year, String _month, String _date){
        // Loop though all time slots to get its availability
        for(TimeSlots ts : timeSlotNumbers){
            // Specify which time slot
            String tmp = String.valueOf(ts.getSlotNumber() - 1);
            reference.child(professionalNumber).child(_year).child("months").child(_month).child("dates").child(_date).child(tmp).child("available").addValueEventListener(new ValueEventListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Boolean availability = snapshot.getValue(Boolean.class);
                    // Change background color and set unclickable if not available
                    if(!availability){
                        timeSlots.get(ts.getSlotNumber() - 1).setBackgroundColor(R.color.black);
                        timeSlots.get(ts.getSlotNumber() - 1).setClickable(false);
                    }else{
                        timeSlots.get(ts.getSlotNumber() - 1).setBackgroundColor(R.color.myBlue);
                        timeSlots.get(ts.getSlotNumber() - 1).setClickable(true);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    // Update Appointment on Firebase
    private void UpdateAppointmentOnFireBaseDatabase(String professionalNumber, DatabaseReference reference, String _year, String _month, String _date, String _slot){
        reference.child(professionalNumber).child(_year).child("months").child(_month).child("dates").child(_date).child(_slot).child("available").addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tmp =  String.valueOf(snapshot.getValue(Boolean.class));
                if(tmp == "true"){
                    // Update value of time slot availability from true to false
                    reference.child(professionalNumber).child(_year).child("months").child(_month).child("dates").child(_date).child(_slot).child("available").setValue(false);

                    // Change color and set unclickable
                    timeSlots.get(Integer.parseInt(_slot)).setBackgroundColor(R.color.white);
                    timeSlots.get(Integer.parseInt(_slot)).setClickable(false);

                    // Move to confirmation activity
                    TransferDataToConfirmationActivity(serviceName, servicePrice, professionalName, monthSelected + "/" + dateSelected + "/" + yearSelected, timeSlotValueSelected, String.valueOf(customerNote.getText()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    //Switch status
    @SuppressLint("ResourceAsColor")
    private boolean SwitchStatus(Boolean status, Integer timeSlotSelected){
        if(!status){
            info.setText(timeSlots.get(timeSlotSelected - 1).getText() + " on " + monthSelected + "/" + dateSelected + " has been selected!");
            timeSlots.get(timeSlotSelected - 1).setBackgroundColor(R.color.button_background_color);
        }else{
            info.setText(timeSlots.get(timeSlotSelected - 1).getText() + " on " + monthSelected + "/" + dateSelected + " has been deselected!");
            timeSlots.get(timeSlotSelected - 1).setBackgroundColor(R.color.myRed);
        }
        return !status;
    }
}