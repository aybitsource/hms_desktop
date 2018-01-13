package com.hms.controller;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hms.model.Season;
import com.hms.services.SeasonService;

public class SeasonController {
	public Season obj_season;
	SeasonService season_service;

	public SeasonController()
	{
		season_service = new SeasonService();
	}
	public SeasonController(Season obj_season)
	{
		this.obj_season = obj_season;
		season_service = new SeasonService(obj_season);
	}
	public SeasonController(DefaultTableModel tableModel, JTable table) {
		// TODO Auto-generated constructor stub
		season_service = new SeasonService(tableModel, table);
	}
	public int submitService(String query)
	{
		return season_service.submitService(query);
	}
	public String generateID() {
		// TODO Auto-generated method stub
		return season_service.generateID();
	}
	public void retrieveAll(String query)
	{
		season_service.retrieveAll(query);
	}
	public void retrieve(String query, String param)
	{
		season_service.retrieve(query, param);
	}
	public void updateService(String query1, String query2, String param)
	{
		season_service.updateService(query1, query2, param);
	}
	public String seasonCouponDiscount(String couponName)
	{
		return season_service.seasonCouponDiscount(couponName);
	}
}