package com.onestep.service;

import com.onestep.entity.Tag;
import java.util.List;
import java.util.Map;

public interface TagService {
  int count();
  Tag selectTagByName(String name);
  Tag selectTagById(Integer tagId);
  List<Tag> selectTags();

  List<Tag>  selectTagList(Map<String,Object> map);

  int batchInsertTagByName(List<String> names);

  int batchDeleteTagById(List<Integer> tagIds);
}
