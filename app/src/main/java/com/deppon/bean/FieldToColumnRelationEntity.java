package com.deppon.bean;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/** 
 * @ClassName: FieldToColumnRelationEntity 
 * @Description: TODO(记录实体类中属性与表中字段对应关系实体类) 
 * @author fenglei015@deppon.com/273219 
 * @date 2015-12-28 上午10:55:07 
 *  
 */ 
@DatabaseTable(tableName="tb_fcr")
public class FieldToColumnRelationEntity {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String fieldName;
	@DatabaseField
	private String columnName;
	@DatabaseField
	private String tableName;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


}
