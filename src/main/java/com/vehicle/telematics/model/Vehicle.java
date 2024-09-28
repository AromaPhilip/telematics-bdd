package com.vehicle.telematics.model;

import java.time.Instant;

public class Vehicle {

    private String vehicleId;
    private double speed;
    private double fuelLevel;
    private String diagnostics;
    private Instant timestamp;
    private double latitude;
    private double longitude;

    // Constructor
    public Vehicle(String vehicleId, double speed, double fuelLevel, String diagnostics, double latitude,
                   double longitude) {
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.fuelLevel = fuelLevel;
        this.diagnostics = diagnostics;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = Instant.now();
    }

    // Getters and setters
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public String getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(String diagnostics) {
        this.diagnostics = diagnostics;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // ToString method to print the object easily
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", speed=" + speed +
                ", fuelLevel=" + fuelLevel +
                ", diagnostics='" + diagnostics + '\'' +
                ", timestamp=" + timestamp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

