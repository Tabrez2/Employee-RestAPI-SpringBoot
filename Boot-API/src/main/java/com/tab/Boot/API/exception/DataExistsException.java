package com.tab.Boot.API.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DataExistsException extends RuntimeException {
  String msg = "Data is repeated with";

}
