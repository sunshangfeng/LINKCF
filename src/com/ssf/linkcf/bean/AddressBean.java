package com.ssf.linkcf.bean;

public class AddressBean {

	private int UserID;
	//�û�ID
	private int AddressID;
	//��ַID
	private String Address;
	//��ַ
	private int IsDefault;
	//�Ƿ�Ĭ��
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public int getAddressID() {
		return AddressID;
	}
	public void setAddressID(int addressID) {
		AddressID = addressID;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getIsDefault() {
		return IsDefault;
	}
	public void setIsDefault(int isDefault) {
		IsDefault = isDefault;
	}
	
	
	@Override
	public String toString() {
		return "AddressBean [UserID=" + UserID + ", AddressID=" + AddressID
				+ ", Address=" + Address + ", IsDefault=" + IsDefault + "]";
	}
	
	
	
}
