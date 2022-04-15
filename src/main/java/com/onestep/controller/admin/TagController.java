package com.onestep.controller.admin;

import com.github.pagehelper.PageInfo;
import com.onestep.entity.Tag;
import com.onestep.service.TagService;
import com.onestep.util.Result;
import com.onestep.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Slf4j
public class TagController {
  @Resource
  private TagService tagService;

  @GetMapping("tag")
  public String select(Model model) {
    log.debug("Get->/amdin/tag");
    Map<String, Object> map = new HashMap<>();
    map.put("pageSize", 10);
    map.put("pageNum", 1);
    List<Tag> tags = tagService.selectTagList(map);
    PageInfo pageInfo = new PageInfo(tags);
    model.addAttribute("pageInfo", pageInfo);
    model.addAttribute("tags", tags);
    return "admin/tag";
  }

  @GetMapping("tag/list")
  public String select(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "search", required = false) String search) {
    log.debug("Get->/amdin/tag/list");
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
    List<Tag> tags = tagService.selectTagList(param);
    PageInfo pageInfo = new PageInfo(tags);
    model.addAttribute("tags", tags);
    model.addAttribute("pageInfo", pageInfo);
    model.addAttribute("search", search);
    return "admin/tag::updateTag";
  }

  @PostMapping("tag")
  @ResponseBody
  public Result batchInsertTags(@RequestParam("names[]") String[] names) {
    log.debug("Post->/amdin/tag");
    if (tagService.batchInsertTagByName(Arrays.asList(names)) > 0) {
      return ResultGenerator.generateSuccessResult("标签添加成功");
    } else {
      return ResultGenerator.generateFailResult("标签添加失败");
    }
  }

  @DeleteMapping("tag")
  @ResponseBody
  public Result batchDeleteTagById(@RequestParam("ids[]") Integer[] ids) {
    log.debug("Delete->/amdin/tag");
    if (tagService.batchDeleteTagById(Arrays.asList(ids)) > 0) {
      return ResultGenerator.generateSuccessResult("标签删除成功");
    } else {
      return ResultGenerator.generateFailResult("标签删除失败");
    }
  }
}
