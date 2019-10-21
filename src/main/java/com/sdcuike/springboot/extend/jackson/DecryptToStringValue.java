package com.sdcuike.springboot.extend.jackson;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sdcuike
 * @DATE 2019/10/17
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
//@JsonDeserialize(using = StringDecryptDeserializer.class)
@JacksonAnnotationsInside
public @interface DecryptToStringValue {
}
