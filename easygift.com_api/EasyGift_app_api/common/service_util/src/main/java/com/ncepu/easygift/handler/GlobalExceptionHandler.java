package com.ncepu.easygift.handler;

import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.result.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception ex) {
        return R.error().message(ex.getMessage());
    }


    @ExceptionHandler(value = SQLException.class)
    public R handleSqlException(SQLException ex) {
        return R.error().message(ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public R handleRuntimeException(RuntimeException ex) {
        return R.error().message("运行期异常");
    }

    @ExceptionHandler(value = XzException.class)
    public R handleXzException(XzException ex) {
        return R.error().code(ex.getCode()).message(ex.getMessage());
    }
}
