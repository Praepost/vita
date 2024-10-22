package org.service.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.service.web.exception.dto.ErrorResponseBuilder;
import org.service.web.task.exception.DraftException;
import org.service.web.user.exception.UserRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private ErrorResponseBuilder errorResponseBuilder;

    @ExceptionHandler(UserRegisterException.class)
    public ResponseEntity<ErrorResponse> userRegistrationException(RuntimeException ex, WebRequest request) {
        log.error("ошибка регистрации пользователя", ex);
        ErrorResponse errorResponse = errorResponseBuilder.builder()
                .setErrorCode(CommonErrorCodes.OPERATION_PERFORM_ERROR)
                .setDescription("пользователь уже существует: " + ex.getMessage())
                .setMessage("ошибка регистрации пользователя")
                .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DraftException.class)
    public ResponseEntity<ErrorResponse> draftException(RuntimeException ex, WebRequest request) {
        log.error("некорректный статус заявки", ex);
        ErrorResponse errorResponse = errorResponseBuilder.builder()
                .setErrorCode(CommonErrorCodes.OPERATION_PERFORM_ERROR)
                .setDescription("у заявки статус не черновик: " + ex.getMessage())
                .setMessage("некорректный статус заявки")
                .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}