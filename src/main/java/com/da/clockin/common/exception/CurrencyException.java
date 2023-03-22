//开发时间 : 2023/2/2 9:49

package com.da.clockin.common.exception;

public class CurrencyException extends RuntimeException{
    private String msg;

    public CurrencyException() {
        super();
    }

    public CurrencyException(String message) {
        super(message);
        this.msg = message;
    }



}
