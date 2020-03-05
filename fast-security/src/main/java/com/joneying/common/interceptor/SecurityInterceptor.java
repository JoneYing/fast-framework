package com.joneying.common.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.joneying.common.annotation.ExculdeAnnotationUtil;
import com.joneying.common.config.SecurityConfig;
import com.joneying.common.constant.SecurityConstant;
import com.joneying.common.redis.RedisManager;
import com.joneying.common.session.Session;
import com.joneying.common.web.response.MessageConstant;
import com.joneying.common.web.response.RespBulider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 *@Title
 *@Description 权限校验拦截器
 *@author Yingjianghua
 *@date 14:29 2018/11/7
 *@param
 *@return
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisManager redisManager;

	@Autowired
	private SecurityConfig securityConfig;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
		// 执行被拦截的处理请求的方法，result的值就是被拦截方法的返回值
		if(SecurityConstant.OPTIONS.equals(httpServletRequest.getMethod()) || ExculdeAnnotationUtil.hasExculdeLoginAnnotation(handler)) {
			return true;
		}

		String ticket = getTicket(httpServletRequest);
		boolean ticketisEmpty = StringUtils.isEmpty(ticket);

		//判断ticket是否为空,为空则重定向到登录页面
		if(ticketisEmpty){
			String gotoUrl = httpServletRequest.getParameter(SecurityConstant.GOTO_URL);
			if(StringUtils.isEmpty(gotoUrl)){
                gotoUrl = securityConfig.getDefaultGotoUrl();
			}
			//gotoUrl地址转码
			gotoUrl = URLEncoder.encode(gotoUrl, SecurityConstant.ENCODE_UTF8);
			StringBuilder sb = new StringBuilder(securityConfig.getLoginPage());
			sb.append("?action=toLogin&gotoUrl=");
			sb.append(gotoUrl);
			httpServletResponse.sendRedirect(sb.toString());
			return false;
		}

		//获取用户信息
		Session session = (Session) redisManager.get(ticket);
		//判断session是否为空
		if(ObjectUtils.isEmpty(session)){
			//判断是否为登录请求，不是则返回当前会话过期
			if(!securityConfig.getLoginRequest().contains(httpServletRequest.getRequestURI()
					.replaceFirst(httpServletRequest.getContextPath(), "").trim())){
				OutputStream out = httpServletResponse.getOutputStream();
				out.write(JSONObject.toJSONString(RespBulider.error(MessageConstant.SESSION_EXPIRE))
						.getBytes());
				return false;
			}
			//表示为登录请求
			return true;
		}

		//判断是否为标识ExculdeSecurity注解接口
		if(ExculdeAnnotationUtil.hasExculdeSecurityAnnotation(handler)){
			return true;
		}

		//判断是否为管理员
		if(session.getAdmin()){
			return true;
		}

		//获取请求地址并进行权限比对
		String requestURI = httpServletRequest.getRequestURI();
		List<String> auths = session.getAuths();
		for(String url : auths){
			if(!StringUtils.isEmpty(url) && url.indexOf(requestURI) > -1){
				return true;
			}
		}

		//返回结果
		OutputStream out = httpServletResponse.getOutputStream();
		out.write(JSONObject.toJSONString(RespBulider.error(MessageConstant.AUTHENTICATION_EXPIRE))
				.getBytes(SecurityConstant.ENCODE_UTF8));
		return false;
	}

	/**
	 *getTicket 获取令牌
	 */
	public String getTicket(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (securityConfig.getTicketName().equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

	}

}
