package com.ofss;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService1 {

	@Autowired
	CustomerRepository customerRepository;
	
	public List<Customer> fetchAllCustomers(){
		System.out.println("JPA fetchAllCustomers...");
		List<Customer> allCustomers=new ArrayList<>();
		customerRepository.findAll()
		.forEach(customer -> allCustomers.add(customer));
		return allCustomers;
		
	}
	
	public Optional<Customer> getCustomer(String custId) {
		System.out.println("JPA find one customer");
		return customerRepository.findById(custId);
	}
	
	
	public String addCustomer(Customer cust) {
		customerRepository.save(cust);
		
		return cust.getFirstName()+" has been added successfully";
	}
	
	public String deleteCustomer(String custId) {
		try {
		customerRepository.deleteById(custId);
		return "deleted successfully";
		}
		catch(org.springframework.dao.EmptyResultDataAccessException e)
		{
			return "No such customer id exists";
		}
	}
	
	public Customer updateCustomer(String cid, Customer cust)
	{
		cust.setCustId(cid); // manually assigning the primary key
		System.out.println("Updating customer using customer repository "+cust.getFirstName());
		return customerRepository.save(cust); // this will just overwrite the existing record
	}
	
}
