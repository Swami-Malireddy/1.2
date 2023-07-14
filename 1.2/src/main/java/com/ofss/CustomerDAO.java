package com.ofss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO extends JdbcDaoSupport{

	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	public void initialize()
	{
		System.out.println("setting the datasource");
		setDataSource(dataSource);
	}
	
	public String insertCustomer(Customer cust)
	{
		String sql="INSERT INTO CUSTOMER(CUST_ID, FIRST_NAME, LAST_NAME,PHONE_NUMBER,EMAIL_ID) VALUES(?,?,?,?,?)";
		getJdbcTemplate().update(sql,cust.getCustId(), cust.getFirstName(), cust.getLastName(), cust.getPhoneNumber(), cust.getEmailId() );
		return cust.getFirstName()+" has been inserted into db table successfully";
	}
	
	public List<Customer> getAllCustomers()
	{
		String sql="select * from customer";
		List<Map<String, Object>> rows=getJdbcTemplate().queryForList(sql);
		List<Customer> allCustomers=new ArrayList<>();
		for (Map<String,Object> row:rows) 
		{
			Customer c=new Customer();
			c.setCustId(row.get("CUST_ID").toString());// column name from the table
			c.setFirstName((String)row.get("FIRST_NAME"));
			c.setLastName((String)row.get("LAST_NAME"));
			c.setPhoneNumber(row.get("PHONE_NUMBER").toString());
			c.setEmailId((String)row.get("EMAIL_ID"));
			allCustomers.add(c);
		}
		return allCustomers;
	}
	
	public String deleteACustomer(String custId)
	{
		String sql="DELETE FROM CUSTOMER WHERE CUST_ID="+custId;
		int noOfRowsGotAffected=getJdbcTemplate().update(sql);
		if (noOfRowsGotAffected > 0)
		{
			return "successfully deleted";
		}
		else
			return "no matching id, so nothing got deleted";
	}
	
	public String updateACustomer(String cid, Customer cust)
	{
		String sql="UPDATE CUSTOMER SET ";
		
		// checking first name is not null
		if (cust.getFirstName()!=null)
		{
			sql=sql+"FIRST_NAME = '"+cust.getFirstName()+"',";
			
		}
		// checking last name is not null
		if (cust.getLastName()!=null)
		{
			sql=sql+"LAST_NAME= '"+cust.getLastName()+"',";
		}
		
		// checking last name is not null
		if (cust.getPhoneNumber()!=null)
		{
			sql=sql+"PHONE_NUMBER="+cust.getPhoneNumber()+",";
		}
		
		// checking EMAIL ID is not null
				if (cust.getEmailId()!=null)
				{
					sql=sql+"EMAIL_ID='"+cust.getEmailId()+"',";
				}
		sql=sql.substring(0,sql.length()-1);
		sql=sql+" WHERE CUST_ID="+cid;
		
		System.out.println("The sql command to be exeuted "+sql);

		
		int noOfRowsUpdated=getJdbcTemplate().update(sql);
		if (noOfRowsUpdated>0)
			return "updated successfully";
		
		return "no matching id, so nothing got updated";
			
	}
}
