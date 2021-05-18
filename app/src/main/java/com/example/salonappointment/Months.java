package com.example.salonappointment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Months {
    private int month;
    private List<Integer> dates;

    public Months() {
        dates = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30, 31));
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDates() {
        return dates;
    }

    public void setDates(List<Integer> dates) {
        this.dates = dates;
    }
}
