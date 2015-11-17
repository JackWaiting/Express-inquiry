package com.catcher.seeexpress.entity;

public class ExpressQureyEntity {

	private String exNumber;
	private String exType;
	private String exName;

	public ExpressQureyEntity() {
	}

	public ExpressQureyEntity(String exNumber, String exType, String exName) {
		super();
		this.exNumber = exNumber;
		this.exType = exType;
		this.exName = exName;
	}

	public String getExNumber() {
		return exNumber;
	}

	public void setExNumber(String exNumber) {
		this.exNumber = exNumber;
	}

	public String getExType() {
		return exType;
	}

	public void setExType(String exType) {
		this.exType = exType;
	}

	public String getExName() {
		return exName;
	}

	public void setExName(String exName) {
		this.exName = exName;
	}

}
