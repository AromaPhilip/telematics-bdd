Feature: Send vehicle telematics data at intervals

  Scenario Outline: Sending vehicle telematics data every <periodSeconds> seconds
    Given a vehicle with id <vehicleId>
    When device send telematics data every <periodSeconds> seconds for <totalDuration> minutes
    Then aggregator EP should return the aggregated <expected> km distance
    Examples:
      | vehicleId | periodSeconds | totalDuration | expected |
      | "1234"    | 5             | 1             | 0.5      |
      | "4567"    | 4             | 1             | 0.4      |
