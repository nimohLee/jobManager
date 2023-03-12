package com.nimoh.jobManager.commons;

import com.nimoh.jobManager.commons.crawler.CrawlerErrorResult;
import com.nimoh.jobManager.commons.crawler.CrawlerException;
import com.nimoh.jobManager.commons.job.JobErrorResult;
import com.nimoh.jobManager.commons.job.JobException;
import com.nimoh.jobManager.commons.token.TokenErrorResult;
import com.nimoh.jobManager.commons.token.TokenException;
import com.nimoh.jobManager.commons.user.UserErrorResult;
import com.nimoh.jobManager.commons.user.UserException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        final List<String> errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.warn("Invalid DTO Parameter errors : {}", errorList);
        return this.makeErrorResponseEntity(errorList.toString());
    }

    @ExceptionHandler({CrawlerException.class})
    public ResponseEntity<ErrorResponse> handleRestApiException(final CrawlerException exception) {
        log.warn("CrawlerException occur:", exception);
        return this.makeErrorResponseEntity(exception.getErrorResult());
    }


    @ExceptionHandler({JobException.class})
    public ResponseEntity<ErrorResponse> handleRestApiException(final JobException exception) {
        log.warn("JobException occur:", exception);
        return this.makeErrorResponseEntity(exception.getErrorResult());
    }

    @ExceptionHandler({UserException.class})
    public ResponseEntity<ErrorResponse> handleRestApiException(final UserException exception) {
        log.warn("UserException occur:", exception);
        return this.makeErrorResponseEntity(exception.getErrorResult());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        log.warn("Exception occur: ", exception);
        return this.makeErrorResponseEntity(JobErrorResult.UNKNOWN_EXCEPTION);
    }

    @ExceptionHandler({TokenException.class})
    public ResponseEntity<ErrorResponse> handleRestApiException(final TokenException exception) {
        log.warn("Exception occur: ", exception);
        return this.makeErrorResponseEntity(exception.getErrorResult());
    }


    private ResponseEntity<Object> makeErrorResponseEntity(final String errorDescription) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorDescription));
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final JobErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final UserErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final CrawlerErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final TokenErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }

    @Getter
    @RequiredArgsConstructor
    static class ErrorResponse {
        private final String code;
        private final String message;
    }
}

