package com.nrkt.springbootmongoex.error;

import com.nrkt.springbootmongoex.exception.BadRequestException;
import com.nrkt.springbootmongoex.exception.GridFSCanNotEmptyException;
import com.nrkt.springbootmongoex.exception.GridFSNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return new ResponseEntity<>(errorDetails(ex.getMessage(), BAD_REQUEST, request), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleMultipartException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return new ResponseEntity<>(errorDetails(ex.getMessage(), BAD_REQUEST, request), BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    ResponseEntity<ApiError> handleMultipartException(MultipartException ex, HttpServletRequest request) {
        return new ResponseEntity<>(errorDetails("Please Select a File", BAD_REQUEST, request), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(errorDetails(ex.getMessage(), BAD_REQUEST, request), BAD_REQUEST);
    }

    @ExceptionHandler({
            BadRequestException.class,
            GridFSNotFoundException.class,
            GridFSCanNotEmptyException.class
    })
    ResponseEntity<ApiError> handleCustomBadRequestException(BadRequestException ex, HttpServletRequest request) {
        return new ResponseEntity<>(errorDetails(ex.getMessage(), BAD_REQUEST, request), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(errorDetails(ex.getMessage(), INTERNAL_SERVER_ERROR, request), INTERNAL_SERVER_ERROR);
    }

    private ApiError errorDetails(String message, HttpStatus httpStatus, HttpServletRequest request) {
        var errorDetail = ApiError.builder()
                .message(message)
                .status(httpStatus.value())
                .timestamp(new Date())
                .error(httpStatus.getReasonPhrase())
                .path(request.getRequestURI().substring(request.getContextPath().length())).build();

        log.error(errorDetail.toString());
        return errorDetail;
    }
}