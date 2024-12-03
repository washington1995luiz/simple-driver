package br.com.washington.simple_driver.http.controller.impl;

import br.com.washington.simple_driver.http.controller.SimpleController;
import br.com.washington.simple_driver.http.request.SimpleRequest;
import br.com.washington.simple_driver.http.response.SimpleResponse;
import br.com.washington.simple_driver.service.SimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/simple")
public class SimpleControllerImpl implements SimpleController {

    private final SimpleService service;

    @Override
    public SimpleResponse request(SimpleRequest request) {
        return service.request(request);
    }
}
