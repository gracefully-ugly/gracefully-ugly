package com.gracefullyugly.common.exception;

import com.gracefullyugly.common.exception.custom.AuthenticationException;
import com.gracefullyugly.common.exception.custom.ExistException;
import com.gracefullyugly.common.exception.custom.ExpiredException;
import com.gracefullyugly.common.exception.custom.ForbiddenException;
import com.gracefullyugly.common.exception.custom.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<String> handleBadRequestException(RestClientResponseException e) {
        log.error(e.getMessage());
        log.error("RestClientResponseException ", e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage());
        log.error("AccessDeniedException ", e);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        log.error("NotFoundException ", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        log.error("AuthenticationException ", e);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(ExistException.class)
    public ResponseEntity<String> handleExistException(ExistException e) {
        log.error(e.getMessage());
        log.error("ExistException ", e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException e) {
        log.error(e.getMessage());
        log.error("ForbiddenException ", e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(ExpiredException.class)
    public ResponseEntity<String> handleExpiredException(ExpiredException e) {
        log.error(e.getMessage());
        log.error("ExpiredException ", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    // 임시 핸들러
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
//        log.error(e.getMessage());
//        log.error("IllegalArgumentException ", e);
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(e.getMessage());
//    }
}
