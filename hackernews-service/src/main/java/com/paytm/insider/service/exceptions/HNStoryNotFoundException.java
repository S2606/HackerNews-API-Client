package com.paytm.insider.service.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Exception when story was not found
 */
public class HNStoryNotFoundException extends Exception {
    private final String errorMessage;

    public HNStoryNotFoundException(final String errorMessage) {
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

    public String getErrorMessage() {
        return errorMessage;
    }
}
