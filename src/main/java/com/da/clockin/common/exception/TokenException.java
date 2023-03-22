//开发时间 : 2023/2/1 19:15

package com.da.clockin.common.exception;

import com.auth0.jwt.exceptions.SignatureVerificationException;

import com.da.clockin.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;

import java.util.List;
import java.util.StringJoiner;

@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
@ResponseBody
public class TokenException {

    @ExceptionHandler(SignatureVerificationException.class)
    public Result<String> jwtException(SignatureVerificationException s) {
        return Result.error("请重新登录!");
    }


    @ExceptionHandler(CurrencyException.class)
    public Result<String> loginEx(CurrencyException e){
        if (e.getMessage().equals("登录异常") || e.getMessage().equals("登录过期")){
            return Result.error(e.getMessage(),401);
        }
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public Result<String> uploadExcepttion(MultipartException e){
        return Result.error("大小超出限制");
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> MANVE(MethodArgumentNotValidException e){
        //提示：多个错误
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringJoiner joiner = new StringJoiner("; ");
        String defaultMessage = null;
        for(FieldError fieldError : fieldErrors){
            joiner.add(fieldError.getDefaultMessage());
            defaultMessage = fieldError.getDefaultMessage();
        }
        return Result.error(defaultMessage);
    }
//    @ExceptionHandler(Throwable.class)
    public Result<String> handlerException(Throwable e) {
        return Result.error("系统错误");
    }
}
