package com.hms.controller;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hms.model.RoomFacilities;
import com.hms.services.RoomFacilitiesService;

public class RoomFacilitiesController {
	public RoomFacilities obj_roomFacilities;
	RoomFacilitiesService room_facilities_service;
	
	public RoomFacilitiesController(RoomFacilities obj_roomFacilities)
	{
		this.obj_roomFacilities = obj_roomFacilities;
		room_facilities_service = new RoomFacilitiesService(obj_roomFacilities);
	}
	public RoomFacilitiesController(DefaultTableModel tableModel, JTable table) {
		// TODO Auto-generated constructor stub
		room_facilities_service = new RoomFacilitiesService(tableModel, table);
	}
	public int submitFacility()
	{
		return room_facilities_service.submitService();
	}
	public String generateID() {
		// TODO Auto-generated method stub
		return room_facilities_service.generateFacilitiesId();
	}
	public void retrieveAll(String query)
	{
		room_facilities_service.retrieveAll(query);
	}
	public void retrieve(String query, String param)
	{
		room_facilities_service.retrieve(query, param);
	}
	public void updateService(String query1, String query2, String param)
	{
		room_facilities_service.updateService(query1, query2, param);
	}

}
