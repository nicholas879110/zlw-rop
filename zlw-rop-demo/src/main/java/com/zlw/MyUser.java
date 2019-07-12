package com.zlw;


import com.rop.AbstractRopRequest;



public class MyUser extends AbstractRopRequest{

	private String address;
	private User user;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
