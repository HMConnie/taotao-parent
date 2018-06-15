package com.taotao.advice;

import com.taotao.common.exception.CustomException;
import com.taotao.common.pojo.TaotaoResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public TaotaoResult handleException(CustomException e) {
        return TaotaoResult.build(HttpStatus.FORBIDDEN.value(), e.getTips());
    }
}
