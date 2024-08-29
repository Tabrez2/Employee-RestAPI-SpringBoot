package com.tab.Boot.API.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ResponseStructure<T> {
  String msg;
  T data;
}
