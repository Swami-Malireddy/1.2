package com.ofss;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController	
public class CustomerController {

	@Autowired
	CustomerService1 cs;


	@RequestMapping(value="/customers", method=RequestMethod.GET)
	public List<Customer> allCustomers()
	{
		System.out.println("Reaching controller class...");

		return cs.fetchAllCustomers();


	}
	// Here cid is the path parameter
	@RequestMapping(value="/customer/id/{cid}", method=RequestMethod.GET)
	public Optional<Customer> getACustomer(@PathVariable("cid") String cid1)
	{
		return cs.getCustomer(cid1);
	}
	
	// Here cid is the path parameter
	@RequestMapping(value="/customer/no-id/{cid}", method=RequestMethod.GET)
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public Optional<Customer> doNotGetACustomer(@PathVariable("cid") String cid1)
	{
		return cs.getCustomer(cid1);
	}
	@RequestMapping(value="/customer/id/{cid}", method=RequestMethod.PUT)
	public Customer getACustomer(@PathVariable("cid") String cid1, @RequestBody Customer cust)
	{
		return cs.updateCustomer(cid1, cust);
	}

	// Here name is the path parameter
	@RequestMapping(value="/customer/firstname/{name}", method=RequestMethod.GET)
	public Customer getACustomer1(@PathVariable("name") String name1)
	{
		return null;
	}
	
	@RequestMapping(value="/customer", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String addACustomer(@RequestBody Customer cust)
	{
		System.out.println("post method is invoked");
		return cs.addCustomer(cust);
		//return "Created"; // instead of default OK, now this will be returned
	}
//	
	@RequestMapping(value="/customer/id/{cid}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteACustomer(@PathVariable("cid") String cid1)
	{
		System.out.println("delete method is invoked for the customer id "+cid1);
		return cs.deleteCustomer(cid1);
	}
}
