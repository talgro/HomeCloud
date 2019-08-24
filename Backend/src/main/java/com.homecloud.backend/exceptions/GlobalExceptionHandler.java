package com.homecloud.backend.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDenied(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   Exception e) {
    log.error("Access denied: ", e);
    return new ResponseEntity<>("access_denied", HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ClientServiceException.class)
  public ResponseEntity<String> handleClientServiceException(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        Exception e) {
    log.error("client service exception", e);
    ResponseEntity<String> result = new ResponseEntity<>("not_found", HttpStatus.NOT_FOUND);

    // Dont cache errors
    result.getHeaders().remove(HttpHeaders.CACHE_CONTROL);
    return result;
  }

  @ExceptionHandler(Exception.class)
  //@ResponseBody
  public ResponseEntity<String> handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
    try {
      log.error("Exception: ", e);
      ResponseEntity<String> result;
      result = new ResponseEntity<>("internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR);

      // Dont cache errors
      result.getHeaders().remove(HttpHeaders.CACHE_CONTROL);
      return result;
    } catch (Exception ex) {
      return new ResponseEntity<>("internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
