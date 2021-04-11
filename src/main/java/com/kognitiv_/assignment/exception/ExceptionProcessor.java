package com.kognitiv_.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ExceptionProcessor {

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBadRequest(HttpServletRequest request, BadRequestException exception) {
        return problemDetails(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InternalServerException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetails handleInternalServerError(HttpServletRequest request, InternalServerException exception) {
        return problemDetails(request, "Something went wrong, Please try later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DateTimeParseException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleDateTimeException(HttpServletRequest request, DateTimeParseException exception) {
        return problemDetails(request, "Invalid date provided: " + exception.getParsedString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ProblemDetails handleNotFoundException(HttpServletRequest request, NotFoundException exception) {
        return problemDetails(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ProblemDetails problemDetails(HttpServletRequest request, String message, HttpStatus status) {
        ProblemDetails details = new ProblemDetails();
        details.setErrorCode(status.value());
        details.setMessage(message);
        details.setPath(request.getRequestURI());
        details.setTime(LocalDateTime.now());
        return details;
    }
}
