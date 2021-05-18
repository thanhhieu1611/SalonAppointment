package com.example.salonappointment;

public class Services {
    // Service object attributes
    private String serviceName;
    private int serviceQuantity;        // Default 1
    private double servicePrice;

    // Constructor
    public Services(String _serviceName, double _servicePrice){
        this.serviceQuantity = 1;
        this.serviceName = _serviceName;
        this.servicePrice = _servicePrice;
    }

    // Getters and Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceQuantity() {
        return serviceQuantity;
    }

    public void setServiceQuantity(int serviceQuantity) {
        this.serviceQuantity = serviceQuantity;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    // Methods
    public String ServiceInfo(){
        return serviceName + " - $" + servicePrice;
    }




}
