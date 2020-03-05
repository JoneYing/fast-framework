package com.joneying.common.web.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @author Yingjianghua
 * @version v1.0
 * @ClassName GetMapping
 * @Description
 * @date
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VersionPath {

    String name() default "v1";

    String description()  default "";

}
