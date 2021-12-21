package com.onestep.config;

import com.onestep.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//处理rememberme shiro session失效
public class AdminInfoInterceptor implements HandlerInterceptor {
  @Resource
  AdminService adminService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isRemembered() && !subject.isAuthenticated())
      subject.getSession().setAttribute("admin",adminService.selectAdmin(subject.getPrincipal().toString()));
    return true;
  }
}