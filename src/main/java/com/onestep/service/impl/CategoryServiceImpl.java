package com.onestep.service.impl;

import com.onestep.dao.ArticleMapper;
import com.onestep.dao.CategoryMapper;
import com.onestep.entity.Category;
import com.onestep.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Resource
  CategoryMapper categoryMapper;
  @Resource
  ArticleMapper articleMapper;


  @Override
  public int count() {
    return categoryMapper.count();
  }

  @Override
  public Category selectCategoryByName(String name) {
    return categoryMapper.selectCategoryByName(name);
  }

  @Override
  public List<Category> selectCategories() {
    return categoryMapper.selectCategories();
  }

  @Override
  public List<Category> selectCategoryList(Map<String, Object> param) {
    return categoryMapper.selectCategoryList(param);
  }

  @Override
  public int insertCategory(Category category) {
    if (categoryMapper.selectCategoryByName(category.getName()) == null) {
      return categoryMapper.insertCategory(category);
    }
    return 0;
  }

  @Override
  public int updateCategory(Category category) {
    Category categorySelective = categoryMapper.selectCategoryByName(category.getName());
    if (categorySelective == null || categorySelective.getId() == category.getId()) {
      return categoryMapper.updateCategory(category);
    }
    return 0;
  }

  @Override
  @Transactional
  public int batchDeleteCategoryById(Integer[] ids) {
    List<Integer> idsList = new ArrayList<>();
    for (Integer id : ids) {
      if (articleMapper.selectArticleByCategoryId(id).size() == 0) {
        idsList.add(id);
      }
    }
    if (idsList.size() > 0) {
      return categoryMapper.batchDeleteCategoryById(idsList);
    }
    return 0;
  }
}
