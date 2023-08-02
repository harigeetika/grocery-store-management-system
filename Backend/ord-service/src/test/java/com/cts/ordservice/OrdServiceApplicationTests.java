package com.cts.ordservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.ordservice.common.OrdRequest;
import com.cts.ordservice.common.OrderResponse;
import com.cts.ordservice.entity.CustomerOrder;
import com.cts.ordservice.entity.OrderItems;
import com.cts.ordservice.repository.OrderRepository;
import com.cts.ordservice.service.OrderService;



@SpringBootTest
class OrdServiceApplicationTests {
	
	@Autowired
	private OrderService orderService;
	
	@MockBean
	private OrderRepository orderRepository;

	@Test
	public void getAllOrders(){
		
		List<OrderItems> ct = new ArrayList<OrderItems>();
		when(orderRepository.findAll()).thenReturn(Stream
				.of(new CustomerOrder(3,Instant.now(),"success",101,"abcd","abcd@gmail.com",12345678,120,"ACB Street",ct)).collect(Collectors.toList()));
		
		assertEquals(1,orderService.getAllOrderDetails().size());
	}
	
	@Test
	public void saveOrderTest()
	{
		OrdRequest od = new OrdRequest("ABC Street",12345689);
		List<OrderItems> ct = new ArrayList<OrderItems>();
		when(orderRepository.findAll()).thenReturn(Stream
				.of(new CustomerOrder(3,Instant.now(),"success",101,"abcd","abcd@gmail.com",12345678,120,"ACB Street",ct)).collect(Collectors.toList()));
		
		CustomerOrder ord = new CustomerOrder();
		ord.setOrderId(101);
		ord.setCustomerId(1);
		ord.setCustomerName("asgc");
		ord.setCustomerEmail("abcd@gmail.com");
		ord.setCustomerAddress(od.getCustomerAddress());
		ord.setCustomerContact(od.getCustomerContact());
		ord.setOrderDate(Instant.now());
		ord.setOrderStatus("Success");
		ord.setTotalAmount(100);
		ord.setOrderItems(ct);
		
		OrderResponse or = new OrderResponse();
		or.setOrder(ord);
		or.setMessage("order placed successfully");
		
		when(orderRepository.save(ord)).thenReturn(ord);
		
		
		
	}

}
