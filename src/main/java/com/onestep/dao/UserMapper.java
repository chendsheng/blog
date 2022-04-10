package com.onestep.dao;

import com.onestep.entity.User;

import java.util.List;

public interface UserMapper {
  int count();
  List<User> selectUsers();
  User selectUser(String name);
  String selectRole(String name);
  int insertUser(User user);
  int updateUserbyId(User user);

  int batchDeleteUserbyIds(Integer[] ids);
}
