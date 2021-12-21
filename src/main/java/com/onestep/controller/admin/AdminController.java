package com.onestep.controller.admin;

import com.onestep.entity.Admin;
import com.onestep.entity.Category;
import com.onestep.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {
  @Resource
  private AdminService adminService;
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
            .addAttribute("admin", SecurityUtils.getSubject().getSession().getAttribute("admin"));
    return "admin/index";
  }

  @GetMapping("/logout")
  public String logout() {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return "redirect:/admin/login";
  }

  @GetMapping("edit")
  public String edit(Model model) {
    List<Category> categories = categoryService.selectCategories();
    model.addAttribute("categories", categories)
            .addAttribute("admin", SecurityUtils.getSubject().getSession().getAttribute("admin"));
    return "admin/edit";
  }

  @GetMapping("system")
  public String system(Model model) {
    List<Admin> admins = adminService.selectAdmins();
    model.addAttribute("admins", admins)
            .addAttribute("admin", SecurityUtils.getSubject().getSession().getAttribute("admin"));
    return "admin/system";
  }

  @GetMapping("/side")
  public String side() {
    return "admin/siderbar";
  }

  @GetMapping("/login")
  public String login() {
    return "admin/login";
  }

  @PostMapping("/login")
  public String login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "remember", required = false) Boolean rememberMe, Model model) {
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
      return "redirect:/admin/index";
    } catch (Exception e) {
      model.addAttribute("message", "用户名或密码不正确");
      return "admin/login";
    }
  }

  @ResponseBody
  @GetMapping("/list")
  public List<Admin> queryAllAdmin() {
    return adminService.selectAdmins();
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
    Admin admin = new Admin();
    admin.setName(name.trim());
    admin.setPassword(password.trim());
    if (!"".equals(role)) {
      admin.setRole(role.trim());
    }
    if (!photo.isEmpty()) {
      admin.setPhoto("/upload/editormdPic/" + Upload.upload(photo));
    }
    if (adminService.insertAdmin(admin) > 0) {
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
    Admin admin = new Admin();
    admin.setId(id);
    admin.setName(name.trim());
    admin.setPassword(password.trim());
    if (!"".equals(role)) {
      admin.setRole(role.trim());
    }
    if (!photo.isEmpty()) {
      admin.setPhoto("/upload/editormdPic/" + Upload.upload(photo));
    }
    if (adminService.updateAdminbyId(admin) > 0){
      return ResultGenerator.generateSuccessResult("用户修改成功");
    }else {
      return ResultGenerator.generateFailResult("用户修改失败");
    }
  }

  @DeleteMapping("/user")
  @ResponseBody
  public Result user(@RequestParam("ids[]") Integer[] ids) {
    if (adminService.batchDeleteAdminbyIds(ids)>0){
      return ResultGenerator.generateSuccessResult("用户删除成功");
    }else {
      return ResultGenerator.generateFailResult("用户删除失败");
    }
  }
}
