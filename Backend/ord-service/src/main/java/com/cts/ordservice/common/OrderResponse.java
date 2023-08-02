package com.cts.ordservice.common;

import com.cts.ordservice.entity.CustomerOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	
	private CustomerOrder order;
//	private String transactionId;
	private String message;
	
}
