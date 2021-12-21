package com.onestep.dao;

import com.onestep.entity.ArticleTag;

import java.util.List;

public interface ArticleTagMapper {
  List<Integer> selectArticleIdByTagId(Integer tagId);

  List<Integer> selecTagIdByArticleId(Integer articleId);

  List<Integer> selectTagIds();

  int batchInsertArticleTag(List<ArticleTag> articleTags);

  int DeleteArticleTagByArticleId(Integer articleId);
}