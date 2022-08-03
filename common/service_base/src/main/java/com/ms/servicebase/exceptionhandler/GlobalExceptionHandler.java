package com.ms.servicebase.exceptionhandler;

import com.ms.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author MS
 * @create 2022-07-14-20:16
 */
@Slf4j
@ControllerAdvice // 表明为异常处理类
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R resolveException(Exception ex){
        ex.printStackTrace();
        return R.error().message("执行了全局异常处理...");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R resolveException(ArithmeticException ex){
        ex.printStackTrace();
        return R.error().message("执行了数学运算异常处理...");
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R resolveException(EduException ex){
        log.error(ex.getMsg());
        ex.printStackTrace();
        return R.error().code(ex.getCode()).message(ex.getMsg());
    }
}
