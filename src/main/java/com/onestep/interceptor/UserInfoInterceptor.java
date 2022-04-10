package com.onestep.interceptor;

import com.onestep.entity.User;
import com.onestep.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//处理rememberme shiro session失效
public class UserInfoInterceptor implements HandlerInterceptor {
  @Resource
  UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isRemembered() && !subject.isAuthenticated())
      request.getSession().setAttribute("user",subject.getPrincipal());
    return true;
  }
}