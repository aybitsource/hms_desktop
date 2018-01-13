package com.hms.dbutil;

public class HMSDBQueries {
	
	static final String GET_ALL_CUSTOMERS = "Select * from CUSTOMERS";
	static final String ADD_CUSTOMER = "insert into customers(customerId,firstName, lastName, gender, city, email, mobile, addrs) "+ "values(?,?,?,?,?,?,?,?)";
	static final String UPDATE_CUSTOMER = "update customers set firstName = ?,lastName = ?, city = ?, email = ?, mobile = ?, addrs = ? where customerID = ?";

}
