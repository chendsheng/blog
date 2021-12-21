package com.onestep.service;

import com.onestep.entity.Admin;

import java.util.List;
import java.util.Set;

public interface AdminService {
  int count();
  String selectRole(String name);
  Admin selectAdmin(String name);
  List<Admin> selectAdmins();
  int insertAdmin(Admin admin);
  int updateAdminbyId(Admin admin);
  int batchDeleteAdminbyIds(Integer[] ids);
}
