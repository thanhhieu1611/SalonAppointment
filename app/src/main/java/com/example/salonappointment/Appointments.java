package com.example.salonappointment;

public class Appointments {
    private String professionalName;
    private int selectedDate;
    private int selectedMonth;
    private int selectedYear;
    private int selectedTimeSlot;

    public Appointments(String professionals, int selectedDate, int selectedMonth, int selectedYear, int selectedTimeSlot) {
        this.professionalName = professionals;
        this.selectedDate = selectedDate;
        this.selectedMonth = selectedMonth;
        this.selectedYear = selectedYear;
        this.selectedTimeSlot = selectedTimeSlot;
    }

    public Appointments() {
    }

    public String getProfessionals() {
        return professionalName;
    }

    public void setProfessionals(String professionals) {
        this.professionalName = professionals;
    }

    public int getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(int selectedDate) {
        this.selectedDate = selectedDate;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public int getSelectedTimeSlot() {
        return selectedTimeSlot;
    }

    public void setSelectedTimeSlot(int selectedTimeSlot) {
        this.selectedTimeSlot = selectedTimeSlot;
    }
}
