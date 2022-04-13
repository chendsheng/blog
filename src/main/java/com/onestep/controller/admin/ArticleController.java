package com.onestep.controller.admin;

import com.github.pagehelper.PageInfo;
import com.onestep.entity.Category;
import com.onestep.entity.vo.ArticleDetail;
import com.onestep.service.ArticleService;
import com.onestep.service.CategoryService;
import com.onestep.util.Result;
import com.onestep.util.ResultGenerator;
import com.onestep.util.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Slf4j
public class ArticleController {
  @Resource
  ArticleService articleService;
  @Resource
  CategoryService categoryService;

  @PostMapping("article")
  @ResponseBody
  public Result insert(
          @RequestParam("title") String title,
          @RequestParam("summary") String summary,
          @RequestParam("tags") String tags,
          @RequestParam("categoryId") Integer categoryId,
          @RequestParam("content") String content,
          @RequestParam(value = "pic", required = false) MultipartFile file,
          @RequestParam(value = "randomPic", required = false) Boolean randomPic) {
    log.debug("Post:/admin/article");
    ArticleDetail articleDetail = new ArticleDetail();
    articleDetail.setTitle(title.trim());
    articleDetail.setSummary(summary.trim());
    articleDetail.setContent(content);
    articleDetail.setTagNames(tags.trim());
    articleDetail.setCategoryId(categoryId);
    if (randomPic != null) {
      articleDetail.setPic("/admin/img/pic/" + (int) (Math.floor(Math.random() * 10)) + ".jpeg");
    } else {
      articleDetail.setPic("/upload/editormdPic/" + Upload.upload(file));
    }
    if (articleService.insertArticle(articleDetail) > 0) {
      return ResultGenerator.generateSuccessResult("文章发布成功");
    } else {
      return ResultGenerator.generateFailResult("文章标题或已存在,请重新发布");
    }
  }

  @PutMapping("article")
  @ResponseBody
  public Result update(
          @RequestParam("id") Integer id,
          @RequestParam("title") String title,
          @RequestParam("summary") String summary,
          @RequestParam("tags") String tags,
          @RequestParam("categoryId") Integer categoryId,
          @RequestParam(value = "pic", required = false) MultipartFile file,
          @RequestParam("content") String content) {
    log.debug("Put:/admin/article");
    ArticleDetail articleDetail = new ArticleDetail();
    if (file != null) {
      articleDetail.setPic("/upload/editormdPic/" + Upload.upload(file));
    }
    articleDetail.setId(id);
    articleDetail.setTitle(title.trim());
    articleDetail.setSummary(summary.trim());
    articleDetail.setContent(content);
    articleDetail.setTagNames(tags.trim());
    articleDetail.setCategoryId(categoryId);
    if (articleService.updateArticleById(articleDetail) > 0) {
      return ResultGenerator.generateSuccessResult("文章修改成功");
    } else {
      return ResultGenerator.generateFailResult("修改失败,文章标题或已存在");
    }
  }

  @GetMapping("article")
  public String select(Model model) {
    log.debug("Get:/admin/article");
    Map<String, Object> map = new HashMap<>();
    map.put("pageNum", 1);
    map.put("pageSize", 5);
    List<ArticleDetail> articleDetails = articleService.selectArticleList(map);
    PageInfo pageInfo = new PageInfo(articleDetails);

    model.addAttribute("pageInfo", pageInfo)
            .addAttribute("articleDetails", articleDetails);
    return "admin/article";
  }

  @GetMapping("/article/list")
  public String select(Model model, @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) String search) {
    log.debug("Get:/admin/article/list");
    Map<String, Object> map = new HashMap<>();
    map.put("pageNum", pageNum);
    map.put("pageSize", pageSize);
    List<ArticleDetail> articleDetails;
    if (search == null || "".equals(search.trim())) {
      search = null;
      articleDetails = articleService.selectArticleList(map);
    } else {
      search = search.trim();
      map.put("column", "title");
      map.put("value", search);
      articleDetails = articleService.selectArticleList(map);
    }
    PageInfo pageInfo = new PageInfo(articleDetails);

    model.addAttribute("articleDetails", articleDetails);
    model.addAttribute("pageInfo", pageInfo);
    model.addAttribute("search", search);
    return "admin/article::updateArticle";
  }

  @GetMapping("/article/{articleId}")
  public String update(@PathVariable("articleId") Integer articleId, Model model) {
    log.debug("Get:/admin/article/" + articleId);
    ArticleDetail articleDetail = articleService.selectArticleById(articleId);
    Map<String, Object> map = new HashMap<>();
    List<Category> categories = categoryService.selectCategoryList(map);
    model.addAttribute("categories", categories);
    model.addAttribute("articleDetail", articleDetail);
    return "admin/edit";
  }

  @DeleteMapping("/articles")
  @ResponseBody
  public Result batchDeleteBlogById(@RequestParam("ids[]") Integer[] ids) {
    log.debug("Delete:/admin/articles");
    if (articleService.batchDeleteArticleById(ids) > 0) {
      return ResultGenerator.generateSuccessResult("文章删除成功");
    } else {
      return ResultGenerator.generateFailResult("文章删除失败");
    }
  }
}
