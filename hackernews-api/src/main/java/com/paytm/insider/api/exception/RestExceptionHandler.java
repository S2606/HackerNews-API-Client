package com.paytm.insider.api.exception;

import com.paytm.insider.api.model.ErrorResponse;
import com.paytm.insider.service.exceptions.HNCommentFromStoryIDNotGivenException;
import com.paytm.insider.service.exceptions.HNCommentNotFoundException;
import com.paytm.insider.service.exceptions.HNStoryNotFoundException;
import com.paytm.insider.service.exceptions.HNUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/***
 * Single class for exception handling
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HNStoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> storyNotFoundException(HNStoryNotFoundException exe) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().message(exe.getErrorMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(HNUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(HNStoryNotFoundException exe) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().message(exe.getErrorMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(HNCommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> commentNotFoundException(HNStoryNotFoundException exe) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().message(exe.getErrorMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(HNCommentFromStoryIDNotGivenException.class)
    public ResponseEntity<ErrorResponse> CommentFromStoryIDNotGiven(HNCommentFromStoryIDNotGivenException exe) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().message(exe.getErrorMessage()), HttpStatus.BAD_REQUEST
        );
    }

}
