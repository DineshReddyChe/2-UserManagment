package com.dinesh.dto;

public class UserDto {

	private Integer userId;
	private String email;
	private String pwd;
	private String updatedPwd;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUpdatedPwd() {
		return updatedPwd;
	}

	public void setUpdatedPwd(String updatedPwd) {
		this.updatedPwd = updatedPwd;
	}

}
