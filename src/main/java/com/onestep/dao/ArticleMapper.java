package com.onestep.dao;

import com.onestep.entity.Article;
import com.onestep.entity.vo.ArticleDetail;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
  int count();
  ArticleDetail selectArticleById(Integer id);
  Article selectArticleByTitile(String title);
  List<Article> selectArticleByCategoryId(Integer categoryId);

  List<Article> seclectRecentArticles();

  //分页查询(包括按照不同的列查询)
  List<ArticleDetail> selectArticleList(Map<String,Object> param);

  int insertArticle(Article article);
  int updateArticleById(Article article);
  int updateArticleViews(ArticleDetail articleDetail);
  int batchDeleteArticleById(Integer[] ids);
}
