package com.iris.request;

public class UserLoginResponseModel {
	private final boolean success;
	private final String token;

	public UserLoginResponseModel(boolean success, String token) {
		this.success = success;
		this.token = token;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getToken() {
		return token;
	}

}
