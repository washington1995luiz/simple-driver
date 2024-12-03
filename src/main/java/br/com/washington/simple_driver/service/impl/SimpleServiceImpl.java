package br.com.washington.simple_driver.service.impl;

import br.com.washington.simple_driver.client.SimpleClient;
import br.com.washington.simple_driver.client.response.ClientResponse;
import br.com.washington.simple_driver.http.request.SimpleRequest;
import br.com.washington.simple_driver.http.response.SimpleResponse;
import br.com.washington.simple_driver.service.SimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@RequiredArgsConstructor
@Service
public class SimpleServiceImpl implements SimpleService {

    private final SimpleClient simpleClient;

    @Value("${env.minimum}")
    private Double minimumToPay;

    @Value("${env.app-tax}")
    private Double appTax;

    @Value("${env.amount-paid-per-km}")
    private Double amountPaidPerKm;

    @Override
    public SimpleResponse request(SimpleRequest request) {
        String[] urlInitial = request.urlStart().split("/@")[1].split(",");
        String[] urlFinal = request.urlEnd().split("/@")[1].split(",");

        ClientResponse clientResponse =  simpleClient.request(
                urlInitial[1], urlInitial[0],
                urlFinal[1], urlFinal[0]
        );
        Double distanceInKm = convertDistanceToKm(clientResponse.routes().getFirst().distance());
        return new SimpleResponse(
                clientResponse.waypoints().getFirst().name(),
                clientResponse.waypoints().getLast().name(),
                clientResponse.routes().getFirst().distance(),
                distanceInKm,
                calcTax(clientResponse)
        );
    }

    private SimpleResponse.DetailsTravelValues calcTax(ClientResponse client){
        Double distanceInKm = convertDistanceToKm(client.routes().getFirst().distance());
        if(distanceInKm <= minimumToPay){
            var calculateTax = minimumToPay * (appTax / 100);
            var valueReceivedDriver = minimumToPay - calculateTax;
            return new SimpleResponse.DetailsTravelValues(
                    new BigDecimal(minimumToPay).setScale(2, RoundingMode.CEILING),
                    new BigDecimal(valueReceivedDriver).setScale(2, RoundingMode.FLOOR),
                    new BigDecimal(calculateTax).setScale(2, RoundingMode.FLOOR),
                    appTax
            );
        }

        var valueToPassengerPay = distanceInKm * amountPaidPerKm;
        var calculateTax = valueToPassengerPay * (appTax / 100);
        var valueReceivedDriver = valueToPassengerPay - calculateTax;
        return new SimpleResponse.DetailsTravelValues(
                new BigDecimal(valueToPassengerPay).setScale(2, RoundingMode.CEILING),
                new BigDecimal(valueReceivedDriver).setScale(2, RoundingMode.FLOOR),
                new BigDecimal(calculateTax).setScale(2, RoundingMode.FLOOR),
                appTax
        );
    }


    private Double convertDistanceToKm(Double distance) {
        var convert = new DecimalFormat("#.00").format(distance / 1000);
        return Double.valueOf(convert);
    }


}
