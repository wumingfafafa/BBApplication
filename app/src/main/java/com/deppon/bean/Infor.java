package com.deppon.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tb_infor")
public class Infor{
	@DatabaseField(columnName="infor_id" ,generatedId=true)
	private int id;
	@DatabaseField(columnName="detile")
	private String detile;
	@DatabaseField(columnName="title")
	private String title;
	@ForeignCollectionField(eager=true)
	private ForeignCollection<User> users;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDetile() {
		return detile;
	}
	public void setDetile(String detile) {
		this.detile = detile;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ForeignCollection<User> getUsers() {
		return users;
	}
	public void setUsers(ForeignCollection<User> users) {
		this.users = users;
	}
	
	
	

}
