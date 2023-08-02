package com.cts.loginregister.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.loginregister.Dto.LoginDto;
import com.cts.loginregister.Dto.LoginResponse;
import com.cts.loginregister.entity.Customer;
import com.cts.loginregister.entity.SubscribeCustomers;
import com.cts.loginregister.exceptions.CustomerNotFoundException;
import com.cts.loginregister.repository.CustomerRepository;
import com.cts.loginregister.repository.SubscribeCustomersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SubscribeCustomersRepository subsCustRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public String saveCustomer(Customer customer) {
	
		Customer cust = new Customer(
				customer.getCustomerId(),
				customer.getCustomerName(),
				customer.getCustomerEmail(),
				this.passwordEncoder.encode(customer.getCustomerPassword())
		);
		Customer c = customerRepository.findByCustomerEmail(customer.getCustomerEmail());
		if(c==null)
		{
			customerRepository.save(cust);
			log.info("Customer with "+cust.getCustomerId()+" inserted.");
			return "Customer with "+cust.getCustomerId()+" inserted.";
		}
		else {
			return "Email already exists";
		}
		
	}
	
	public String saveSubscribeCustomers(SubscribeCustomers sc) {
		subsCustRepo.save(sc);
		return "Customer succesfully subscribed";
	}
	public List<Customer> getAllCustomers(){
		List<Customer> cust = customerRepository.findAll();
		return cust;
	}
	
public LoginResponse loginCustomer(LoginDto loginDto) {
		
		String msg="";
		Customer cust1 = customerRepository.findByCustomerEmail(loginDto.getCustomerEmail());
		if(cust1!=null) {
			String pass= loginDto.getCustomerPassword();
			String encodePass = cust1.getCustomerPassword();
			Boolean status =passwordEncoder.matches(pass, encodePass);
			if(status) {
				Optional<Customer> customer = 
							customerRepository.findOneByCustomerEmailAndCustomerPassword(loginDto.getCustomerEmail(),encodePass);
				System.out.println(customer);
				long cid = cust1.getCustomerId();
				String cname = cust1.getCustomerName();
				if(customer.isPresent()) {
					log.info("Customer Succesfully logged in");
					return new LoginResponse(cid,cname,"Login Success",true);
				}else {
					log.info("Failure occured in customer login");
					return new LoginResponse(0,null,"Login Failed",false);
				}
			}else {
				log.info("Given passwords dont match");
				return new LoginResponse(0,null,"Passwords dont match",false);
			}
		}else {
			log.info("Customer Email dosent exist in the database");
			return new LoginResponse(0,null,"Email Dosent exist",false);
		}
		
	}
	
	public Customer getCustomerById(long customerId) throws CustomerNotFoundException {
		Customer c = customerRepository.findByCustomerId(customerId);
		if(c==null)
		{
			throw new CustomerNotFoundException("Customer with ID "+customerId+" not found!");
		}
		else {
			return c;
		}
	}
	
	public String deleteCustomerbyId(long custId) throws CustomerNotFoundException 
	{
		
		Customer c = customerRepository.findByCustomerId(custId);
		if(c!=null) {
			customerRepository.deleteById(custId);
			log.info("Customer with Id "+custId+" is deleted");
			return "Customer with Id "+custId+" is deleted";
		}
		else {
			throw new CustomerNotFoundException("Customer with "+custId+" not found!");
		}
		
		
	}
}
