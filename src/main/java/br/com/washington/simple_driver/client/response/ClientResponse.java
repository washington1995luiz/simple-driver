package br.com.washington.simple_driver.client.response;

import java.util.List;

public record ClientResponse(
        List<Routes> routes,
       List<WayPoints> waypoints
) {
    public record Routes(Double distance){}
    public record WayPoints(String name){}
}
