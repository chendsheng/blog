package com.onestep.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class OthersController {
  @GetMapping("/error/unAuthorized")
  public String unAuthorized() {
    log.debug("Get->/error/unAuthorized");
    return "error/unAuthorized";
  }
}
