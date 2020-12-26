package com.nrkt.springbootmongoex.error;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NonNull
@ToString
@EqualsAndHashCode
@Builder
public class ApiError {
  String message;
  Date timestamp;
  Integer status;
  String error;
  String path;
}
