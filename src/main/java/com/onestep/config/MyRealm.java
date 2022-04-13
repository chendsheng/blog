package com.onestep.config;

import com.onestep.entity.User;
import com.onestep.service.UserService;
import com.onestep.util.SaltMd5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class MyRealm extends AuthorizingRealm {
  @Resource
  UserService userService;

  @Override
  //授权
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    //获取用户名
    String username = principals.getPrimaryPrincipal().toString();
    String role = userService.selectRole(username);
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    //设置用户角色
    info.addRole(role);
    //设置用户权限
    //info.setStringPermissions();
    return info;
  }

  @Override
  //验证
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    // 根据 Token 获取用户名
    String name = (String) token.getPrincipal();
    // 根据用户名从数据库中查询该用户
    User user = userService.selectUser(name);
    AuthenticationInfo authcInfo = null;
    if (user != null) {
      ((UsernamePasswordToken) token).setPassword(SaltMd5Util.salt(((UsernamePasswordToken) token).getPassword(), user.getId()).toCharArray());
      authcInfo = new SimpleAuthenticationInfo(name, user.getPassword(), ByteSource.Util.bytes(user.getId()), getName());
      SecurityUtils.getSubject().getSession().setAttribute("user", user);
    }
    return authcInfo;
  }
}
