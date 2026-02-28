package com.airtribe.task_master.common.response;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (body instanceof ApiResponse) {
            return body; // avoid double wrapping
        }

        if (body instanceof ApiErrorResponse) {
            return body; // avoid double wrapping
        }

        ApiSuccess apiSuccess = returnType.getMethodAnnotation(ApiSuccess.class);

        HttpStatus status = HttpStatus.OK;
        String message = "Success";

        if (apiSuccess != null) {
            status = apiSuccess.status();
            message = apiSuccess.message();
        }

        if (response instanceof ServletServerHttpResponse servletResponse) {
            servletResponse.getServletResponse().setStatus(status.value());
        }

        return ApiResponse.builder()
                          .status(status.value())
                          .message(message)
                          .path(request.getURI().getPath())
                          .timestamp(LocalDateTime.now())
                          .data(body)
                          .build();
    }
}