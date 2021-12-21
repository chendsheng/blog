package com.onestep.dao;

import com.onestep.entity.Admin;
import com.onestep.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface AdminMapper {
  int count();
  List<Admin> selectAdmins();
  Admin selectAdmin(String name);
  String selectRole(String name);
  int insertAdmin(Admin admin);
  int updateAdminbyId(Admin admin);

  int batchDeleteAdminbyIds(Integer[] ids);
}
