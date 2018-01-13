package com.hms.model;

public class Season {
	private String slno;
	private String season_ID;
	private String season_name;
	private java.sql.Date date_start;
	private java.sql.Date date_end;
	private String coupon_ID;
	private String coupon_name;
	public String getSeason_ID() {
		return season_ID;
	}
	public void setSeason_ID(String season_ID) {
		this.season_ID = season_ID;
	}
	public String getSeason_name() {
		return season_name;
	}
	public void setSeason_name(String season_name) {
		this.season_name = season_name;
	}
 
 
 
	public String getCoupon_ID() {
		return coupon_ID;
	}
	public void setCoupon_ID(String coupon_ID) {
		this.coupon_ID = coupon_ID;
	}
	public java.sql.Date getDate_start() {
		return date_start;
	}
	public void setDate_start(java.sql.Date date_start) {
		this.date_start = date_start;
	}
	public java.sql.Date getDate_end() {
		return date_end;
	}
	public void setDate_end(java.sql.Date date_end) {
		this.date_end = date_end;
	}
	public String getSlno() {
		return slno;
	}
	public void setSlno(String slno) {
		this.slno = slno;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
 

}
