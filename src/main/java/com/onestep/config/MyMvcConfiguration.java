package com.onestep.config;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.onestep.interceptor.UserInfoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.Properties;

@Configuration
public class MyMvcConfiguration implements WebMvcConfigurer {

  @Bean
  public UserInfoInterceptor infoInterceptor() {
    return new UserInfoInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //拦截所有用到user的路径
    registry.addInterceptor(infoInterceptor()).addPathPatterns("/admin", "/admin/*", "/admin/article/*").excludePathPatterns("/admin/articles", "/admin/article/list");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String location;
    if (File.separator.equals("/")) {
      location = "file:/home/dds/blog/";
    } else {
      location = "file:f:/jar/";
    }
    registry.addResourceHandler("/upload/**").addResourceLocations(location);
  }

  //验证码
  @Bean
  public DefaultKaptcha kaptcha() {
    DefaultKaptcha kaptcha = new DefaultKaptcha();
    Properties properties = new Properties();
    properties.put("kaptcha.border", "no");
    properties.put("kaptcha.textproducer.font.color", "black");
    properties.put("kaptcha.image.width", "150");
    properties.put("kaptcha.image.height", "40");
    properties.put("kaptcha.textproducer.font.size", "30");
    properties.put("kaptcha.session.key", "verifyCode");
    properties.put("kaptcha.textproducer.char.space", "5");
    Config config = new Config(properties);
    kaptcha.setConfig(config);
    return kaptcha;
  }
}

