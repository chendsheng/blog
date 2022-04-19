package com.onestep.controller.admin;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
  @Resource
  private DefaultKaptcha kaptcha;

  @GetMapping("/kaptcha")
  public void verifycode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setDateHeader("Expires", 0);
    response.setHeader("Cache-Control",
            "no-store, no-cache, must-revalidate");
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    response.setHeader("Pragma", "no-cache");
    response.setContentType("image/jpeg");
    ServletOutputStream outputStream = response.getOutputStream();

    String text = kaptcha.createText();
    request.getSession().setAttribute(kaptcha.getConfig().getSessionKey(), text);
    BufferedImage image = kaptcha.createImage(text);

    try {
      ImageIO.write(image, "jpg", outputStream);
    } catch (IOException e) {
      return;
    }
    outputStream.flush();
    outputStream.close();
  }
}
