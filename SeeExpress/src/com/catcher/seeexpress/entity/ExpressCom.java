package com.catcher.seeexpress.entity;

/**
 * ��ݹ�˾ʵ����
 * 
 * @author Administrator
 *
 */
public class ExpressCom {

	private String name; //��ݹ�˾������
	private String type; //��ݹ�˾�ڿ��100API�ӿ��еĴ���
	private String iconPath; //��ݹ�˾LOGO��·��
	private String phone; //��ݹ�˾�Ŀͷ��绰

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
