package com.example.salonappointment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Years {
    private int year;
    private List<Integer> months;

    public Years(int year) {
        this.year = year;
        months = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
    }

    public Years() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public void setMonths(List<Integer> months) {
        this.months = months;
    }
}
