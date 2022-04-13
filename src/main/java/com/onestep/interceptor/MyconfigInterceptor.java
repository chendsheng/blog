package com.onestep.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MyconfigInterceptor implements HandlerInterceptor {
  @Value("${iconfig.icp}")
  private String icp;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (request.getAttribute("icp") == null) {
      log.debug("进入icp拦截器");
      request.setAttribute("icp", icp);
    }
    return true;
  }

  public void setIcp(String icp) {
    this.icp = icp;
  }
}
