package com.onestep.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.UUID;

public class SaltMd5Util {
  public static String createSalt() {
    //生成32为随机盐
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  public static String salt(Object srcPwd, String saltValue) {
    return new SimpleHash("MD5", srcPwd, saltValue, 10).toHex();
  }

  public static void main(String[] args) {
    System.out.println(salt("lovest1997", "1"));
  }
}
