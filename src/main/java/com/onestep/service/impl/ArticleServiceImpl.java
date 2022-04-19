package com.onestep.service.impl;

import com.onestep.dao.ArticleMapper;
import com.onestep.dao.ArticleTagMapper;
import com.onestep.dao.TagMapper;
import com.onestep.entity.Article;
import com.onestep.entity.ArticleTag;
import com.onestep.entity.Tag;
import com.onestep.entity.vo.ArticleDetail;
import com.onestep.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
  @Resource
  ArticleMapper articleMapper;
  @Resource
  ArticleTagMapper articleTagMapper;
  @Resource
  TagMapper tagMapper;


  @Override
  public int count() {
    return articleMapper.count();
  }

  @Override
  public ArticleDetail selectArticleById(Integer id) {
    return articleMapper.selectArticleById(id);
  }

  @Override
  public Article selectArticleByTitile(String title) {
    return articleMapper.selectArticleByTitile(title);
  }

  @Override
  public List<Article> selectArticleByCategoryId(Integer categoryId) {
    return articleMapper.selectArticleByCategoryId(categoryId);
  }

  @Override
  public List<Article> seclectRecentArticles() {
    return articleMapper.seclectRecentArticles();
  }

  @Override
  public List<ArticleDetail> selectArticleList(Map<String, Object> param) {
    return articleMapper.selectArticleList(param);
  }

  @Override
  @Transactional
  public int insertArticle(ArticleDetail articleDetail) {
    if (articleMapper.selectArticleByTitile(articleDetail.getTitle()) != null) {
      return 0;
    }
    String tagNames = articleDetail.getTagNames();
    Article article = new Article();
    article.setTitle(articleDetail.getTitle());
    article.setSummary(articleDetail.getSummary());
    article.setCategoryId(articleDetail.getCategoryId());
    article.setPic(articleDetail.getPic());
    article.setContent(articleDetail.getContent());
    //插入文章
    articleMapper.insertArticle(article);

    String[] nameTags = tagNames.split(",");
    List<String> tagNameList = new ArrayList<>();

    for (String name : nameTags) {
      if (tagMapper.selectTagByName(name) == null) {
        tagNameList.add(name);
      }
    }
    //批量插入tag
    if (!tagNameList.isEmpty()) {
      tagMapper.batchInsertTagByName(tagNameList);
    }

    //获得相关联的tag
    List<Tag> tags = tagMapper.selectTagsByNames(nameTags);

    List<ArticleTag> articleTags = new ArrayList<>();

    for (Tag tag : tags) {
      ArticleTag articleTag = new ArticleTag();
      articleTag.setArticleId(article.getId());
      articleTag.setTagId(tag.getId());
      articleTags.add(articleTag);
    }
    //批量插入articleTag
    articleTagMapper.batchInsertArticleTag(articleTags);

    return 1;
  }

  @Override
  @Transactional
  public int updateArticleById(ArticleDetail articleDetail) {
    //标题被修改时，进一步判断库中是否同名
    if (articleMapper.selectArticleByTitile(articleDetail.getTitle()).getId() != articleDetail.getId()) {
      return 0;
    }
    String tagNames = articleDetail.getTagNames();
    Article article = new Article();
    article.setId(articleDetail.getId());
    article.setTitle(articleDetail.getTitle());
    article.setSummary(articleDetail.getSummary());
    article.setCategoryId(articleDetail.getCategoryId());
    article.setPic(articleDetail.getPic());
    article.setContent(articleDetail.getContent());
    //更新文章
    articleMapper.updateArticleById(article);

    String[] newTagNames = tagNames.split(",");

    //新增的tag
    List<String> tagNameList = new ArrayList<>();

    //旧关联的tag
    List<Tag> oldTags = tagMapper.selectTagsByArticleId(article.getId());

    for (String newTagName : newTagNames) {
      if (tagMapper.selectTagByName(newTagName) == null) {
        tagNameList.add(newTagName);
      }
    }

    // 被删除的tag
    List<Integer> deleteTagList = new ArrayList<>();
    for (Tag tag : oldTags) {
      if (tagNames.indexOf(tag.getName()) == -1 && articleTagMapper.selectArticleIdByTagId(tag.getId()).size() == 1) {
        deleteTagList.add(tag.getId());
      }
    }

    //先删除所有该文章对应的的articletag记录
    articleTagMapper.DeleteArticleTagByArticleId(article.getId());

    //批量插入新tag
    if (tagNameList.size() > 0) {
      tagMapper.batchInsertTagByName(tagNameList);
    }

    //批量删除无关联tag
    if (deleteTagList.size() > 0) {
      tagMapper.batchDeleteTagById(deleteTagList);
    }

    //新ArticleTag映射
    List<ArticleTag> insertArticleTags = new ArrayList<>();

    List<Tag> insertTags = tagMapper.selectTagsByNames(newTagNames);
    for (Tag tag : insertTags) {
      ArticleTag articleTag = new ArticleTag();
      articleTag.setArticleId(article.getId());
      articleTag.setTagId(tag.getId());
      insertArticleTags.add(articleTag);
    }
    //批量插入articleTag
    articleTagMapper.batchInsertArticleTag(insertArticleTags);
    return 1;
  }

  @Override
  public int updateArticleViews(ArticleDetail articleDetail) {
    return articleMapper.updateArticleViews(articleDetail);
  }

  @Override
  @Transactional
  public int batchDeleteArticleById(Integer[] ids) {
    int i = articleMapper.batchDeleteArticleById(ids);
    List<Integer> tagIdList = new ArrayList<>();
    List<Tag> tags = tagMapper.selectTags();
    //删除article后,剩下的article-tag映射
    List<Integer> tagIds = articleTagMapper.selectTagIds();
    for (Tag tag : tags) {
      if (!tagIds.contains(tag.getId())) {
        tagIdList.add(tag.getId());
      }
    }
    tagMapper.batchDeleteTagById(tagIdList);
    return i;
  }
}
