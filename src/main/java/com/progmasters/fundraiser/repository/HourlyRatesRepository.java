package com.progmasters.fundraiser.repository;

import com.progmasters.fundraiser.domain.HourlyRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HourlyRatesRepository extends JpaRepository<HourlyRates, Long> {

    HourlyRates findByTimeBetween(LocalDateTime from, LocalDateTime to);

    List<HourlyRates> findAllByOrderByTimeDesc();
}
