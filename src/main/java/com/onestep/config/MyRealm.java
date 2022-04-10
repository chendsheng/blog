package com.onestep.config;

import com.onestep.entity.User;
import com.onestep.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class MyRealm extends AuthorizingRealm {
  @Resource
  UserService adminService;

  @Override
  //授权
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    //获取用户名
    User username = (User)principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    //设置用户角色
    info.addRole(username.getRole());
    //设置用户权限
    //info.setStringPermissions();
    return info;
  }

  @Override
  //验证
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    // 根据 Token 获取用户名，如果您不知道该 Token 怎么来的，先可以不管，下文会解释
    String name = (String) token.getPrincipal();
    // 根据用户名从数据库中查询该用户
    User admin = adminService.selectUser(name);
    if(admin != null){
      // 把当前用户存到 Session 中
      Session session = SecurityUtils.getSubject().getSession();
      session.setAttribute("admin", admin);

      // 传入用户名和密码进行身份认证，并返回认证信息
      AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(admin, admin.getPassword(), "myRealm");
      return authcInfo;
    } else {
      return null;
    }
  }
}
