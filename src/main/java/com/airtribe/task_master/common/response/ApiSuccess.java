package com.airtribe.task_master.common.response;

import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiSuccess {

    HttpStatus status() default HttpStatus.OK;

    String message() default "Success";
}