package com.deppon.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName="tb_user")
public class User {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(columnName="username")
	private String name;
	@DatabaseField(columnName="desc")
	private String desc;
	@DatabaseField(columnName="infor",foreignColumnName="infor_id",foreign=true,foreignAutoCreate=true,foreignAutoRefresh=true)
	private Infor infor;
	@DatabaseField(columnName="userNum")
	private String num;
	@DatabaseField(columnName="usersex")
	private int sex;
	@DatabaseField(columnName="userdate")
	private Date userdate;
	@DatabaseField(columnName="userdatelong")
	private long userdatelong;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}	
	public Infor getInfor() {
		return infor;
	}
	public void setInfor(Infor infor) {
		this.infor = infor;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getUserdate() {
		return userdate;
	}

	public void setUserdate(Date userdate) {
		this.userdate = userdate;
	}

	public long getUserdatelong() {
		return userdatelong;
	}

	public void setUserdatelong(long userdatelong) {
		this.userdatelong = userdatelong;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", desc='" + desc + '\'' +
				", infor=" + infor +
				", num='" + num + '\'' +
				", sex=" + sex +
				", userdate=" + userdate +
				", userdatelong=" + userdatelong +
				'}';
	}
}
