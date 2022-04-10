package com.onestep.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


  @Bean
  public MyRealm myAuthRealm() {
    return new MyRealm();
  }

  @Bean
  public SimpleCookie simpleCookie() {
    SimpleCookie rememberMe = new SimpleCookie("rememberMe");
    rememberMe.setMaxAge(60 * 60 * 24 * 7);
    return rememberMe;
  }

  @Bean
  public CookieRememberMeManager cookieRememberMeManager(SimpleCookie simpleCookie) {
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(simpleCookie);
    //CipherKey 需要长度是16的字节数组
    cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("QnV5SXRZb3VyU2VsZk9rPw=="));
    return cookieRememberMeManager;
  }

  @Bean
  public DefaultWebSecurityManager securityManager(MyRealm myRealm, CookieRememberMeManager cookieRememberMeManager) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(myRealm);
    securityManager.setRememberMeManager(cookieRememberMeManager);
    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean factoryBean(DefaultWebSecurityManager securityManager) {
    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    bean.setSecurityManager(securityManager);

    // 设置默认登录的 URL，身份认证失败会访问该 URL
    bean.setLoginUrl("/admin/login");
    //设置无权限时，跳转页面
    bean.setUnauthorizedUrl("/error/unAuthorized");

    // LinkedHashMap 是有序的，进行顺序拦截器配置
    Map<String, String> filterChainMap = new LinkedHashMap<>();
    // 配置可以匿名访问的地址，可以根据实际情况自己添加，放行一些静态资源等，anon 表示放行


/*    anno:无需认证（登陆）可以访问

    authc:必须认证才能访问

    user：如果使用rememberMe的功能可以直接访问

    perms:该资源必须得到资源权限可以访问

    role:该资源必须得到角色权限才能访问
*/

    // 登录 URL 放行
    filterChainMap.put("/user/login", "anon");
    filterChainMap.put("/**/*.js", "anon");
    filterChainMap.put("/**/*.css", "anon");
    filterChainMap.put("/**/*.jpeg", "anon");
    filterChainMap.put("/**/*.png", "anon");
    filterChainMap.put("/**/*.ico", "anon");
    filterChainMap.put("/**/*.jpg", "anon");
    filterChainMap.put("/*.ico", "anon");
    // 以"/user/" 开头的用户需要身份认证，authc 表示要进行身份认证
    // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最下边，不然"/**"后面的过滤会不管用-->:
    filterChainMap.put("/admin/system", "roles[admin]");
    filterChainMap.put("/admin/**", "user");

    bean.setFilterChainDefinitionMap(filterChainMap);
    return bean;
  }
}
