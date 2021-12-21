package com.onestep.dao;

import com.onestep.entity.Tag;
import java.util.List;
import java.util.Map;

public interface TagMapper {
  int count();
  Tag selectTagByName(String name);
  Tag selectTagById(Integer tagId);
  List<Tag> selectTags();

  List<Tag> selectTagsByArticleId(Integer articleId);

  List<Tag> selectTagsByNames(String[] names);
  List<Tag>  selectTagList(Map<String,Object> map);

  int batchInsertTagByName(List<String> names);

  int batchDeleteTagById(List<Integer> ids);
}
