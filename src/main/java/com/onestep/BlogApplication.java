package com.onestep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.onestep.dao")
@SpringBootApplication
public class BlogApplication {

  public static void main(String[] args) { SpringApplication.run(BlogApplication.class, args);
  }

}
