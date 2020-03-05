package com.joneying.common.web.response;

import com.joneying.common.web.utils.PropertiesUtil;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@Title
 *@Description 请求结果封装模型
 *@author Yingjianghua
 *@date 16:36 2018/11/14
 *@param
 *@return
 */
public class Resp<T>  implements Serializable{

	private static final long serialVersionUID = 2169140102785610954L;

	private static final Pattern pattern = Pattern.compile("-?[0-9]+(\\\\.[0-9]+)?");

	//响应码(200:成功)
	private String code;

	// 信息内容
	private String message;

	// 数据对象
	private T data;

	public Resp(){}

	public Resp(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Resp(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * 
	 * @Description Resp
	 *
	 * @author YingJH
	 * @Date 2018年6月6日
	 * @return
	 */
	public Resp<T> success() {
		this.code = MessageConstant.SUCCESS;
		Locale locale = LocaleContextHolder.getLocale();
		this.message = PropertiesUtil.getMessage(locale.toLanguageTag(), MessageConstant.SUCCESS);
		return this;
	}


	/**
	 * 
	 * @Description Resp
	 *
	 * @author YingJH
	 * @Date 2018年6月6日
	 * @param data
	 * @return
	 */
	public Resp<T> success(T data) {
		success();
		this.data = data;
		return this;
	}
	
	/**
	 * 
	 * @Description Resp
	 *
	 * @author YingJH
	 * @Date 2018年6月6日
	 * @return
	 */
	public Resp<T> failure() {
		this.code = MessageConstant.VALIDATE_PARAMETER_ERROR;
		Locale locale = LocaleContextHolder.getLocale();
		this.message = PropertiesUtil.getMessage(locale.toLanguageTag(),MessageConstant.VALIDATE_PARAMETER_ERROR);
		return this;
	}
	
	/**
	 * 
	 * @Description Resp
	 *
	 * @author YingJH
	 * @Date 2018年6月6日
	 * @param param
	 * @return
	 */

	public Resp<T> failure(String param) {
		Matcher isNum = pattern.matcher(param);
		if (isNum.matches()) {
			this.code = param;
			Locale locale = LocaleContextHolder.getLocale();
			this.message = PropertiesUtil.getMessage(locale.toLanguageTag(),param);
		}else{
			this.code = MessageConstant.VALIDATE_PARAMETER_ERROR;
			this.message = param;
		}
		return this;
	}
	
	/**
	 * 
	 * @Description Resp
	 *
	 * @author YingJH
	 * @Date 2018年6月6日
	 * @param code,data
	 * @return
	 */
	public Resp<T> failure(String code, T data) {
		failure(code);
		this.data = data;
		return this;
	}
	
	public Resp<T> failure(String code, String message) {
		this.code = code;
		this.message = message;
		return this;
	}
	
	public Resp<T> error() {
		this.code = MessageConstant.SERVER_ERROR;
		Locale locale = LocaleContextHolder.getLocale();
		this.message = PropertiesUtil.getMessage(locale.toLanguageTag(), MessageConstant.SERVER_ERROR);
		return this;
	}
	
	public Resp<T> error(String code) {
		this.code = code;
		Locale locale = LocaleContextHolder.getLocale();
		this.message = PropertiesUtil.getMessage(locale.toLanguageTag(), code);
		return this;
	}

	public Resp<T> error(String code, String message) {
		this.code = code;
		this.message = message;
		return this;
	}

	public Resp<T> error(String code, T data) {
		error(code);
		this.data = data;
		return this;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Resp [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
}
