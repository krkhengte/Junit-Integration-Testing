package com.junit.test.exception;

public class ResourseNotFoundException extends RuntimeException{

    public ResourseNotFoundException(String msg){

        super(msg);
    }

    ResourseNotFoundException(String msg,Throwable cause){

        super(msg,cause);

    }

}

