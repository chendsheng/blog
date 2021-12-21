package com.onestep.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OthersController {

  @GetMapping("/error/unAuthorized")
  public String unAuthorized(){
    return "error/unAuthorized";
  }
}
