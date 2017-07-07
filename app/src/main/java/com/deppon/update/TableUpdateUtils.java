package com.deppon.update;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TableUpdateUtils {
	public static Map<String,String> map=new HashMap<String,String>();

	public static Map<String,String> getAllFields(Class table) {
			Field[] field = table.getDeclaredFields();
			for (int i = 0; i < field.length; i++) {
				String fileType=field[i].getType().toString();
				String type=fileType.substring(fileType.lastIndexOf(".")+1);
				
				String fileName=field[i].getName();
				if(fileName.startsWith("_")){
				map.put(fileName, type);
				}
			}
		return map;

	}

}
