package com.progmasters.fundraiser.repository;

import com.progmasters.fundraiser.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUserName(String userName);

    Account findByEmail(String email);

}
