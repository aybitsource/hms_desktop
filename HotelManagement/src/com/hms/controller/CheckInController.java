package com.hms.controller;

import java.util.List;

import com.hms.model.CheckIn;
import com.hms.services.CheckInService;

public class CheckInController {
	public CheckIn obj_checkIn;
	CheckInService checkIn_service;
	
	public CheckInController(CheckIn obj_checkIn)
	{
		this.obj_checkIn = obj_checkIn;
		checkIn_service = new CheckInService(obj_checkIn);
	}
	public int submitCheckIn()
	{
		return checkIn_service.submitService();
	}
	public void retrieveBookings(String query)
	{
		  checkIn_service.retrieveAll(query);
	}
	public List<String> retrieveBookingIDs(String query, String param)
	{
		return checkIn_service.retrieveBookingIDs(query, param);
	}
}
