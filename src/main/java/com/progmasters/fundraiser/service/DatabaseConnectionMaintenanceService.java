package com.progmasters.fundraiser.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class DatabaseConnectionMaintenanceService {

    @PersistenceContext
    EntityManager em;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void makeCallEveryHourToKeepConnection() {
        Query query = em.createNativeQuery("SELECT 1");
        query.getResultList();
    }

}
