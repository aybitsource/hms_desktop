package com.hms.controller;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hms.model.Booking;
import com.hms.services.BookingCancelService;
import com.hms.services.BookingTransactionService;

public class BookingCancelController {
	private BookingCancelService obj_booking_cancel_service;
	
	public BookingCancelController(DefaultTableModel tableModel, JTable table)
	{
		obj_booking_cancel_service = new BookingCancelService(tableModel, table);
		
	}
	public BookingCancelController(Booking obj_booking)
	{
		obj_booking_cancel_service = new BookingCancelService(obj_booking);
	}
	public int submitCancelService()
	{
		int result = obj_booking_cancel_service.submitService();
		return result;
	}
	public void retrieveBookingDetails(String bookingID)
	{
		
		obj_booking_cancel_service.retrieveBookings(bookingID);
	}
	public void retrieve(String query, String param)
	{
		obj_booking_cancel_service.retrieve(query, param);
	}
	public void retrieveAll(String query, String parameter)
	{
		obj_booking_cancel_service.retrieveAll(query, parameter);
	}
}
