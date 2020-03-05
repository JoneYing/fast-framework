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


/**
 *@Title
 *@Description Service 切面通知
 *@author Yingjianghua
 *@date 11:06 2018/11/6
 *@param
 *@return
 */
@Aspect
@Component
public class ServiceAspect {
	
	// 日志输出者
	private final static Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

	// Service层切点
	@Pointcut("execution(* com..*.service..*.*(..)) && @annotation(com.joneying.common.annotation.CommonService)")
	public void serviceAspect(){}

	/**
	 * 
	 * @Description 环绕通知，对带有CommonService注解的接口有效
	 * @author JHYing
	 * @time 2017年5月26日 上午11:40:48
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("serviceAspect()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		// 获取接口名称
		String requestName = CommonAnnotationUtil.getServiceMthodDescription(pjp);
		logger.debug("接口名称：{}", requestName);

		// 获取接口方法名
		String methodName = CommonAnnotationUtil.getMethodNameFromJoinPoint(pjp);
		logger.debug("接口方法：{}", methodName);

		// 获取接口方法参数列表
		Object[] args = CommonAnnotationUtil.getArgsFromJoinPoint(pjp);
		logger.debug("接口参数：{}", JSONObject.toJSONString(args));

		// 执行被拦截的处理请求的方法，result的值就是被拦截方法的返回值
		Object result = pjp.proceed();

		// 打印接口返回结果
		logger.debug("接口返回结果：{}", JSONObject.toJSONString(result));

		logger.debug("接口处理完成");
		return result;
	}

	/**
	 * 
	 * @Description 异常通知 用于拦截service层记录异常日志
	 * @author JHYing
	 * @time 2017年5月26日 上午11:41:38
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		
		/* ==========记录本地异常日志========== */
		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}",
				joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(),
				e.getMessage(), joinPoint.getArgs());

	}
}
