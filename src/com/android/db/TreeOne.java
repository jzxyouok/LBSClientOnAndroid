package com.android.db;

public class TreeOne {
	private int id;
	private int xbbh;//小班编号
	private int ydbh;//样地编号
	private int treenumber;//树木编号
	private double treehigh;
	private String bz;
	public TreeOne() {
	}
	public TreeOne(int Id, int xbbh,int ydbh, int treenumber,double high) {
		super();
		this.id = Id;
		this.xbbh = xbbh;
		this.ydbh = ydbh;
		this.treenumber = treenumber;
		this.treehigh=high;
	}
	public TreeOne(int Id, int xbbh,int ydbh, int treenumber,double high,String bz) {
		super();
		this.id = Id;
		this.xbbh = xbbh;
		this.ydbh = ydbh;
		this.treenumber = treenumber;
		this.treehigh=high;
		this.bz=bz;
	}
	public int getId() {
		return id;
	}

	public void setId(int userId) {
		this.id = userId;
	}
	
	public void setxbbh(int xbbh) {
		this.xbbh = xbbh;
	}
	public int getxbbh() {
		return xbbh;
	}

	public void setydbh(int ydbh) {
		this.ydbh = ydbh;
	}
	public int getydbh() {
		return ydbh;
	}

	public void settreenumber(int treenumber) {
		this.treenumber = treenumber;
	}
	public int gettreenumber() {
		return treenumber;
	}
	
	public void sethigh(double high) {
		this.treehigh = high;
	}
	public double gethigh() {
		return treehigh;
	}
	
	public void setbz(String bz) {
		this.bz = bz;
	}
	public String getbz() {
		return bz;
	}
	
	public String toString() {
		return "treeone [Id=" + id + ", xbbh=" + xbbh
				+ ", ydbh=" + ydbh + ", treenumber=" + treenumber + ", treehigh="
				+treehigh+", BZ="+bz+"]";
	}
}
