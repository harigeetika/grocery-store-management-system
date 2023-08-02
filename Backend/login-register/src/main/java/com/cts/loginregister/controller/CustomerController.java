package com.cts.loginregister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cts.loginregister.Dto.LoginDto;
import com.cts.loginregister.Dto.LoginResponse;
import com.cts.loginregister.entity.Customer;
import com.cts.loginregister.entity.SubscribeCustomers;
import com.cts.loginregister.exceptions.CustomerNotFoundException;
import com.cts.loginregister.service.CustomerService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	
	
	@PostMapping("/saveCustomer")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveCustomer(@RequestBody @Valid Customer customer) {
		System.out.println(customer.getCustomerEmail());
		return customerService.saveCustomer(customer);
	}
	
	@PostMapping("/subsCustomer")
	@ResponseStatus(HttpStatus.CREATED)
	public String subsCustomer(@RequestBody SubscribeCustomers subsCust) {
		return customerService.saveSubscribeCustomers(subsCust);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/allCustomers")
	public List<Customer> all() {
		return customerService.getAllCustomers();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginCustomer(@RequestBody LoginDto loginDto){
		
		LoginResponse loginMessage = customerService.loginCustomer(loginDto);
		return ResponseEntity.ok(loginMessage);
	}
	
	@GetMapping("/{id}")
	public Customer customerById (@PathVariable("id") long customerId) throws CustomerNotFoundException{
		return customerService.getCustomerById(customerId);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteCustById(@PathVariable("id") long custId) throws CustomerNotFoundException
	{
		return customerService.deleteCustomerbyId(custId);
	}
}
