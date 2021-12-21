package com.onestep.service;

import com.onestep.entity.ArticleTag;
import java.util.List;

public interface ArticleTagService {
  List<Integer> selectArticleIdByTagId(Integer tagId);
  List<Integer> selectTagIds();

  List<Integer> selecTagIdByArticleId(Integer articleId);

  int batchInsertArticleTag(List<ArticleTag> articleTags);
}
