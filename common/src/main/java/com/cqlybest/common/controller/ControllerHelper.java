package com.cqlybest.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cqlybest.common.bean.LoginUser;

public abstract class ControllerHelper {

  protected ResponseEntity<Object> ok() {
    return new ResponseEntity<Object>(HttpStatus.OK);
  }

  protected ResponseEntity<Object> error(String message) {
    return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
  }

  protected ResponseEntity<Object> illegal() {
    return error("非法请求");
  }

  protected LoginUser getUser() {
    return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

}
