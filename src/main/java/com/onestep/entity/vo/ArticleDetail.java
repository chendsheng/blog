package com.onestep.entity.vo;

import com.onestep.entity.Category;
import com.onestep.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleDetail {
  private Integer id;
  private String title;
  private String summary;
  private String content;
  private String pic;
  private List<Tag> tags;
  private String tagNames;
  private Integer views;
  private Integer categoryId;
  private Category category;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;

  public String getTagNames() {
    if(this.tags!=null){
      String tagNames = "";
      for (Tag tag : this.tags) {
        tagNames = tagNames + tag.getName()+",";
      }
     this.tagNames = tagNames.substring(0,tagNames.lastIndexOf(","));
    }
   return this.tagNames;
  }
}
