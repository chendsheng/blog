package com.onestep.service;

import com.onestep.entity.Category;
import java.util.List;
import java.util.Map;

public interface CategoryService {
  int count();
  Category selectCategoryByName(String name);
  List<Category> selectCategories();

  List<Category> selectCategoryList(Map<String,Object> param);

  int insertCategory(Category category);
  int updateCategory(Category category);

  int batchDeleteCategoryById(Integer[] ids);
}
