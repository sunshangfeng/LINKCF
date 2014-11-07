package com.ssf.linkcf.bean;

import java.io.Serializable;

public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int UserID;

	private int Status;

	private String UserName;

	private String Password;

	private String Portrait;

	private String AccessToken;

	private String Tel;

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPortrait() {
		return Portrait;
	}

	public void setPortrait(String portrait) {
		Portrait = portrait;
	}

	public String getAccessToken() {
		return AccessToken;
	}

	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	@Override
	public String toString() {
		return "LoginBean [UserID=" + UserID + ", Status=" + Status
				+ ", UserName=" + UserName + ", Password=" + Password
				+ ", Portrait=" + Portrait + ", AccessToken=" + AccessToken
				+ ", Tel=" + Tel + "]";
	}
	
}
