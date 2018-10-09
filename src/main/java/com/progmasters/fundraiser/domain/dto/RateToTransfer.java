package com.progmasters.fundraiser.domain.dto;

import com.progmasters.fundraiser.domain.CurrencyType;
import com.progmasters.fundraiser.domain.HourlyRates;

import java.util.HashMap;
import java.util.Map;

public class RateToTransfer {

    private Map<String, Double> rates = new HashMap<>();

    public RateToTransfer() {
    }

    public RateToTransfer(HourlyRates hourlyRates) {
        Map<String, Double> rates = new HashMap<>();
        for (Map.Entry<CurrencyType, Double> entry : hourlyRates.getRates().entrySet()) {
            rates.put(entry.getKey().getDisplayName(), entry.getValue());
        }
        this.rates = rates;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
