package com.hms.controller;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hms.model.Coupon;
import com.hms.services.CouponService;

public class CouponController {
	public Coupon obj_coupon;
	CouponService coupon_service;
	
	public CouponController(Coupon obj_coupon)
	{
		this.obj_coupon = obj_coupon;
		coupon_service = new CouponService(obj_coupon);
	}
	public CouponController(DefaultTableModel tableModel, JTable table) {
		// TODO Auto-generated constructor stub
			coupon_service = new CouponService(tableModel, table);
	}
	public int submitCoupon(String query)
	{
		return coupon_service.submitService(query);
	}
	public String generateID() {
		// TODO Auto-generated method stub
		return coupon_service.generateCouponId();
	}
	public void retrieveAll(String query)
	{
		coupon_service.retrieveAll(query);
	}
	public void retrieve(String query, String param)
	{
		coupon_service.retrieve(query, param);
	}
	public void updateService(String query1, String query2, String param)
	{
		coupon_service.updateService(query1, query2, param);
	}
}