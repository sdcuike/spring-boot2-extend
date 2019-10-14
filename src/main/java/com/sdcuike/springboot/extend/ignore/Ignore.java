package com.sdcuike.springboot.extend.ignore;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sdcuike
 * @date 2019/10/13
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
public @interface Ignore {
}
