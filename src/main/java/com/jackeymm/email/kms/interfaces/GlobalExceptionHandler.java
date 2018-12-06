package com.jackeymm.email.kms.interfaces;

import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;
import org.h2.jdbc.JdbcSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.invoke.MethodHandles;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
class GlobalExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @ExceptionHandler(KmsTenantNoFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  Response<String> handleKmsTenantNoFoundException(KmsTenantNoFoundException ex) {
    LOG.error("tenant is not exist", ex);
    return Response.failed(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(BAD_REQUEST)
  Response<String> handleInvalidRequest(IllegalArgumentException ex) {
    LOG.error("Invalid request", ex);
    return Response.failed(BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  Response<String> handleUnexpectedException(Exception ex) {
    LOG.error("Unexpected exception", ex);
    return Response.failed(INTERNAL_SERVER_ERROR, ex.getMessage());
  }


  @ExceptionHandler(JdbcSQLException.class)
  @ResponseStatus(CONFLICT)
  Response<String> handleJdbcSQLException(JdbcSQLException ex) {
    LOG.error("handleJdbcSQLException ", ex);
    if(ex.getCause().getMessage().contains("Unique")){
      return Response.failed(CONFLICT, ex.getMessage());
    }
    return Response.failed(INTERNAL_SERVER_ERROR, ex.getMessage());
  }
}
