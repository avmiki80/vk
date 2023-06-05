package ru.avm.vktest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import ru.avm.vktest.exception.JsonException;
import ru.avm.vktest.exception.VkServiceException;

@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    @ExceptionHandler(VkServiceException.class)
    public final ResponseEntity<JsonException> handleVkServiceException(VkServiceException exception, WebRequest request) {
        request.getDescription(false);
        return new ResponseEntity<>(new JsonException(exception.getClass().getSimpleName(), exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<JsonException> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        request.getDescription(false);
        return new ResponseEntity<>(new JsonException(exception.getClass().getSimpleName(), exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<JsonException> handleAllExceptions(Exception exception, WebRequest request) {
        request.getDescription(false);
        return new ResponseEntity<>(new JsonException(exception.getClass().getSimpleName(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
