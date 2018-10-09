package com.progmasters.fundraiser.service;

import com.progmasters.fundraiser.domain.CurrencyType;
import com.progmasters.fundraiser.domain.ExchangeRate;
import com.progmasters.fundraiser.domain.HourlyRates;
import com.progmasters.fundraiser.domain.dto.ExchangeRateData;
import com.progmasters.fundraiser.domain.dto.RateToTransfer;
import com.progmasters.fundraiser.repository.HourlyRatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class HourlyRatesService {

    private static final String API_URL = "http://data.fixer.io/api/latest?access_key=c1db2e8acee63a3830383ee46b45cd7d&symbols=USD,HUF,GBP,EUR&format=5";

    private HourlyRatesRepository hourlyRatesRepository;

    @Autowired
    public HourlyRatesService(HourlyRatesRepository hourlyRatesRepository) {
        this.hourlyRatesRepository = hourlyRatesRepository;
    }

    public HourlyRates save(ExchangeRateData exchangeRateData) {
        HourlyRates hourlyRates = new HourlyRates();

        Map<CurrencyType, Double> rates = new HashMap<>();
        for (Map.Entry<String, Double> entry : exchangeRateData.getRates().entrySet()) {
            rates.put(CurrencyType.valueOf(entry.getKey()), entry.getValue());
        }

        hourlyRates.setTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(exchangeRateData.getTimestamp()),
                TimeZone.getDefault().toZoneId()));
        hourlyRates.setBaseCurrencyType(CurrencyType.valueOf(exchangeRateData.getBase()));
        hourlyRates.setRates(rates);

        return hourlyRatesRepository.save(hourlyRates);
    }

    public RateToTransfer getActualRates() {
        LocalDateTime now = LocalDateTime.now();
        RateToTransfer result;

        if (findRatesBetweenTimes(now.minusHours(1), now) != null) {
            result = new RateToTransfer(findRatesBetweenTimes(now.minusHours(1), now));
        } else {
            ExchangeRateData rates = getRatesFromFixerAPI();
            if (rates == null) {
                result = new RateToTransfer(hourlyRatesRepository.findAllByOrderByTimeDesc().get(0));
            } else {
                result = new RateToTransfer(save(rates));
            }
        }

        return result;
    }

    private HourlyRates findRatesBetweenTimes(LocalDateTime from, LocalDateTime to) {
        return hourlyRatesRepository.findByTimeBetween(from, to);
    }

    private ExchangeRateData getRatesFromFixerAPI() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.getForObject(
                    API_URL,
                    ExchangeRateData.class);
        } catch (Exception exception) {
            return null;
        }
    }

    public Double getUSDExchangeRate(CurrencyType currency, Map<String, Double> rates) {
        List<ExchangeRate> ratesList = addCurrencies(rates);
        ExchangeRate rate = new ExchangeRate();
        rate.setFrom(currency);
        rate.setTo(CurrencyType.valueOf("USD"));

        return ratesList.get(ratesList.indexOf(rate)).getRate();
    }

    private List<ExchangeRate> addCurrencies(Map<String, Double> rates) {
        List<ExchangeRate> ratesToReturn = new ArrayList<>();

        ratesToReturn.add(new ExchangeRate(CurrencyType.HUF, CurrencyType.HUF, 1d));
        ratesToReturn.add(new ExchangeRate(CurrencyType.HUF, CurrencyType.EUR, rates.get("HUF")));
        ratesToReturn.add(new ExchangeRate(CurrencyType.HUF, CurrencyType.USD, (rates.get("HUF") / rates.get("USD"))));
        ratesToReturn.add(new ExchangeRate(CurrencyType.HUF, CurrencyType.GBP, (rates.get("HUF") / rates.get("GBP"))));
        ratesToReturn.add(new ExchangeRate(CurrencyType.EUR, CurrencyType.HUF, (1d / rates.get("HUF"))));
        ratesToReturn.add(new ExchangeRate(CurrencyType.EUR, CurrencyType.EUR, 1d));
        ratesToReturn.add(new ExchangeRate(CurrencyType.EUR, CurrencyType.USD, (1d / rates.get("USD"))));
        ratesToReturn.add(new ExchangeRate(CurrencyType.EUR, CurrencyType.GBP, (1d / rates.get("GBP"))));

        ratesToReturn.add(new ExchangeRate(CurrencyType.USD, CurrencyType.HUF, (rates.get("USD") / rates.get("HUF"))));
        ratesToReturn.add(new ExchangeRate(CurrencyType.USD, CurrencyType.EUR, rates.get("USD")));
        ratesToReturn.add(new ExchangeRate(CurrencyType.USD, CurrencyType.USD, 1d));
        ratesToReturn.add(new ExchangeRate(CurrencyType.USD, CurrencyType.GBP, (rates.get("USD") / rates.get("GBP"))));
        ratesToReturn.add(new ExchangeRate(CurrencyType.GBP, CurrencyType.HUF, (rates.get("GBP") / rates.get("HUF"))));
        ratesToReturn.add(new ExchangeRate(CurrencyType.GBP, CurrencyType.EUR, rates.get("GBP")));
        ratesToReturn.add(new ExchangeRate(CurrencyType.GBP, CurrencyType.USD, (rates.get("GBP") / rates.get("USD"))));
        ratesToReturn.add(new ExchangeRate(CurrencyType.GBP, CurrencyType.GBP, 1d));

        return ratesToReturn;
    }
}
