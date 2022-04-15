package com.onestep.controller.admin;

import com.github.pagehelper.PageInfo;
import com.onestep.entity.Category;
import com.onestep.service.CategoryService;
import com.onestep.util.Result;
import com.onestep.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
@Slf4j
public class CategoryController {
  @Resource
  CategoryService categoryService;

  @GetMapping("category")
  public String category(Model model) {
    log.debug("Get->/admin/category");
    Map<String, Object> param = new HashMap<>();
    param.put("pageNum", 1);
    param.put("pageSize", 5);
    List<Category> categories = categoryService.selectCategoryList(param);
    PageInfo pageInfo = new PageInfo(categories);
    model.addAttribute("categories", categories);
    model.addAttribute("pageInfo", pageInfo);
    return "admin/category";
  }

  @GetMapping("category/list")
  public String category(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
                         @RequestParam(value = "search", required = false) String search) {
    log.debug("Get->/admin/category/list");
    Map<String, Object> param = new HashMap<>();
    param.put("pageNum", pageNum);
    param.put("pageSize", pageSize);
    if (search == null || "".equals(search.trim())) {
      search = null;
    } else {
      search = search.trim();
      param.put("column", "name");
      param.put("value", search);
    }
    List<Category> categories = categoryService.selectCategoryList(param);
    PageInfo pageInfo = new PageInfo(categories);
    model.addAttribute("categories", categories);
    model.addAttribute("pageInfo", pageInfo);
    model.addAttribute("search", search);
    return "admin/category::updateCategory";
  }

  @PostMapping("category")
  @ResponseBody
  public Result insertCategory(@RequestParam("name") String name, @RequestParam("icon") String icon) {
    log.debug("Post->/admin/category");
    Category category = new Category();
    category.setName(name.trim());
    category.setIcon(icon);
    if (categoryService.insertCategory(category) > 0) {
      return ResultGenerator.generateSuccessResult("分类添加成功");
    } else {
      return ResultGenerator.generateFailResult("分类名或已存在,添加失败");
    }
  }

  @PutMapping("category/{id}")
  @ResponseBody
  public Result updateCategory(@PathVariable("id") Integer id, @RequestParam("name") String name, @RequestParam("icon") String icon) {
    log.debug("Put->/admin/category/{}", id);
    Category category = new Category();
    category.setId(id);
    category.setName(name.trim());
    category.setIcon(icon);
    if (categoryService.updateCategory(category) > 0) {
      return ResultGenerator.generateSuccessResult("分类修改成功");
    } else {
      return ResultGenerator.generateFailResult("修改失败,分类名或已存在");
    }
  }

  @DeleteMapping("category")
  @ResponseBody
  public Result batchDeleteCategoryById(@RequestParam("ids[]") Integer[] ids) {
    log.debug("Delete->/admin/category");
    if (categoryService.batchDeleteCategoryById(ids) > 0) {
      return ResultGenerator.generateSuccessResult("分类删除成功");
    } else {
      return ResultGenerator.generateFailResult("分类删除失败");
    }
  }
}
