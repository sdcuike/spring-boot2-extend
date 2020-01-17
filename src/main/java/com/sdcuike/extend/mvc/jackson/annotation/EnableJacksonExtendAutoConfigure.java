package com.sdcuike.extend.mvc.jackson.annotation;

import com.sdcuike.extend.mvc.jackson.CustomWebMvcConfigurer;
import com.sdcuike.extend.mvc.jackson.Jackson2Customizer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author sdcuke
 * @DATE 2020/1/17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({Jackson2Customizer.class, CustomWebMvcConfigurer.class})
public @interface EnableJacksonExtendAutoConfigure {
}
