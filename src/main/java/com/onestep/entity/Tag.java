package com.onestep.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tag {
  private Integer id;
  private String name;
  private LocalDateTime createTime;
}
