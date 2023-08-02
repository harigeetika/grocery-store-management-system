package com.cts.ordservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.cts.ordservice.entity.CustomerOrder;


@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
	
	List<CustomerOrder> findByCustomerId(long customerId);
	
	CustomerOrder findByOrderId(long orderId);

}
