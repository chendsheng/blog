package com.onestep.service.impl;

import com.onestep.dao.ArticleTagMapper;
import com.onestep.entity.ArticleTag;
import com.onestep.service.ArticleTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleTagServiceImpl implements ArticleTagService {
  @Resource
  ArticleTagMapper articleTagMapper;


  @Override
  public List<Integer> selectArticleIdByTagId(Integer tagId) {
    return articleTagMapper.selectArticleIdByTagId(tagId);
  }

  @Override
  public List<Integer> selectTagIds() {
    return articleTagMapper.selectTagIds();
  }

  @Override
  public List<Integer> selecTagIdByArticleId(Integer articleId) {
    return articleTagMapper.selecTagIdByArticleId(articleId);
  }

  @Override
  public int batchInsertArticleTag(List<ArticleTag> articleTags) {
    return articleTagMapper.batchInsertArticleTag(articleTags);
  }
}
