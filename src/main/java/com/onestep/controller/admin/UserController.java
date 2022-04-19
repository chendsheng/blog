package com.onestep.controller.admin;

import com.onestep.entity.Category;
import com.onestep.entity.User;
import com.onestep.service.ArticleService;
import com.onestep.service.CategoryService;
import com.onestep.service.TagService;
import com.onestep.service.UserService;
import com.onestep.util.Result;
import com.onestep.util.ResultGenerator;
import com.onestep.util.SaltMd5Util;
import com.onestep.util.Upload;
import lombok.extern.slf4j.Slf4j;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
@Slf4j
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
    log.debug("Get->/amdin");
    int articleCount = articleService.count();
    int categoryCount = categoryService.count();
    int tagCount = tagService.count();
    model.addAttribute("articleCount", articleCount)
            .addAttribute("tagCount", tagCount)
            .addAttribute("categoryCount", categoryCount);
    return "admin/index";
  }

  @GetMapping("/logout")
  public String logout() {
    log.debug("Get->/amdin/logout");
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return "redirect:/admin/login";
  }

  @GetMapping("edit")
  public String edit(Model model) {
    log.debug("Get->/admin/edit");
    List<Category> categories = categoryService.selectCategories();
    model.addAttribute("categories", categories);
    return "admin/edit";
  }

  @GetMapping("system")
  public String system(Model model) {
    log.debug("Get->/admin/system");
    List<User> users = userService.selectUsers();
    model.addAttribute("users", users);
    return "admin/system";
  }

  @GetMapping("/side")
  public String side() {
    log.debug("Get->/admin/side");
    return "admin/siderbar";
  }

  @GetMapping("/login")
  public String login(HttpServletRequest request) {
    log.debug("Get->/admin/login");
    return "admin/login";
  }

  @PostMapping("/login")
  public String login(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password,
                      @RequestParam(value = "remember", required = false) Boolean rememberMe, @RequestParam("verifyCode") String verifyCode, Model model) {
    log.debug("Post->/admin/login");
    if (verifyCode == null || !verifyCode.equals(session.getAttribute("verifyCode"))) {
      model.addAttribute("message", "验证码错误");
      return "admin/login";
    }
    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      model.addAttribute("message", "请输入用户名或密码");
      return "admin/login";
    }
    //用户认证
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    if (rememberMe != null) {
      token.setRememberMe(rememberMe);
    }

    try {
      subject.login(token);
      session.setAttribute("user", userService.selectUser(username));
      return "redirect:/admin/index";
    } catch (Exception e) {
      System.out.println(e);
      model.addAttribute("message", "用户名或密码不正确");
      return "admin/login";
    }
  }

  @ResponseBody
  @GetMapping("/list")
  public List<User> queryAllUser() {
    log.debug("Get->/admin/list");
    return userService.selectUsers();
  }

  @ResponseBody
  @PostMapping("/upload/editormdPic")
  public Map editormdPic(HttpServletRequest request, @RequestParam("editormd-image-file") MultipartFile file) {
    log.debug("Post->/admin/upload/editormdPic");
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
    log.debug("Post->/admin/user");
    String salt = SaltMd5Util.createSalt();
    User user = new User();
    user.setId(salt);
    user.setName(name.trim());
    user.setPassword(SaltMd5Util.salt(password.trim(), salt));
    if (!"".equals(role.trim())) {
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
                     @RequestParam("id") String id) {
    log.debug("Put->/admin/user");
    User user = new User();
    user.setId(id);
    user.setName(name.trim());
    user.setPassword(SaltMd5Util.salt(password.trim(), id));
    if (!"".equals(role.trim())) {
      user.setRole(role.trim());
    }
    if (!photo.isEmpty()) {
      user.setPhoto("/upload/editormdPic/" + Upload.upload(photo));
    }
    if (userService.updateUserbyId(user) > 0) {
      return ResultGenerator.generateSuccessResult("用户修改成功");
    } else {
      return ResultGenerator.generateFailResult("用户修改失败");
    }
  }

  @DeleteMapping("/user")
  @ResponseBody
  public Result user(@RequestParam("ids[]") Integer[] ids) {
    log.debug("Delete->/admin/user");
    if (userService.batchDeleteUserbyIds(ids) > 0) {
      return ResultGenerator.generateSuccessResult("用户删除成功");
    } else {
      return ResultGenerator.generateFailResult("用户删除失败");
    }
  }
}
