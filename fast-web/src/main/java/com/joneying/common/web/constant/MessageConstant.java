package com.joneying.common.web.constant;

public interface MessageConstant {
	/**
	 * 获取成功
	 */
	String SUCCESS = "200";

	/**
	 * 参数不正确
	 */
	String FAILURE = "500";

	/**
	 * 失败
	 */
	String VALIDATE_PARAMETER_ERROR = "501";

	/**
	 * 502,服务器有误,程序员小哥开小差
	 */
	String SERVER_ERROR = "502";
	
	/**
	 * 验证码发送成功
	 */
	String CODE_SEND_SCUESS = "503";
	
	/**
	 * 验证码发送失败
	 */
	String CODE_SEND_ERROR = "505";

	/**
	 * 验证码错误
	 */
	String CHECK_ERROR = "507";
	
	/**
	 * 用户未登录&Session过期
	 */
	String SESSION_EXPIRE = "600";

	/**
	 * 用户没有权限
	 */
	String AUTHENTICATION_EXPIRE = "700";

	/**
	 * 用户没有角色
	 */
	String ROLE_EXPIRE = "800";

	String SHOW_ERROR_INFO = "9999";

}
