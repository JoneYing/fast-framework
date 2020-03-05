package com.joneying.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.joneying.common.annotation.CommonAnnotationUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *@Title
 *@Description Controller 切面通知类
 *@author Yingjianghua
 *@date 10:54 2018/11/6
 *@param
 *@return
 */
@Aspect
@Component
public class ControllerAspect {

	// 日志输出者
	private final static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

	// Controller层切点
	@Pointcut("execution(* com..*.controller..*.*(..)) && @annotation(com.joneying.common.annotation.CommonController)")
	public void controllerAspect(){}

	/**
	 * @Description: 环绕方法，对controller包里的所有action有效
	 * @author yingjianghua
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerAspect()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();

		// 获取请求名称
		String requestName = CommonAnnotationUtil.getControllerMethodDescription(pjp);
		logger.debug("请求名称：{}", requestName);

		// 获取请求地址
		StringBuffer requestUrl = request.getRequestURL();
		logger.debug("请求地址：{}", requestUrl);

		// 获取请求参数
		Map<?, ?> inputParamMap = request.getParameterMap();

		logger.debug("请求参数：{}", JSONObject.toJSONString(inputParamMap));

		// 执行被拦截的处理请求的方法，result的值就是被拦截方法的返回值
		Object result = pjp.proceed();

		// 打印应答内容
		logger.debug("请求结果：{}", JSONObject.toJSONString(result));

		return result;
	}

	/**
	 * 异常通知 用于拦截controller层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		/* ==========记录本地异常日志========== */
		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}",
				joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(),
				e.getMessage(), joinPoint.getArgs());
		logger.error("error:", e);
	}
}
