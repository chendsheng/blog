package com.onestep.util;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Upload {
  public static String upload(MultipartFile file){
    String originFileName = file.getOriginalFilename();
    String suffix = originFileName.substring(originFileName.lastIndexOf("."));
    String fileName = UUID.randomUUID().toString().replaceAll("-","") +suffix;
    String path;
    if(File.separator.equals("/")){
      path = "/home/dds/blog/editormdPic";
    }else {
      path  = "f:/jar/editormdPic";
    }
    File targetFile = new File(path,fileName);
    if(!targetFile.exists()){
      targetFile.mkdirs();
    }
    //保存
    try {
      file.transferTo(targetFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileName;
  }
}
