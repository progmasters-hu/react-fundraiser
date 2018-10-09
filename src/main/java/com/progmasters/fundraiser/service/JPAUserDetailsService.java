/*
 * Copyright © Progmasters (QTC Kft.), 2016.
 * All rights reserved. No part or the whole of this Teaching Material (TM) may be reproduced, copied, distributed, 
 * publicly performed, disseminated to the public, adapted or transmitted in any form or by any means, including 
 * photocopying, recording, or other electronic or mechanical methods, without the prior written permission of QTC Kft. 
 * This TM may only be used for the purposes of teaching exclusively by QTC Kft. and studying exclusively by QTC Kft.’s 
 * students and for no other purposes by any parties other than QTC Kft.
 * This TM shall be kept confidential and shall not be made public or made available or disclosed to any unauthorized person.
 * Any dispute or claim arising out of the breach of these provisions shall be governed by and construed in accordance with the laws of Hungary.
 */

package com.progmasters.fundraiser.service;

import com.progmasters.fundraiser.domain.Account;
import com.progmasters.fundraiser.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    private AccountRepository repository;

    @Autowired
    public JPAUserDetailsService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = repository.findByUserName(userName);

        if (account == null) {
            throw new UsernameNotFoundException("No user found with name: " + userName);
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("kutya");

        return User
                .withUsername(userName)
                .password(account.getPassword())
                .authorities(authorities)
                .build();
    }
}
