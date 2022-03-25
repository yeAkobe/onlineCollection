package com.zzx.servicebase.handler;

import com.zzx.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常会执行这个方法
    @ExceptionHandler(Exception.class)
    //因为他不在Controller中。没有@RestController，所以数据不会返回，需要加@ResponeseBody返回数据
    @ResponseBody
    public
    R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理。。。");
    }

    //处理特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了自定义异常");
    }

    //自定义异常处理，需要创建zzxException异常类，在代码中抛出zzxException异常来使用
    @ExceptionHandler(zzxException.class)
    @ResponseBody
    public R error(zzxException e){
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }


}

