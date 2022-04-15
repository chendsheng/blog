package com.onestep.interceptor;

import com.onestep.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//rememberme时向httpsession中放入user
@Slf4j
public class UserInfoInterceptor implements HandlerInterceptor {
  @Resource
  UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Subject subject = SecurityUtils.getSubject();
    if (request.getSession().getAttribute("user") == null && subject.isRemembered() && !subject.isAuthenticated()) {
      log.debug("remmberMe登录->sssion中放入user对象");
      log.debug("subject->{}", subject.getPrincipal().toString());
      request.getSession().setAttribute("user", userService.selectUser((String) subject.getPrincipal()));
    }
    return true;
  }
}