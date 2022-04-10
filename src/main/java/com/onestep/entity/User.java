package com.onestep.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
  private Integer id;
  private String name;
  private String password;
  private String role;
  private String photo;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
