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
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @param e @RequestBody로 들어온 객체가 검증되지 않았을 때
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : {}", e.getMessage(), e);
        String message = Optional.ofNullable(e.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("유효하지 않은 요청입니다.");
        ErrorResponse<?> errorResponse = ErrorResponse.of(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY, message);
        return buildResponse(errorResponse);
    }

    /**
     * @param e @RequestParam, @ModelAttribute로 들어온 객체가 검증되지 않았을 때
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse<?>> handleBindException(BindException e) {
        log.error("BindException : {}", e.getMessage(), e);
        String message = Optional.ofNullable(e.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("유효하지 않은 요청입니다.");
        ErrorResponse<?> errorResponse = ErrorResponse.of(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY, message);
        return buildResponse(errorResponse);
    }

    /**
     * @param e 들어온 요청과 DTO가 서로 호환되지 않아서 바인딩에 실패했을 때
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_BODY));
    }

    /**
     * @param e Http Method가 맞지 않을 때
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER));
    }

    /**
     * @param e RequestPart 누락
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse<?>> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        log.error("MissingServletRequestPartException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.INVALID_HTTP_MESSAGE_PARAMETER));
    }

    /**
     * @param e 지원되지 않는 Http Method를 호출했을 때
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.UNSUPPORTED_HTTP_METHOD));
    }

    /**
     * @param e 존재하지 않는 엔드포인트 호출
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException : {}", e.getMessage(), e);
        return buildResponse(ErrorResponse.from(ErrorResponseCode.NOT_FOUND_ENDPOINT));
    }

    /**
     * @param e 존재하지 않는 엔드포인트를 호출하여, Static Resources까지 탐색했지만 실패한 경우
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleNoResourceFoundException(NoResourceFoundException e) {
        log.error("NoResourceFoundException : {}", e.getMessage(), e);
        ErrorResponse<?> errorResponse = ErrorResponse.from(ErrorResponseCode.NOT_FOUND_ENDPOINT);
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
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
