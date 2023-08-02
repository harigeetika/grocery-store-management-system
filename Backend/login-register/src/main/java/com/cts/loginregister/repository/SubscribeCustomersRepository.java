package com.cts.loginregister.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.loginregister.entity.SubscribeCustomers;

@Repository
public interface SubscribeCustomersRepository extends JpaRepository<SubscribeCustomers,Long>{

}
