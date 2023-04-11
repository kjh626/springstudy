package com.gdu.app01.java01_practice;

public class Friend {
	
	private String name;
	private String gender;
	private Address addr;
	
	
	public Friend() {
		
	}
	public Friend(String name, String gender, Address addr) {
		super();
		this.name = name;
		this.gender = gender;
		this.addr = addr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Address getAddr() {
		return addr;
	}
	public void setAddr(Address addr) {
		this.addr = addr;
	}
	
}
