package com.joneying.common.annotation;

import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

/**
 *@Title 
 *@Description 权限注解获取工具类
 *@author Yingjianghua
 *@date 17:04 2018/11/6
 *@param 
 *@return        
 */
public class ExculdeAnnotationUtil {

	/**
	 * 是否有ExculdeLogin注解
	 *
	 * @param handler
	 * @return
	 */
	public static boolean hasExculdeLoginAnnotation(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 如果方法上的注解不为空 则表示该方法不需登录校验
			if (!ObjectUtils.isEmpty(handlerMethod.getMethod().getAnnotation(ExculdeLogin.class))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否有ExculdeSecurity注解
	 *
	 * @param handler
	 * @return
	 */
	public static boolean hasExculdeSecurityAnnotation(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 如果方法上的注解不为空 则表示该方法不需登录校验
			if (!ObjectUtils.isEmpty(handlerMethod.getMethod().getAnnotation(ExculdeSecurity.class))) {
				return true;
			}
		}
		return false;
	}

}
