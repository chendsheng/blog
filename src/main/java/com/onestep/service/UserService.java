package com.onestep.service;


import com.onestep.entity.User;
import java.util.List;

public interface UserService {
  int count();
  String selectRole(String name);
  User selectUser(String name);
  List<User> selectUsers();
  int insertUser(User user);
  int updateUserbyId(User user);
  int batchDeleteUserbyIds(Integer[] ids);
}
