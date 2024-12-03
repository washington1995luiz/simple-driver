package br.com.washington.simple_driver.http.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record SimpleResponse(
        String streetInitial,
        String streetFinal,
        @JsonProperty("distance_in_meters") Double distance,
        @JsonProperty("distance_in_km") Double km,
        @JsonProperty("details_travel_values") DetailsTravelValues detailsTravelValues
) {
    public record DetailsTravelValues(
            BigDecimal valuePaidPassenger,
            BigDecimal valueReceivedDriver,
            BigDecimal valueReceivedIntermediate,
            Double taxOfIntermediate
    ){}
}
