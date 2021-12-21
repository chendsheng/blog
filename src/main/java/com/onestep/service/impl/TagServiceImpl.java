package com.onestep.service.impl;


import com.onestep.dao.ArticleTagMapper;
import com.onestep.dao.TagMapper;
import com.onestep.entity.Tag;
import com.onestep.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {
  @Resource
  TagMapper tagMapper;
  @Resource
  ArticleTagMapper articleTagMapper;


  @Override
  public int count() {
    return tagMapper.count();
  }

  @Override
  public Tag selectTagByName(String name) {
    return tagMapper.selectTagByName(name);
  }

  @Override
  public Tag selectTagById(Integer tagId) {
    return tagMapper.selectTagById(tagId);
  }

  @Override
  public List<Tag> selectTags() {
    return tagMapper.selectTags();
  }

  @Override
  public List<Tag> selectTagList(Map<String, Object> map) {
    return tagMapper.selectTagList(map);
  }

  @Override
  public int batchInsertTagByName(List<String> names) {
    List<String> namesList = new ArrayList<>();
    for (String name : names) {
      if(tagMapper.selectTagByName(name)==null){
        namesList.add(name);
      }
    }
    if(namesList.size()>0){
     return tagMapper.batchInsertTagByName(namesList);
    }
    return 0;
  }

  @Override
  public int batchDeleteTagById(List<Integer> tagIds) {
    List<Integer> tagIdList = new ArrayList<>();
    for (Integer tagId : tagIds) {
      if(articleTagMapper.selectArticleIdByTagId(tagId).size()==0){
        tagIdList.add(tagId);
      }
    }
    if(tagIdList.size()>0){
      return tagMapper.batchDeleteTagById(tagIdList);
    }
    return 0;
  }
}
