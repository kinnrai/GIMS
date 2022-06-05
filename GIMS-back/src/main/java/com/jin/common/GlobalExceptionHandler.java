package com.jin.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 金磊
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public Result handler(SQLIntegrityConstraintViolationException e){
        log.error("SQLIntegrityConstraintViolationException:{}",e.getMessage());
        return Result.error("由于外键约束,无法删除");
    }

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    //@ExceptionHandler(value = RuntimeException.class)
    //public Result handler(RuntimeException e) {
    //    log.error("RuntimeException:{}",e.getMessage());
    //    return Result.error(e.getMessage());
    //}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("IllegalArgumentException:{}",e.getMessage());
        return Result.error(e.getMessage());
    }



}
