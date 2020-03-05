package com.joneying.common.web.response;


/**
 *@Title
 *@Description 消息code常量表
 *@author Yingjianghua
 *@date 17:01 2018/11/14
 *@param
 *@return
 */
public interface MessageConstant {
	/**
	 * 成功
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
	 * 服务器有误
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
	 * 获取用户信息错误
	 */
	String USER_INFO_ERROR = "508";
	
	/**
	 * 用户未登录或登录过期
	 */
	String SESSION_EXPIRE = "600";

	/**
	 * 用户没有权限
	 */
	String AUTHENTICATION_EXPIRE = "700";

	/**
	 * 用户没有分配角色
	 */
	String ROLE_EXPIRE = "800";

	String SHOW_ERROR_INFO = "9999";

}
