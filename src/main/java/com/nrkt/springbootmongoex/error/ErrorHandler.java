package com.nrkt.springbootmongoex.error;

import com.nrkt.springbootmongoex.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    void handleMultipartException(IllegalArgumentException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        loggingError(BAD_REQUEST, ex.getMessage(), request);
        response.sendError(BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    void handleMultipartException(MethodArgumentNotValidException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        loggingError(BAD_REQUEST, ex.getMessage(), request);
        response.sendError(BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({
            BadRequestException.class,
    })
    void handleCustomBadRequestException(BadRequestException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        loggingError(BAD_REQUEST, ex.getMessage(), request);
        response.sendError(BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    void handleMultipartException(MultipartException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        loggingError(BAD_REQUEST, ex.getMessage(), request);
        response.sendError(BAD_REQUEST.value(), "Please select a file");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    void handleConstraintViolationException(ConstraintViolationException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        loggingError(BAD_REQUEST, ex.getMessage(), request);
        response.sendError(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    void handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        loggingError(BAD_REQUEST, ex.getMessage(), request);
        response.sendError(HttpStatus.BAD_REQUEST.value(),"Please enter one of compatible media types (Accepted Types: xml, json or hal)");
    }

    @ExceptionHandler(Exception.class)
    void handleConstraintViolationException(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        loggingError(INTERNAL_SERVER_ERROR, ex.getMessage(), request);
        response.sendError(INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    private void loggingError(HttpStatus httpStatus, String message, HttpServletRequest request) {
        var errorDetail = ApiError.builder()
                .message(message)
                .status(httpStatus.value())
                .timestamp(new Date())
                .error(httpStatus.getReasonPhrase())
                .path(request.getRequestURI().substring(request.getContextPath().length())).build();

        log.error(errorDetail.toString());
    }
}