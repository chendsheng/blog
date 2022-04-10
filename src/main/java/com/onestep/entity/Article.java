package com.onestep.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article implements Serializable {
  private Integer id;
  private String title;
  private String summary;
  private String content;
  private String pic;
  private Integer views;
  private Integer categoryId;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
