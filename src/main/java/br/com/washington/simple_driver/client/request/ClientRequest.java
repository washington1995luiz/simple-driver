package br.com.washington.simple_driver.client.request;

public record ClientRequest(
        String longitudeInitial,
        String latitudeInitial,
        String longitudeFinal,
        String latitudeFinal
) {
}
