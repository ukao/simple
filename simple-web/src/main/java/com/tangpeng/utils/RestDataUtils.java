package com.tangpeng.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.tangpeng.bean.JsonField;
import com.tangpeng.sdk.New;


/**
 * @author tangpeng
 */
public class RestDataUtils {

	/**
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String getDeclaredFields( Object o ) throws Exception{
		 StringBuffer sb = new StringBuffer();
		 List<Field> allField= New.arrayList();
		 Field[] myfields=o.getClass().getDeclaredFields();
		 Field[] superfields=o.getClass().getSuperclass().getDeclaredFields();
		 allField.addAll(Arrays.asList(myfields));
		 allField.addAll(Arrays.asList(superfields));
		 for(int i=0;i<allField.size();i++){
			 Field field = allField.get(i);
			 String fieldName =field.getName();
			 JsonField fieldAnnotation = field.getAnnotation(JsonField.class);
			 if( fieldAnnotation != null ){
				 String jsonField = fieldName;
				 if( !"".equals(fieldAnnotation.field()) )
				 {
					 jsonField = fieldAnnotation.field();
				 }
				 Method method = o.getClass().getMethod(
						 "get" + fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase()) );
				 sb.append(",'" + jsonField + "':'" + RestDataUtils.replaceStr(method.invoke(o),"'") + "'");
			 }
		 }
		 return sb.toString().replaceFirst(",",""); 
	}
	
	/**
	 * @param str
	 * @param replaceChar
	 * @return
	 */
	public static String replaceStr(Object str,String replaceChar)
	{
		if( str!= null)
		{
			return str.toString().replaceAll("'", "\\\'");
		}
		else
		{
			return "";
		}
	}
	
}
