package com.cts.loginregister;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.loginregister.Dto.LoginDto;
import com.cts.loginregister.Dto.LoginResponse;
import com.cts.loginregister.entity.Customer;
import com.cts.loginregister.exceptions.CustomerNotFoundException;
import com.cts.loginregister.repository.CustomerRepository;
import com.cts.loginregister.service.CustomerService;

@SpringBootTest
class LoginRegisterApplicationTests {

	
	@Autowired
	private CustomerService customerService;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void getCustomersTest()
	{
		when(customerRepository.findAll()).thenReturn(Stream
				.of(new Customer(3,"csrf","csrf@gmail.com","wbcr"),new Customer(4,"llll","llll@gmail.com","llll")).collect(Collectors.toList()));
		
		assertEquals(2,customerService.getAllCustomers().size());
	}
	
	
	
	
	@Test
	public void saveCustomerTest()
	{
		Customer c = new Customer();
		c.setCustomerId(1);
		c.setCustomerName("asdf");
		c.setCustomerEmail("asdf@gmail.com");
		c.setCustomerPassword("asdf");
		when(customerRepository.save(c)).thenReturn(c);
		//expected actual
		assertEquals("Customer with 1 inserted.",customerService.saveCustomer(c));
	}
	
	
	@Test
	public void getCustomerByIdTest() throws CustomerNotFoundException
	{
		Customer c = new Customer();
		c.setCustomerId(1);
		c.setCustomerName("asdf");
		c.setCustomerEmail("asdf@gmail.com");
		c.setCustomerPassword("asdf");
		when(customerRepository.findByCustomerId(1)).thenReturn(c);
		Customer cc = customerService.getCustomerById(1);
		assertEquals(c,cc);
	}
	
	@Test
	public void deleteCustomerByIdTest() throws CustomerNotFoundException
	{
		Customer c = new Customer();
		c.setCustomerId(7);
		c.setCustomerName("asdf");
		c.setCustomerEmail("asdf@gmail.com");
		c.setCustomerPassword("asdf");
		when(customerRepository.findByCustomerId(7)).thenReturn(c).thenReturn(null);
		String result = customerService.deleteCustomerbyId(7);
        verify(customerRepository, times(1)).deleteById(7L);
//        assertEquals(result,"Customer with Id 7 is deleted");

	}


}
