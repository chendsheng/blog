package com.onestep.config;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

  //记住我
  @Bean(name = "rememberMeManager")
  public CookieRememberMeManager rememberMeManager(){
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(rememberMeCookie());
    //这个地方有点坑，不是所有的base64编码都可以用，长度过大过小都不行，没搞明白，官网给出的要么0x开头十六进制，要么base64
    cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
    return cookieRememberMeManager;
  }
  //cookie管理
  @Bean
  public SimpleCookie rememberMeCookie() {
    SimpleCookie cookie = new SimpleCookie("rememberMe");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(1 * 60 * 60);
    return cookie;
  }

  @Bean
  public MyRealm myAuthRealm(){
    return new MyRealm();
  }

  @Bean
  public DefaultWebSecurityManager securityManager(MyRealm myRealm){
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(myRealm);
    securityManager.setRememberMeManager(rememberMeManager());
    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean factoryBean(DefaultWebSecurityManager securityManager){
    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    bean.setSecurityManager(securityManager);

    // 设置默认登录的 URL，身份认证失败会访问该 URL
    bean.setLoginUrl("/admin/login");
    //设置无权限时，跳转页面
    bean.setUnauthorizedUrl("/error/unAuthorized");
    // 设置成功之后要跳转的链接
    bean.setSuccessUrl("/admin/index");

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
    filterChainMap.put("/admin/login", "anon");
    filterChainMap.put("/**/*.js","anon");
    filterChainMap.put("/**/*.css","anon");
    filterChainMap.put("/**/*.jpeg","anon");
    filterChainMap.put("/**/*.png","anon");
    filterChainMap.put("/**/*.ico","anon");
    filterChainMap.put("/**/*.jpg","anon");
    filterChainMap.put("/*.ico","anon");
    // 以"/admin/" 开头的用户需要身份认证，authc 表示要进行身份认证
    // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最下边，不然"/**"后面的过滤会不管用-->:
    filterChainMap.put("/admin/system","roles[admin]");
    filterChainMap.put("/admin/**","user");

    bean.setFilterChainDefinitionMap(filterChainMap);
    return bean;
  }
}