package com.testboard4.dto;

public class MemberDTO {

	
	// Fields
	private int num;
	private String name;
	private String id;
	private String phone;
	
	
	public MemberDTO() {}
	
	public MemberDTO(int num, String name, String id, String phone) {
		super();
		this.num = num;
		this.name = name;
		this.id = id;
		this.phone = phone;
	}
	// Getters and Setters
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	// toString()
	@Override
	public String toString() {
		return "MemberDTO [num=" + num + ", name=" + name + ", id=" + id + ", phone=" + phone + "]";
	}

	
}
