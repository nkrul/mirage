package com.kncept.mirage.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.kncept.mirage.util.enumeration.SimpleEnum;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationWithEnum {
	
	SimpleEnum enumValue();
	SimpleEnum defaultValue() default SimpleEnum.SECOND;
	
}
