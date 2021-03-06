package com.onestep.controller.front;

import com.github.pagehelper.PageInfo;
import com.onestep.entity.Article;
import com.onestep.entity.Category;
import com.onestep.entity.Tag;
import com.onestep.entity.vo.ArticleDetail;
import com.onestep.service.ArticleService;
import com.onestep.service.ArticleTagService;
import com.onestep.service.CategoryService;
import com.onestep.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FrontController {
  @Resource
  ArticleService articleService;
  @Resource
  TagService tagService;
  @Resource
  CategoryService categoryService;
  @Resource
  ArticleTagService articleTagService;

  @GetMapping({"", "/"})
  public String index(Model model,
                      @RequestParam(required = false) String article,
                      @RequestParam(required = false) Integer categoryId,
                      @RequestParam(required = false) Integer tagId) {
    Map<String, Object> map = new HashMap<>();
    map.put("pageSize", 6);
    map.put("pageNum", 1);
    List<ArticleDetail> articleDetails;
    if ("all".equals(article)) {
      model.addAttribute("pic", false);
    }
    if (tagId != null) {
      model.addAttribute("pic", false);
      map.put("column", "tag.id");
      map.put("value", tagId);
      articleDetails = articleService.selectArticleList(map);
    } else if (categoryId != null) {
      model.addAttribute("pic", false);
      map.put("column", "category.id");
      map.put("value", categoryId);
      articleDetails = articleService.selectArticleList(map);
    } else {
      articleDetails = articleService.selectArticleList(map);
    }
    PageInfo pageInfo = new PageInfo(articleDetails);

    List<Article> articlesRecent = articleService.seclectRecentArticles();
    List<Tag> tags = tagService.selectTags();
    List<Category> categories = categoryService.selectCategories();


    model.addAttribute("articleDetails", articleDetails)
            .addAttribute("articlesRecent", articlesRecent)
            .addAttribute("tags", tags)
            .addAttribute("categories", categories)
            .addAttribute("pageInfo", pageInfo);
    return "front/index";
  }

  @GetMapping("/list")
  public String list(Model model,
                     @RequestParam(defaultValue = "6") Integer pageSize,
                     @RequestParam(defaultValue = "1") Integer pageNum,
                     @RequestParam(required = false) String title,
                     @RequestParam(required = false) Integer tagId,
                     @RequestParam(required = false) Integer categoryId) {
    Map<String, Object> map = new HashMap<>();
    map.put("pageSize", pageSize);
    map.put("pageNum", pageNum);
    String selectType;
    String selectValue;

    List<ArticleDetail> articleDetails;
    if (tagId != null) {
      map.put("column", "tag.id");
      map.put("value", tagId);
      selectType = "tagId";
      selectValue = tagId + "";
    } else if (categoryId != null) {
      map.put("column", "category.id");
      map.put("value", categoryId);
      selectType = "categoryId";
      selectValue = categoryId + "";
    } else if (title != null) {
      map.put("column", "title");
      map.put("value", title);
      selectType = "title";
      selectValue = title;
    } else {
      selectType = null;
      selectValue = null;
    }
    articleDetails = articleService.selectArticleList(map);
    PageInfo pageInfo = new PageInfo(articleDetails);

    model.addAttribute("articleDetails", articleDetails)
            .addAttribute("pageInfo", pageInfo)
            .addAttribute("selectType", selectType)
            .addAttribute("selectValue", selectValue);

    return "front/index::articlesList";
  }

  @GetMapping("/detail/{id}")
  public String deltail(Model model, @PathVariable("id") Integer id) {
    ArticleDetail articleDetail = articleService.selectArticleById(id);
    if (articleDetail == null) {
      return "error/4xx";
    }
    articleDetail.setViews(articleDetail.getViews() == null ? 1 : articleDetail.getViews() + 1);
    articleService.updateArticleViews(articleDetail);

    model.addAttribute("articleDetail", articleDetail);
    return "front/detail";
  }

  @GetMapping("space")
  public String space() {
    return "front/space";
  }
}
