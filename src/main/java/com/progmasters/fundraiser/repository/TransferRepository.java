package com.progmasters.fundraiser.repository;

import com.progmasters.fundraiser.domain.Account;
import com.progmasters.fundraiser.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    List<Transfer> findAllByFromOrderByTimeStampDesc(Account from);

    List<Transfer> findAllByToOrderByTimeStampDesc(Account to);

    List<Transfer> findAllByOrderByTimeStampDesc();

    Transfer findById(Long id);

    @Query("SELECT t from Transfer t WHERE t.isPending = false and t.isFulfilled = false and t.transactionDate < current_timestamp ")
    List<Transfer> findAllFalsePendingAndNotFulfilledTransactions();

    @Query("SELECT t from Transfer t WHERE t.isPending = false and t.isFulfilled = false and t.transactionDate > current_timestamp ")
    List<Transfer> sumFutureDebit();

    @Query("SELECT t from Transfer t WHERE t.isPending = true and t.isTimed = true and t.transactionDate < current_timestamp")
    List<Transfer> findAllTruePendingAndNotFulfilledTransactions();
}
