package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.entity.AccountDetails;

public interface AccountDetailsRepository extends JpaRepository<AccountDetails, String> {

}
