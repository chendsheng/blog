package com.onestep.config;


import com.onestep.interceptor.UserInfoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;

@Configuration
public class MyMvcConfiguration implements WebMvcConfigurer{

  @Bean
  UserInfoInterceptor infoInterceptor(){
    return new UserInfoInterceptor();
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

}
