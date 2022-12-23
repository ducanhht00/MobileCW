package com.example.mobilecw;

public class Trip {
    int Id;
    String TripName;
    String Destination;
    String Date;
    String Duration;
    String Risk;
    String Description;
    String Vehicle;

    public Trip(int id, String tripName, String destination, String date, String duration, String risk, String description, String vehicle) {
        Id = id;
        TripName = tripName;
        Destination = destination;
        Date = date;
        Duration = duration;
        Risk = risk;
        Description = description;
        Vehicle = vehicle;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTripName() {
        return TripName;
    }

    public void setTripName(String tripName) {
        TripName = tripName;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getRisk() {
        return Risk;
    }

    public void setRisk(String risk) {
        Risk = risk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
