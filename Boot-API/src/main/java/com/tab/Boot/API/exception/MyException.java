package com.tab.Boot.API.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tab.Boot.API.dto.ResponseStructure;

@RestControllerAdvice
public class MyException {

  @Autowired
  ResponseStructure<String> rs;

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<ResponseStructure<String>> handle(DataNotFoundException exception) {
    rs.setMsg("Data Not Found");
    rs.setData(exception.getMsg());
    rs.setStatusCode(HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataExistsException.class)
  public ResponseEntity<ResponseStructure<String>> handle(DataExistsException exception) {
    rs.setMsg("Data Exists");
    rs.setData(exception.getMsg());
    rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.BAD_REQUEST);
  }

}
