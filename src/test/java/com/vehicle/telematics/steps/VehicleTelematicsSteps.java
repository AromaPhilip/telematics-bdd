package com.vehicle.telematics.steps;

import com.vehicle.telematics.model.Vehicle;
import com.vehicle.telematics.scheduler.VehicleTelematicsScheduler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.concurrent.TimeUnit;

public class VehicleTelematicsSteps {

    Vehicle vehicle;
    VehicleTelematicsScheduler vehicleTelematicsScheduler;

    @Given("a vehicle with id {string}")
    public void aVehicleWithId(String vehicleId) {
        vehicle = new Vehicle(vehicleId,
                80.0,
                50.0,
                "No issues",
                37.7749,
                -122.4194);
    }

    @When("device send telematics data every {int} seconds for {int} minutes")
    public void deviceSendTelematicsDataEveryNSecondsForMMinutes(int periodSeconds, int totalDuration) {
        vehicleTelematicsScheduler = new VehicleTelematicsScheduler();
        vehicleTelematicsScheduler.startSendingTelematicsData(vehicle, periodSeconds,
                totalDuration);
        try {
            TimeUnit.SECONDS.sleep((totalDuration * 60L) / periodSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("aggregator EP should return the aggregated {double} km distance")
    public void aggregatorEPShouldReturnTheAggregatedDistance(double expected) {
        Assert.assertEquals(expected, vehicleTelematicsScheduler.getDistanceCovered(), 0.5);
    }

}
