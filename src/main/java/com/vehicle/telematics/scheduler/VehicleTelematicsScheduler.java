package com.vehicle.telematics.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.telematics.model.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VehicleTelematicsScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private int timeElapsed = 0;

    private double fuelBalance;

    private double latitude;
    private double longitude;

    private double totalDistance;
    private double distanceCoveredPerSecond;
    private double distanceCovered;

    public void startSendingTelematicsData(Vehicle vehicle, int periodSeconds, final int totalDuration) {
        latitude = vehicle.getLatitude();
        longitude = vehicle.getLongitude();
        fuelBalance = vehicle.getFuelLevel();
        distanceCoveredPerSecond = vehicle.getSpeed()/3600;
        scheduler.scheduleAtFixedRate(() -> sendTelematicsData(vehicle.getVehicleId(), periodSeconds, totalDuration * 60),
                0, periodSeconds, TimeUnit.SECONDS);
        System.out.println("after exec");
    }

    private void sendTelematicsData(String vehicleId, int periodSeconds, int totalDuration) {
        if (timeElapsed >= totalDuration) {
            stopScheduler();
            return;
        }

        // Increment elapsed time by period seconds
        timeElapsed += periodSeconds;

        // Simulate data
        distanceCovered += calculateDistanceCovered( periodSeconds);
        double fuelConsumed = calculateFuelConsumed(periodSeconds);

        fuelBalance -= fuelConsumed;
        updateLatLong(distanceCovered);

        Map<String, Object> telematicsData = new HashMap<>();
        telematicsData.put("vehicleId", vehicleId);
        telematicsData.put("timeElapsed", timeElapsed);
        telematicsData.put("distanceCovered", String.format("%.2f",distanceCovered));
        telematicsData.put("fuelConsumed", String.format("%.2f",fuelConsumed));
        telematicsData.put("latitude", String.format("%.4f",latitude));
        telematicsData.put("longitude", String.format("%.4f",longitude));
        telematicsData.put("fuelBalance", String.format("%.2f",fuelBalance));
        telematicsData.put("totalDistance", String.format("%.2f",totalDistance));

        try {
            // Convert the data map to JSON
            String jsonData = objectMapper.writeValueAsString(telematicsData);
            // Send the JSON data to API
            sendDataToAPI(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDataToAPI(String jsonData) {
        // Print the data for debugging purposes
        System.out.println(jsonData);
    }

    private double calculateDistanceCovered(int periodSeconds) {
        return distanceCoveredPerSecond * periodSeconds;
    }

    private double calculateFuelConsumed( int periodSeconds) {
        return (distanceCoveredPerSecond/12)*periodSeconds; // Example: fuel consumption 15km/l
    }

    private void updateLatLong(double distanceCovered) {
        // Simulate movement by increasing latitude and longitude based on the distance covered
        double delta = distanceCovered / 111.32;  // Rough conversion: 1 degree ≈ 111.32 km
        latitude += delta;
        longitude += delta * Math.cos(Math.toRadians(latitude));  // Adjust longitude based on latitude
    }

    private void stopScheduler() {
        scheduler.shutdown();
        System.out.println("Scheduler stopped. All data sent.");
    }

    public double getDistanceCovered() {
        return distanceCovered;
    }

   /* public static void main(String[] args) {
        VehicleTelematicsScheduler telematicsScheduler = new VehicleTelematicsScheduler();
        telematicsScheduler.startSendingTelematicsData(null,10,1);
    }*/
}
