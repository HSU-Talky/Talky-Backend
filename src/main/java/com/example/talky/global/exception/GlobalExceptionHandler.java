package com.example.talky.global.exception;

import com.example.talky.global.response.ErrorResponse;
import com.example.talky.global.response.code.ErrorResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : {}", e.getMessage(), e);
        String message = Optional.ofNullable(e.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("유효하지 않은 요청입니다.");
        ErrorResponse<?> errorResponse = ErrorResponse.of(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY, message);
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse<?>> handleBindException(BindException e) {
        log.error("BindException : {}", e.getMessage(), e);
        String message = Optional.ofNullable(e.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("유효하지 않은 요청입니다.");
        ErrorResponse<?> errorResponse = ErrorResponse.of(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY, message);
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse<?>> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        log.error("MissingServletRequestPartException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.UNSUPPORTED_HTTP_METHOD));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.NOT_FOUND_ENDPOINT));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse<?>> handleBaseException(BaseException e) {
        log.error("BaseException : {}", e.getBaseResponseCode().getMessage(), e);
        return buildResponse(ErrorResponse.from(e.getBaseResponseCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<?>> handleException(Exception e) {
        log.error("Unhandled Exception : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.SERVER_ERROR));
    }

    // 공통 응답 빌더
    private ResponseEntity<ErrorResponse<?>> buildResponse(ErrorResponse<?> errorResponse) {
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }
}
