package com.example.salonappointment;

public class TimeSlots {
    private boolean available;
    private int slotNumber;

    public TimeSlots(boolean available, int slotNumber) {
        this.available = available;
        this.slotNumber = slotNumber;
    }

    public TimeSlots() {
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }
}
