package com.joneying.common.annotation;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface ExculdeSecurity {
	String description()  default "";    
}
