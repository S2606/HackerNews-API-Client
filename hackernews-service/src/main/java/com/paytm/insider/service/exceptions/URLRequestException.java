package com.paytm.insider.service.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Exception for errors due to network connection
 */
public class URLRequestException extends Exception {
    private final Integer code;
    private final String errorMessage;

    public URLRequestException(final Integer code, final String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public Integer getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
