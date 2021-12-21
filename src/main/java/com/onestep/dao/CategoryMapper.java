package com.onestep.dao;

import com.onestep.entity.Article;
import com.onestep.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
  int count();
  Category selectCategoryByName(String name);

  List<Category> selectCategories();
  List<Category> selectCategoryList(Map<String,Object> param);

  int insertCategory(Category category);
  int updateCategory(Category category);

  int batchDeleteCategoryById(List<Integer> ids);
}
