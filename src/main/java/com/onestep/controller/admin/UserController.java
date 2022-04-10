package com.onestep.controller.admin;

import com.onestep.entity.User;
import com.onestep.entity.Category;
import com.onestep.service.UserService;
import com.onestep.service.ArticleService;
import com.onestep.service.CategoryService;
import com.onestep.service.TagService;
import com.onestep.util.Result;
import com.onestep.util.ResultGenerator;
import com.onestep.util.Upload;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {
  @Resource
  private UserService userService;
  @Resource
  private ArticleService articleService;
  @Resource
  private CategoryService categoryService;
  @Resource
  private TagService tagService;

  @GetMapping({"", "/", "index"})
  public String index(Model model) {
    int articleCount = articleService.count();
    int categoryCount = categoryService.count();
    int tagCount = tagService.count();
    model.addAttribute("articleCount", articleCount)
            .addAttribute("tagCount", tagCount)
            .addAttribute("categoryCount", categoryCount)
            .addAttribute("user", SecurityUtils.getSubject().getSession().getAttribute("user"));
    return "user/index";
  }

  @GetMapping("/logout")
  public String logout() {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return "redirect:/user/login";
  }

  @GetMapping("edit")
  public String edit(Model model) {
    List<Category> categories = categoryService.selectCategories();
    model.addAttribute("categories", categories)
            .addAttribute("user", SecurityUtils.getSubject().getSession().getAttribute("user"));
    return "user/edit";
  }

  @GetMapping("system")
  public String system(Model model) {
    List<User> users = userService.selectUsers();
    model.addAttribute("users", users)
            .addAttribute("user", SecurityUtils.getSubject().getSession().getAttribute("user"));
    return "user/system";
  }

  @GetMapping("/side")
  public String side() {
    return "user/siderbar";
  }

  @GetMapping("/login")
  public String login() {
    return "user/login";
  }

  @PostMapping("/login")
  public String login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "remember", required = false) Boolean rememberMe, Model model) {
    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      model.addAttribute("message", "请输入用户名或密码");
      return "user/login";
    }
    //用户认证
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    if (rememberMe != null) {
      token.setRememberMe(rememberMe);
    }

    try {
      subject.login(token);
      return "redirect:/user/index";
    } catch (Exception e) {
      model.addAttribute("message", "用户名或密码不正确");
      return "user/login";
    }
  }

  @ResponseBody
  @GetMapping("/list")
  public List<User> queryAllUser() {
    return userService.selectUsers();
  }

  @ResponseBody
  @PostMapping("/upload/editormdPic")
  public Map editormdPic(HttpServletRequest request, @RequestParam("editormd-image-file") MultipartFile file) {
    Map<String, Object> map = new HashMap<>();
    //request.getContextPath()项目路径  /blog
    map.put("url", request.getContextPath() + "/upload/editormdPic/" + Upload.upload(file));
    map.put("success", 1);
    map.put("message", "upload success!");
    return map;
  }

  @PostMapping("/user")
  @ResponseBody
  public Result user(@RequestParam("name") String name,
                     @RequestParam("password") String password,
                     @RequestParam(value = "role", required = false) String role,
                     @RequestParam(value = "photo", required = false) MultipartFile photo) {
    User user = new User();
    user.setName(name.trim());
    user.setPassword(password.trim());
    if (!"".equals(role)) {
      user.setRole(role.trim());
    }
    if (!photo.isEmpty()) {
      user.setPhoto("/upload/editormdPic/" + Upload.upload(photo));
    }
    if (userService.insertUser(user) > 0) {
      return ResultGenerator.generateSuccessResult("用户添加成功");
    } else {
      return ResultGenerator.generateFailResult("用户添加失败");
    }
  }

  @PutMapping("/user")
  @ResponseBody
  public Result user(@RequestParam("name") String name,
                     @RequestParam("password") String password,
                     @RequestParam("role") String role,
                     @RequestParam("photo") MultipartFile photo,
                     @RequestParam("id") Integer id) {
    User user = new User();
    user.setId(id);
    user.setName(name.trim());
    user.setPassword(password.trim());
    if (!"".equals(role)) {
      user.setRole(role.trim());
    }
    if (!photo.isEmpty()) {
      user.setPhoto("/upload/editormdPic/" + Upload.upload(photo));
    }
    if (userService.updateUserbyId(user) > 0){
      return ResultGenerator.generateSuccessResult("用户修改成功");
    }else {
      return ResultGenerator.generateFailResult("用户修改失败");
    }
  }

  @DeleteMapping("/user")
  @ResponseBody
  public Result user(@RequestParam("ids[]") Integer[] ids) {
    if (userService.batchDeleteUserbyIds(ids)>0){
      return ResultGenerator.generateSuccessResult("用户删除成功");
    }else {
      return ResultGenerator.generateFailResult("用户删除失败");
    }
  }
}
