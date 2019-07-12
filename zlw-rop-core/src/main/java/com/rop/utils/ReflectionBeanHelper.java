package com.rop.utils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


/**
 * 实现给实体对象赋值以及获取实体对象值到Map中
 * @author: Guan.yuxuan.CD
 *
 * @date 2014年6月19日 下午5:13:45
 */
public abstract class ReflectionBeanHelper {
	
	private static Logger logger = LoggerFactory.getLogger(ReflectionBeanHelper.class);
	
	private static final String _STRING = "String";
	private static final String _DATE = "Date";
	private static final String _INTEGER = "Integer";
	private static final String _INT = "int";
	private static final String _LONG = "Long";
	private static final String _long = "long";
	private static final String _DOUBLE = "Double";
	private static final String _double = "double";
	private static final String _BOOLEAN = "Boolean";
	private static final String _boolean = "boolean";

	private static final String PRE_SET = "set";
	private static final String PRE_GET = "get";
	
	/** 
	 * 方法名称:transStringToMap 
	 * 传入参数:mapString 形如 {"username":"chenziwen" , "password":"1234"} 
	 * 返回值:Map 
	*/  
	private static Map<String,String> transStringToMap(String mapString){  
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();  
		Map<String, String> retMap = gson.fromJson(mapString,  new TypeToken<Map<String, String>>() {}.getType());
	  return retMap;  
	}
	
	/**
	 * 取Bean的属性和值对应关系的MAP
	 * 
	 * @param bean
	 * @return Map
	 */
	public static Map<String , String> getFieldValueMap (Object bean) {
		Class<?> cls = bean.getClass( );
		Map<String , String> valueMap = new HashMap<String , String>( );
		Method [] methods = cls.getDeclaredMethods( );
		Field [] fields = cls.getDeclaredFields( );
		for (Field field : fields) {
			try {
				String fieldType = field.getType( ).getSimpleName( );
				String fieldGetName = parGetName(field.getName( ));
				if ( !checkGetMet(methods , fieldGetName) ) {
					continue;
				}
				Method fieldGetMet = cls.getMethod(fieldGetName , new Class [] {});
				Object fieldVal = fieldGetMet.invoke(bean , new Object [] {});
				String result = null;
				if ( _DATE.equals(fieldType) ) {
					result = fmtDate((Date) fieldVal);
				} else {
					if ( null != fieldVal ) {
						result = String.valueOf(fieldVal);
					}
				}
				valueMap.put(field.getName( ) , result);
			} catch (Exception e) {
				continue;
			}
		}
		return valueMap;
	}

	public static void setFieldValue (Object bean , Map<String , String> valMap) {
		Class<?> cls = bean.getClass( );
		Method [] methods = cls.getDeclaredMethods( );
		Field [] fields = cls.getDeclaredFields( );
		for (Field field : fields) {
			try {
				String fieldSetName = parSetName(field.getName( ));
				if ( !checkSetMet(methods , fieldSetName) ) {
					continue;
				}
				Method fieldSetMet = cls.getMethod(fieldSetName , field.getType( ));
				String value = valMap.get(field.getName( ));
				if ( null != value && !"".equals(value) ) {
					String fieldType = field.getType( ).getSimpleName( );
					if ( _STRING.equals(fieldType) ) {
						fieldSetMet.invoke(bean , value);
					} else if ( _DATE.equals(fieldType) ) {
						Date temp = parseDate(value);
						fieldSetMet.invoke(bean , temp);
					} else if ( _INTEGER.equals(fieldType) || _INT.equals(fieldType) ) {
						Integer intval = Integer.parseInt(value);
//						Integer intval = StringUtils.hexStringToInteger(value);
						fieldSetMet.invoke(bean , intval);
					} else if ( _LONG.equalsIgnoreCase(fieldType) ||  _long.equalsIgnoreCase(fieldType)) {
						Long temp = Long.parseLong(value);
						fieldSetMet.invoke(bean , temp);
					} else if ( _DOUBLE.equalsIgnoreCase(fieldType) || _double.equalsIgnoreCase(fieldType) ) {
						Double temp = Double.parseDouble(value);
						fieldSetMet.invoke(bean , temp);
					} else if ( _BOOLEAN.equalsIgnoreCase(fieldType) || _boolean.equalsIgnoreCase(fieldType) ) {
						Boolean temp = Boolean.parseBoolean(value);
						fieldSetMet.invoke(bean , temp);
					} else {
						logger.warn("not supper type" + fieldType);
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
	}

	public static void setFieldValue (Object bean , String jsonString) {
		Map<String , String> valMap = transStringToMap(jsonString);
		setFieldValue(bean, valMap);
	}
	/**
	 * 拼接某属性的 get方法
	 * 
	 * @param fieldName
	 * @return String
	 */
	private static String parGetName (String fieldName) {
		if ( null == fieldName || "".equals(fieldName) ) {
			return null;
		}
		return PRE_GET + fieldName.substring(0 , 1).toUpperCase( ) + fieldName.substring(1);
	}

	private static String parSetName (String fieldName) {
		if ( null == fieldName || "".equals(fieldName) ) {
			return null;
		}
		return PRE_SET + fieldName.substring(0 , 1).toUpperCase( ) + fieldName.substring(1);
	}

	/**
	 * 日期转化为String
	 * 
	 * @param date
	 * @return date string
	 */
	private static String fmtDate (Date date) {
		if ( null == date ) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.US);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	private static Date parseDate (String datestr) {
		if ( null == datestr || "".equals(datestr) ) {
			return null;
		}
		try {
			String fmtstr = null;
			if ( datestr.indexOf(':') > 0 ) {
				fmtstr = "yyyy-MM-dd HH:mm:ss";
			} else {
				fmtstr = "yyyy-MM-dd";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(fmtstr , Locale.UK);
			return sdf.parse(datestr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断是否存在某属性的 get方法
	 * 
	 * @param methods
	 * @param fieldGetMet
	 * @return boolean
	 */
	private static boolean checkGetMet (Method [] methods , String fieldGetMet) {
		for (Method met : methods) {
			if ( fieldGetMet.equals(met.getName( )) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否存在某属性的 set方法
	 * 
	 * @param methods
	 * @param fieldSetMet
	 * @return boolean
	 */
	private static boolean checkSetMet (Method [] methods , String fieldSetMet) {
		for (Method met : methods) {
			if ( fieldSetMet.equals(met.getName( )) ) {
				return true;
			}
		}
		return false;
	}
}
