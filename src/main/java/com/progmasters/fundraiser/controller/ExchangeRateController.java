package com.progmasters.fundraiser.controller;

import com.progmasters.fundraiser.domain.dto.RateToTransfer;
import com.progmasters.fundraiser.service.HourlyRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rates")
public class ExchangeRateController {

    private HourlyRatesService hourlyRatesService;

    @Autowired
    public ExchangeRateController(HourlyRatesService hourlyRatesService) {
        this.hourlyRatesService = hourlyRatesService;
    }

    @GetMapping
    public ResponseEntity<RateToTransfer> getRates() {
        return new ResponseEntity<>(hourlyRatesService.getActualRates(), HttpStatus.OK);
    }
}
