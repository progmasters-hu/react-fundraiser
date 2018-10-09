package com.progmasters.fundraiser.domain.dto;

import java.util.HashMap;
import java.util.Map;

public class ExchangeRateData {

    private Long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates = new HashMap<>();

    public ExchangeRateData() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
