package com.ssf.linkcf.bean;

public class ForeignerBean {

	//����ID
	private String ForeignerID;
	//ͷ��
	private String Portrait;
	//�ǳ�
	private String NickName;
	//��ʱ
	private String Punctual;
	//��ͨ
	private String Communication;
	//�۸�
	private String UnitPrice;
	//����ԤԼ����
	private String FreeDay;
	//��Сʱ�ڿ�ԤԼʱ��
	private String FreeTime;
	//�������ԤԼ��ʱ���
	private String FreeTimeStamp;
	//����
	private String Distance;
	//��ʱ��
	private String ClassCount;
	public String getForeignerID() {
		return ForeignerID;
	}
	public void setForeignerID(String foreignerID) {
		ForeignerID = foreignerID;
	}
	public String getPortrait() {
		return Portrait;
	}
	public void setPortrait(String portrait) {
		Portrait = portrait;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getPunctual() {
		return Punctual;
	}
	public void setPunctual(String punctual) {
		Punctual = punctual;
	}
	public String getCommunication() {
		return Communication;
	}
	public void setCommunication(String communication) {
		Communication = communication;
	}
	public String getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		UnitPrice = unitPrice;
	}
	public String getFreeDay() {
		return FreeDay;
	}
	public void setFreeDay(String freeDay) {
		FreeDay = freeDay;
	}
	public String getFreeTime() {
		return FreeTime;
	}
	public void setFreeTime(String freeTime) {
		FreeTime = freeTime;
	}
	public String getFreeTimeStamp() {
		return FreeTimeStamp;
	}
	public void setFreeTimeStamp(String freeTimeStamp) {
		FreeTimeStamp = freeTimeStamp;
	}
	public String getDistance() {
		return Distance;
	}
	public void setDistance(String distance) {
		Distance = distance;
	}
	public String getClassCount() {
		return ClassCount;
	}
	public void setClassCount(String classCount) {
		ClassCount = classCount;
	}
	@Override
	public String toString() {
		return "ForeignerBean [ForeignerID=" + ForeignerID + ", Portrait="
				+ Portrait + ", NickName=" + NickName + ", Punctual="
				+ Punctual + ", Communication=" + Communication
				+ ", UnitPrice=" + UnitPrice + ", FreeDay=" + FreeDay
				+ ", FreeTime=" + FreeTime + ", FreeTimeStamp=" + FreeTimeStamp
				+ ", Distance=" + Distance + ", ClassCount=" + ClassCount + "]";
	}
	
	
	
}
