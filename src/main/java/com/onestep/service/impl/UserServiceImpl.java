package com.onestep.service.impl;

import com.onestep.dao.UserMapper;
import com.onestep.entity.User;
import com.onestep.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  @Resource
  private UserMapper userMapper;

  @Override
  public int count() {
    return userMapper.count();
  }

  @Override
  public String selectRole(String name) {
    return userMapper.selectRole(name);
  }

  @Override
  public User selectUser(String name) {
    return userMapper.selectUser(name);
  }

  @Override
  public List<User> selectUsers() {
    return userMapper.selectUsers();
  }


  @Override
  public int insertUser(User user) {
    if (userMapper.selectUser(user.getName()) == null) {
      return userMapper.insertUser(user);
    } else {
      return 0;
    }
  }

  @Override
  public int updateUserbyId(User user) {
    User userSelected = userMapper.selectUser(user.getName());
    if (userSelected == null || userSelected.getId().equals(user.getId())) {
      return userMapper.updateUserbyId(user);
    } else {
      return 0;
    }
  }

  @Override
  public int batchDeleteUserbyIds(Integer[] ids) {
    if (ids.length>0){
      return userMapper.batchDeleteUserbyIds(ids);
    }else {
      return 0;
    }
  }
}
