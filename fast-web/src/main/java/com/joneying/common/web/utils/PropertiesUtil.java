package com.joneying.common.web.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *@Title
 *@Description PropertiesUtil
 *@author Yingjianghua
 *@date 13:39 2018/12/27
 *@param
 *@return
 */
public class PropertiesUtil {
	
	// 日志输出者
	protected final static Logger logger = LogManager.getLogger();

	private static Map<String, Object> map = new HashMap<String, Object>();

	/*
	 * 获取静态的配置文件
	 */
	public static String getPropertiesValue(String propFileName, String language, String propertiesKey) {
		try {
			Properties properties = new Properties();
			if (null == map.get(language)) {
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				InputStreamReader config = new InputStreamReader(cl.getResourceAsStream(propFileName+"_"+language+".properties"), "UTF-8");
				properties.load(config);
				config.close();
				map.put(language, properties);
				return properties.getProperty(propertiesKey);
			} else {
				properties = (Properties) map.get(language);
				return properties.getProperty(propertiesKey);
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return null;
	}

	public static String getMessage(String propFileName, String language, String propertiesKey) {return getPropertiesValue(propFileName, language, propertiesKey);}

	public static String getMessage(String language, String propertiesKey) {return getPropertiesValue("i18n/messages", language, propertiesKey);}


}
