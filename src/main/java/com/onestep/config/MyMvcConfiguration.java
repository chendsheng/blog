package com.onestep.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MyMvcConfiguration implements WebMvcConfigurer{

  @Bean
  AdminInfoInterceptor infoInterceptor(){
    return new AdminInfoInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //拦截所有用到subject.getSession()的路径
    registry.addInterceptor(infoInterceptor()).addPathPatterns("/admin","/admin/*","/admin/article/*").excludePathPatterns("/admin/articles","/admin/article/list");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String location;
    if (File.separator.equals("/")){
      location = "file:/home/dds/blog/";
    }else {
      location = "file:f:/jar/";
    }
    registry.addResourceHandler("/upload/**").addResourceLocations(location);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            //是否发送Cookie
            .allowCredentials(true)
            //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
            .allowedOriginPatterns("*")
            //放行哪些请求方式
            .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
            //.allowedMethods("*") //或者放行全部
            //放行哪些原始请求头部信息
            .allowedHeaders("*")
            //暴露哪些原始请求头部信息
            .exposedHeaders("*");
  }

}
