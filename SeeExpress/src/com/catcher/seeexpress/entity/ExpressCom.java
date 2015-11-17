package com.catcher.seeexpress.entity;

/**
 * 快递公司实体类
 * 
 * @author Administrator
 *
 */
public class ExpressCom {

	private String name; //快递公司的名称
	private String type; //快递公司在快递100API接口中的代码
	private String iconPath; //快递公司LOGO的路径
	private String phone; //快递公司的客服电话

	public ExpressCom() {
		super();
	}

	public ExpressCom(String name, String type, String iconPath, String phone) {
		super();
		this.name = name;
		this.type = type;
		this.iconPath = iconPath;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
