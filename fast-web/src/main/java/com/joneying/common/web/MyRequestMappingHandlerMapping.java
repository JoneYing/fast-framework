package com.joneying.common.web;

import com.alibaba.fastjson.JSON;
import com.joneying.common.web.annotation.GetMapping;
import com.joneying.common.web.annotation.PostMapping;
import com.joneying.common.web.annotation.VersionPath;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yingjianghua
 * @version v1.0
 * @ClassName MyRequestMappingHandlerMapping
 * @Description 自定义注解解析器
 * @date 2018/11/14 14:41
 */
public class MyRequestMappingHandlerMapping extends RequestMappingHandlerMapping {


    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {

        //判断是否为自定义注解,不是则不做自定义操作
        if (ObjectUtils.isEmpty(AnnotatedElementUtils.findMergedAnnotation(method, GetMapping.class))&&
                ObjectUtils.isEmpty(AnnotatedElementUtils.findMergedAnnotation(method, PostMapping.class))) {
            return super.getMappingForMethod(method, handlerType);
        }
        RequestMappingInfo info, typeInfo;
        StringBuilder tmpString;

        String className = method.getDeclaringClass().getSimpleName().replace("Controller", "");

        //判断是否包含大小写字符，并将大写字符转化成小写，插入斜杠"/"
        tmpString = new StringBuilder();
        for (char c : className.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tmpString.append("/");
            }
            tmpString.append(Character.toLowerCase(c));
        }

        className = tmpString.toString();

        //判断是否将版本号拼接在path上
        VersionPath annotation = method.getDeclaringClass().getAnnotation(VersionPath.class);
        if(!ObjectUtils.isEmpty(annotation) && !StringUtils.isEmpty(annotation.name())){
            className = "/" + annotation.name() + className;
        }

        info = createRequestMappingInfo(method, className, method.getName(), method.getParameters());
        if (info != null) {
            typeInfo = createRequestMappingInfo(handlerType, className, method.getName(), method.getParameters());
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
        }
        return info;
    }

    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element, String controllerName, String methodName, Parameter[] parameters) {
        String tmpStr;
        PathVariable pathVariable;

        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = (element instanceof Class<?> ?
                super.getCustomTypeCondition((Class<?>) element) : super.getCustomMethodCondition((Method) element));

        if (requestMapping == null) {return null;}

        String[] values = requestMapping.value();
        // 参数拼装
        String variableStr = "";
        if (null != parameters && parameters.length > 0) {
            for (Parameter parameter : parameters) {
                pathVariable = parameter.getAnnotation(PathVariable.class);
                if (null != pathVariable && !StringUtils.isEmpty(tmpStr = pathVariable.value().trim())) {
                    variableStr += "/{" + tmpStr + "}";
                }
            }
        }

        // path value 拼装
        List<String> patternList = new ArrayList<>();
        if (null != values && values.length > 0) {
            for (String value : values) {
                patternList.add(controllerName + value + variableStr);
            }
        } else {
            patternList.add(controllerName + "/" + methodName + variableStr);
        }

        RequestMethod[] methods = requestMapping.method();
        if (null == methods || methods.length <= 0) {
            methods = new RequestMethod[]{RequestMethod.GET};
        }

        logger.info("request mapping: " + Arrays.toString(patternList.toArray(new String[]{})) + "-------" + JSON.toJSON(methods));

        return new RequestMappingInfo(
                new PatternsRequestCondition(patternList.toArray(new String[]{}), getUrlPathHelper(), getPathMatcher(), true, true, new ArrayList<String>()),
                new RequestMethodsRequestCondition(methods),
                new ParamsRequestCondition(requestMapping.params()),
                new HeadersRequestCondition(requestMapping.headers()),
                new ConsumesRequestCondition(requestMapping.consumes(), requestMapping.headers()),
                new ProducesRequestCondition(requestMapping.produces(), requestMapping.headers(), new ContentNegotiationManager()),
                condition);
    }

}
