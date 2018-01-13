package com.hms.controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hms.model.Room;
import com.hms.services.RoomService;

public class RoomController {
	public Room obj_room;
	RoomService room_service;
	public String generateRoomID(){
		return room_service.generateRoomID();
	}
	public int roomCount()
	{
		return room_service.getTotalRooms();
	}
	public List<String> roomCategoryIDList(String query)
	{
		return room_service.roomCategoryIDList(query);
	}
	public RoomController(DefaultTableModel tableModel, JTable table)
	{
		room_service = new RoomService(tableModel, table);
	}
	public RoomController(Room obj_room)
	{
		this.obj_room = obj_room;
		room_service = new RoomService(obj_room);
	}
	public int submitRoom()
	{
		return room_service.submitService();
	}
	public void retrieveAllRooms(String query)
	{
		 room_service.retrieveAllRooms(query);
	}
	public void retrieveRoom(String query, String param)
	{
		 room_service.retrieveRoom(query, param);
	}
	public void updateRoom(String query, String param)
	{
		room_service.updateRoom(query, param);
	}

}