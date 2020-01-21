package com.report.adapter.api.error;

import com.report.common.exception.LogicException;
import com.report.common.exception.NotFoundException;
import com.report.common.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String internalErrorMessage = "Sorry, something went wrong (500 Internal Server Error).";

    public RestExceptionHandler() {
        super();
    }

    @ExceptionHandler(NotFoundException.class) // 404
    public ResponseEntity<Object> handleNotFound(final RuntimeException exception, final WebRequest request) {
        return handleException(exception, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LogicException.class) // 409
    public ResponseEntity<Object> handleConflict(final LogicException exception, final WebRequest request) {
        return handleException(exception, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class) // 422
    public ResponseEntity<Object> handleValidation(final ValidationException exception, final WebRequest request) {
        return handleException(exception, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ // 500
            NullPointerException.class,
            RuntimeException.class
    })
    public ResponseEntity<Object> handleInternals(final RuntimeException exception, final WebRequest request) {
        return handleException(exception, request, internalErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleException(
            RuntimeException exception,
            WebRequest request,
            HttpStatus status) {
        return handleException(
                exception,
                request,
                exception.getMessage(),
                status
        );
    }

    private ResponseEntity<Object> handleException(
            RuntimeException exception,
            WebRequest request,
            String message,
            HttpStatus status) {
        logThatExceptionIsHandled(status, exception);

        return handleExceptionInternal(
                exception,
                message,
                new HttpHeaders(),
                status,
                request
        );
    }

    private void logThatExceptionIsHandled(HttpStatus status, RuntimeException exception) {
        final int code = status.value();
        String message = String.format("Exception is handled. Status code: %d. ", code);

        if(status.is5xxServerError()) {
            logger.error(message, exception);
        } else if(status.is4xxClientError()) {
            message += exception.getMessage();
            logger.info(message);
        } else {
            logger.info(message, exception);
        }
    }
}
