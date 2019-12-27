package com.sdcuike.springboot.config;

import com.sdcuike.springboot.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

/**
 * TODO: 其他mvc异常添加
 *
 * @author sdcuike
 * @DATE 2019-12-27
 */
@ControllerAdvice
@RestController
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({Throwable.class})
    public final ResponseEntity<Object> handleException(Throwable ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Throwable ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        doLogException(ex, body, headers, status, request);
        return new ResponseEntity<>(new Response<>(status.value(), ex.getMessage(), body), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        doLogException(ex, body, headers, status, request);
        return new ResponseEntity<>(new Response<>(status.value(), ex.getMessage(), body), headers, status);
    }


    private void doLogException(Throwable ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (status.is5xxServerError()) {
            log.error("WebRequest:{},headers:{}, status:{},body:{}", request, headers, status, body, ex);
        } else {
            log.warn("WebRequest:{},headers:{}, status:{},body:{}", request, headers, status, body, ex);
        }
    }
}
